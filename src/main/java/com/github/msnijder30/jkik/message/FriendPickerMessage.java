package com.github.msnijder30.jkik.message;

import java.util.ArrayList;

import com.github.msnijder30.jkik.Chat;

import lombok.Getter;

public class FriendPickerMessage extends Message {
	
	@Getter
	private ArrayList<String> picked;

	public FriendPickerMessage(Chat chat, long timestamp, String mention, boolean readReceiptRequested, String id, ArrayList<String> picked) {
		super(chat, timestamp, mention, readReceiptRequested, MessageType.FRIEND_PICKER, id, null);
		this.picked = picked;
	}

}
