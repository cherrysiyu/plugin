package com.cherry.socket.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;
import com.cherry.socket.netty.server.MessageProcessThread;

@Sharable
public class ServerMessageHandler extends ChannelInboundHandlerAdapter{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MessageListener messageListener;	
	
	public ServerMessageHandler(MessageListener messageListener) {
		if(messageListener == null)
			throw new NullPointerException(" messageListener is null");
		this.messageListener = messageListener;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message)throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		SocketFrame frame	= (SocketFrame)message;
		if(!frame.isHearBeat()){
			logger.debug("ServerMessageIoHandler 收到客户端消息 FrameNo:"+frame.getFrameNo()+" time:"+currentTimeMillis);
			if(ctx.executor().inEventLoop()){
				frame.setFrameBody(messageListener.onMessage(frame));
			}else{
				Future<byte[]> future = ctx.executor().submit(new MessageProcessThread(messageListener,frame));
				if(future != null){
					frame.setFrameBody(future.get());
				}
			}
		}else{
			frame.setHearBeat(true);
			String msg = "server received client hearbeat success,appNumber:"+frame.getAppNumber()+"channel: "+ctx.channel();
			//这里其实可以写一些有用的信息，比如说服务器端的负载等等
			frame.setFrameBody(msg.getBytes());
		}
		frame.buildFrameBytes();
		ctx.writeAndFlush(message);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("ServerMessageHandler there is a session registered,ip:" + ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("ServerMessageHandler there is a session unregistered,ip:" + ctx.channel());
		if (ctx.channel().isActive()) {
			ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("ServerMessageHandler there is a session active,ip:" + ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("ServerMessageHandler there is a session inactive,ip:" + ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		logger.error("ServerMessageHandler there is a session exceptionCaught,ip:" + ctx.channel(),cause);
	}

	
	
	
}
