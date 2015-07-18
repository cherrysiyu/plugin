package com.cherry.socket.mina.echo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;

public class EchoMessageListener implements MessageListener{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public byte[] onMessage(SocketFrame requestSocketFrame) {
		byte[] frameBody = requestSocketFrame.getFrameBody();
		logger.info("服务器端收到客户端消息:"+new String(frameBody));
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()).getBytes();
	}

}
