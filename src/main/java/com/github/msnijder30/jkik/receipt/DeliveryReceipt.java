package com.github.msnijder30.jkik.receipt;

import java.util.ArrayList;

import com.github.msnijder30.jkik.Chat;
import com.github.msnijder30.jkik.message.Message;
import com.github.msnijder30.jkik.message.MessageType;

import lombok.Getter;

public class DeliveryReceipt extends Message {

	@Getter
	private ArrayList<String> messageIds;

	public DeliveryReceipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, ArrayList<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, type, id, null);
		this.messageIds = messageIds;
	}

}
