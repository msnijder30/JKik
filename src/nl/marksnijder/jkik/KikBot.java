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

public abstract class KikBot {
	
	@Getter @Setter
	private KikApi api;
	
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
	    JsonObject rootObj = root.getAsJsonObject();
	    
	    JsonArray msgObject = rootObj.get("messages").getAsJsonArray();
	    
	    for(int i = 0; i < msgObject.size(); i++) {
	    	JsonObject obj = (JsonObject) msgObject.get(i);
	    	
		    MessageType type = MessageType.valueOf(obj.get("type").getAsString().replaceAll("-", "_").toUpperCase());

		    String from = obj.get("from").getAsString();
		    String id = obj.get("id").getAsString();
		    String chatId = obj.get("chatId").getAsString();
		    long timestamp = obj.get("timestamp").getAsLong();
		    String mention = obj.get("mention").isJsonNull() ? null : obj.get("mention").getAsString();
		    boolean readReceiptRequested = obj.get("readReceiptRequested").getAsBoolean();
		    JsonObject metadata = obj.has("metadata") && !obj.get("metadata").isJsonNull() ? obj.get("metadata").getAsJsonObject() : null;
		    

		    Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		    ArrayList<String> participants = new Gson().fromJson(obj.get("participants"), listType);
		    
		    Chat chat = new Chat(participants, chatId, from);
			
			switch(type) {
			
			case TEXT:
			    String body = obj.get("body").getAsString();
			    
			    TextMessage textMessage = new TextMessage(chat, timestamp, mention, readReceiptRequested, id, body, metadata);
			    
			    onTextReceived(textMessage);
				break;
				
			case LINK:
				String url = obj.get("url").getAsString();
				String text = obj.has("text") && !obj.get("text").isJsonNull() ? obj.get("text").getAsString() : null;
				
				JsonObject dataAttr = obj.has("kikJsData") && !obj.get("kikJsData").isJsonNull() && obj.get("kikJsData").isJsonObject() ? obj.get("attribution").getAsJsonObject() : null;
				
				JsonObject objAttr = (JsonObject) obj.get("attribution");
			    MessageAttribute at = new MessageAttribute(objAttr.get("iconUrl").getAsString(), objAttr.get("style").getAsString(), objAttr.get("name").getAsString());

			    LinkMessage linkMessage = new LinkMessage(chat, timestamp, mention, readReceiptRequested, id, at, url, readReceiptRequested, dataAttr, text, metadata);
				onLinkReceived(linkMessage);
				break;
				
			case PICTURE:
			    String pictureUrl = obj.get("picUrl").getAsString();
			    
			    objAttr = (JsonObject) obj.get("attribution");
			    at = new MessageAttribute(objAttr.get("iconUrl").getAsString(), objAttr.get("style").getAsString(), objAttr.get("name").getAsString());
			    PictureMessage pictureMessage = new PictureMessage(chat, timestamp, mention, readReceiptRequested, id, at, pictureUrl, metadata);
			    
			    onPictureReceived(pictureMessage);
				break;
				
			case VIDEO:
			    String videoUrl = obj.get("videoUrl").getAsString();

			    objAttr = (JsonObject) obj.get("attribution");
			    at = new MessageAttribute(objAttr.get("iconUrl").getAsString(), objAttr.get("style").getAsString(), objAttr.get("name").getAsString());
			    VideoMessage videoMessage = new VideoMessage(chat, timestamp, mention, readReceiptRequested, id, at, videoUrl, metadata);
			    
			    onVideoReceived(videoMessage);
				break;
				
			case START_CHATTING:
			    body = obj.get("body").getAsString();
			    
			    textMessage = new TextMessage(chat, timestamp, mention, readReceiptRequested, id, body, metadata);
			    
			    onStartChattingReceived(textMessage);
				break;
				
			case SCAN_DATA:
				if(obj.get("data").isJsonObject()) {
				    JsonObject dataObj = (JsonObject) obj.get("data");
				    String referrer = dataObj.has("referrer") ? dataObj.get("referrer").getAsString() : null;
				    int storeId = dataObj.has("store_id") ? dataObj.get("store_id").getAsInt() : -1;
				    
				    ScanDataMessage scanDataMessage = new ScanDataMessage(chat, timestamp, mention, readReceiptRequested, id, referrer, storeId, metadata);
				    
				    onScanDataReceived(scanDataMessage);
				} else {
				    ScanDataMessage scanDataMessage = new ScanDataMessage(chat, timestamp, mention, readReceiptRequested, id, obj.get("data").getAsString(), metadata);
				    
				    onScanDataReceived(scanDataMessage);
				}
				break;
				
			case STICKER:
			    String stickerUrl = obj.get("stickerUrl").getAsString();
			    String stickerPackId = obj.get("stickerPackId").getAsString();
			    
			    StickerMessage stickerMessage = new StickerMessage(chat, timestamp, mention, readReceiptRequested, id, stickerUrl, stickerPackId, metadata);
			    
			    onStickerReceived(stickerMessage);
				break;
				
			case IS_TYPING:
			    boolean isTyping = obj.get("isTyping").getAsBoolean();
			    
			    IsTypingMessage isTypingMessage = new IsTypingMessage(chat, timestamp, mention, readReceiptRequested, id, isTyping);
			    
			    onIsTypingReceived(isTypingMessage);
				break;
				
			case DELIVERY_RECEIPT:
			    ArrayList<String> messageIds = new Gson().fromJson(obj.get("participants"), listType);	
			    
			    DeliveryReceipt deliveryReceipt = new DeliveryReceipt(chat, timestamp, mention, readReceiptRequested, type, id, messageIds);
			    
			    onDeliveryReceiptReceived(deliveryReceipt);
				break;
				
			case READ_RECEIPT:
			    messageIds = new Gson().fromJson(obj.get("participants"), listType);
			    
			    ReadReceipt readReceipt = new ReadReceipt(chat, timestamp, mention, readReceiptRequested, type, id, messageIds);
			    
			    onReadReceiptReceived(readReceipt);
				break;
				
			case FRIEND_PICKER:
			    ArrayList<String> picked = new Gson().fromJson(obj.get("picked"), listType);
			    
			    FriendPickerMessage friendPickerMessage = new FriendPickerMessage(chat, timestamp, mention, readReceiptRequested, id, picked);
			    
			    onFriendPickerReceived(friendPickerMessage);
				break;
				
			}		    
		    
	    }
		
	}

}
