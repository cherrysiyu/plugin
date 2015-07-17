package com.cherry.utils.cache.ehcache;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.LogUtils;
import com.cherry.utils.cache.CacheException;
import com.cherry.utils.cache.CacheProvider;
import com.cherry.utils.cache.CacheWrapper;

public class EhCacheProvider  implements CacheProvider{
	
	private static  URL ehCacheUrl = null;
	private static  CacheManager manager = null;
	static{
		ehCacheUrl = EhCacheProvider.class.getClassLoader().getResource("com/cherry/resource/ehcache.xml");
		manager = new CacheManager(ehCacheUrl);
	}
	private ConcurrentMap<String, Ehcache> cacheManager = new ConcurrentHashMap<String, Ehcache>();
	
	private String cacheName;
	public EhCacheProvider(String cacheName) {
		super();
		this.cacheName = cacheName;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CacheWrapper getCache() {
		if(CommonMethod.isNotEmpty(cacheName)){
			return new EhCacheWrapper(createCache(cacheName));
		}else{
			return null;
		}
	}
	
	/**
	 * 如果没有则新建
	 * @param cacheName
	 * @return
	 */	
	private Ehcache createCache(String cacheName){
		  Ehcache ehcache = cacheManager.get(cacheName);
	    	if(ehcache != null)
	    		return ehcache ;
		    try {
	            net.sf.ehcache.Cache cache = manager.getCache(cacheName);
	            if (cache == null) {
	                LogUtils.info("Could not find configuration [" + cacheName + "]; using defaults.");
	                manager.addCache(cacheName);
	                cache = manager.getCache(cacheName);
	                cacheManager.putIfAbsent(cacheName,cache);
	                LogUtils.info("started EHCache region: " + cacheName);                
	            }
	            return cache;
		    }
	        catch (net.sf.ehcache.CacheException e) {
	            throw new CacheException(e);
	        }
	    }

}
