package nl.marksnijder.jkik.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.keyboard.Keyboard;
import nl.marksnijder.jkik.keyboard.TextButton;

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
	
	@Getter
	private Keyboard keyboard;
	
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
		params.addProperty("type", type.getType());
		params.addProperty("to", chat.getFrom());
		params.addProperty("chatId", chat.getChatId());
		params.addProperty("typeTime", typeTime > 0 ? typeTime : 0);
		
		JsonObject extraParams = ((Sendable)this).initSending();
		
		for(Entry<String, JsonElement> set : extraParams.entrySet()) {
			params.add(set.getKey(), set.getValue());
		}
		
		JsonArray keyboards = new JsonArray();
		if(keyboard != null) {
			System.out.println("KEYBOARD");
			System.out.println(keyboard.getJson().toString());
			keyboards.add(keyboard.getJson());
		}
		
		params.add("keyboards", keyboards);
		
		System.out.println(params.toString());
		
		return params;
	}
	
	public Message setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
		return this;
	}
	
	public Message setKeyboard(ArrayList<TextButton> buttons) {
		this.keyboard = new Keyboard(buttons);
		return this;
	}
	
	public Message setKeyboard(TextButton... buttons) {
		this.keyboard = new Keyboard(Arrays.asList(buttons));
		return this;
	}
	
}
