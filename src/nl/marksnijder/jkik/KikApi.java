package nl.marksnijder.jkik;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Getter;
import nl.marksnijder.jkik.threaded.KikServer;

public class KikApi {

	@Getter
	private String username;
	
	@Getter
	private String apiKey;
	
	@Getter
	private int port;
	
	@Getter
	private KikBot bot;
	private KikServer server;
	
	public static final String URL = "https://api.kik.com/v1/";
	
	public static final String CONFIG_URL = URL + "config";
	public static final String MESSAGE_URL = URL + "message";
	public static final String USER_URL = URL + "user/";
	
	public KikApi(String username, String apiKey, int port, KikBot bot) {
		this.username = username;
		this.apiKey = apiKey;
		this.port = port;
		this.bot = bot;
		createConnection();
		MessageSender.setApi(this);
		System.out.println("Program starting");
	}
	
	private void createConnection() {
		JsonObject finalObject = new JsonObject();
		finalObject.addProperty("webhook", "http://" + Utils.getPublicIP() + ":" + port + "/" + username);

		JsonObject object = new JsonObject();
		object.addProperty("manuallySendReadReceipts", true);
		object.addProperty("receiveReadReceipts", true);
		object.addProperty("receiveDeliveryReceipts", true);
		object.addProperty("receiveIsTyping", true);

		finalObject.add("features", object);
		
		executePost(CONFIG_URL, new Gson().toJson(finalObject));
		
		server = new KikServer(this);
		server.start();
	}
	
	public String executePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;
		
		try {
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			connection.setRequestProperty ("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + apiKey).getBytes("utf-8")));
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  
			
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			//Get Response  
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
			  response.append(line);
			  response.append('\r');
			}
			
			rd.close();
			return response.toString();

		  } catch (Exception e) {
	  		e.printStackTrace();
		  	return null;
		  } finally {
			  if (connection != null) {
		      connection.disconnect();
			  }
		  }
	}

	
}
