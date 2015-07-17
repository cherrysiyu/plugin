package com.cherry.utils.cache;

import java.lang.reflect.Constructor;

import com.cherry.utils.LogUtils;
import com.cherry.utils.cache.ehcache.EhCacheProvider;

/**
 * 缓存工厂,需要用哪一种缓存必须自行导入相应的缓存的jar包,否则运行报错,缺少jar包
 * @author:Cherry
 * @version:1.0
 * @date:May 10, 2013
 */
@SuppressWarnings("rawtypes")
public class CacheFactory {
	 
	private CacheFactory(){}
	
	/**
	 * 提供默认的缓存方式,默认的缓存方式是EhCache
	 * @param cacheName EhCache的名称
	 * @return
	 */
	
	public static CacheWrapper getCache(String cacheName){
		return getCache(ProviderName.EhCacheProvider,cacheName);
	}
	
	/**
	 * 
	 * @param providerName 缓存策略，详见ProviderName枚举对象
	 * @param cacheNameOrDBIndex 如果是EhCache策略的话那么传入cacheName，如果是Redis缓存策略的话则传递DBIndex([0,15])如果数字不合法的话则用默认的0数据库
	 							 如果是Memcached的话则传入一个字符串，主要用来算hash值的负载均衡用的
	 * @return
	 */
	public static CacheWrapper getCache(ProviderName providerName,String cacheNameOrDBIndex){
		CacheProvider cacheProvider = null;
		try {
			Class<?> classType = Class.forName(providerName.getProviderName());
			Constructor<?> constructor = classType.getConstructor(String.class);
			cacheProvider = (CacheProvider)constructor.newInstance(cacheNameOrDBIndex);
		} catch (Exception e) {
			LogUtils.error("Unabled to initialize cache provider:" + providerName.getProviderName()+ ", using ehcache default.", e);
			cacheProvider = new EhCacheProvider(cacheNameOrDBIndex);
		}
		return  cacheProvider.getCache();
	}
	
	
	
}
