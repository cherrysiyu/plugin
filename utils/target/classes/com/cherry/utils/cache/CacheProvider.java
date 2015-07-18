package com.cherry.utils.cache;


/**
 * 缓存提供者
 * @author:Cherry
 * @version:1.0
 * @date:May 10, 2013
 */
public interface CacheProvider {

	@SuppressWarnings("rawtypes")
	public CacheWrapper getCache();
}
