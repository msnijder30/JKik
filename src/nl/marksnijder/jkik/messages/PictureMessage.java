package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class PictureMessage extends Message {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String pictureUrl;

	public PictureMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, MessageAttribute attribution, String pictureUrl) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.attribution = attribution;
		this.pictureUrl = pictureUrl;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
