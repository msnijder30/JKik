package nl.marksnijder.jkik.message;

import com.google.gson.JsonObject;

import lombok.Getter;

public class MessageAttribute {
	
	@Getter
	public String iconUrl;
	
	@Getter
	public String style;
	
	@Getter
	public String name;
	
	
	public MessageAttribute(String iconUrl, String style, String name) {
		this.iconUrl = iconUrl;
		this.style = style;
		this.name = name;
	}
	
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
