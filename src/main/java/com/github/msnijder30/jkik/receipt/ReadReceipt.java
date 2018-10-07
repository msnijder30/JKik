package com.github.msnijder30.jkik.receipt;

import java.util.Arrays;
import java.util.List;

import com.github.msnijder30.jkik.Chat;
import com.github.msnijder30.jkik.message.MessageType;
import com.github.msnijder30.jkik.message.Sendable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

public class ReadReceipt extends Sendable {
	
	@Getter
	private List<String> messageIds;
	
	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public ReadReceipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, List<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.READ_RECEIPT, id, null);
		this.messageIds = messageIds;
	}
	
	public ReadReceipt(List<String> messageIds) {
		super(MessageType.READ_RECEIPT);
		this.messageIds = messageIds;
	}
	
	public ReadReceipt(String... messageIds) {
		super(MessageType.READ_RECEIPT);
		this.messageIds = Arrays.asList(messageIds);
	}
	
	public JsonObject toJsonObject() {
		JsonObject params = new JsonObject();
		params.addProperty("type", getType().getType());
		params.addProperty("to", getChat().getFrom());
		if(getChat().getChatId() != null) params.addProperty("chatId", getChat().getChatId());
		
		
		JsonArray messageIds = new JsonArray();
		if(getMessageIds() != null) {
			for(String msg : getMessageIds()) {
				messageIds.add(msg);
			}
		}
		
		params.add("messageIds", messageIds);
		
		return params;
	}

}
