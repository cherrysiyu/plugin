package com.cherry.socket.common.manager;

import com.cherry.socket.common.bean.StatusEnum;

public final class MessageResponse {
	
	private StatusEnum status = StatusEnum.CORRECT;
	
	private byte[] msgBody;

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public byte[] getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(byte[] msgBody) {
		this.msgBody = msgBody;
	}
	
	
	

}
