package com.cherry.utils.serializer;

import java.util.concurrent.Callable;

public class KryoThread implements Callable<Long> {

	private Object obj;

	public KryoThread(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public Long call() throws Exception {
		long nanoTime = System.nanoTime();
		for (int i = 0; i < 50; i++) {
			byte[] serialize = KryoSerializerUtils.serialize(obj);
			KryoSerializerUtils.deserialize(serialize);
		}
		return (System.nanoTime() - nanoTime) / 1000;
	}

}
