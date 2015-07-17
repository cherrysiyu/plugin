package com.cherry.utils.http;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.DateUtils;
import com.cherry.utils.DoubleUtils;
import com.cherry.utils.http.sync.HttpConnectionManager;
import com.cherry.utils.threadPool.ThreadExector;
import com.cherry.utils.threadPool.ThreadPoolFactory;
import com.cherry.utils.threadPool.dto.TimerTask;

public class DownLoadStat {
	public static  String filePath = "c:/temp/";
	public static final String fileName = DateUtils.getCurrentTime("yyyyMMddHHmmss")+".txt";
	private static final int cpuSize = Runtime.getRuntime().availableProcessors();
	public static String utl="http://192.168.10.104:7777/group2/M00/00/00/wKgK6lUdDCmAN7klAADkSN0mnPE581.jpg";
	public static int threadSize = 100000;
	public static void main(String[] args) {
		long period = 2;
		if(args != null && args.length>0){
			filePath=args[0];
			if(args.length >1)
				utl=args[1];
				
		}
		ThreadPoolFactory.executTimerTask(new TimerTask(new ExecuteTimer(), 0, period, TimeUnit.MINUTES));
	}
	
	
	
	private static class ExecuteTimer implements Runnable{
		
		@Override
		public void run() {
			CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
			ThreadExector exector = ThreadPoolFactory.getCallableThreadExector(cpuSize+1);
			List<Callable<StatBean>> taskThreads = new ArrayList<Callable<StatBean>>(threadSize);
			for (int i = 0; i < threadSize; i++) {
				taskThreads.add(new DownLoadThread(new StatBean(), httpClient, HttpConnectionManager.buildHttpRequest(utl,false)));
			}
			List<StatBean> result = exector.excuteTasksAndWaitForResult(taskThreads);
			stat(result);
		}
		
		
		private void stat(List<StatBean> datas){
			double totalRequest = threadSize*1.0d;
			int fails = 0;
			long requestTotalTime = 0;
			long downLoadTotalTime = 0;
			int successRequest = 0;
			long fileSize = 0;
			if(CommonMethod.isCollectionNotEmpty(datas)){
				for (StatBean statBean : datas) {
					if(statBean.isSuccess()){
						fileSize = statBean.getFileLength();
						successRequest++;
						requestTotalTime += statBean.getHttpRequestTime();
						downLoadTotalTime += statBean.getDownloadSuccessTime();
					}else{
						fails++;
					}
				}
			}
			
			double avgSuccessRequest = DoubleUtils.getDoubleValue(successRequest/totalRequest, 2);
			double avgFailRequest = DoubleUtils.getDoubleValue(fails/totalRequest, 2);
			long avgRequestTime = requestTotalTime/successRequest;
			long avgDownloadTime = downLoadTotalTime/successRequest;
			
			StringBuilder sb = new StringBuilder();
			sb.append("统计时间:").append(DateUtils.getCurrentTime()).append("\r\n");
			sb.append("本次共有").append(threadSize).append("个请求").append("\r\n")
			   .append("下载的文件大小:").append(fileSize/1024).append("kb").append("\r\n")
			  .append("成功访问http总耗时:").append(requestTotalTime).append("ms").append("\r\n")
			 .append("成功访问http平均耗时:").append(avgRequestTime).append("ms").append("\r\n")
			 .append("成功访问http并下载成功总耗时:").append(downLoadTotalTime).append("ms").append("\r\n")
			 .append("成功访问http并下载成功平均耗时:").append(avgDownloadTime).append("ms").append("\r\n")
			 .append("访问成功率:").append(avgSuccessRequest*100).append("%").append("\r\n")
			 .append("访问失败率:").append(avgFailRequest*100).append("%").append("\r\n")
			 .append("\r\n\r\n");
			write2File(sb.toString());
		}
		
		private void write2File(String info){
			BufferedWriter wb = null;
			try {
				 wb = new BufferedWriter(new FileWriter(filePath+fileName, true), 1024);
				 wb.write(info);
				 wb.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(wb != null){
					try {
						wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("本次执行结束，结果:"+info);
		}
		
	}

}
