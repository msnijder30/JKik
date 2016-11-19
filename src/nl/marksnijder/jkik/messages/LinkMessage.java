package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class LinkMessage extends Message {
	
	@Getter
	private MessageAttribute attribute;
	
	@Getter
	private String url;
	
	@Getter
	private boolean noForward;
	
	@Getter
	private String text;

	public LinkMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id, MessageAttribute attribute, String url, boolean noForward, String text) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
		this.attribute = attribute;
		this.url = url;
		this.noForward = noForward;
		this.text = text;
	}

	@Override
	public HashMap<String, Object> initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
