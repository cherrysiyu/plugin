package com.cherry.utils.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.FilePathEnum;
import com.cherry.utils.LogUtils;
import com.cherry.utils.PropertyUtils;
import com.cherry.utils.cache.CacheException;

public class RedisUtils {
	private static final String path = "com/cherry/resource";
	private static final String fileName = "redis";
	private static JedisPool pool;
	static{
		initRedis();
	}
	
	private static void initRedis() {
		LogUtils.info("初始化Redis的配置文件信息.......");
		PropertyUtils propertyUtils = PropertyUtils.newInstance(fileName,path,FilePathEnum.RELATIVE_PATH);
		String ip = propertyUtils.getProperty("redis.ip");
	    if(CommonMethod.isEmpty(ip))
	    	throw new IllegalArgumentException("redis server ip is empty!");
		 //创建jedis池配置实例  
        JedisPoolConfig config = new JedisPoolConfig(); 
        //设置池配置项值  
        config.setMaxTotal(Integer.parseInt(propertyUtils.getProperty("redis.pool.maxActive","1024")));    
        config.setMaxIdle(Integer.parseInt(propertyUtils.getProperty("redis.pool.maxIdle","200")));    
        config.setMaxWaitMillis(Long.parseLong(propertyUtils.getProperty("redis.pool.maxWait","1000"))); 
        config.setTestOnBorrow(Boolean.valueOf(propertyUtils.getProperty("redis.pool.testOnBorrow","true")));    
        config.setTestOnReturn(Boolean.valueOf(propertyUtils.getProperty("redis.pool.testOnReturn","true")));    
        //根据配置实例化jedis池  
       pool = new JedisPool(config,ip,Integer.parseInt(propertyUtils.getProperty("redis.port","6379")));  
		
	}
	
	public static Jedis getRedisConnection(){
		return pool.getResource();
	}
	
	public static Jedis getRedisConnection(int dbIndex){
		if(dbIndex >= 0 && dbIndex <= 16 ){
			 Jedis resource = pool.getResource();
			 resource.select(dbIndex);
			 return resource;
		}else{
			throw new CacheException("redis数据库的编号必须在[0,15]");
		}
	}
	
	public static void  colseRedisConnection(Jedis jedis){
		 pool.returnResource(jedis);
	}
	
	public static void destroy(){
		pool.destroy();
	}
}
