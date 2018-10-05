package nl.marksnijder.jkik.message;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.keyboard.Keyboard;

public class LinkMessage extends Sendable {
	
	@Getter
	private MessageAttribute attribute;
	
	@Getter
	private String url;
	
	@Getter
	private boolean noForward;
	
	@Getter
	private String text;
	
	@Getter
	private JsonObject kikJsData;

	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public LinkMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, MessageAttribute attribute, String url, boolean noForward, JsonObject kikJsData, String text, JsonObject metadata) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.LINK, id, metadata);
		this.attribute = attribute;
		this.url = url;
		this.noForward = noForward;
		this.kikJsData = kikJsData;
		this.text = text;
	}
	
	public LinkMessage(String url, String to, String chatId, MessageAttribute attribute) {
		super(to, MessageType.LINK, chatId);
		this.url = url;
		this.attribute = attribute;
	}
	
	public LinkMessage(String url, String to, String chatId) {
		super(to, MessageType.LINK, chatId);
		this.url = url;
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public LinkMessage(String url, MessageAttribute attribute) {
		super(MessageType.LINK);
		this.url = url;
		this.attribute = attribute;
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
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
		if(attribute != null) params.add("attribution", attribute.toJsonObject());
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
