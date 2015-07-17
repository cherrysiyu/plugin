package com.cherry.socket.mina.heartbeat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.MinaSessionAttribute;
import com.cherry.socket.common.bean.SocketFrame;

/**
 * 客户端心跳维护，客户端主动发送心跳
 * @author:Cherry
 * @date:2014-6-28
 * @version:1.0
 */
public class ClientKeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//是否收到回应报文
	/*
	 * 当且仅当返回的是心跳回应消息时返回true
	 * (non-Javadoc)
	 * @see org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public boolean isResponse(IoSession session, Object message) {
		if (message instanceof SocketFrame) {
			SocketFrame socketFrame = (SocketFrame) message;
			if (socketFrame.isHearBeat()) {
				if (!socketFrame.isHearBeatSaved()) {
					socketFrame.setHearBeatSaved(true);
					socketFrame.buildFrameBytes();
					logger.info("客户端收到心跳报文" + message.toString()+"  frameNo:"+socketFrame.getFrameNo());
				}
				return true;
			}
		}
		return false;
	}
	
	//主动发送报文的
	/* Returns a (new) keep-alive request message.
     * Returns <tt>null</tt> if no request is required.
     * 返回一条请求报文信息，如果不需要请求报文信息则返回null
	 * (non-Javadoc)
	 * @see org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public Object getRequest(IoSession session) {
		SocketFrame frame = new SocketFrame(((MinaSessionAttribute)session.getAttribute(Constans.SESSIONATTR_KEY)).getAppNumber());
		frame.setHearBeat(true);
		frame.buildFrameBytes();
		logger.info("客户端主动发送心跳报文：" + frame.toString()+"  frameNo:"+frame.getFrameNo());
		return frame;
	}

	
	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Object getResponse(IoSession session, Object request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
