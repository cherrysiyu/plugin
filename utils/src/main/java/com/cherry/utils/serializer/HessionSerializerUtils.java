package com.cherry.utils.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * 
 优先使用Hession序列化，因为Hession序列化是需要实现java的序列化接口，如果没有序列化则报错，为了解决此问题引入了kyro
 * 
 * 此hession有一个bug，BigDecimal序列化和反序列会失效，都变成0，推荐使用kryo序列化，kryo序列化比Hessionession更小更快
 * @author Cherry
 * @version 1.0.0
 */
public class HessionSerializerUtils {
	private static Logger logger = LoggerFactory.getLogger(HessionSerializerUtils.class);
	
	public static byte[] serialize(Object object) {
		byte[] result = null;
		ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
		Hessian2Output out = new Hessian2Output(byteArrayOutputStream); 
		try {
			out.writeObject(object);
			out.flush();
			out.close();
			result = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			logger.error("Hession serialize error",e);
		}
		return result;
	}

	public static Object deserialize(byte[] bytes){
		 if(bytes == null || bytes.length == 0)
		        return null;
		Object obj=null;
		ByteArrayInputStream  byteArrayInputStream = new ByteArrayInputStream(bytes);
		Hessian2Input in = new Hessian2Input(byteArrayInputStream);  
		try {
			obj = in.readObject();
		} catch (IOException e) {
			logger.error("Hession deserialize error",e);
		}
		return obj;
	}
	
	

}
