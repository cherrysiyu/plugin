package com.cherry.utils.serializer;

import com.cherry.utils.serializer.kryo.KryoSerialization;
import com.cherry.utils.serializer.kryo.KyroFactory;

public final class KryoSerializerUtils {
	
	private KryoSerializerUtils(){
		
	}
	
	private static KyroFactory kyroFactory = new KyroFactory();
	private static KryoSerialization kryoSerialization = new KryoSerialization(kyroFactory);
	public static void createKyroFactory(final int maxTotal, final int minIdle, final long maxWaitMillis, final long minEvictableIdleTimeMillis){
		kyroFactory = new KyroFactory(maxTotal, minIdle, maxWaitMillis, minEvictableIdleTimeMillis);
		kryoSerialization.setKyroFactory(kyroFactory);
	}
	
	
	
	public static byte[] serialize(Object object) {
		return kryoSerialization.serialize(object);
	}
	
	public static Object deserialize(byte[] bytes){
		if(bytes == null || bytes.length == 0)
	        return null;
		return kryoSerialization.deserialize(bytes);
	}
	
	
	
	

}
