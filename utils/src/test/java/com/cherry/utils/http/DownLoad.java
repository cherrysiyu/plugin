package com.cherry.utils.http;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.poi.util.IOUtils;

import com.cherry.utils.RandomUtils;
import com.cherry.utils.http.sync.HttpConnectionManager;
import com.cherry.utils.threadPool.ThreadPoolFactory;
import com.cherry.utils.threadPool.dto.TimerTask;

public class DownLoad {
	public static String url = "http://a.llyx.com/ndisi/adclk?id=THZ1Wp&mcpid=lkZndZWt&show_form=0&bn=6";
	public static void main(String[] args) throws Exception {
		
		ThreadPoolFactory.executTimerTask(new TimerTask(new HttpThreadTimer(), 0, 100, TimeUnit.SECONDS));
		
	}
	
	
	private static class HttpThreadTimer implements Runnable{

		@Override
		public void run() {
			List<String> urls = new ArrayList<String>();
			for (int i = 0; i < 1; i++) {
				urls.add(url);
			}
			List<CloseableHttpResponse> httpResults = HttpConnectionManager.getHttpResponseResults(urls, false);
			for (CloseableHttpResponse bs : httpResults) {
				HttpEntity entity = bs.getEntity();
				try {
					IOUtils.copy(entity.getContent(), new BufferedOutputStream(new FileOutputStream("c:/temp/"+RandomUtils.randomString(10)+".dat"), 1024));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						bs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	

}
