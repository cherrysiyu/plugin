package com.cherry.utils.cache.redis;

import redis.clients.jedis.Jedis;

import com.cherry.utils.cache.CacheWrapper;
import com.cherry.utils.serializer.KryoSerializerUtils;

public class RedisCache<K,V> implements CacheWrapper<K, V> {

	
	private int dbIndex;
	
	public RedisCache(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	
	public Jedis getRedisConnection(){
		return RedisUtils.getRedisConnection(dbIndex);
	}
	
	public void colseRedisConnection(Jedis connection){
		if(connection != null)
			RedisUtils.colseRedisConnection(connection);
	}
	
	/**
	 * 清除指定数据库的缓存数据
	 * @param dbIndex(0-15)之间
	 */
	public void clearDBCaches() {
		Jedis redisConnection = getRedisConnection();
		try{
			redisConnection.flushDB();
		}finally{
			colseRedisConnection(redisConnection);
		}
		
	}
	
	@Override
	public boolean deleteCache(K key) {
		Jedis redisConnection = getRedisConnection();
		try{
			redisConnection.del(redisConnection.get(KryoSerializerUtils.serialize(key)));
		}finally{
			colseRedisConnection(redisConnection);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getCache(K key) {
		Jedis redisConnection = getRedisConnection();
		try{
			return (V)KryoSerializerUtils.deserialize(redisConnection.get(KryoSerializerUtils.serialize(key)));
		}finally{
			colseRedisConnection(redisConnection);
		}
	}

	@Override
	public void putCache(K key, V value) {
		Jedis redisConnection = getRedisConnection();
		try{
			redisConnection.set(KryoSerializerUtils.serialize(key), KryoSerializerUtils.serialize(value));
		}finally{
			colseRedisConnection(redisConnection);
		}
		
	}

	@Override
	public void removeAllCaches() {
		Jedis redisConnection = getRedisConnection();
		try{
			redisConnection.flushDB();
		}finally{
			colseRedisConnection(redisConnection);
		}
		
	}

	@Override
	public void updateCache(K key, V value) {
		Jedis redisConnection = getRedisConnection();
		try{
			redisConnection.setnx(KryoSerializerUtils.serialize(key), KryoSerializerUtils.serialize(value));
		}finally{
			colseRedisConnection(redisConnection);
		}
	}
	

}
