package com.github.msnijder30.jkik.message;

import java.io.File;
import java.util.UUID;

import com.github.msnijder30.jkik.Chat;
import com.github.msnijder30.jkik.KikApi;
import com.github.msnijder30.jkik.keyboard.Keyboard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

public class PictureMessage extends Sendable {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String picUrl;

	@Getter
	private boolean sendAsCamera;
	
	public PictureMessage(KikApi api, File pic, String to, String chatId) {
		super(to, MessageType.PICTURE, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.picUrl = api.getFiles().getPublicFileUrl(uuid);
	}
	
	public PictureMessage(String url, String to, String chatId) {
		super(to, MessageType.PICTURE, chatId);
		this.picUrl = url;
	}
	
	public PictureMessage(KikApi api, File pic, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.PICTURE, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.picUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	public PictureMessage(String url, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.PICTURE, chatId);
		this.picUrl = url;
		this.attribution = attr;
	}
	
	public PictureMessage(KikApi api, File pic, MessageAttribute attr) {
		super(MessageType.PICTURE);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.picUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(String url, MessageAttribute attr) {
		super(MessageType.PICTURE);
		this.picUrl = url;
		this.attribution = attr;
	}

	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(KikApi api, File pic) {
		super(MessageType.PICTURE);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.picUrl = api.getFiles().getPublicFileUrl(uuid);
	}

	/**
	 * Use this constructor only when using the message in the {@link com.github.msnijder30.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(String url) {
		super(MessageType.PICTURE);
		this.picUrl = url;
	}
	
	public PictureMessage setSendAsCamera(boolean value) {
		sendAsCamera = value;
		return this;
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

		params.addProperty("picUrl", getPicUrl());
		
		if(this.attribution != null && !isSendAsCamera()) {
			params.add("attribution", this.attribution.toJsonObject());
		} else {
			params.addProperty("attribution", isSendAsCamera() ? "camera" : "gallery");
		}
		
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
