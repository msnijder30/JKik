package nl.marksnijder.jkik.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.keyboard.Keyboard;
import nl.marksnijder.jkik.keyboard.TextButton;

public abstract class Sendable extends Message {


	@Getter
	private List<Keyboard> keyboards;
	
	/**
	 * @deprecated Please use the other constructors as they have the arguments you need exactly. This constructor is mostly for internal use.
	 */
	@Deprecated
	public Sendable(Chat chat, long timestamp, String mention, boolean readReceiptRequested, MessageType type, String id) {
		super(chat, timestamp, mention, readReceiptRequested, type, id);
	}
	
	public Sendable(String from, MessageType type, String chatId) {
		super(from, type, chatId);
	}

	/**
	 * Use this constructor only when using the message in the {@link nl.marksnijder.jkik.message.Message#sendReply(Sendable...)}
	 */
	public Sendable(MessageType type) {
		super(type);
	}
	
	public Sendable setKeyboards(Keyboard... keyboards) {
		this.keyboards = Arrays.asList(keyboards);
		return this;
	}
	
	public Sendable addKeyboard(ArrayList<TextButton> buttons) {
		this.keyboards.add(new Keyboard(buttons));
		return this;
	}
	
	public Sendable addKeyboard(TextButton... buttons) {
		this.keyboards.add(new Keyboard(buttons));
		return this;
	}
	
	public abstract JsonObject toJsonObject();

}
