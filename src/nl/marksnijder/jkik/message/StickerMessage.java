package nl.marksnijder.jkik.message;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class StickerMessage extends Message {

	@Getter
	private String stickerUrl;
	
	@Getter
	private String stickerPackId;
	
	public StickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String stickerUrl, String stickerPackId, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.STICKER, id, metadata);
		this.stickerUrl = stickerUrl;
		this.stickerPackId = stickerPackId;
	}

}
