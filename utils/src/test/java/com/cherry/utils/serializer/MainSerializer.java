package com.cherry.utils.serializer;

import com.cherry.utils.cache.CacheFactory;
import com.cherry.utils.cache.CacheWrapper;
import com.cherry.utils.cache.ProviderName;

public class MainSerializer {
	static CacheWrapper<String,SerializerBean> cache = CacheFactory.getCache(ProviderName.EhCacheProvider,"defaultCache");
	static CacheWrapper<String,SerializerBean> cache2 = CacheFactory.getCache(ProviderName.MemcachedProvider,"defaultCache");
	static CacheWrapper<String,SerializerBean> cache3 = CacheFactory.getCache(ProviderName.RedisCacheProvider,"defaultCache");
	public static void main(String[] args) {
		String key = "SerializerBean";
		SerializerBean serializerBean = new SerializerBean();
		System.out.println(serializerBean);
		cache.putCache(key,serializerBean);
		System.out.println(cache.getCache(key));
		
		
		cache2.putCache(key,serializerBean);
		System.out.println(cache2.getCache(key));
		
		
		cache3.putCache(key,serializerBean);
		System.out.println(cache3.getCache(key));
	}

}
