package com.cherry.socket.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

public abstract class AbstractEncoder extends ProtocolEncoderAdapter{
	protected  Charset charset;
	
	public AbstractEncoder(){
		super();
		this.charset=Charset.forName("UTF-8");
	}
	public AbstractEncoder(Charset charset) {
		super();
		if(charset != null)
			this.charset = charset;
		else
			this.charset=Charset.forName("UTF-8");
	}
	
	

}
