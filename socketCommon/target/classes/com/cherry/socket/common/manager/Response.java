package com.cherry.socket.common.manager;

import com.cherry.socket.common.bean.SocketFrame;


public final class Response {

	private SocketFrame  frame;
	private boolean has;
	private long timeOut ;
	public Response(long timeOut) {
		super();
		this.timeOut = timeOut;
	}
	
	public synchronized SocketFrame take() {
		if(!has){
			try {
				wait(timeOut);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		has = false;
		notify();
		return frame;
	}
	public synchronized void put(SocketFrame frame) {
		
		if(has){
			try {
				wait(timeOut);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		has = true;
		this.frame = frame;
		notify();
	}
	 
}
