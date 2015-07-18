package com.cherry.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.cherry.utils.cache.CacheFactory;
import com.cherry.utils.cache.CacheWrapper;
import com.cherry.utils.cache.ProviderName;
import com.cherry.utils.cache.redis.RedisCache;

public class CacheTest {

	
	public static void main(String[] args) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 50000; i++) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("lon", "121.451");
			map.put("lat", "32.1542");
			map.put("time", "1321251554");
			list.add(map);
		}
		 CacheWrapper<String,List<Map<String,String>>> cache = CacheFactory.getCache(ProviderName.EhCacheProvider,"defaultCache");
		 long beginTime = System.currentTimeMillis();
		 cache.putCache("m1location3", list);
		System.out.println("放入缓存耗时："+(System.currentTimeMillis() - beginTime)+"ms");
		 beginTime = System.currentTimeMillis();
		 List<Map<String, String>> cache2 = cache.getCache("m1location3");
		 System.out.println("读缓存耗时："+(System.currentTimeMillis() - beginTime)+"ms");
		 cache.deleteCache("m1location3");
		 System.out.println(cache2.size());
		 System.out.println(cache2.get(0));
		 cache.removeAllCaches();
		 
		 RedisCache c =  (RedisCache)CacheFactory.getCache(ProviderName.RedisCacheProvider,"0");
		 Jedis redisConnection = c.getRedisConnection();
		 System.out.println(redisConnection.keys("*"));
		 CacheWrapper<String,List<Map<String,String>>> cache12 = CacheFactory.getCache(ProviderName.EhCacheProvider,"defaultCache");
		 cache12.putCache("m1location4", list);
		 System.out.println(cache12.getCache("m1location4").size());
	}
}
