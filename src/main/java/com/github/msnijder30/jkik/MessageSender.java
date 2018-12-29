package com.github.msnijder30.jkik;

import com.github.msnijder30.jkik.message.Sendable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.Setter;

public class MessageSender {

	@Getter
	@Setter
	public static KikApi api;

	private MessageSender() {

	}

	/**
	 * Allows you to send 100 messages in 1 request, doesn't have the per-second
	 * limit as the sendMessage method
	 *
	 * @param messages
	 *            An array of messages
	 */
	public static boolean broadcastMessages(Sendable... messages) {
		return sendMessages(KikApi.BROADCAST_URL, messages);
	}

	public static boolean sendMessages(Sendable... messages) {
		return sendMessages(KikApi.MESSAGE_URL, messages);
	}

	/**
	 *
	 * @param url
	 *            The url to which the sendables should be sent
	 * @param messages
	 *            The sendables which should be send to the url
	 * @return Returns if the messages were sent successfully (true) or if an
	 *         error occurred (false)
	 */
	private static boolean sendMessages(String url, Sendable... messages) {
		JsonObject toSend = new JsonObject();
		JsonArray msgArray = new JsonArray();

		for (Sendable msg : messages) {
			JsonObject jsonData = msg.toJsonObject();
			msgArray.add(jsonData);
		}
		toSend.add("messages", msgArray);

		return api.executePost(url, toSend.toString(), MethodType.POST) != null;
	}

}
