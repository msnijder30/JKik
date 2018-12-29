package com.github.msnijder30.jkik.thread;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.msnijder30.jkik.KikApi;
import com.github.msnijder30.jkik.Utils;

public class KikServerConnection extends Thread {

	private static final Logger logger = LogManager.getLogger(KikServerConnection.class);

	private KikApi api;
	private Socket socket;

	public KikServerConnection(KikApi api, Socket socket) {
		this.api = api;
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO clean this up
		logger.debug(String.format("New connection from %s", socket.getInetAddress().toString()));

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			logger.error("Error while getting socket inputstream", e);
			closeSocket();
			return;
		}

		String methodType = "";
		UUID uuid = null;

		String signature = "";

		String line = null;
		int contentLength = 0;
		StringBuilder socketData = new StringBuilder();
		try {
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(line.split(" ")[1]);
				}

				if (line.startsWith("X-Kik-Signature")) {
					signature = line.split(" ")[1];
				}

				if (line.startsWith("GET")) {
					methodType = "GET";
					uuid = UUID.fromString(line.split(" ")[1].substring(1));
					logger.debug(String.format("New request for file %s", api.getFiles().getPublicFileUrl(uuid)));
				} else if (line.startsWith("POST")) {
					methodType = "POST";
				}

				if (line.isEmpty()) {
					for (int i = 0; i < contentLength; i++) {
						socketData.append((char) reader.read());
					}
					break;
				}

			}
		} catch (IllegalArgumentException e) {
			logger.error(String.format("Invalid data received", line), e);
			closeSocket();
			return;
		} catch (IOException e) {
			logger.error("Error while reading socket inputstream", e);
			closeSocket();
			return;
		}

		logger.debug(String.format("Request data: %s", socketData.toString()));

		PrintStream writer;
		try {
			writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			logger.error("Error while getting socket outputstream", e);
			closeSocket();
			return;
		}

		writer.print("HTTP/1.0 200 OK\r\n");
		writer.print("Date: " + new Date() + "\r\n");
		writer.print("Server: JKik Server 1.0\r\n");

		if (methodType.equals("GET")) {
			File f = api.getFiles().getFile(uuid);
			try (FileInputStream fis = new FileInputStream(f)) {
				writer.print("Content-Type: " + Files.probeContentType(f.toPath()) + "\r\n");
				writer.print("Content-Length: " + f.length() + "\r\n\r\n");

				writer.flush();
				byte[] buf = new byte[4096];
				int len;

				while ((len = fis.read(buf)) > 0) {
					writer.write(buf, 0, len);
				}
			} catch (FileNotFoundException e) {
				logger.error("Requested file not found", e);
			} catch (IOException e) {
				logger.error("Error occured while sending file", e);
			}
		} else {
			writer.print("Content-Type: text/html\r\n");
		}

		writer.flush();
		writer.close();
		closeSocket();

		if (!methodType.equals("POST")) {
			closeSocket();
			return;
		}

		if (signature.isEmpty()) {
			logger.warn(String.format("Signature header not included in request from kik, entire request was %s",
					socketData.toString()));
			closeSocket();
			return;
		}

		String hashed;
		try {
			hashed = Utils.HmacSHA1(socketData.toString(), api.getApiKey());
		} catch (InvalidKeyException e) {
			logger.error(String.format("Error while generating hash for message %s", socketData.toString()), e);
			closeSocket();
			return;
		} catch (NoSuchAlgorithmException e) {
			logger.error(String.format("Error while generating hash for message %s", socketData.toString()), e);
			closeSocket();
			return;
		}

		if (!signature.equals(hashed)) {
			logger.error(String.format("Signature didn't match, received %s but should be %s, entire request was %s",
					signature, hashed, socketData.toString()));
			closeSocket();
		}

		api.getBot().onMessageReceived(socketData.toString());

	}

	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			logger.error("Failed to close socket, closing input and outputstream", e);
			try {
				socket.shutdownInput();
				socket.shutdownOutput();
			} catch (IOException e1) {
				logger.error("Failed to shutdown input/output stream", e1);
			}
		}
	}

}
