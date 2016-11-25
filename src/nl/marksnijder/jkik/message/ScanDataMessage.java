package nl.marksnijder.jkik.message;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class ScanDataMessage extends Message {

	@Getter
	private String referrer;
	
	@Getter
	private int storeId;
	
	@Getter
	private String data;
	
	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String referrer, int storeId) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id);
		this.referrer = referrer;
		this.storeId = storeId;
	}

	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String data) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id);
		this.data = data;
	}
	
}
