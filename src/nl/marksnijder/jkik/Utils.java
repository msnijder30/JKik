package nl.marksnijder.jkik;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class Utils {
	
	private static String ip;
	public static String getPublicIP() {
		if(ip != null) return ip;
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
	
	public static String HmacSHA1(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		
		return DatatypeConverter.printHexBinary(mac.doFinal(data.getBytes()));
	}
	
	public static void saveFile(String url, String path) throws MalformedURLException, IOException {
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla");

		InputStream is = connection.getInputStream();
		OutputStream os = new FileOutputStream(path);
	 
		byte[] b = new byte[2048];
		int length;
	 
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
	 
		is.close();
		os.close();
	}

}
