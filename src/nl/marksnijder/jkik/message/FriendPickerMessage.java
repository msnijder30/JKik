package nl.marksnijder.jkik.message;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class FriendPickerMessage extends Message implements Sendable {
	
	@Getter
	private ArrayList<String> picked;

	public FriendPickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, ArrayList<String> picked) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.FRIEND_PICKER, id);
		this.picked = picked;
	}

	@Override
	public JsonObject initSending() {
		// TODO Auto-generated method stub
		return null;
	}

}
