package com.github.msnijder30.jkik;

import java.util.ArrayList;

import lombok.Getter;

public class Chat {

	@Getter
	private ArrayList<String> participants;
	
	@Getter
	private String chatId;
	
	@Getter
	private String from;
	
	public Chat(ArrayList<String> participants, String chatId, String from) {
		this.participants = participants;
		this.chatId = chatId;
		this.from = from;
	}
	
}
