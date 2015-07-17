package com.cherry.utils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



public class QueueDemo {
	
	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(5000);
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		exec.submit(new Producer(queue));
		exec.submit(new Consumer(queue));
		
		exec.shutdown();
	}
	

}
class Producer implements Runnable{

	private BlockingQueue<String> queue;
	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		try{
			for (int i = 0; i < 50; i++) {
				Thread.sleep(new Random().nextInt(1000));
				queue.put(produce());
			}
		}catch (Exception e) {
			LogUtils.error(e);
		}
		long endTime = System.currentTimeMillis();
		LogUtils.debug("花费时间:"+(endTime-beginTime)+"ms");
	}
	private String produce() {
		String temp = ""  +(char)('A'+ (int)(Math.random()*26));
		LogUtils.debug("生产者生产了:" + temp);
		return temp;
	}
}


class Consumer implements Runnable{

	private BlockingQueue<String> queue;
	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
		@Override
		public void run() {
			
			try{
				for (int i = 0; i < 50; i++) {
					consum(queue.take());
				}
			}catch (Exception e) {
			
			
		}
	}

		private void consum(String take) {
			LogUtils.debug("消费者消费产品:" +take);
		}
	
	
}