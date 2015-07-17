package com.cherry.socket.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;

public class MessageEncoder extends AbstractEncoder{
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public MessageEncoder() {
		super();
	}
	
	public MessageEncoder(Charset charset) {
		super(charset);
	}
	
	@Override
	public void encode(IoSession session, Object message,ProtocolEncoderOutput out) throws Exception {
		SocketFrame socketFrame = (SocketFrame)message;
		//logger.debug("MessageEncoder encode FrameNo:"+socketFrame.getFrameNo()+" time:"+System.currentTimeMillis());
		out.write(IoBuffer.wrap(socketFrame.getFrameBytes()));
	}

}
