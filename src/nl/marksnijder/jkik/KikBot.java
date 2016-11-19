package nl.marksnijder.jkik;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import nl.marksnijder.jkik.messages.IsTypingMessage;
import nl.marksnijder.jkik.messages.Message;
import nl.marksnijder.jkik.messages.MessageAttribute;
import nl.marksnijder.jkik.messages.MessageType;
import nl.marksnijder.jkik.messages.PictureMessage;
import nl.marksnijder.jkik.messages.TextMessage;
import nl.marksnijder.jkik.messages.VideoMessage;

public abstract class KikBot {
	
	public abstract void onTextReceived(TextMessage msg);
	public abstract void onLinkReceived(Message msg);
	public abstract void onPictureReceived(PictureMessage msg);
	public abstract void onVideoReceived(VideoMessage msg);
	public abstract void onStartChattingReceived(Message msg);
	public abstract void onScanDataReceived(Message msg);
	public abstract void onStickerReceived(Message msg);
	public abstract void onIsTypingReceived(IsTypingMessage msg);
	public abstract void onDeliveryReceiptReceived(Message msg);
	public abstract void onReadReceiptReceived(Message msg);
	public abstract void onFriendPickerReceived(Message msg);
	
	public void onMessageReceived(String data) {
		
		JsonParser jp = new JsonParser();
	    JsonElement root = jp.parse(data); 
	    JsonObject rootObj = root.getAsJsonObject();
	    
	    JsonArray msgObject = rootObj.get("messages").getAsJsonArray();
	    
	    for(int i = 0; i < msgObject.size(); i++) {
	    	JsonObject obj = (JsonObject) msgObject.get(i);
	    	
		    MessageType type = MessageType.valueOf(obj.get("type").getAsString().replaceAll("-", "").toUpperCase());

		    String from = obj.get("from").getAsString();
		    String id = obj.get("id").getAsString();
		    String chatId = obj.get("chatId").getAsString();
		    long timestamp = obj.get("timestamp").getAsLong();
		    String mention = obj.get("mention").isJsonNull() ? null : obj.get("mention").getAsString();
		    boolean readReceiptRequested = obj.get("readReceiptRequested").getAsBoolean();
		    

		    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		    ArrayList<String> participants = new Gson().fromJson(obj.get("participants"), listType);
		    
		    Chat chat = new Chat(participants, chatId, from);
			
			switch(type) {
			
			case TEXT:
			    String body = obj.get("body").getAsString();
			    
			    TextMessage textMessage = new TextMessage(chat, timestamp, mention, readReceiptRequested, type, id, body);
			    
			    onTextReceived(textMessage);
				break;
				
			case LINK:
				
				break;
				
			case PICTURE:
			    String pictureUrl = obj.get("pictureUrl").getAsString();
			    
			    MessageAttribute at = new MessageAttribute("", "", "");
			    PictureMessage pictureMessage = new PictureMessage(chat, timestamp, mention, readReceiptRequested, type, id, at, pictureUrl);
			    
			    onPictureReceived(pictureMessage);
				break;
				
			case VIDEO:
			    String videoUrl = obj.get("body").getAsString();
			    
			    at = new MessageAttribute("", "", "");
			    VideoMessage videoMessage = new VideoMessage(chat, timestamp, mention, readReceiptRequested, type, id, at, videoUrl);
			    
			    onVideoReceived(videoMessage);
				break;
				
			case STARTCHATTING:

				break;
				
			case SCANDATA:

				break;
				
			case STICKER:

				break;
				
			case ISTYPING:
			    boolean isTyping = obj.get("isTyping").getAsBoolean();
			    
			    IsTypingMessage isTypingMessage = new IsTypingMessage(chat, timestamp, mention, readReceiptRequested, type, id, isTyping);
			    
			    onIsTypingReceived(isTypingMessage);
				break;
				
			case DELIVERYRECEIPT:

				break;
				
			case READRECEIPT:

				break;
				
			case FRIENDPICKER:

				break;
				
			}		    
		    
//		    Message msg = new Message(chat, body, timestamp, mention, readReceiptRequested, type, id);
			System.out.println(type);
	    }
		
//		switch(msg.getType()) {
//		
//		case TEXT:
//			onTextReceived(msg);
//			break;
//			
//		case LINK:
//			onLinkReceived(msg);
//			break;
//			
//		case PICTURE:
//			onPictureReceived(msg);
//			break;
//			
//		case VIDEO:
//			onVideoReceived(msg);
//			break;
//			
//		case STARTCHATTING:
//			onStartChattingReceived(msg);
//			break;
//			
//		case SCANDATA:
//			onScanDataReceived(msg);
//			break;
//			
//		case STICKER:
//			onStickerReceived(msg);
//			break;
//			
//		case ISTYPING:
//			onIsTypingReceived(msg);
//			break;
//			
//		case DELIVERYRECEIPT:
//			onDeliveryReceiptReceived(msg);
//			break;
//			
//		case READRECEIPT:
//			onReadReceiptReceived(msg);
//			break;
//			
//		case FRIENDPICKER:
//			onFriendPickerReceived(msg);
//			break;
//			
//		}
		
	}

}
