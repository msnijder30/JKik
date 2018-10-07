package com.github.msnijder30.jkik.message;

import com.google.gson.JsonObject;

import lombok.Getter;

public class MessageAttribute {
	
	@Getter
	public String iconUrl;
		
	@Getter
	public String name;
	
	public MessageAttribute(String iconUrl, String name) {
		this.iconUrl = iconUrl;
		this.name = name;
	}

	public JsonObject toJsonObject() {
		JsonObject res = new JsonObject();
		res.addProperty("name", name);
		res.addProperty("iconUrl", iconUrl);
		return res;
	}

}
