package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class TextMessage extends Message {

	@Getter
	private String body;

	public TextMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, String body) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.body = body;
	}
	
	public TextMessage(String body, String to, String chatId) {
		super(to, MessageType.TEXT, chatId);
		this.body = body;
	}

	@Override
	public HashMap<String, Object> initSending() {
		HashMap<String, Object> toReturn = new HashMap<String, Object>();
		toReturn.put("body", body);
		return toReturn;
	}

}
