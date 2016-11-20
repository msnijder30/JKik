package nl.marksnijder.jkik.keyboard;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.message.MessageType;

public class FriendPicker extends TextButton {
	
	@Getter
	private int max;
	
	@Getter
	private int min;
	
	@Getter
	private List<String> preselected;
	
	public FriendPicker(String body, int min, int max) {
		this(body, min, max, null);
	}
	
	public FriendPicker(String body, int min, int max, List<String> preselected) {
		super(body, MessageType.FRIEND_PICKER);
		this.max = max;
		this.min = min;
		this.preselected = preselected;
	}
	
	public JsonObject getJson() {
		System.out.println("Callling friendpicker  method");
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
