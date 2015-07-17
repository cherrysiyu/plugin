package com.cherry.socket.mina.handler;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.manager.DataManager;
import com.cherry.socket.mina.client.IoSessionMapping;

public class ClientMessageIoHandler  extends IoHandlerAdapter{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("there is a session created,ip：" + session.getRemoteAddress().toString());
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)throws Exception {
		logger.info("session idle,State:" + status.toString());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)throws Exception {
		logger.error("there is a session exception,cause:",cause);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		//logger.debug("message sent,message:" + message.toString());
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("there is a session opened,ip:" + session.getRemoteAddress().toString());
	}
	@Override
	public void sessionClosed(final IoSession session) throws Exception {
		logger.info("there is a session closed,ip:" + session.getRemoteAddress().toString());
        CloseFuture future = session.close(true);
        future.addListener(new IoFutureListener<IoFuture>(){
            public void operationComplete(IoFuture future){
                if(future instanceof CloseFuture){
                    ((CloseFuture)future).setClosed();
                    IoSessionMapping.removeSession(session);
                }
            }
        });
	}

	@Override
	public void messageReceived(IoSession session, Object message)throws Exception {
		SocketFrame frame = (SocketFrame)message;
		if(!frame.isHearBeat()){
			logger.debug("ClientMessageIoHandler 收到消息,frameNo:"+frame.getFrameNo()+" time:"+System.currentTimeMillis());
			DataManager.receivedMessage((SocketFrame)message);
		}else{
			logger.info("client received hearbeat message:"+new String(frame.getFrameBody()));
		}
	}
}
