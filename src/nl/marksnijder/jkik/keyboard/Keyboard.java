package nl.marksnijder.jkik.keyboard;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class Keyboard {
	
	@Getter @Setter
	private List<TextButton> buttons;

	@Getter @Setter
	private boolean hidden;
	
	@Getter @Setter
	private String to;
	
	@Getter
	private String type;

	public Keyboard(TextButton... buttons) {
		this(Arrays.asList(buttons), false);
	}
	
	public Keyboard(List<TextButton> buttons) {
		this(buttons, false);
	}
	
	public Keyboard(List<TextButton> buttons, boolean hidden) {
		this(buttons, false, null);
	}
	
	public Keyboard(List<TextButton> buttons, boolean hidden, String to) { 
		this.buttons = buttons;
		this.hidden = hidden;
		this.to = to;
		this.type = "suggested";
	}
	
	public Keyboard addButton(TextButton text) {
		buttons.add(text);
		return this;
	}

	public JsonObject getJson() {
		JsonObject obj = new JsonObject();
		if(to != null && !to.isEmpty()) obj.addProperty("to", to);
		obj.addProperty("type", type);
		
		JsonArray responses = new JsonArray();
		for(TextButton button : buttons) {
			responses.add(button.getJson());
		}
		
		obj.add("responses", responses);
		
		return obj;
	}

}
