package com.cherry.socket.mina.codec;


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public   class CodecFactory implements ProtocolCodecFactory {
	
	protected  AbstractDecoder decoder;
	protected  AbstractEncoder encoder;
	
	public CodecFactory(AbstractEncoder encoder,AbstractDecoder decoder) {
		super();
		if(encoder == null)
			throw new NullPointerException("编码器不能为null");
		if(decoder == null)
			throw new NullPointerException("解码器不能为null");
		this.decoder = decoder;
		this.encoder = encoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
	

}
