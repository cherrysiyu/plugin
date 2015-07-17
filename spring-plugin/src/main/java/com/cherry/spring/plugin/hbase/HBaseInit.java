package com.cherry.spring.plugin.hbase;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.LoggerFactory;

/**
 * 
 	服务初始化参数等
 * 
 * <p>
 * Copyright: Copyright (c) 2014年10月22日 上午10:18:17
 * <p>
 * 
 * <p>
 * 
 * @author Cherry
 * @version 1.0.0
 */
public class HBaseInit {
	
	private String hbaseConfPath ;
	
	private boolean lifo = false;  
	private int maxActive = 5;  
	private int maxIdle = 5;  
	private int minIdle = 1;  
	private int maxWait = 5 * 1000;
	
	
	
	public HBaseInit(String hbaseConfPath, boolean lifo, int maxActive,
			int maxIdle, int minIdle, int maxWait) {
		super();
		this.hbaseConfPath = hbaseConfPath;
		this.lifo = lifo;
		this.maxActive = maxActive;
		this.maxIdle = maxIdle;
		this.minIdle = minIdle;
		this.maxWait = maxWait;
		initHbase();
	}
	

	public HBaseInit() {
		super();
		initHbase();
	}


	public void initHbase(){
		Configuration	conf = new Configuration();
		HBaseAdmin admin = null;
		try {
			if(StringUtils.isNotEmpty(hbaseConfPath) && (new File(hbaseConfPath).exists())){
		    	conf.addResource(new FileInputStream(new File(hbaseConfPath)));
			}else{
				hbaseConfPath = "hbase-site.xml";
				conf.addResource(hbaseConfPath);
			}
    		conf =  HBaseConfiguration.create(conf);
    		HBaseUtils.setConf(conf);
			admin = new HBaseAdmin(conf);
			HBaseUtils.setAdmin(admin);
			PoolableObjectFactory<HConnection> factory = new HBaseConnectionPoolableObjectFactory();  
	        GenericObjectPool.Config config = new GenericObjectPool.Config();  
	        config.lifo = lifo;  
	        config.maxActive = maxActive;  
	        config.maxIdle = maxIdle;  
	        config.minIdle = minIdle;  
	        config.maxWait = maxWait;
	        GenericObjectPool<HConnection> objectPool = new GenericObjectPool<HConnection>(factory, config);
	        HBaseUtils.setObjectPool(objectPool);
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("init HBase error,  System will exit",e);
		}
		LoggerFactory.getLogger(getClass()).info("HBase init success !!!,path:"+hbaseConfPath);
	}
	
	public String getHbaseConfPath() {
		return hbaseConfPath;
	}
	public void setHbaseConfPath(String hbaseConfPath) {
		this.hbaseConfPath = hbaseConfPath;
	}

	public boolean isLifo() {
		return lifo;
	}

	public void setLifo(boolean lifo) {
		this.lifo = lifo;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
	

}
