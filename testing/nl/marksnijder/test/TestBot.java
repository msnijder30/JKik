package nl.marksnijder.test;

import nl.marksnijder.jkik.KikBot;
import nl.marksnijder.jkik.MessageSender;
import nl.marksnijder.jkik.messages.IsTypingMessage;
import nl.marksnijder.jkik.messages.Message;
import nl.marksnijder.jkik.messages.PictureMessage;
import nl.marksnijder.jkik.messages.TextMessage;
import nl.marksnijder.jkik.messages.VideoMessage;

public class TestBot extends KikBot {

	@Override
	public void onTextReceived(TextMessage msg) {
		System.out.println("text");
		MessageSender.sendMessage(new TextMessage("This is a test msg", msg.getChat().getFrom(), msg.getChat().getChatId()));
	}

	@Override
	public void onLinkReceived(Message msg) {
		System.out.println("link");
		
	}

	@Override
	public void onPictureReceived(PictureMessage msg) {
		System.out.println("picture");
		
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
	public void onScanDataReceived(Message msg) {
		System.out.println("scandata");
		
	}

	@Override
	public void onStickerReceived(Message msg) {
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
	public void onFriendPickerReceived(Message msg) {
		System.out.println("friend");
		
	}

	@Override
	public void onIsTypingReceived(IsTypingMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
