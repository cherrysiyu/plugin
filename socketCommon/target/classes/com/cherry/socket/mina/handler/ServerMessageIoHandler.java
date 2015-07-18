package com.cherry.socket.mina.handler;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;
import com.cherry.socket.mina.client.IoSessionMapping;
import com.cherry.socket.mina.server.MessageProcessThread;
/**
 * 
 * @author:Cherry
 * @date:2014-6-28
 * @version:1.0
 */
public class ServerMessageIoHandler  extends IoHandlerAdapter{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ThreadPoolExecutor threadPool;
	private MessageListener messageListener;
	
	public ServerMessageIoHandler(ThreadPoolExecutor threadPool,MessageListener messageListener) {
		super();
		if(threadPool == null)
			throw new NullPointerException(" threadPool is null");
		this.threadPool = threadPool;
		if(messageListener == null)
			throw new NullPointerException(" messageListener is null");
		this.messageListener = messageListener;
	}
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
		long currentTimeMillis = System.currentTimeMillis();
		SocketFrame frame	= (SocketFrame)message;
		if(!frame.isHearBeat()){
			frame.setHearBeat(false);
			logger.debug("ServerMessageIoHandler 收到客户端消息 FrameNo:"+frame.getFrameNo()+" time:"+currentTimeMillis);
			threadPool.submit(new MessageProcessThread(messageListener,frame, session));
		}else{
			frame.setHearBeat(true);
			String msg = "server received client hearbeat success,appNumber:"+frame.getAppNumber()+"session: "+session;
			//这里其实可以写一些有用的信息，比如说服务器端的负载等等
			frame.setFrameBody(msg.getBytes());
			frame.buildFrameBytes();
			session.write(frame);
		}
	}


}
