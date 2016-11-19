package nl.marksnijder.jkik.messages;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class DeliveryReceiptMessage extends Message {

	@Getter
	private ArrayList<String> messageIds;

	public DeliveryReceiptMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, ArrayList<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.DELIVERYRECEIPT, id);
		this.messageIds = messageIds;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
