package com.github.msnijder30.jkik.message;

import com.github.msnijder30.jkik.Chat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;


public class ScanDataMessage extends Message {

	@Getter
	private JsonElement data;

	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, JsonElement data, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id, metadata);
		this.data = data;
	}
	
}
