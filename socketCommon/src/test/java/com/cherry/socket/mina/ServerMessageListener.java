package com.cherry.socket.mina;
import org.apache.commons.lang.ArrayUtils;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;


public class ServerMessageListener implements MessageListener{
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//private AtomicLong seq = new AtomicLong();
	
	@Override
	public byte[] onMessage(SocketFrame requestSocketFrame) {
		//long beginTime = System.currentTimeMillis();
		//long incrementAndGet = seq.incrementAndGet();
		byte[] frameBody = requestSocketFrame.getFrameBody();
		ArrayUtils.reverse(frameBody);
		requestSocketFrame.setFrameBody(frameBody);
		//logger.debug("--- cost time " +(System.currentTimeMillis()-beginTime)+"ms");
		return frameBody;
	}
}
