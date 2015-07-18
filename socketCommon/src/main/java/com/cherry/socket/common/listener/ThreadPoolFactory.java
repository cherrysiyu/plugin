package com.cherry.socket.common.listener;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义线程池，为了监控知道并发量
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年9月20日 下午8:39:16
 */
public class ThreadPoolFactory extends ThreadPoolExecutor{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private int busySize;
	public ThreadPoolFactory(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit,int queueSize) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(queueSize), new CustomeRejectedExecutionHandler());
		this.busySize = queueSize/5;
		logger.info(new StringBuffer("init ThreadPool success,corePoolSize:").append(corePoolSize)
				.append(",maximumPoolSize:").append(maximumPoolSize).append(",keepAliveTime:").append(keepAliveTime)
				.append(",unit:").append(unit).append(",queueSize:").append(queueSize).toString());
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		if(this.getQueue().remainingCapacity() < busySize)
			logger.error("ThreadPool is busy,current remainingCapacity:"+this.getQueue().remainingCapacity());
		super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		// TODO Auto-generated method stub
		super.afterExecute(r, t);
	}
	
	
	private static class CustomeRejectedExecutionHandler implements RejectedExecutionHandler{
		private Logger logger = LoggerFactory.getLogger(this.getClass());
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			 if (!executor.isShutdown()) {
				 logger.info("线程池已满，外部运行一个线程...");
	                r.run();
	            }
		}
		
	}

}
