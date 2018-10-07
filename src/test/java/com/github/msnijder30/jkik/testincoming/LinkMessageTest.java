package com.github.msnijder30.jkik.testincoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.msnijder30.jkik.message.LinkMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public class LinkMessageTest extends IncomingMessageTest {

	@Override
	protected String getJSONTestData() {
		return "{\r\n" + 
				"    \"messages\": [\r\n" + 
				"        {\r\n" + 
				"            \"chatId\": \"b3be3bc15dbe59931666c06290abd944aaa769bb2ecaaf859bfb65678880afab\",\r\n" + 
				"            \"type\": \"link\",\r\n" + 
				"            \"from\": \"laura\",\r\n" + 
				"            \"participants\": [\"laura\"],\r\n" + 
				"            \"id\": \"6d8d060c-3ae4-46fc-bb18-6e7ba3182c0f\",\r\n" + 
				"            \"timestamp\": 83294238952,\r\n" + 
				"            \"url\": \"http://mywebpage.com\",\r\n" + 
				"            \"attribution\": {\r\n" + 
				"                \"name\": \"My App\",\r\n" + 
				"                \"iconUrl\": \"http://example.kik.com/anicon.png\"\r\n" + 
				"            },\r\n" + 
				"            \"noForward\": true,\r\n" + 
				"            \"readReceiptRequested\": true,\r\n" + 
				"            \"mention\": null,\r\n" + 
				"            \"metadata\": null,\r\n" + 
				"            \"chatType\": \"direct\"\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";
	}

	@Override
	protected String getExpectedChatId() {
		return "b3be3bc15dbe59931666c06290abd944aaa769bb2ecaaf859bfb65678880afab";
	}

	@Override
	protected String getExpectedFrom() {
		return "laura";
	}

	@Override
	protected ArrayList<String> getExpectedParticipants() {
		return new ArrayList<>(Arrays.asList("laura"));
	}

	@Override
	protected String getExpectedMessageId() {
		return "6d8d060c-3ae4-46fc-bb18-6e7ba3182c0f";
	}

	@Override
	protected String getExpectedMention() {
		return null;
	}

	@Override
	protected JsonElement getExpectedMetadata() {
		return JsonNull.INSTANCE;
	}

	@Override
	protected long getExpectedTimestamp() {
		return 83294238952L;
	}

	@Override
	@Test
	protected void messageSpecificTest() {
		assertTrue(parsedMessage instanceof LinkMessage);
		LinkMessage message = (LinkMessage) parsedMessage;
		
		assertEquals("My App", message.getAttribution().getName());	
		assertEquals("http://example.kik.com/anicon.png", message.getAttribution().getIconUrl());	
		
		assertEquals("http://mywebpage.com", message.getUrl());	
		assertTrue(message.isNoForward());
		assertTrue(message.isReadReceiptRequested());
	}

}
