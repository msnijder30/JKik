package nl.marksnijder.jkik.threaded;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import nl.marksnijder.jkik.KikApi;

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

			
			
			String line;
			int contentLength = 0;
			StringBuilder socketData = new StringBuilder();
			while((line = reader.readLine()) != null) {
				
				//Content-Length: 298
				if(line.startsWith("Content-Length")) {
					contentLength = Integer.parseInt(line.split(" ")[1]);
				}
				
				System.out.println(line);
				if(line.isEmpty()) {
					
					for(int i = 0; i < contentLength; i++) {
						socketData.append((char)reader.read());
					}
					
					break;
				}
			
			
			}
			
			//{"messages": [{"body": "P", "from": "msnijder30", "timestamp": 1479582125741, "mention": null, "participants": ["msnijder30"], "readReceiptRequested": true, "type": "text", "id": "2e0484f9-4c9d-4b31-97c4-ed1049df8a0f", "chatId": "5fd9018a97b4c5ad256e8fa717af6e06c8f93d5b9c08b3b3f83c850af493b583"}]}
			System.out.println(socketData.toString());
			
			PrintStream writer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

			writer.print("HTTP/1.0 200 OK\r\n");
			writer.print("Content-Type: text/html\r\n");
			writer.flush();
			writer.close();
			socket.close();
			
		    api.getBot().onMessageReceived(socketData.toString());

		    
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
