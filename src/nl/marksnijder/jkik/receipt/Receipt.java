package nl.marksnijder.jkik.receipt;

import java.util.ArrayList;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.message.MessageType;

public abstract class Receipt {

	@Getter
	private MessageType type;

	@Getter
	private long timestamp;

	@Getter
	private String mention;

	@Getter
	private boolean readReceiptRequested;

	@Getter
	private String id;

	@Getter
	private Chat chat;
	
	@Getter
	private ArrayList<String> messageIds;
	
	public Receipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, ArrayList<String> messageIds) {
		this.chat = chat;
		this.timestamp = timestamp;
		this.mention = mention;
		this.readReceiptRequested = readReceiptRequested;
		this.type = type;
		this.id = id;
		this.messageIds = messageIds;
	}

}
