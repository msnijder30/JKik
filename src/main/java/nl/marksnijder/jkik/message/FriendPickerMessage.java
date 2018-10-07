package nl.marksnijder.jkik.message;

import java.util.ArrayList;

import lombok.Getter;
import nl.marksnijder.jkik.Chat;

public class FriendPickerMessage extends Message {
	
	@Getter
	private ArrayList<String> picked;

	public FriendPickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, ArrayList<String> picked) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.FRIEND_PICKER, id, null);
		this.picked = picked;
	}

}
