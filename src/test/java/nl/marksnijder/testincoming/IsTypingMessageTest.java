package nl.marksnijder.testincoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import nl.marksnijder.jkik.message.IsTypingMessage;

public class IsTypingMessageTest extends IncomingMessageTest {

	@Override
	protected String getJSONTestData() {
		return "{\r\n" + 
				"    \"messages\": [\r\n" + 
				"        {\r\n" + 
				"            \"chatId\": \"b3be3bc15dbe59931666c06290abd944aaa769bb2ecaaf859bfb65678880afab\",\r\n" + 
				"            \"id\": \"cf02ade9-0659-4962-8d58-12d900cc03bb\",\r\n" + 
				"            \"type\": \"is-typing\",\r\n" + 
				"            \"from\": \"laura\",\r\n" + 
				"            \"participants\": [\"laura\"],\r\n" + 
				"            \"timestamp\": 238423943284,\r\n" + 
				"            \"isTyping\": false,\r\n" + 
				"            \"readReceiptRequested\": false,\r\n" + 
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
		return "cf02ade9-0659-4962-8d58-12d900cc03bb";
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
		return 238423943284L;
	}

	@Override
	@Test
	protected void messageSpecificTest() {
		assertTrue(parsedMessage instanceof IsTypingMessage);
		IsTypingMessage message = (IsTypingMessage) parsedMessage;
		
		assertFalse(message.isTyping());
		assertFalse(message.isReadReceiptRequested());
	}
	
}
