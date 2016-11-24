package nl.marksnijder.jkik.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.MessageSender;
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

	@Getter @Setter
	private Chat chat;
		
	@Getter @Setter
	private int typeTime = 3;
	
	@Getter @Setter
	private int delay = 0;
	
	
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
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	public void sendReply(Sendable... messages) {
		for(Sendable send : messages) {
			send.setChat(getChat());
		}
		MessageSender.sendMessages(messages);
	}

	
}
