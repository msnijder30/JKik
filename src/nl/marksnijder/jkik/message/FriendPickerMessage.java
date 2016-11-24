package nl.marksnijder.jkik.message;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;
import nl.marksnijder.jkik.keyboard.Keyboard;

public class FriendPickerMessage extends Message {
	
	@Getter
	private ArrayList<String> picked;

	public FriendPickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, ArrayList<String> picked) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.FRIEND_PICKER, id);
		this.picked = picked;
	}

}
