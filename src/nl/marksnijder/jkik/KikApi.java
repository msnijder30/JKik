package nl.marksnijder.jkik;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.thread.KikServer;

public class KikApi {

	@Getter
	private String username;
	
	@Getter
	private String apiKey;
	
	@Getter
	private int port;
	
	@Getter @Setter
	private KikBot bot;
	
	@Getter
	private KikSettings settings;
	
	@Getter
	private String publicIP;
	
	private KikServer server;
	
	@Getter
	private KikFiles files;
	
	public static final String URL = "https://api.kik.com/v1/";
	
	public static final String CONFIG_URL = URL + "config";
	public static final String MESSAGE_URL = URL + "message";
	public static final String USER_URL = URL + "user/";
	public static final String BROADCAST_URL = URL + "broadcast";
	
	
	public KikApi(String username, String apiKey, int port, KikBot bot) {
		this(username, apiKey, port, bot, null);
	}

	
	public KikApi(String username, String apiKey, int port, KikBot bot, KikSettings settings) {
		this.username = username;
		this.apiKey = apiKey;
		this.port = port;
		this.settings = settings == null ? new KikSettings(false, false, false, false) : settings;
		this.files = new KikFiles(this);
		this.bot = bot;
		this.bot.setApi(this);
		publicIP = Utils.getPublicIP();
		createConnection();
		MessageSender.setApi(this);
	}
	
	public User getUserInfo(String username) {
		String data = executePost(USER_URL + username, "", MethodType.GET);
		JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
		
	    String lastName = obj.get("lastName").getAsString();
	    String firstName = obj.get("firstName").getAsString();
	    String profilePicUrl = obj.get("profilePicUrl").getAsString();
	    long profilePicLastModified = obj.get("profilePicLastModified").isJsonNull() ? null : obj.get("profilePicLastModified").getAsLong();
	    
	    return new User(firstName, lastName, profilePicUrl, profilePicLastModified);
	}
	
	
	private void createConnection() {
		JsonObject finalObject = new JsonObject();
		finalObject.addProperty("webhook", "http://" + publicIP + ":" + port + "/" + username);

		JsonObject object = new JsonObject();
		object.addProperty("manuallySendReadReceipts", settings.isManuallySendReadReceipts());
		object.addProperty("receiveReadReceipts", settings.isReceiveReadReceipts());
		object.addProperty("receiveDeliveryReceipts", settings.isReceiveDeliveryReceipts());
		object.addProperty("receiveIsTyping", settings.isReceiveIsTyping());

		finalObject.add("features", object);
		
		executePost(CONFIG_URL, new Gson().toJson(finalObject), MethodType.POST);
		
		server = new KikServer(this);
		server.start();
		
		System.out.println("JKik starting on " + publicIP + ":" + port + "/" + username);
	}
	
	public String executePost(String targetURL, String urlParameters, MethodType type) {
		HttpURLConnection connection = null;
		
		try {
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(type.toString().toUpperCase());
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			
			connection.setRequestProperty ("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + apiKey).getBytes("utf-8")));
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  
			
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			if(type == MethodType.POST) {
				connection.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			}
			
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
