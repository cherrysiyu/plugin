package com.cherry.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {

	/**
	 * 将序列化后的byte数组反序列化为对应类
	 * @param data
	 * @return
	 */
	public static Object serialBytesToObject(byte[] data) {
		if(data != null && data.length > 0){
			ByteArrayInputStream bin = new ByteArrayInputStream(data);
			try {
				ObjectInputStream obin = new ObjectInputStream(bin);
				Object obj = obin.readObject();
				return obj;
			} catch (Exception e) {
				LogUtils.error("serialByteToObject", e);
			}
		}
		return null;
	}

	/**
	 * 将对象序列化为byte数组
	 * @param o
	 * @return
	 */
	public static byte[] serialObjectToBytes(Object o) {
		if(o != null){
			ByteArrayOutputStream obj = new ByteArrayOutputStream();
			try {
				ObjectOutputStream out = new ObjectOutputStream(obj);
				out.writeObject(o);
				return obj.toByteArray();
			} catch (Exception e) {
				LogUtils.error("serialObjectToBytes", e);
			}
		}
		return null;
	}
}
