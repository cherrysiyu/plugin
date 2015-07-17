package com.cherry.utils.cache;

import com.cherry.utils.cache.ehcache.EhCacheProvider;
import com.cherry.utils.cache.memcached.MemcachedProvider;
import com.cherry.utils.cache.redis.RedisCacheProvider;


/**
 * 缓存分类
 * @author:Cherry
 * @version:1.0
 * @date:May 10, 2013
 */
public enum ProviderName {
	
	EhCacheProvider(EhCacheProvider.class.getName()),
	MemcachedProvider(MemcachedProvider.class.getName()),
	RedisCacheProvider(RedisCacheProvider.class.getName());
	
	/**
	 * cacheName
	 */
	private final String providerName;
	
	ProviderName(String providerName){
		this.providerName = providerName;
	}

	public String getProviderName() {
		return providerName;
	}
}
