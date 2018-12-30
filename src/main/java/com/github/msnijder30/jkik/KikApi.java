package com.github.msnijder30.jkik;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.msnijder30.jkik.keyboard.Keyboard;
import com.github.msnijder30.jkik.thread.KikServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;

public class KikApi {

	private static final Logger logger = LoggerFactory.getLogger(KikApi.class);

	@Getter
	private String username;

	@Getter
	private String apiKey;

	@Getter
	private int port;

	@Getter
	@Setter
	private KikBot bot;

	@Getter
	private KikSettings settings;

	@Getter
	private String publicIP;

	private KikServer server;

	@Getter
	private KikFiles files;

	@Getter
	private Keyboard staticKeyboard;

	public static final String URL = "https://api.kik.com/v1/";

	public static final String CONFIG_URL = URL + "config";
	public static final String MESSAGE_URL = URL + "message";
	public static final String USER_URL = URL + "user";
	public static final String BROADCAST_URL = URL + "broadcast";
	public static final String CODE_URL = URL + "code";

	public KikApi(String username, String apiKey, int port, KikBot bot) throws IOException {
		this(username, apiKey, port, bot, null, null);
	}

	public KikApi(String username, String apiKey, int port, KikBot bot, Keyboard staticKeyboard) throws IOException {
		this(username, apiKey, port, bot, null, staticKeyboard);
	}

	public KikApi(String username, String apiKey, int port, KikBot bot, KikSettings settings) throws IOException {
		this(username, apiKey, port, bot, settings, null);
	}

	public KikApi(String username, String apiKey, int port, KikBot bot, KikSettings settings, Keyboard staticKeyboard)
			throws IOException {

		this.username = username;
		this.apiKey = apiKey;
		this.port = port;
		this.settings = settings == null ? new KikSettings(false, false, false, false) : settings;
		files = new KikFiles(this);
		this.staticKeyboard = staticKeyboard;
		this.bot = bot;
		this.bot.setApi(this);
		publicIP = Utils.getPublicIP();
		MessageSender.setApi(this);
	}

	public User getUserInfo(String username) {
		// TODO map this using GSON instead of doing it manually.
		String data = executePost(USER_URL + "/" + username, "", MethodType.GET);
		JsonObject obj = new JsonParser().parse(data).getAsJsonObject();

		String lastName = obj.get("lastName").getAsString();
		String firstName = obj.get("firstName").getAsString();

		String profilePicUrl = null;
		if (obj.has("profilePicUrl")) {
			profilePicUrl = obj.get("profilePicUrl").isJsonNull() ? null : obj.get("profilePicUrl").getAsString();
		}

		long profilePicLastModified = -1;
		if (obj.has("profilePicLastModified")) {
			profilePicLastModified = obj.get("profilePicLastModified").isJsonNull() ? null
					: obj.get("profilePicLastModified").getAsLong();
		}

		return new User(firstName, lastName, profilePicUrl, profilePicLastModified);
	}

	public String generateKikCodeUrl(String data, KikColor color) {
		JsonObject obj = new JsonObject();
		obj.addProperty("data", data);

		String result = executePost(CODE_URL, obj.toString(), MethodType.POST);
		JsonParser parser = new JsonParser();
		String resultId = parser.parse(result).getAsJsonObject().get("id").getAsString();
		return CODE_URL + "/" + resultId + "?c=" + color.ordinal();
	}

	private void createConnection() throws Exception {
		JsonObject finalObject = new JsonObject();
		finalObject.addProperty("webhook", "http://" + publicIP + ":" + port + "/" + username);

		JsonObject object = new JsonObject();
		object.addProperty("manuallySendReadReceipts", settings.isManuallySendReadReceipts());
		object.addProperty("receiveReadReceipts", settings.isReceiveReadReceipts());
		object.addProperty("receiveDeliveryReceipts", settings.isReceiveDeliveryReceipts());
		object.addProperty("receiveIsTyping", settings.isReceiveIsTyping());

		finalObject.add("features", object);

		if (staticKeyboard != null) {
			finalObject.add("staticKeyboard", staticKeyboard.toJsonObject());
		}

		if (executePost(CONFIG_URL, new Gson().toJson(finalObject), MethodType.POST) == null) {
			throw new Exception("An error occured when trying to establish a connection to KIK");
		}

		server = new KikServer(this);
		server.start();

		logger.info(String.format("JKik starting on %s:%d/%s", publicIP, port, username));
	}

	public String executePost(String targetURL, String urlParameters, MethodType type) {
		logger.debug(String.format("Attempting to send %s request with data %s", type.toString(), urlParameters));

		HttpURLConnection connection = null;

		try {
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(type.toString().toUpperCase());
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			connection.setRequestProperty("Authorization",
					"Basic " + Base64.getEncoder().encodeToString((username + ":" + apiKey).getBytes("utf-8")));
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			if (type == MethodType.POST) {
				connection.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.write(urlParameters.getBytes(Charset.forName("UTF-8")));
				wr.flush();
				wr.close();
			}

			int responseCode = connection.getResponseCode();

			InputStream is = null;
			if (responseCode >= 200 && responseCode < 400) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}

			// Get Response
			StringBuilder response = new StringBuilder();
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(String.format("%s%n", line));
				}

				logger.debug(
						String.format(
								"Response received with response code %d and response message %s, response was %s",
								connection.getResponseCode(),
								connection.getResponseMessage(), response));
			}

			if (!(responseCode >= 200 && responseCode < 400)) {
				logger.error(
						String.format(
								"Error received with response code %d and response message %s, send data was %s and response was %s",
								connection.getResponseCode(),
								connection.getResponseMessage(), urlParameters, response));
				throw new Exception("Error code: " + responseCode + " message: " + connection.getResponseMessage());
			}
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

	public void start() throws Exception {
		createConnection();
	}

}
