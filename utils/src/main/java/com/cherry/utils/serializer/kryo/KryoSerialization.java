package com.cherry.utils.serializer.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public final class KryoSerialization {
	
	private  KyroFactory kyroFactory;
	
	public KryoSerialization(KyroFactory kyroFactory) {
		this.kyroFactory = kyroFactory;
	}
	
	public byte[] serialize(final Object obj){
		if(obj == null)
			return null;
		byte[] result = null;
		Output output = null;
		Kryo kryo = null;
		try{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			kryo = kyroFactory.getKryo();
			output = new Output(bos);
			kryo.writeClassAndObject(output, obj);
			output.flush();
			result = bos.toByteArray();
		}finally{
			if(kryo != null)
				kyroFactory.returnKryo(kryo);
			if(output != null)
				output.close();
		}
		return result;
	}
	
	public Object deserialize(byte[] bytes ) {
		if(bytes == null || bytes.length == 0)
	        return null;
		Kryo kryo = null;
		Object result = null;
		Input input = null;
		try{
			kryo = kyroFactory.getKryo();
			input = new Input(new ByteArrayInputStream(bytes));
			result = kryo.readClassAndObject(input);
			
		}finally{
			if(kryo != null)
				kyroFactory.returnKryo(kryo);
			if(input != null)
				input.close();
		}
		return result;
	}

	public void setKyroFactory(KyroFactory kyroFactory) {
		this.kyroFactory = kyroFactory;
	}
	
}
