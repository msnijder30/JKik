package nl.marksnijder.jkik.message;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.Chat;

public class VideoMessage extends Message implements Sendable {
	
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

	public VideoMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribution, String videoUrl) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.VIDEO, id);
		this.attribution = attribution;
		this.videoUrl = videoUrl;
	}

	@Override
	public JsonObject initSending() {
		JsonObject obj = new JsonObject();
		obj.addProperty("videoUrl", videoUrl);
		obj.addProperty("loop", false);
		obj.addProperty("muted", muted);
		obj.addProperty("autoplay", autoplay);
		obj.addProperty("noSave", noSave);
		return obj;
	}

}
