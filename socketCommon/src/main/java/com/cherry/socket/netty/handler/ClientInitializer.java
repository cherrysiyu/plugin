package com.cherry.socket.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;

import com.cherry.socket.netty.codec.MessageDecoder;
import com.cherry.socket.netty.codec.MessageEncoder;

public class ClientInitializer extends ChannelInitializer<SocketChannel>{

	private ChannelInboundHandler clientMessageHandler;
	private Map<String,ChannelHandler> handlers;
	public ClientInitializer(ChannelInboundHandler clientMessageHandler,Map<String,ChannelHandler> handlers) {
		super();
		if(clientMessageHandler == null)
			throw new NullPointerException("clientMessageHandler is null");
		this.clientMessageHandler = clientMessageHandler;
		this.handlers=handlers;
	}


	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if(handlers != null){
			for (Map.Entry<String, ChannelHandler> entry : pipeline) {
				pipeline.addLast(entry.getKey(),entry.getValue());
			}
		}
		pipeline.addLast("encoder",new MessageEncoder());
        pipeline.addLast("decoder",new MessageDecoder());
        pipeline.addLast(clientMessageHandler);
	}

}
