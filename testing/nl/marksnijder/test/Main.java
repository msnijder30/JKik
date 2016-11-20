package nl.marksnijder.test;

import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.KikSettings;
import nl.marksnijder.jkik.User;

public class Main {

	public static final String username = "username";
	public static final String key = "key";

	
	public static void main(String[] args) {
		
		KikSettings settings = new KikSettings(true, true, true, true);
		KikApi api = new KikApi(username, key, 8080, new TestBot(), settings);
		User user = api.getUserInfo("username");
		
		
	}

}
