package com.cherry.utils.cache.ehcache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.cherry.utils.LogUtils;
import com.cherry.utils.cache.CacheWrapper;
import com.cherry.utils.serializer.KryoSerializerUtils;

public class EhCacheWrapper<K,V> implements CacheWrapper<K, V> {

	private  Ehcache cache;
	public EhCacheWrapper(Ehcache cache) {
		super();
		this.cache = cache;
	}

	public Ehcache getEhCacheObject() {
		return cache;
	}

	@Override
	public synchronized   boolean deleteCache(K key) {
		return cache.remove(KryoSerializerUtils.serialize(key));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public  V getCache(K key) {
		
		Element element = cache.get(KryoSerializerUtils.serialize(key));
		if(element != null){
			try{
				return deserializeValue(element.getObjectValue());
			}catch (RuntimeException e) {
				LogUtils.info("获取缓存异常key="+key);
				return deserializeValue(element.getValue());
			}
		}
		return null;
	}

	@Override
	public synchronized void putCache(K key, V value) {
		cache.put(new Element(KryoSerializerUtils.serialize(key),KryoSerializerUtils.serialize(value)));
	}

	@Override
	public synchronized void removeAllCaches() {
		cache.removeAll();
	}

	@Override
	public synchronized void updateCache(K key, V value) {
		cache.put(new Element(KryoSerializerUtils.serialize(key),KryoSerializerUtils.serialize(value)));
	}
	
	@SuppressWarnings("unchecked")
	private V deserializeValue(Object object){
		if(object instanceof byte[])
			return (V)KryoSerializerUtils.deserialize((byte[])object);
		else
			return (V)object;
	} 

}
