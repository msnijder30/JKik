package com.github.msnijder30.jkik.thread;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.msnijder30.jkik.KikApi;

public class KikServer extends Thread {

	private static final Logger logger = LogManager.getLogger(KikServer.class);

	private KikApi api;
	private ServerSocket socket;

	public KikServer(KikApi api) throws IOException {
		this.api = api;
		try {
			socket = new ServerSocket(api.getPort());
			socket.setReuseAddress(true);
		} catch (IOException e) {
			logger.error("Exception occurred starting server", e);
			throw new IOException(e);
		}
	}

	@Override
	public void run() {

		while (socket.isBound()) {
			// TODO use an executorservice
			try {
				new KikServerConnection(api, socket.accept()).start();
			} catch (IOException e) {
				logger.error("Exception occurred while receiving message", e);
			}

		}

		logger.fatal("The socket is not bound anymore!");

		try {
			socket.close();
		} catch (IOException e) {
			logger.error("Failed to close socket", e);
		}

	}

}
