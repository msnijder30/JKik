package nl.marksnijder.jkik;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.messages.Message;

public class MessageSender {
	
	@Getter @Setter
	public static KikApi api;
	
	private MessageSender() {
		
	}
	
	public static void sendMessage(Message... messages) {
		JsonObject toSend = new JsonObject();
		JsonArray msgArray = new JsonArray();

		for(Message msg : messages) {
			JsonObject jsonData = msg.getJsonData();
			msgArray.add(jsonData);
			System.out.println(jsonData);
		}
		System.out.println(msgArray.toString());
		toSend.add("messages", msgArray);
		System.out.println(toSend.toString());
		api.executePost(KikApi.MESSAGE_URL, toSend.toString());
		
	}

}
