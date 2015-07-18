package com.cherry.utils.threadPool;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.LogUtils;
import com.cherry.utils.threadPool.callable.CallableThreadExector;
import com.cherry.utils.threadPool.dto.TimerTask;
import com.cherry.utils.threadPool.runnable.RunnableThreadExector;


public class ThreadPoolFactory {
	
	private ThreadPoolFactory(){}
	/**
	 * 得到Callable类型的线程池执行器,线程池的大小为CPU的核数
	 * @return
	 */
	public static ThreadExector  getCallableThreadExector(){
		ThreadExector exector = new CallableThreadExector();
		return exector;
	}
	/**
	 * 得到Callable类型的线程池执行器
	 * @param poolSize 线程池的大小
	 * @return
	 */
	public static ThreadExector  getCallableThreadExector(int poolSize){
		ThreadExector exector = new CallableThreadExector(poolSize);
		return exector;
	}
	
	/**
	 * 得到Runnable类型的线程池执行器,线程池的大小为CPU的核数
	 * @return
	 */
	public static ThreadExector  getRunnableThreadExector(){
		ThreadExector exector = new RunnableThreadExector();
		return exector;
	}
	
	/**
	 * 得到Runnable类型的线程池执行器
	 * @param poolSize 线程池的大小
	 * @return
	 */
	public static ThreadExector  getRunnableThreadExector(int poolSize){
		ThreadExector exector = new RunnableThreadExector(poolSize);
		return exector;
	}
	
	/**
	 * 得到顺序执行线程的线程池执行器,执行任务列表，按照任务列表的顺序执行
	 * @return
	 */
	public static ThreadExector getSequenceThreadExector(){
		return getCallableThreadExector(1);
	}

	/**
	 * 本方法用来定期的执行某一批任务。
	 * @param taskThreads 要执行的任务列表
	 * @param initialDelay  首次执行的延迟时间
	 * @param period 连续执行之间的周期
	 * @param unit initialDelay 和 period 参数的时间单位
	 */
	public static void executTimerTasks(List<TimerTask> taskThreads){
		if(CommonMethod.isCollectionNotEmpty(taskThreads)){
			int taskSize = taskThreads.size();
			LogUtils.info("本次共"+taskSize+"个定期任务");
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(taskSize);
			int i = 1;
			for (TimerTask timerTask : taskThreads) {
				LogUtils.info("执行第"+i+"个定期任务"+timerTask.getUnit().toSeconds(timerTask.getInitialDelay())+" 秒后开始执行单个定期任务,每 "+timerTask.getUnit().toSeconds(timerTask.getPeriod())+" 秒执行一次");
				scheduler.scheduleAtFixedRate(timerTask.getCommand(), timerTask.getInitialDelay(), timerTask.getPeriod(), timerTask.getUnit());
				i++;
			}
		}else{
			LogUtils.info("本次没有定期任务，taskThreads为空，不执行.");
		}
	}
	
	/**
	 * 本方法用来定期的执行某一个任务，为了取消用以前的Timer来完成定期的任务，Timer方式多个定期任务之间会有线程交互的bug，用此种方法可以避免此问题
	 * @param command 要执行的任务
	 * @param initialDelay 多长时间后开始执行任务
	 * @param period 每多长时间执行一次
	 * @param unit initialDelay 和 period 参数的时间单位
	 */
	public static void executTimerTask(TimerTask timerTask){
		LogUtils.info(timerTask.getUnit().toSeconds(timerTask.getInitialDelay())+" 秒后开始执行单个定期任务,每 "+timerTask.getUnit().toSeconds(timerTask.getPeriod())+" 秒执行一次");
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(timerTask.getCommand(), timerTask.getInitialDelay(), timerTask.getPeriod(), timerTask.getUnit());
	}
	
}
