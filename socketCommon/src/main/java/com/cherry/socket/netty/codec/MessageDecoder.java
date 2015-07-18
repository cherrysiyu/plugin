package com.cherry.socket.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.SocketFrame;

public class MessageDecoder extends LengthFieldBasedFrameDecoder{
	
	public MessageDecoder() {
        this(1048576);
    }
	public MessageDecoder(int maxObjectSize) {
        super(maxObjectSize, Constans.lengthFieldOffset, Constans.lengthFieldLength, 0, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        if (frame.readableBytes() < Constans.FRAME_MIN_LENGTH) {
        	frame.resetReaderIndex();
        	return null;
        }
        in.markReaderIndex();
        byte head = frame.readByte();
        if(head == Constans.FRAME_HEAD){
        	long frameNo = frame.readLong();
    		boolean isHearBeat = frame.readBoolean();
    		int appNumber = frame.readInt();
    		byte extField = frame.readByte();
    		int bodyLength = frame.readInt();
    		if (frame.readableBytes() < bodyLength) {
    	        //如果消息内容不够，则重置，相当于不读取size  
    			 frame.resetReaderIndex();
    	         return null;
    	     }
    		byte[] frameBody = frame.readBytes(bodyLength).array();
    		SocketFrame socketFrame = new SocketFrame(appNumber,frameNo);
    		socketFrame.setHearBeat(isHearBeat);
    		socketFrame.setExtField(extField);
    		socketFrame.setFrameBody(frameBody);
    		socketFrame.buildFrameBytes();
    		frame.release();
            return socketFrame;
        }else{
        	frame.resetReaderIndex();
        	 return null;
        }
		
    }
}
