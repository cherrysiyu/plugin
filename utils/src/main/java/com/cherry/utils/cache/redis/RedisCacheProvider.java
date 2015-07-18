package com.cherry.utils.cache.redis;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.cache.CacheProvider;
import com.cherry.utils.cache.CacheWrapper;

public class RedisCacheProvider implements CacheProvider{

	private String dbIndex;
	public RedisCacheProvider(String dbIndex) {
		super();
		this.dbIndex = dbIndex;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CacheWrapper getCache() {
		return new RedisCache(getDBIndex());
	}
	
	private int getDBIndex(){
		int index = 0;//0数据库，默认数据库
		if(CommonMethod.isInteger(dbIndex)){
			int temp = Integer.parseInt(dbIndex);
			if(temp >= 0 && temp < 16)
				index = temp;
		}
		return index;
	}
	
}
