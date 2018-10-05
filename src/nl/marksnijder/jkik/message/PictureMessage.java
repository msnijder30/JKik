package nl.marksnijder.jkik.message;

import java.io.File;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.keyboard.Keyboard;

public class PictureMessage extends Sendable {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String pictureUrl;

	@Getter
	private boolean sendAsCamera;
	
	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public PictureMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribution, String pictureUrl, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.PICTURE, id, metadata);
		this.attribution = attribution;
		this.pictureUrl = pictureUrl;
	}
	
	public PictureMessage(KikApi api, File pic, String to, String chatId) {
		super(to, MessageType.PICTURE, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.pictureUrl = api.getFiles().getPublicFileUrl(uuid);
	}
	
	public PictureMessage(String url, String to, String chatId) {
		super(to, MessageType.PICTURE, chatId);
		this.pictureUrl = url;
	}
	
	public PictureMessage(KikApi api, File pic, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.PICTURE, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.pictureUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	public PictureMessage(String url, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.PICTURE, chatId);
		this.pictureUrl = url;
		this.attribution = attr;
	}
	
	public PictureMessage(KikApi api, File pic, MessageAttribute attr) {
		super(MessageType.PICTURE);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.pictureUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(String url, MessageAttribute attr) {
		super(MessageType.PICTURE);
		this.pictureUrl = url;
		this.attribution = attr;
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(KikApi api, File pic) {
		super(MessageType.PICTURE);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.pictureUrl = api.getFiles().getPublicFileUrl(uuid);
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public PictureMessage(String url) {
		super(MessageType.PICTURE);
		this.pictureUrl = url;
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

		params.addProperty("picUrl", getPictureUrl());
		
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
