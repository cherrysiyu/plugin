package com.cherry.spring.plugin.redis;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.cherry.spring.plugin.redis.serializer.HessionRedisSerializer;

@Service
public class RedisClientWapper<K,V> {
	
	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	@PostConstruct
	public void init(){
		redisTemplate.setDefaultSerializer(new HessionRedisSerializer());
	}
	
	public RedisTemplate<K, V> getRedisTemplate() {
		return this.redisTemplate;
	}
	public void set(K key, V value){
		redisTemplate.opsForValue().set(key,value);
	}
	
	public void set(K key, V value, long timeout, TimeUnit unit){
		redisTemplate.opsForValue().set(key,value, timeout, unit);
	}

	public Boolean setIfAbsent(K key, V value){
		return redisTemplate.opsForValue().setIfAbsent(key,value);
	}

	public void multiSet(Map<? extends K, ? extends V> m){
		redisTemplate.opsForValue().multiSet(m);
	}

	public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m){
		return redisTemplate.opsForValue().multiSetIfAbsent(m);
	}

	public V get(K key){
		return redisTemplate.opsForValue().get(key);
	}

	public V getAndSet(K key, V value){
		return redisTemplate.opsForValue().getAndSet(key, value);
	}

	public List<V> multiGet(Collection<K> keys){
		return redisTemplate.opsForValue().multiGet(keys);
	}

	public Long increment(K key, long delta){
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Double increment(K key, double delta){
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Integer append(K key, String value){
		return redisTemplate.opsForValue().append(key, value);
	}

	public String get(K key, long start, long end){
		return redisTemplate.opsForValue().get(key, start, end);
	}

	public void set(K key, V value, long offset){
		 redisTemplate.opsForValue().set(key, value, offset);
	}
	
	public void remove(K key){
		redisTemplate.delete(key);
	}
	
	public void batchRemove(Collection<K> keys){
		redisTemplate.delete(keys);
	}
	
	public Long size(K key){
		return redisTemplate.opsForValue().size(key);
	}
	
	public void watch(K key){
		redisTemplate.watch(key);
	}

	public void BatchWatch(Collection<K> keys){
		redisTemplate.watch(keys);
	}
	
	public List<V> sort(SortQuery<K> query){
		return redisTemplate.sort(query);
	}

	public <T> List<T> sort(SortQuery<K> query, RedisSerializer<T> resultSerializer){
		return redisTemplate.sort(query,resultSerializer);
	}
	
	public Long sort(SortQuery<K> query, K storeKey){
		return redisTemplate.sort(query, storeKey);
	}
	
	public void rename(K oldKey, K newKey){
		 redisTemplate.rename(oldKey, newKey);
	}

	public Boolean renameIfAbsent(K oldKey, K newKey){
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	public Boolean expire(K key, long timeout, TimeUnit unit){
		return redisTemplate.expire(key, timeout, unit);
	}

	public Boolean expireAt(K key, Date date){
		return redisTemplate.expireAt(key, date);
	}
}
