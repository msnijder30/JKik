package nl.marksnijder.jkik.message;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class ScanDataMessage extends Message {

	@Getter
	private String referrer;
	
	@Getter
	private int storeId;
	
	@Getter
	private String data;
	
	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String referrer, int storeId, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id, metadata);
		this.referrer = referrer;
		this.storeId = storeId;
	}

	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String data, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id, metadata);
		this.data = data;
	}
	
}
