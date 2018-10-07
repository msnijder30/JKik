package com.github.msnijder30.jkik.testincoming;

import com.github.msnijder30.jkik.KikBot;
import com.github.msnijder30.jkik.message.FriendPickerMessage;
import com.github.msnijder30.jkik.message.IsTypingMessage;
import com.github.msnijder30.jkik.message.LinkMessage;
import com.github.msnijder30.jkik.message.Message;
import com.github.msnijder30.jkik.message.PictureMessage;
import com.github.msnijder30.jkik.message.ScanDataMessage;
import com.github.msnijder30.jkik.message.StickerMessage;
import com.github.msnijder30.jkik.message.TextMessage;
import com.github.msnijder30.jkik.message.VideoMessage;
import com.github.msnijder30.jkik.receipt.DeliveryReceipt;
import com.github.msnijder30.jkik.receipt.ReadReceipt;

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
