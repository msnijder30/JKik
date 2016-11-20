package nl.marksnijder.jkik.message;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class IsTypingMessage extends Message {
	
	@Getter
	private boolean isTyping;

	public IsTypingMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, boolean isTyping) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.IS_TYPING, id);
		this.isTyping = isTyping;
	}

}
