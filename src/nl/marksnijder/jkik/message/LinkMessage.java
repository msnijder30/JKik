package nl.marksnijder.jkik.message;

import java.util.HashMap;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class LinkMessage extends Message implements Sendable {
	
	@Getter
	private MessageAttribute attribute;
	
	@Getter
	private String url;
	
	@Getter
	private boolean noForward;
	
	@Getter
	private String text;

	public LinkMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribute, String url, boolean noForward, String text) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.LINK, id);
		this.attribute = attribute;
		this.url = url;
		this.noForward = noForward;
		this.text = text;
	}

	@Override
	public JsonObject initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
