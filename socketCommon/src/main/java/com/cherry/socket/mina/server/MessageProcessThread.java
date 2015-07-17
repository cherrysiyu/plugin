package com.cherry.socket.mina.server;

import org.apache.mina.core.session.IoSession;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;

public class MessageProcessThread implements Runnable {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private MessageListener messageListener;
	private SocketFrame requestSocketFrame;
	private IoSession session;
	public MessageProcessThread(MessageListener messageListener,SocketFrame requestSocketFrame, IoSession session) {
		super();
		this.messageListener = messageListener;
		this.requestSocketFrame = requestSocketFrame;
		this.session = session;
	}

	@Override
	public void run() {
		//long beginTime = System.currentTimeMillis();
		requestSocketFrame.setFrameBody(messageListener.onMessage(requestSocketFrame));
		requestSocketFrame.buildFrameBytes();
		//long beginTime2 = System.currentTimeMillis();
		session.write(requestSocketFrame);
		//logger.debug("FrameNo:"+onMessage.getFrameNo()+" 线程:"+Thread.currentThread().getName()+"-----服务端耗时："+(beginTime2-beginTime)+"ms-------像客户端session.write完耗时:"+(System.currentTimeMillis()-beginTime2)+"ms");
	}
}
