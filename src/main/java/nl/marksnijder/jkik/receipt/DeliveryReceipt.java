package nl.marksnijder.jkik.receipt;

import java.util.ArrayList;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.MessageType;

public class DeliveryReceipt extends Message {

	@Getter
	private ArrayList<String> messageIds;

	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public DeliveryReceipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, ArrayList<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, type, id, null);
		this.messageIds = messageIds;
	}

}
