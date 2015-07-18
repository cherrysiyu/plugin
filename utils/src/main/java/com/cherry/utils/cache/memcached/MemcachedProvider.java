package com.cherry.utils.cache.memcached;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.cache.CacheProvider;
import com.cherry.utils.cache.CacheWrapper;

public class MemcachedProvider implements CacheProvider{
	
	private String cacheName;
	public MemcachedProvider(String cacheName) {
		super();
		this.cacheName = cacheName;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CacheWrapper getCache() {
		if(CommonMethod.isNotEmpty(cacheName)){
			return new MemcachedCache(cacheName);
		}
		return null;
	}
}
