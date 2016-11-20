package nl.marksnijder.jkik;

import lombok.Data;

public @Data class KikSettings {
	
	private final boolean manuallySendReadReceipts;
	private final boolean receiveReadReceipts;
	private final boolean receiveDeliveryReceipts;
	private final boolean receiveIsTyping;

}
