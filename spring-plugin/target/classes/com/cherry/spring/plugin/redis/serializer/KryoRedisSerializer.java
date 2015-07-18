package com.cherry.spring.plugin.redis.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
/**
 * 
 Kryo序列化不需要写入的东西实现java的Serializable接口即可，序列化速度比Hession慢一些，但是反序列比Hession的性能好一些
 * 
 * <p>
 * Copyright: Copyright (c) 2014年11月14日 下午5:09:41
 * <p>
 * 
 * <p>
 * 
 * @author Cherrya@c-platform.com
 * @version 1.0.0
 */
public class KryoRedisSerializer implements RedisSerializer<Object> {
	private  Kryo kryo = new Kryo();
	public KryoRedisSerializer(){
		kryo.addDefaultSerializer(Timestamp.class, TimestampSerializer.class);
	}
	
	public  void registerObject(Class<?> t) {
		kryo.register(t);
	}
	@Override
	public byte[] serialize(Object t) throws SerializationException {
		Output output = null;
		byte[] result = null;
	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        output = new Output(baos);
	        kryo.writeClassAndObject(output, t);
	        output.flush();
	        result =  baos.toByteArray();
	    }finally{
	        if(output != null)
	            output.close();
	    }
	    return result;
	}
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		 if(bytes == null || bytes.length == 0)
		        return null;
		 	Object obj=null;
		    Input ois = null;
		    try {
		        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		        ois = new Input(bais);
		        obj = kryo.readClassAndObject(ois);
		    }finally {
		        if(ois != null)
		            ois.close();
		    }
		   return obj; 
	}
	
}
