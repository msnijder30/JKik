package nl.marksnijder.jkik.message;

import java.util.HashMap;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class TextMessage extends Message implements Sendable {

	@Getter
	private String body;

	public TextMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String body) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.TEXT, id);
		this.body = body;
	}
	
	public TextMessage(String body, String to, String chatId) {
		super(to, MessageType.TEXT, chatId);
		this.body = body;
	}

	@Override
	public JsonObject initSending() {
		JsonObject res = new JsonObject();
		res.addProperty("body", body);
		return res;
	}

}
