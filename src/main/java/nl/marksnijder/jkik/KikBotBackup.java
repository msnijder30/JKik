package nl.marksnijder.jkik;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import nl.marksnijder.jkik.message.FriendPickerMessage;
import nl.marksnijder.jkik.message.IsTypingMessage;
import nl.marksnijder.jkik.message.LinkMessage;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.MessageAttribute;
import nl.marksnijder.jkik.message.MessageType;
import nl.marksnijder.jkik.message.PictureMessage;
import nl.marksnijder.jkik.message.ScanDataMessage;
import nl.marksnijder.jkik.message.StickerMessage;
import nl.marksnijder.jkik.message.TextMessage;
import nl.marksnijder.jkik.message.VideoMessage;
import nl.marksnijder.jkik.receipt.DeliveryReceipt;
import nl.marksnijder.jkik.receipt.ReadReceipt;

public abstract class KikBotBackup {
	
	@Getter @Setter
	private KikApi api;
	
	private static Gson gson = new Gson();
	
	public abstract void onTextReceived(TextMessage msg);
	public abstract void onLinkReceived(LinkMessage msg);
	public abstract void onPictureReceived(PictureMessage msg);
	public abstract void onVideoReceived(VideoMessage msg);
	public abstract void onStartChattingReceived(Message msg);
	public abstract void onScanDataReceived(ScanDataMessage msg);
	public abstract void onStickerReceived(StickerMessage msg);
	public abstract void onIsTypingReceived(IsTypingMessage msg);
	public abstract void onDeliveryReceiptReceived(DeliveryReceipt msg);
	public abstract void onReadReceiptReceived(ReadReceipt msg);
	public abstract void onFriendPickerReceived(FriendPickerMessage msg);
	
	public final void onMessageReceived(String data) {
		
		JsonParser jp = new JsonParser();
	    JsonElement root = jp.parse(data); 

		Messages messages = gson.fromJson(root, Messages.class);
	    
	    for(JsonObject obj : messages.getMessages()) {
		    MessageType type = MessageType.valueOf(obj.get("type").getAsString().replaceAll("-", "_").toUpperCase());

		    Chat chat = gson.fromJson(obj, Chat.class);	
		    
		    Message parsedMessage = null;
		    
			switch(type) {
			
			case TEXT:
			    TextMessage textMessage = gson.fromJson(obj, TextMessage.class);
			    textMessage.setChat(chat);
			    parsedMessage = textMessage;
			    
			    onTextReceived(textMessage);
				break;
				
			case LINK:
				LinkMessage linkMessage = gson.fromJson(obj, LinkMessage.class);
				linkMessage.setChat(chat);
			    parsedMessage = linkMessage;
			    
			    onLinkReceived(linkMessage);
				break;
				
			case PICTURE:
				PictureMessage pictureMessage = gson.fromJson(obj, PictureMessage.class);
				pictureMessage.setChat(chat);
			    parsedMessage = pictureMessage;
				
			    onPictureReceived(pictureMessage);
				break;
				
			case VIDEO:
				VideoMessage videoMessage = gson.fromJson(obj, VideoMessage.class);
				videoMessage.setChat(chat);
			    parsedMessage = videoMessage;

			    onVideoReceived(videoMessage);
				break;
				
			case START_CHATTING:
				TextMessage startChattingMessage = gson.fromJson(obj, TextMessage.class);
				startChattingMessage.setChat(chat);
			    parsedMessage = startChattingMessage;
				
			    onStartChattingReceived(startChattingMessage);
				break;
				
			case SCAN_DATA:
				ScanDataMessage scanDataMessage = gson.fromJson(obj, ScanDataMessage.class);
				scanDataMessage.setChat(chat);
			    parsedMessage = scanDataMessage;
				
			    onScanDataReceived(scanDataMessage);
				break;
				
			case STICKER:
				StickerMessage stickerMessage = gson.fromJson(obj, StickerMessage.class);
				stickerMessage.setChat(chat);
			    parsedMessage = stickerMessage;
				
			    onStickerReceived(stickerMessage);
				break;
				
			case IS_TYPING:
				IsTypingMessage isTypingMessage = gson.fromJson(obj, IsTypingMessage.class);
				isTypingMessage.setChat(chat);
			    parsedMessage = isTypingMessage;
			    
			    onIsTypingReceived(isTypingMessage);
				break;
				
			case DELIVERY_RECEIPT:
				DeliveryReceipt deliveryReceipt = gson.fromJson(obj, DeliveryReceipt.class);
				deliveryReceipt.setChat(chat);
			    parsedMessage = deliveryReceipt;
			    
			    onDeliveryReceiptReceived(deliveryReceipt);
				break;
				
			case READ_RECEIPT:
				ReadReceipt readReceipt = gson.fromJson(obj, ReadReceipt.class);
				readReceipt.setChat(chat);
			    parsedMessage = readReceipt;
			    
			    onReadReceiptReceived(readReceipt);
				break;
				
			case FRIEND_PICKER:
				FriendPickerMessage friendPickerMessage = gson.fromJson(obj, FriendPickerMessage.class);
				friendPickerMessage.setChat(chat);
			    parsedMessage = friendPickerMessage;
			    
			    onFriendPickerReceived(friendPickerMessage);
				break;
				
			}	
			
			messages.getParsedMessages().add(parsedMessage);
		    
	    }
		
	}

	private class Messages {
		
		@Getter
		private ArrayList<JsonObject> messages;
		
		@Getter @Setter
		private ArrayList<Message> parsedMessages = new ArrayList<>();
		
	}
	
}
