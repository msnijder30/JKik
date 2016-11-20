package nl.marksnijder.jkik.thread;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.UUID;

import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.Utils;

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
			System.out.println("new connection");
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String methodType = "";
			UUID uuid = null;
			
			String signature = "";
			
			String line;
			int contentLength = 0;
			StringBuilder socketData = new StringBuilder();
			while((line = reader.readLine()) != null) {
				
				//Content-Length: 298
				if(line.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(line.split(" ")[1]);
				}
				
				if(line.startsWith("X-Kik-Signature")) {
					signature = line.split(" ")[1];
				}
			
				if(line.startsWith("GET")) {
					methodType = "GET";
					uuid = UUID.fromString(line.split(" ")[1].substring(1));
				} else if(line.startsWith("POST")) {
					methodType = "POST";
				}
				
				System.out.println(line);
				if(line.isEmpty()) {
					for(int i = 0; i < contentLength; i++) {
						socketData.append((char)reader.read());
					}
					
					break;
				}
			
			
			}
			
			System.out.println(socketData.toString());
			
			PrintStream writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

			writer.print("HTTP/1.0 200 OK\r\n");
			
			if(methodType.equals("GET")) {
				System.out.println(uuid.toString());
				System.out.println();
				
				File f = api.getFiles().getFile(uuid);
				writer.println("Content-Type: " + Files.probeContentType(f.toPath()) + "\r\n");
				
				FileInputStream fis = new FileInputStream(f);
				
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
			
			System.out.println();
			System.out.println(signature);
			System.out.println(hashed);
			if(!signature.equals(hashed)) {
				throw new Exception("Signature didn't match");
			}
			
		    api.getBot().onMessageReceived(socketData.toString());
		    
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
