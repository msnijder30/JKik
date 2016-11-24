package nl.marksnijder.jkik.message;

import java.io.File;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.keyboard.Keyboard;

public class VideoMessage extends Sendable {
	
	@Getter
	private MessageAttribute attribution;
	
	@Getter
	private String videoUrl;
	
	@Getter @Setter
	private boolean loop;

	@Getter @Setter
	private boolean muted;

	@Getter @Setter
	private boolean autoplay;

	@Getter @Setter
	private boolean noSave;
	
	@Getter
	private boolean sendAsCamera;

	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public VideoMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribution, String videoUrl) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.VIDEO, id);
		this.attribution = attribution;
		this.videoUrl = videoUrl;
	}
	
	public VideoMessage(KikApi api, File pic, String to, String chatId) {
		super(to, MessageType.VIDEO, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.videoUrl = api.getFiles().getPublicFileUrl(uuid);
	}
	
	public VideoMessage(String url, String to, String chatId) {
		super(to, MessageType.VIDEO, chatId);
		this.videoUrl = url;
	}
	
	public VideoMessage(KikApi api, File pic, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.VIDEO, chatId);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.videoUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	public VideoMessage(String url, String to, String chatId, MessageAttribute attr) {
		super(to, MessageType.VIDEO, chatId);
		this.videoUrl = url;
		this.attribution = attr;
	}
	
	public VideoMessage(KikApi api, File pic, MessageAttribute attr) {
		super(MessageType.VIDEO);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.videoUrl = api.getFiles().getPublicFileUrl(uuid);
		this.attribution = attr;
	}
	
	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public VideoMessage(String url, MessageAttribute attr) {
		super(MessageType.VIDEO);
		this.videoUrl = url;
		this.attribution = attr;
	}
	
	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public VideoMessage(KikApi api, File pic) {
		super(MessageType.VIDEO);
		
		UUID uuid = api.getFiles().addFile(pic);
		this.videoUrl = api.getFiles().getPublicFileUrl(uuid);
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public VideoMessage(String url) {
		super(MessageType.VIDEO);
		this.videoUrl = url;
	}
	
	public VideoMessage setSendAsCamera(boolean value) {
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
		params.addProperty("chatId", getChat().getChatId());
		params.addProperty("typeTime", getTypeTime() > 0 ? getTypeTime() : 0);
		params.addProperty("delay", getDelay());

		params.addProperty("videoUrl", videoUrl);
		params.addProperty("loop", false);
		params.addProperty("muted", muted);
		params.addProperty("autoplay", autoplay);
		params.addProperty("noSave", noSave);

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
