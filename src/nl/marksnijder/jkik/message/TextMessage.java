package nl.marksnijder.jkik.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.keyboard.Keyboard;

public class TextMessage extends Sendable {

	@Getter
	private String body;

	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public TextMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, String body) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.TEXT, id);
		this.body = body;
	}
	
	public TextMessage(String body, String to, String chatId) {
		super(to, MessageType.TEXT, chatId);
		this.body = body;
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
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
		params.addProperty("chatId", getChat().getChatId());
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
