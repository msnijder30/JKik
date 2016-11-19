package nl.marksnijder.jkik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class Utils {
	
	public static String getPublicIP() {
		String ip = null;
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

			ip = in.readLine(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	public static <T> T getValue(JsonObject obj, String find) {
		T nullableVal = (T) obj.get(find);
		T val = (nullableVal instanceof JsonNull) ? null : (T) nullableVal;
		return val;
	}

}
