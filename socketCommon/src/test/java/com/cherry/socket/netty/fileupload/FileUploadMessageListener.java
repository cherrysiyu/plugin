package com.cherry.socket.netty.fileupload;

import io.netty.util.CharsetUtil;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.cherry.socket.common.bean.SocketFrame;
import com.cherry.socket.common.listener.MessageListener;
import com.cherry.socket.utils.ByteBufferReader;

public class FileUploadMessageListener implements MessageListener{

	@Override
	public byte[] onMessage(SocketFrame requestSocketFrame) {
		ByteBufferReader reader = new ByteBufferReader(requestSocketFrame.getFrameBody());
		String destFilePath = reader.readIntString();
		byte[] array = reader.readBytes(reader.readableBytes());
		BufferedOutputStream bos = null;
		try {
			 bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
			 bos.write(array);
			 bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ("upload file "+destFilePath +"-----success").getBytes(CharsetUtil.UTF_8);
	}

}
