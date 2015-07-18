package com.cherry.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Test;

import com.cherry.utils.threadPool.ThreadExector;
import com.cherry.utils.threadPool.callable.CallableThreadExector;
import com.cherry.utils.threadPool.runnable.RunnableThreadExector;


public class ThreadPoolTest {
	
	@Test
	public  void testSingleThread(){
		ThreadExector exector = new CallableThreadExector(1);
		 List<Callable<Integer>> taskThreads = new ArrayList<Callable<Integer>>();
		for (int i = 0; i < 50; i++) {
			taskThreads.add(new SingleThread(i+1));
		}
		List<Integer> list = exector.excuteTasksAndWaitForResult(taskThreads);
		LogUtils.debug(list);
	}
	@Test
	public  void testWait(){
		ThreadExector exector = new RunnableThreadExector(50);
		List<Callable<Object>> list = new ArrayList<Callable<Object>>();
		for (int i = 0; i < 50; i++) {
			list.add(new CallableThread(i+1));
		}
		boolean flag = exector.excuteAndWaitForAllTasksFinish(list);
		LogUtils.debug(flag);
	}
	
	
	
}

class CallableThread implements Callable<Object>{

	private int number;
	public  CallableThread(int number){
		this.number = number;
	}
	@Override
	public Object call() throws Exception {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			LogUtils.error(e);
		}
		LogUtils.debug(number);
		return new Object();
	}
	
}



class SingleThread implements Callable<Integer>{

	private int number;
	public  SingleThread(int number){
		this.number = number;
	}
	
	@Override
	public Integer call() throws Exception {
		Thread.sleep(100);
		LogUtils.debug(number);
		return number;
	}
}
