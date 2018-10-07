package com.github.msnijder30.jkik;

import java.util.ArrayList;

import com.github.msnijder30.jkik.message.FriendPickerMessage;
import com.github.msnijder30.jkik.message.IsTypingMessage;
import com.github.msnijder30.jkik.message.LinkMessage;
import com.github.msnijder30.jkik.message.Message;
import com.github.msnijder30.jkik.message.MessageType;
import com.github.msnijder30.jkik.message.PictureMessage;
import com.github.msnijder30.jkik.message.ScanDataMessage;
import com.github.msnijder30.jkik.message.StickerMessage;
import com.github.msnijder30.jkik.message.TextMessage;
import com.github.msnijder30.jkik.message.VideoMessage;
import com.github.msnijder30.jkik.receipt.DeliveryReceipt;
import com.github.msnijder30.jkik.receipt.ReadReceipt;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;

public abstract class KikBot {

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
	
	public final ArrayList<Message> onMessageReceived(String data) {
		JsonParser jp = new JsonParser();
	    JsonElement root = jp.parse(data); 
	    
		Messages messages = gson.fromJson(root, Messages.class);
		ArrayList<Message> parsedMessages = new ArrayList<>();
	    
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
			
			parsedMessages.add(parsedMessage);
		    
	    }
		
	    return parsedMessages;
	}

	private class Messages {
		
		@Getter
		private ArrayList<JsonObject> messages;
		
	}
	
}
