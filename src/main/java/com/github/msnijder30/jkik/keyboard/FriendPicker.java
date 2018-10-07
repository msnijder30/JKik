package com.github.msnijder30.jkik.keyboard;

import java.util.List;

import com.github.msnijder30.jkik.message.MessageType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

public class FriendPicker extends TextButton {
	
	@Getter
	private int max;
	
	@Getter
	private int min;
	
	@Getter
	private List<String> preselected;
	
	public FriendPicker(String body, int min, int max) throws Exception {
		this(body, min, max, null);
	}
	
	public FriendPicker(String body, int min, int max, List<String> preselected) throws Exception {
		super(body, MessageType.FRIEND_PICKER);
		if(min < 1 || max < 1 || min > 100 || max > 100 || min > max) {
			throw new Exception("Invalid min or max variable");
		}
		this.max = max;
		this.min = min;
		this.preselected = preselected;
	}
	
	public JsonObject getJson() {
		JsonObject obj = super.getJson();
		obj.addProperty("max", max);
		obj.addProperty("min", min);
		
		if(preselected != null) {
			JsonArray pre = new JsonArray();
			for(String s : preselected) {
				pre.add(s);
			}
			obj.add("preselected", pre);
		}
		
		return obj;
	}
	
}
