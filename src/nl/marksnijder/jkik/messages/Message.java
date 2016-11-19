package nl.marksnijder.jkik.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.Chat;

public abstract class Message {

	@Getter
	private MessageType type;

	@Getter
	private long timestamp;

	@Getter
	private String mention;

	@Getter
	private boolean readReceiptRequested;

	@Getter
	private String id;

	@Getter
	private Chat chat;
	
	@Getter @Setter
	private int typeTime = 3;
	
	public Message(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id) {
		this.chat = chat;
		this.timestamp = timestamp;
		this.mention = mention;
		this.readReceiptRequested = readReceiptRequested;
		this.type = type;
		this.id = id;
	}
	
	public Message(String from, MessageType type, String chatId) {
		this.type = type;
		this.chat = new Chat(null, chatId, from);
	}

	/**
	 * ChatId is apparently not necessary when you're private messaging someone.
	 */
	public JsonObject getJsonData() {
		JsonObject params = new JsonObject();
		params.addProperty("type", type.toString().toLowerCase());
		params.addProperty("to", chat.getFrom());
		params.addProperty("chatId", chat.getChatId());
		params.addProperty("typeTime", typeTime);
		
		HashMap<String, Object> extraParams = initSending();
		for(String key : extraParams.keySet()) {
			params.addProperty(key, extraParams.get(key).toString());
		}
		
		return params;
		
	}
	
	public abstract HashMap<String, Object> initSending();

}
