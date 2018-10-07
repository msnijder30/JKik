package com.github.msnijder30.jkik.message;

import com.github.msnijder30.jkik.Chat;

import lombok.Getter;

public class IsTypingMessage extends Message {
	
	@Getter
	private boolean isTyping;

	public IsTypingMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, boolean isTyping) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.IS_TYPING, id, null);
		this.isTyping = isTyping;
	}

}
