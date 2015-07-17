package com.cherry.utils.cache.memcached;
import java.util.ArrayList;
import java.util.List;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.FilePathEnum;
import com.cherry.utils.LogUtils;
import com.cherry.utils.PropertyUtils;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;


public class MemcachedConf {
	
	private static final String path = "com/cherry/resource";
	private static final String fileName = "memcached";
	
	static {
		initMemcachedConfig();
	}
	
	
	private static  void initMemcachedConfig(){
		LogUtils.info("init Memcached config file.......");
		PropertyUtils propertyUtils = PropertyUtils.newInstance(fileName,path,FilePathEnum.RELATIVE_PATH);
		String serverStr = propertyUtils.getProperty("servers");
		if(CommonMethod.isEmpty(serverStr)){
			throw new IllegalArgumentException("memcached servers is empty!");
		}
		List<String> servers = new ArrayList<String>();
		for (String s : serverStr.split(",")) {
			s = s.trim();
			if (!"".equals(s)) {
				servers.add(s);
			}
		}
		if (servers.size() < 1) {
			throw new RuntimeException("cache init faild！");
		}
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers.toArray(new String[] {}));
		pool.setFailover(Boolean.valueOf(propertyUtils.getProperty("failover","true")));
		pool.setInitConn(Integer.valueOf(propertyUtils.getProperty("initConn","100")));
		pool.setMinConn(Integer.valueOf(propertyUtils.getProperty("minConn","25")));
		pool.setMaxConn(Integer.valueOf(propertyUtils.getProperty("maxConn","250")));
		pool.setMaintSleep(Integer.valueOf(propertyUtils.getProperty("maintSleep", "30")));
		pool.setNagle(Boolean.valueOf(propertyUtils.getProperty("nagle","false")));// 关闭nagle算法
		pool.setSocketTO(Integer.valueOf(propertyUtils.getProperty("socketTO","3000")));
		pool.setAliveCheck(Boolean.valueOf(propertyUtils.getProperty("aliveCheck", "true")));
		pool.setHashingAlg(Integer.valueOf(propertyUtils.getProperty("hashingAlg", "0")));
		pool.setSocketConnectTO(Integer.valueOf(propertyUtils.getProperty("socketConnectTO", "3000")));
		String wStr = propertyUtils.getProperty("weights", "");
		
		List<Integer> weights = new ArrayList<Integer>();
		if(CommonMethod.isNotEmpty(wStr)){
			for (String weight : wStr.split(",")) {
				weight = weight.trim();
				if (!"".equals(weight)) {
					weights.add(Integer.valueOf(weight));
	
				}
			}
		}
		
		if (weights.size() == servers.size()) {
			pool.setWeights(weights.toArray(new Integer[] {}));
		}
		pool.initialize();
	}
	
	public static MemCachedClient getMemCachedClient(){
		return new MemCachedClient();
	}

}
