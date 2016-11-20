package nl.marksnijder.jkik.message;

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

}
