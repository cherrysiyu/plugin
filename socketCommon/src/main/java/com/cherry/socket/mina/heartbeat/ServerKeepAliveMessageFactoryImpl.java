package com.cherry.socket.mina.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;

/**
 * 服务器端心跳维护，服务器端只对心跳应答，不主动发送心跳
 * @author:Cherry
 * @date:2014-6-28
 * @version:1.0
 */
public class ServerKeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * Returns <tt>true</tt> if and only if the specified message is a
     * keep-alive request message.
     * 当且仅当收到的是心跳请求报文时返回true
	 * (non-Javadoc)
	 * @see org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isRequest(IoSession session, Object message) {
		if(message instanceof SocketFrame){
			SocketFrame socketFrame = (SocketFrame) message;
			if (socketFrame.isHearBeat()) {
				if (!socketFrame.isHearBeatSaved()) {
					socketFrame.setHearBeatSaved(true);
					socketFrame.buildFrameBytes();
					logger.info("服务器端收到心跳报文:" + socketFrame.toString()+ ",地址:" + session.toString()+"  frameNo:"+socketFrame.getFrameNo());
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * Returns a (new) response message for the specified keep-alive request.
     * Returns <tt>null</tt> if no response is required.
     * 返回一条应答报文
	 * (non-Javadoc)
	 * @see org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public Object getResponse(IoSession session, Object request) {
		if(request instanceof SocketFrame){
			SocketFrame message = (SocketFrame)request;
			SocketFrame frame = new SocketFrame(message.getAppNumber(), message.getFrameNo());
			frame.setHearBeat(message.isHearBeat());
			frame.setExtField(message.getExtField());
			frame.setFrameBody(frame.getFrameBody());
			frame.buildFrameBytes();
			logger.info("服务器发送应答心跳报文：" + frame.toString()+",客户端地址:"+session.toString()+"  frameNo:"+frame.getFrameNo());
			return frame;
		}
		return null;
	}
	
	
	
	
	@Override
	public boolean isResponse(IoSession session, Object message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		return null;
	}

}
