package com.github.msnijder30.jkik.keyboard;

import com.github.msnijder30.jkik.message.MessageType;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class TextButton {
	
	@Getter
	private MessageType type;
	
	@Getter
	private String body;
	
	@Getter @Setter
	public JsonObject metaData;
	
	public TextButton(String body) {
		this(body, MessageType.TEXT);
	}
	
	public TextButton(String body, MessageType type) {
		this.body = body;
		this.type = type;
	}
	
	public JsonObject getJson() {
		JsonObject obj = new JsonObject();
		obj.addProperty("type", type.getType());
		obj.addProperty("body", body);
		
		if(metaData != null) {
			obj.add("metadata", metaData);
		}
		
		return obj;
	}
	

}
