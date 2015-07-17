package com.cherry.utils.cache;


public interface CacheWrapper<K,V> {
	
	/**
	 * 加入一个缓存
	 * @param key 
	 * @param value
	 */
	public void putCache(K key, V value);

	/**
	 * 根据key删除一个缓存
	 * @param key
	 * @return
	 */
	public boolean deleteCache(K key);
	/**
	 * 根据key和给定的value更新一个缓存
	 * @param key
	 * @param value
	 */
	public void updateCache(K key, V value);

	/**
	 * 根据key得到一个缓存
	 * @param key
	 * @return
	 */
	public V getCache(K key);

	/**
	 * 清除所有缓存
	 */
	public void removeAllCaches();
}
