package com.cherry.socket.mina;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.socket.common.bean.StatusEnum;
import com.cherry.socket.common.manager.MessageResponse;
import com.cherry.socket.mina.client.MinaTcpClient;
import com.cherry.socket.utils.RandomUtils;


public class ClientApplication {
	private static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);
	public static final AtomicLong sendCount = new AtomicLong();
	public static final AtomicLong recivedCount = new AtomicLong();
	public static final AtomicLong nullCount = new AtomicLong();
	public static final AtomicLong successCount = new AtomicLong();
	public static final AtomicLong activeThread = new AtomicLong();
	public static final AtomicLong totalTime = new AtomicLong();
	public static final AtomicLong seq = new AtomicLong();
	public static final MinaTcpClient tcpClient1;
	private static final int totalSize = 100000;
	private static final List<Runnable> threads = new ArrayList<Runnable>(totalSize);
	static {
		tcpClient1 = new MinaTcpClient(1433, "127.0.0.1", ServerApplication.serverPort,300000,60,80).startClient();
		for (int i = 0; i < totalSize; i++) {
			//if(i %5 ==0)
				threads.add(new SendThread(tcpClient1));
			/*else if(i %3 ==0)
				threads.add(new SendThread(tcpClient2));
			else if(i %2 ==0)
				threads.add(new SendThread(tcpClient3));
			else
				threads.add(new SendThread(tcpClient4));*/
		}
	}
	
	public static ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 50, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(25000),new ThreadPoolExecutor.DiscardPolicy());
	public static void main(String[] args) throws Exception {
		TimeUnit.SECONDS.sleep(30);
		long beginTime = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < totalSize; i++) {
			count++;
			pool.submit(threads.get(i));
			if(count % 1000 == 0)
				TimeUnit.SECONDS.sleep(1);
		}
		pool.shutdown();
		while(!pool.isTerminated()){
			TimeUnit.SECONDS.sleep(1);
		}
		logger.debug("共耗时:"+(System.currentTimeMillis()-beginTime)+"ms");
		TimeUnit.SECONDS.sleep(10);
		StringBuilder sb = new StringBuilder();
		sb.append("共发送请求个数:").append(sendCount).append("\t").append("共收到回应个数:").append(recivedCount).append("\n")
		  .append("共激活线程个数:").append(activeThread).append("\t").append("共丢弃线程个数:").append(totalSize-activeThread.get()).append("\n")
		  .append("共成功个数:").append(successCount).append("\t").append("共返回null个数:").append(nullCount).append("\n")
		  .append("总消耗的时间:").append(totalTime).append("us\t").append("平均耗时:").append(totalTime.get()/successCount.get()).append("us\n");
		logger.info(sb.toString());
	}
	static class SendThread implements Runnable{
		MinaTcpClient tcpClient;
		public SendThread(MinaTcpClient tcpClient) {
			super();
			this.tcpClient = tcpClient;
		}

		@Override
		public void run() {
			activeThread.incrementAndGet();
				sendCount.incrementAndGet();
				byte[] body = RandomUtils.randomString(10).getBytes();
				long beginTime = System.nanoTime();
				MessageResponse sendMessage = tcpClient.sendMessage(body);
				long endTime = System.nanoTime();
				recivedCount.incrementAndGet();
				long time = (endTime-beginTime)/1000;
				if(sendMessage.getStatus() == StatusEnum.TIMEOUT){
					nullCount.incrementAndGet();
					logger.error("超时了 共耗时:"+time/1000+"ms");
				}else if(sendMessage.getStatus() == StatusEnum.CORRECT){
					ArrayUtils.reverse(body);
					if(ArrayUtils.isEquals(body, sendMessage.getMsgBody())){
						successCount.incrementAndGet();
						if(time >0)
							totalTime.addAndGet(time);
						logger.info("耗时:"+time+"us");
					}
				}
			} 
		}

}

