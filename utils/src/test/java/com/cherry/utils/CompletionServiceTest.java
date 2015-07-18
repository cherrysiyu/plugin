package com.cherry.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.junit.Test;

import com.cherry.utils.threadPool.ThreadExector;
import com.cherry.utils.threadPool.callable.CallableThreadExector;
import com.cherry.utils.threadPool.runnable.RunnableThreadExector;



public class CompletionServiceTest {

	@Test
	public  void testExcuteAndReturn(){
		List<Callable<Integer>> solvers = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 20; i++) {
        	solvers.add(new IntegerThread());
        }
	        
        ThreadExector callableExector = new CallableThreadExector();
        List<Integer> list =  callableExector.excuteTasksAndWaitForResult(solvers);
	    System.err.println(list.size());    
	    System.err.println(list);
	    System.err.println("testExcuteAndReturn()结束");
	}
	
	@Test
	public  void testExcute(){
		List<RunnableThread> solvers = new ArrayList<RunnableThread>();
        for (int i = 0; i < 20; i++) {
        	solvers.add(new RunnableThread());
        }
        ThreadExector callableExector = new CallableThreadExector();
        callableExector.excuteRunnableTasks(solvers);
        System.err.println("testExcute()结束");
	}
	
	@Test
	public  void testRunnable(){
		List<RunnableThread> runnableTaskThreads = new ArrayList<RunnableThread>();
        for (int i = 0; i < 20; i++) {
        	runnableTaskThreads.add(new RunnableThread());
        }
        
        ThreadExector runnableExector = new RunnableThreadExector();
        ThreadExector callableExector = new CallableThreadExector();
        
        runnableExector.excuteRunnableTasks(runnableTaskThreads);
        System.err.println("RunnableThreadExector--->excuteRunnableTasks()结束");
        
        callableExector.excuteRunnableTasks(runnableTaskThreads);
        System.err.println("CallableThreadExector--->excuteRunnableTasks()结束");
        
	}
	@Test
	public  void testCallable(){
		
        List<Callable<Integer>> callableTaskThreads = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 20; i++) {
        	callableTaskThreads.add(new IntegerThread());
        }
	        
        
        ThreadExector runnableExector = new RunnableThreadExector();
        ThreadExector callableExector = new CallableThreadExector();
        
        runnableExector.excuteCallableTasks(callableTaskThreads);
        System.err.println("RunnableThreadExector--->excuteCallableTasks()结束");
        
        callableExector.excuteCallableTasks(callableTaskThreads);
        System.err.println("CallableThreadExector--->excuteCallableTasks()结束");
        
        
	}
	@Test
	public  void testCallableAndWait(){
		
        List<Callable<Integer>> callableTaskThreads = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 20; i++) {
        	callableTaskThreads.add(new IntegerThread());
        }
	        
        
        ThreadExector runnableExector = new RunnableThreadExector();
        ThreadExector callableExector = new CallableThreadExector();
        
        List<Integer>  list = runnableExector.excuteTasksAndWaitForResult(callableTaskThreads);
        LogUtils.debug("RunnableThreadExector--->excuteCallableTasks()结束");
        LogUtils.debug("runnableExector结果大小:"+list.size());
        LogUtils.debug("runnableExector结果:"+list);
        
        list = callableExector.excuteTasksAndWaitForResult(callableTaskThreads);
        LogUtils.debug("CallableThreadExector--->excuteCallableTasks()结束");
        LogUtils.debug("callableExector结果大小:"+list.size());
        LogUtils.debug("callableExector结果:"+list);
        
        
	}
}


class IntegerThread implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		Thread.sleep(100);
		int result =  new Random().nextInt(1000);
		LogUtils.debug(result);
		return result;
	}
}

class RunnableThread implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			LogUtils.error(e);
		}
		int result =  new Random().nextInt(1000);
		LogUtils.debug(result);
	}
}
