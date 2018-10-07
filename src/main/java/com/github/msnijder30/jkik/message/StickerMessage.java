package com.github.msnijder30.jkik.message;

import com.github.msnijder30.jkik.Chat;
import com.google.gson.JsonObject;

import lombok.Getter;

public class StickerMessage extends Message {

	@Getter
	private String stickerUrl;
	
	@Getter
	private String stickerPackId;
	
	public StickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String stickerUrl, String stickerPackId, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.STICKER, id, metadata);
		this.stickerUrl = stickerUrl;
		this.stickerPackId = stickerPackId;
	}

}
