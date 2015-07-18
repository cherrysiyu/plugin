package com.cherry.spring.plugin.redis.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * 
 优先使用Hession序列化，因为Hession序列化是需要实现java的序列化接口，如果没有序列化则报错，为了解决此问题引入了kyro
 * 
 * <p>
 * Copyright: Copyright (c) 2014年11月21日 上午11:46:28
 * <p>
 * 
 * <p>
 * 
 * @author Cherry
 * @version 1.0.0
 */
public class HessionRedisSerializer implements RedisSerializer<Object>{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private KryoRedisSerializer serializer = new KryoRedisSerializer();
	
	@Override
	public byte[] serialize(Object object) throws SerializationException {
		byte[] result = null;
		ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
		Hessian2Output out = new Hessian2Output(byteArrayOutputStream); 
		try {
			out.writeObject(object);
			out.flush();
			out.close();
			result = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			logger.error("Hession serialize error,will use KryoRedisSerializer to serialize",e);
			result = serializer.serialize(object);
		}
		return result;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		 if(bytes == null || bytes.length == 0)
		        return null;
		Object obj=null;
		ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(bytes);
		Hessian2Input in = new Hessian2Input(byteArrayInputStream);  
		try {
			obj = in.readObject();
		} catch (IOException e) {
			logger.error("Hession deserialize error,will use KryoRedisSerializer to deserialize",e);
			obj = serializer.deserialize(bytes);
		}
		return obj;
	}
	
	

}
