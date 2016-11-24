package nl.marksnijder.jkik.receipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.message.MessageType;
import nl.marksnijder.jkik.message.Sendable;

public class ReadReceipt extends Sendable {
	
	@Getter
	private List<String> messageIds;
	
	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public ReadReceipt(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, List<String> messageIds) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.READ_RECEIPT, id);
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
		params.addProperty("chatId", getChat().getChatId());
		
		
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
