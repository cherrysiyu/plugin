package com.cherry.utils.http;

import java.io.IOException;

import com.cherry.utils.http.sync.HttpConnectionManager;

public class HttpTest {
	
	public static void main(String[] args) throws IOException {
		/*String result = HttpConnectionManager.getStringResult(new HttpPost("http://ifeve.com/java-concurrency-thread-directory/"));
		
		LogUtils.debug(result);
		
		byte[] httpResult = HttpConnectionManager.getByteArrayResult(new HttpPost("http://ifeve.com/java-concurrency-thread-directory/"), 5, TimeUnit.SECONDS);
		LogUtils.debug(HttpCommon.getResponseString(httpResult));*/
		
		
		
		/*List<String> urls = new ArrayList<String>();
		urls.add("http://ifeve.com/java-concurrency-thread-directory/");
		urls.add("http://www.baidu.com/");
		urls.add("http://www.iteye.com/");
		urls.add("http://www.sina.com.cn/");
		urls.add("http://home.wind.com.cn:8000/");
		
		
		List<HttpResponse> asyncHttpResults = AsyncHttpConnectionManager.getAsyncHttpResults(urls, true);
		LogUtils.debug(asyncHttpResults.size());
		for (HttpResponse httpResponse : asyncHttpResults) {
			LogUtils.debug(HttpCommon.getResponseString(httpResponse.getEntity()));
		}*/
		/*List<byte[]> httpResults = HttpConnectionManager.getHttpResults(urls, true, 4, TimeUnit.SECONDS);
		LogUtils.debug(httpResults.size());
		for (byte[] httpEntity : httpResults) {
			String responseString = HttpCommon.getResponseString(httpEntity);
			LogUtils.debug(responseString);
		}*/
		String url ="http://127.0.0.1:8080/service/query?serviceType=1&requestInfo= {'tableName':'user_feature_category','columnNames':[],'rowKeys':[],'commandId':8,'maxSize':10} ";
		byte[] byteArrayResult = HttpConnectionManager.getByteArrayResult(url, true);
		String responseString = HttpCommon.getResponseString(byteArrayResult);
	System.out.println(responseString);
	}

}
