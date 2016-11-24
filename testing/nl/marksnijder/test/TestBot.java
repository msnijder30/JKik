package nl.marksnijder.test;

import java.io.File;

import nl.marksnijder.jkik.KikBot;
import nl.marksnijder.jkik.MessageSender;
import nl.marksnijder.jkik.message.FriendPickerMessage;
import nl.marksnijder.jkik.message.IsTypingMessage;
import nl.marksnijder.jkik.message.LinkMessage;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.MessageAttribute;
import nl.marksnijder.jkik.message.PictureMessage;
import nl.marksnijder.jkik.message.ScanDataMessage;
import nl.marksnijder.jkik.message.StickerMessage;
import nl.marksnijder.jkik.message.TextMessage;
import nl.marksnijder.jkik.message.VideoMessage;

public class TestBot extends KikBot {
	
	@Override
	public void onTextReceived(TextMessage msg) {
		
		MessageSender.sendMessages(new TextMessage("hey", msg.getChat().getFrom(), msg.getChat().getChatId()));
	}

	@Override
	public void onLinkReceived(LinkMessage msg) {
		System.out.println("link");
		
	}

	@Override
	public void onPictureReceived(PictureMessage msg) {
		MessageSender.sendMessages(new PictureMessage(getApi(), new File("pic.png"), msg.getChat().getFrom(), msg.getChat().getChatId(), new MessageAttribute("http://s.imgur.com/images/favicon-96x96.png", null, null)));
	}

	@Override
	public void onVideoReceived(VideoMessage msg) {
		System.out.println("video");
		
	}

	@Override
	public void onStartChattingReceived(Message msg) {
		System.out.println("startchatting");
		
	}

	@Override
	public void onScanDataReceived(ScanDataMessage msg) {
		System.out.println("scandata");
		
	}

	@Override
	public void onStickerReceived(StickerMessage msg) {
		System.out.println("sticker");
		
	}

	@Override
	public void onDeliveryReceiptReceived(Message msg) {
		System.out.println("deliveryreceipt");
		
	}

	@Override
	public void onReadReceiptReceived(Message msg) {
		System.out.println("readreceipt");
		
	}

	@Override
	public void onFriendPickerReceived(FriendPickerMessage msg) {
		System.out.println("friend");
		
	}

	@Override
	public void onIsTypingReceived(IsTypingMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
