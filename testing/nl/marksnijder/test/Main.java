package nl.marksnijder.test;

import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.KikBot;

public class Main {
	
	public static final String username = "username";
	public static final String key = "api_key";
	
	public static void main(String[] args) {
		
		KikApi api = new KikApi(username, key, 8080, new TestBot());
		
		
	}

}
