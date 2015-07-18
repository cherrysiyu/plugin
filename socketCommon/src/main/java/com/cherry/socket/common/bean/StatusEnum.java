package com.cherry.socket.common.bean;

public enum StatusEnum {
	/**
	 * 正确返回
	 */
	CORRECT(1),
	/**
	 * 不在线
	 */
	NOTONLINE(2),
	/**
	 * 超时
	 */
	TIMEOUT(3);
	
	private int status;

	 StatusEnum(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
