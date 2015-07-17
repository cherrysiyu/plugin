package com.cherry.spring.plugin.redis.serializer;

import java.sql.Timestamp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TimestampSerializer extends Serializer<Timestamp> {
	public void write(Kryo kryo, Output output, Timestamp object) {
		output.writeLong(object.getTime(), true);
	}

	public Timestamp read(Kryo kryo, Input input, Class<Timestamp> type) {
		return new Timestamp(input.readLong(true));
	}
	
	public Timestamp copy (Kryo kryo, Timestamp original) {
		return new Timestamp(original.getTime());
	}
}
