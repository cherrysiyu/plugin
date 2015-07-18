package com.cherry.socket.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.manager.DataManager;

@Sharable
public class ClientMessageHandler extends ChannelInboundHandlerAdapter{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message)throws Exception {
		//logger.info("ClientMessageHandler channelRead:"+ctx.channel());
		SocketFrame frame	= (SocketFrame)message;
		if(!frame.isHearBeat()){
			logger.debug("ClientMessageHandler 收到消息,frameNo:"+frame.getFrameNo()+" time:"+System.currentTimeMillis());
			DataManager.receivedMessage(frame);
		}else{
			logger.info("client received hearbeat message:"+new String(frame.getFrameBody()));
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("ClientMessageHandler there is a session registered,ip:" + ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("ClientMessageHandler there is a session unregistered,ip:" + ctx.channel());
		if (ctx.channel().isActive()) {
			ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("ClientMessageHandler there is a session active,ip:" + ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("ClientMessageHandler there is a session inactive,ip:" + ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		logger.error("ClientMessageHandler there is a session exceptionCaught,ip:" + ctx.channel(),cause);
	}

	
}
