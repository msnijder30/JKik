package com.github.msnijder30.jkik.thread;

import java.io.IOException;
import java.net.ServerSocket;

import com.github.msnijder30.jkik.KikApi;

public class KikServer extends Thread {

	private KikApi api;
	private ServerSocket socket;
	
	public KikServer(KikApi api) {
		this.api = api;
		try {
			socket = new ServerSocket(api.getPort());
			socket.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while(socket.isBound()) {
			
			try {
				new KikServerConnection(api, socket.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
