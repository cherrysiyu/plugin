package com.cherry.socket.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.cherry.socket.common.bean.SocketFrame;


@Sharable
public class MessageEncoder extends MessageToByteEncoder<SocketFrame>{

	@Override
	protected void encode(ChannelHandlerContext ctx, SocketFrame msg,ByteBuf out) throws Exception {
		//out.writeBytes(wrappedBuffer(msg.getFrameBytes()));
		out.writeBytes(msg.getFrameBytes());
	}

}
