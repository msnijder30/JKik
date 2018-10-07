package com.github.msnijder30.jkik.thread;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

import com.github.msnijder30.jkik.KikApi;
import com.github.msnijder30.jkik.Utils;

public class KikServerConnection extends Thread {
	
	private KikApi api;
	private Socket socket;
	
	
	public KikServerConnection(KikApi api, Socket socket) {
		this.api = api;
		this.socket = socket;
	}
	
	
	@Override
	public void run() {
		try {
			System.out.println("New connection from " + socket.getInetAddress());
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String methodType = "";
			UUID uuid = null;
			
			String signature = "";
			
			String line;
			int contentLength = 0;
			StringBuilder socketData = new StringBuilder();
			while((line = reader.readLine()) != null) {
				
				if(line.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(line.split(" ")[1]);
				}
				
				if(line.startsWith("X-Kik-Signature")) {
					signature = line.split(" ")[1];
				}
			
				if(line.startsWith("GET")) {
					methodType = "GET";
					uuid = UUID.fromString(line.split(" ")[1].substring(1));
					System.out.println("Requesting file: " + api.getFiles().getPublicFileUrl(uuid));
				} else if(line.startsWith("POST")) {
					methodType = "POST";
				}
				
				if(line.isEmpty()) {
					for(int i = 0; i < contentLength; i++) {
						socketData.append((char)reader.read());
					}
					break;
				}
			
			
			}
			
			System.out.println("Request data: " + socketData.toString());
			
			PrintStream writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
			writer.print("HTTP/1.0 200 OK\r\n");
			writer.print("Date: " + new Date() + "\r\n");
			writer.print("Server: JKik Server 1.0\r\n");

			
			if(methodType.equals("GET")) {
				File f = api.getFiles().getFile(uuid);
				FileInputStream fis = new FileInputStream(f);
				writer.print("Content-Type: " + Files.probeContentType(f.toPath()) + "\r\n");
				writer.print("Content-Length: " + f.length() + "\r\n\r\n");
				
				writer.flush();
				byte[] buf = new byte[4096];
				int len;
				
				while((len = fis.read(buf)) > 0) {
					writer.write(buf, 0, len);
				}
				
				fis.close();
			} else {
				writer.print("Content-Type: text/html\r\n");
			}
			
			writer.flush();
			writer.close();
			socket.close();
			if(!methodType.equals("POST")) return;
			
			if(signature.isEmpty()) {
				throw new Exception("Signature header not included in request from kik");
			}
			
			String hashed = Utils.HmacSHA1(socketData.toString(), api.getApiKey());
			
			System.out.println("Kik signature: " + signature);
			System.out.println("Hashed signature: " + hashed);
			
			if(!signature.equals(hashed)) {
				throw new Exception("Signature didn't match");
			}
			
		    api.getBot().onMessageReceived(socketData.toString());
		    
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
