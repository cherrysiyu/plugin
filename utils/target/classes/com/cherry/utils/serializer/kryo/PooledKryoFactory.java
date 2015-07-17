package com.cherry.utils.serializer.kryo;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.SubListSerializers;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

final class PooledKryoFactory extends BasePooledObjectFactory<Kryo> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static AtomicInteger count = new AtomicInteger();
	
	
	@Override
	public Kryo create() throws Exception {
		return createKryo();
	}
	
	@Override
	public void destroyObject(PooledObject<Kryo> p) throws Exception {
		logger.info("销毁一个Kryo对象，对象池中对象个数: "+count.decrementAndGet());
		super.destroyObject(p);
	}

	@Override
	public boolean validateObject(PooledObject<Kryo> p) {
		logger.debug("验证一个Kryo对象,对象状态 "+p.getState());
		return super.validateObject(p);
	}

	@Override
	public void activateObject(PooledObject<Kryo> p) throws Exception {
		logger.debug("激活一个Kryo对象,对象状态 "+p.getState());
		super.activateObject(p);
	}

	@Override
	public void passivateObject(PooledObject<Kryo> p) throws Exception {
		logger.debug("钝化一个Kryo对象,对象状态 "+p.getState());
		super.passivateObject(p);
	}

	@Override
	public PooledObject<Kryo> wrap(Kryo kryo) {
		return new DefaultPooledObject<Kryo>(kryo);
	}
	
	private Kryo createKryo() {
		Kryo kryo = new KryoReflectionFactorySupport() {
			
			@Override
			public Serializer<?> getDefaultSerializer(@SuppressWarnings("rawtypes") final Class clazz) {
				if (EnumMap.class.isAssignableFrom(clazz)) {
					return new EnumMapSerializer();
				}
				if (SubListSerializers.ArrayListSubListSerializer.canSerialize(clazz) || SubListSerializers.JavaUtilSubListSerializer.canSerialize(clazz)) {
					return SubListSerializers.createFor(clazz);
				}
				return super.getDefaultSerializer(clazz);
			}
		};
		kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
		UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		logger.info("创建一个Kryo对象成功，对象池中对象个数: "+count.incrementAndGet());
		return kryo;
	}
}
