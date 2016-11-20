package nl.marksnijder.jkik.keyboard;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.message.MessageType;

public class TextButton {
	
	@Getter
	private MessageType type;
	
	@Getter
	private String body;
	
	public TextButton(String body) {
		this(body, MessageType.TEXT);
	}
	
	public TextButton(String body, MessageType type) {
		this.body = body;
		this.type = type;
	}
	
	public JsonObject getJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("type", type.getType());
		obj.addProperty("body", body);
		return obj;
	}
	

}
