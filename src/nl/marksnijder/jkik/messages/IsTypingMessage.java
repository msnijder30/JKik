package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class IsTypingMessage extends Message {
	
	@Getter
	private boolean isTyping;

	public IsTypingMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, boolean isTyping) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.isTyping = isTyping;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
