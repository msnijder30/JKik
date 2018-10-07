package com.github.msnijder30.jkik.message;

import com.github.msnijder30.jkik.Chat;
import com.github.msnijder30.jkik.keyboard.Keyboard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class TextMessage extends Sendable {

	@Getter
	private String body;

	public TextMessage(String body, String to, String chatId) {
		super(to, MessageType.TEXT, chatId);
		this.body = body;
	}

	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public TextMessage(String body) {
		super(MessageType.TEXT);
		this.body = body;
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

		params.addProperty("body", body);
		
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
