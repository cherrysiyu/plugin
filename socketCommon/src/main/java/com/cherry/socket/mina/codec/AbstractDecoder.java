package com.cherry.socket.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.CumulativeProtocolDecoder;

public abstract class AbstractDecoder extends CumulativeProtocolDecoder{
	
	protected  Charset charset;
	
	public AbstractDecoder(){
		super();
		this.charset=Charset.forName("UTF-8");
	}
	public AbstractDecoder(Charset charset) {
		super();
		if(charset != null)
			this.charset = charset;
		else
			this.charset=Charset.forName("UTF-8");
	}
	
	

}
