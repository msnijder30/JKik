package nl.marksnijder.testincoming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.google.gson.JsonElement;

import nl.marksnijder.jkik.message.Message;
@TestInstance(Lifecycle.PER_CLASS)
public abstract class IncomingMessageTest {

	protected Message parsedMessage;

	/**
	 * Method which should return a string in JSON format
	 * @return
	 */
	protected abstract String getJSONTestData();
	
	/**
	 * Methods that should return the actual values of the JSON test string of the chat information
	 * @return
	 */
	protected abstract String getExpectedChatId();
	protected abstract String getExpectedFrom();
	protected abstract ArrayList<String> getExpectedParticipants();
	
	/**
	 * Methods that should return the actual values of the JSON test string of basic message information
	 * @return
	 */
	protected abstract String getExpectedMessageId();
	protected abstract String getExpectedMention();
	protected abstract JsonElement getExpectedMetadata();
	protected abstract long getExpectedTimestamp();
	
	/**
	 * Force subclass to implement a method that tests for variables which are unique to that message type
	 */
	protected abstract void messageSpecificTest();
	
	@BeforeAll
	protected void createParsedMessage() {
		TestBot bot = new TestBot();
		ArrayList<Message> results = bot.onMessageReceived(getJSONTestData());
		
		assertEquals(1, results.size());
		assertNotNull(results.get(0));
		
		parsedMessage = results.get(0);
	}
	
	@Test
	protected void basicMessageInformationTest() {
		assertEquals(getExpectedTimestamp(), parsedMessage.getTimestamp());
		assertEquals(getExpectedMessageId(), parsedMessage.getId());
		assertEquals(getExpectedMention(), parsedMessage.getMention());
		assertEquals(getExpectedMetadata(), parsedMessage.getMetadata());
	}
	
	@Test
	protected void messageChatTest() {
		assertNotNull(parsedMessage.getChat());
		assertEquals(getExpectedChatId(), parsedMessage.getChat().getChatId());
		assertEquals(getExpectedFrom(), parsedMessage.getChat().getFrom());
		assertEquals(getExpectedParticipants(), parsedMessage.getChat().getParticipants());
	}
	
}
