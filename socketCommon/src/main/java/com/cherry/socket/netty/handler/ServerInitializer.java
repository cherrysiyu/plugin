package com.cherry.socket.netty.handler;

import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import com.cherry.socket.netty.codec.MessageDecoder;
import com.cherry.socket.netty.codec.MessageEncoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private ChannelInboundHandler serverMessageHandler;

	public ServerInitializer(ChannelInboundHandler serverMessageHandler) {
		super();
		if(serverMessageHandler == null)
			throw new NullPointerException("serverMessageHandler is null");
		this.serverMessageHandler = serverMessageHandler;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast("encoder",new MessageEncoder());
        pipeline.addLast("decoder",new MessageDecoder());
		pipeline.addLast(serverMessageHandler);
	}

}
