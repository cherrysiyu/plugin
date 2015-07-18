package com.cherry.utils.cache.memcached;

import com.cherry.utils.cache.CacheWrapper;
import com.cherry.utils.serializer.KryoSerializerUtils;
import com.danga.MemCached.MemCachedClient;

public class MemcachedCache<K,V> implements CacheWrapper<K, V> {
	
	private  MemCachedClient memCachedClient = MemcachedConf.getMemCachedClient();
	private String cacheName;
	private int hash;
	
	
	public MemcachedCache(String name) {
		 this.cacheName = name + '_';
	     this.hash = name.hashCode();   
	}

	@Override
	public boolean deleteCache(K key) {
		return memCachedClient.delete(makeupKey(key));
	}

	@Override
	public V getCache(K key) {
		Object object = memCachedClient.get(makeupKey(key),hash);
		return object == null?null:deserializeValue(object);
	}

	@Override
	public void putCache(K key, V value) {
		memCachedClient.set(makeupKey(key), KryoSerializerUtils.serialize(value), hash);
	}

	@Override
	public void removeAllCaches() {
		memCachedClient.flushAll();       
	}

	@Override
	public void updateCache(K key, V value) {
		memCachedClient.replace(makeupKey(key), KryoSerializerUtils.serialize(value),hash);
	}

	private  String makeupKey(Object key){
    	if(key instanceof Number)
    		return cacheName + key;
    	return cacheName + key.toString().hashCode();
    }
	
	@SuppressWarnings("unchecked")
	public V deserializeValue(Object object){
		if(object instanceof byte[])
			return (V)KryoSerializerUtils.deserialize((byte[])object);
		else
			return (V)object;
	}
	
}
