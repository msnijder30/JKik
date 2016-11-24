package nl.marksnijder.jkik;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.message.Sendable;
import nl.marksnijder.jkik.receipt.ReadReceipt;

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
		sendMessages(KikApi.BROADCAST_URL, messages);
	}
	
	public static void sendMessages(Sendable... messages) {
		sendMessages(KikApi.MESSAGE_URL, messages);
	}
	
	private static void sendMessages(String url, Sendable... messages) {
		JsonObject toSend = new JsonObject();
		JsonArray msgArray = new JsonArray();
		
		for(Sendable msg : messages) {
			JsonObject jsonData = msg.toJsonObject();
			msgArray.add(jsonData);
		}
		toSend.add("messages", msgArray);
		
		api.executePost(url, toSend.toString(), MethodType.POST);
	}

}
