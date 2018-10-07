package nl.marksnijder.testincoming;

import nl.marksnijder.jkik.KikBot;
import nl.marksnijder.jkik.message.FriendPickerMessage;
import nl.marksnijder.jkik.message.IsTypingMessage;
import nl.marksnijder.jkik.message.LinkMessage;
import nl.marksnijder.jkik.message.Message;
import nl.marksnijder.jkik.message.PictureMessage;
import nl.marksnijder.jkik.message.ScanDataMessage;
import nl.marksnijder.jkik.message.StickerMessage;
import nl.marksnijder.jkik.message.TextMessage;
import nl.marksnijder.jkik.message.VideoMessage;
import nl.marksnijder.jkik.receipt.DeliveryReceipt;
import nl.marksnijder.jkik.receipt.ReadReceipt;

public class TestBot extends KikBot {
	
	@Override
	public void onTextReceived(TextMessage msg) {
		
	}

	@Override
	public void onLinkReceived(LinkMessage msg) {
		
	}

	@Override
	public void onPictureReceived(PictureMessage msg) {
		
	}

	@Override
	public void onVideoReceived(VideoMessage msg) {
		
	}

	@Override
	public void onStartChattingReceived(Message msg) {
		
	}

	@Override
	public void onScanDataReceived(ScanDataMessage msg) {
		
	}

	@Override
	public void onStickerReceived(StickerMessage msg) {
		
	}

	@Override
	public void onDeliveryReceiptReceived(DeliveryReceipt msg) {
		
	}

	@Override
	public void onReadReceiptReceived(ReadReceipt msg) {
		
	}

	@Override
	public void onFriendPickerReceived(FriendPickerMessage msg) {
		
	}

	@Override
	public void onIsTypingReceived(IsTypingMessage msg) {
		
	}

}
