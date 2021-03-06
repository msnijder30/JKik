package com.github.msnijder30.jkik.message;

import com.github.msnijder30.jkik.Chat;
import com.github.msnijder30.jkik.keyboard.Keyboard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

public class LinkMessage extends Sendable {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String url;
	
	@Getter
	private boolean noForward;
	
	@Getter
	private String text;
	
	@Getter
	private JsonObject kikJsData;
	
	public LinkMessage(String url, String to, String chatId, MessageAttribute attribute) {
		super(to, MessageType.LINK, chatId);
		this.url = url;
		this.attribution = attribute;
	}
	
	public LinkMessage(String url, String to, String chatId) {
		super(to, MessageType.LINK, chatId);
		this.url = url;
	}

	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public LinkMessage(String url, MessageAttribute attribute) {
		super(MessageType.LINK);
		this.url = url;
		this.attribution = attribute;
	}

	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public LinkMessage(String url) {
		super(MessageType.LINK);
		this.url = url;
	}
	
	/**
	 * ChatId is apparently not necessary when you're private messaging someone.
	 */
	@Override
	public JsonObject toJsonObject() {
		JsonObject params = new JsonObject();
		params.addProperty("type", getType().getType());
		params.addProperty("to", getChat().getFrom());
		if(getChat().getChatId() != null) params.addProperty("chatId", getChat().getChatId());
		params.addProperty("typeTime", getTypeTime() > 0 ? getTypeTime() : 0);
		params.addProperty("delay", getDelay());

		params.addProperty("url", url);
		if(attribution != null) params.add("attribution", attribution.toJsonObject());
		params.addProperty("text", text);
		params.addProperty("noForward", noForward);
		
		JsonArray keyboards = new JsonArray();
		if(getKeyboards() != null) {
			for(Keyboard kb : getKeyboards()) {
				keyboards.add(kb.toJsonObject());
			}
		}
		
		params.add("keyboards", keyboards);
		
		return params;
	}
}
