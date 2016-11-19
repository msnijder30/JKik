package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class StickerMessage extends Message {

	@Getter
	private String stickerUrl;
	
	@Getter
	private long stickerPackId;
	
	public StickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, String stickerUrl, long stickerPackId) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.stickerUrl = stickerUrl;
		this.stickerPackId = stickerPackId;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
