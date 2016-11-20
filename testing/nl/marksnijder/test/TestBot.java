package nl.marksnijder.test;

import java.io.File;

import nl.marksnijder.jkik.KikApi;
import nl.marksnijder.jkik.KikBot;
import nl.marksnijder.jkik.MessageSender;
import nl.marksnijder.jkik.keyboard.FriendPicker;
import nl.marksnijder.jkik.keyboard.Keyboard;
import nl.marksnijder.jkik.keyboard.TextButton;
import nl.marksnijder.jkik.message.FriendPickerMessage;
import nl.marksnijder.jkik.message.IsTypingMessage;
import nl.marksnijder.jkik.message.LinkMessage;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.PictureMessage;
import nl.marksnijder.jkik.message.ScanDataMessage;
import nl.marksnijder.jkik.message.Sendable;
import nl.marksnijder.jkik.message.StickerMessage;
import nl.marksnijder.jkik.message.TextMessage;
import nl.marksnijder.jkik.message.VideoMessage;

public class TestBot extends KikBot {
	
	@Override
	public void onTextReceived(TextMessage msg) {
		System.out.println("text");
		Keyboard keyboard = new Keyboard(/*(new TextButton("button1"), new TextButton("button2"), */new FriendPicker("Choose 2-4 Friends", 2, 4)/**/);
//		keyboard.setTo(msg.getChat().getFrom());
//		keyboard.setHidden(true);
			MessageSender.sendMessage((Sendable)new TextMessage("This is a test msg", msg.getChat().getFrom(), msg.getChat().getChatId()).setKeyboard(keyboard));
	}

	@Override
	public void onLinkReceived(LinkMessage msg) {
		System.out.println("link");
		
	}

	@Override
	public void onPictureReceived(PictureMessage msg) {
		MessageSender.sendMessage(new PictureMessage(getApi(), new File("pic.png"), msg.getChat().getFrom(), msg.getChat().getChatId()));
//			MessageSender.sendMessage(new PictureMessage("https://picturethismaths.files.wordpress.com/2016/03/fig6bigforblog.png?w=419&h=364", msg.getChat().getFrom(), msg.getChat().getChatId()));
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
