package nl.marksnijder.jkik.message;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class ScanDataMessage extends Message {

	@Getter
	private String referrer;
	
	@Getter
	private int storeId;
	
	
	public ScanDataMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String referrer, int storeId) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.SCAN_DATA, id);
		this.referrer = referrer;
		this.storeId = storeId;
		// TODO Auto-generated constructor stub
	}

}
