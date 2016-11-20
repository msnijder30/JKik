package nl.marksnijder.jkik.receipt;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.MessageType;

public class DeliveryReceipt extends Receipt {


	public DeliveryReceipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, ArrayList<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, type, id, messageIds);
	}

}
