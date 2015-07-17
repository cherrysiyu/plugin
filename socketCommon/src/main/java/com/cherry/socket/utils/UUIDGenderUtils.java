package com.cherry.socket.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDGenderUtils {
	
	private static AtomicLong seq = new AtomicLong();
	
	
	public static  long getLongSeq(){
		long incrementAndGet = seq.incrementAndGet();
		if(incrementAndGet >= (Long.MAX_VALUE - 100000) )
			seq.set(0);
		return incrementAndGet;
	}
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
