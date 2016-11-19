package nl.marksnijder.jkik.messages;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class ReadReceiptMessage extends Message {

	@Getter
	private ArrayList<String> messageIds;

	public ReadReceiptMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, ArrayList<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.messageIds = messageIds;
	}
	
	public ReadReceiptMessage(String from, MessageType type, String chatId) {
		super(from, MessageType.READRECEIPT, chatId);
	}

	@Override
	public HashMap<String, Object> initSending() {
		return null;
	}

}
