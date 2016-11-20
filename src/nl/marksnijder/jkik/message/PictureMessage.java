package nl.marksnijder.jkik.message;

import java.io.File;
import java.util.UUID;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;

public class PictureMessage extends Message implements Sendable {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String pictureUrl;
	
	public PictureMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribution, String pictureUrl) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.PICTURE, id);
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
	
	@Override
	public JsonObject initSending() {
		JsonObject res = new JsonObject();
		res.addProperty("picUrl", getPictureUrl());
		return res;
	}

}
