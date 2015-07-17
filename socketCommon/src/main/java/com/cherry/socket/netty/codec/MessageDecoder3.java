package com.cherry.socket.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.SocketFrame;

public class MessageDecoder3 extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,List<Object> out) throws Exception {
		
		 // Wait until the length prefix is available.
        if (in.readableBytes() < Constans.FRAME_MIN_LENGTH) {
        	return;
        }
        in.markReaderIndex();
        
        byte head = in.readByte();
        if(head == Constans.FRAME_HEAD){
        	long frameNo = in.readLong();
    		boolean isHearBeat = in.readBoolean();
    		int appNumber = in.readInt();
    		byte extField = in.readByte();
    		int bodyLength = in.readInt();
    		if (in.readableBytes() < bodyLength) {
    			in.resetReaderIndex();
    	        //如果消息内容不够，则重置，相当于不读取size  
    	         return ;
    	     }
    		byte[] frameBody = in.readBytes(bodyLength).array();
    		SocketFrame socketFrame = new SocketFrame(appNumber,frameNo);
    		socketFrame.setHearBeat(isHearBeat);
    		socketFrame.setExtField(extField);
    		socketFrame.setFrameBody(frameBody);
    		socketFrame.buildFrameBytes();
    		out.add(socketFrame);
        }else{
        	 in.resetReaderIndex();
             return;
        }
		
	}

}
