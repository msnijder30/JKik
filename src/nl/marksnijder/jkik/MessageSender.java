package nl.marksnijder.jkik;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.message.Sendable;

public class MessageSender {
	
	@Getter @Setter
	public static KikApi api;
	
	private MessageSender() {
		
	}
	
	/**
	 * Allows you to send 100 messages in 1 request, doesn't have the per-second limit as the sendMessage method
	 * @param messages An array of messages
	 */
	public static void broadcastMessages(Sendable... messages) {
		sendMessage(KikApi.BROADCAST_URL, messages);
	}
	
	public static void sendMessage(Sendable... messages) {
		sendMessage(KikApi.MESSAGE_URL, messages);
	}
	
	private static void sendMessage(String url, Sendable... messages) {
		JsonObject toSend = new JsonObject();
		JsonArray msgArray = new JsonArray();

		System.out.println("SENDING MESSAGES!!!");
		
		for(Sendable msg : messages) {
			JsonObject jsonData = msg.getJsonData();
			msgArray.add(jsonData);
			System.out.println(jsonData);
		}
		toSend.add("messages", msgArray);
		
		api.executePost(url, toSend.toString(), MethodType.POST);
	}

}
