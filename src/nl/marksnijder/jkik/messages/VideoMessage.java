package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class VideoMessage extends Message {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String videoUrl;

	public VideoMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, MessageAttribute attribution, String videoUrl) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.attribution = attribution;
		this.videoUrl = videoUrl;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
