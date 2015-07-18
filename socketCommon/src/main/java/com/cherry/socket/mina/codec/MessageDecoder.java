package com.cherry.socket.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.Constans;
import com.cherry.socket.common.bean.SocketFrame;

public class MessageDecoder extends AbstractDecoder{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public MessageDecoder() {
		super();
	}
	
	public MessageDecoder(Charset charset) {
		super(charset);
	}
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buff,ProtocolDecoderOutput out) throws Exception {
		//logger.debug("MessageDecoder doDecode beginTime:"+System.currentTimeMillis());
		if(buff.remaining()<Constans.FRAME_MIN_LENGTH){
			logger.error("MessageDecoder doDecode buff.remaining()<Constans.FRAME_MIN_LENGTH endTime:"+System.currentTimeMillis());
			buff.reset();  
			return false;
		}else{
			//标记当前position的快照标记mark，以便后继的reset操作能恢复position位置
            buff.mark(); 
            byte head = buff.get();
			if(head == Constans.FRAME_HEAD){
				long frameNo = buff.getLong();
				boolean isHearBeat = (buff.get()!= 0);
				int appNumber = buff.getInt();
				byte extField = buff.get();
				int bodyLength = buff.getInt();
				if(bodyLength > buff.remaining()){
					 buff.reset();  
				     return false;//接收新数据，以拼凑成完整数据  
				}else{
					byte[] frameBody = new byte[bodyLength];
					buff.get(frameBody, 0, bodyLength);
					SocketFrame frame = new SocketFrame(appNumber,frameNo);
					frame.setHearBeat(isHearBeat);
					frame.setExtField(extField);
					frame.setFrameBody(frameBody);
					frame.buildFrameBytes();
					//logger.debug("MessageDecoder doDecode frameNo:"+frameNo+" endTime:"+System.currentTimeMillis());
					out.write(frame);
					return true;
				}
			}
		}
		return false;
	}
}
