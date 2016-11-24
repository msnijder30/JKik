package nl.marksnijder.jkik.message;

import lombok.Getter;

public enum MessageType {
	
	TEXT("text"),
	LINK("link"),
	PICTURE("picture"),
	VIDEO("video"),
	START_CHATTING("start-chatting"),
	SCAN_DATA("scan-data"),
	STICKER("sticker"),
	IS_TYPING("is-typing"),
	DELIVERY_RECEIPT("delivery-receipt"),
	READ_RECEIPT("read-receipt"),
	FRIEND_PICKER("friend-picker");

	@Getter
	private final String type;
	
	private MessageType(String type) {
		this.type = type;
	}
	
}
