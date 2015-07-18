package com.cherry.utils.http;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class DownLoadThread implements Callable<StatBean>{
	
	private String fileName;

	private StatBean statBean;
	private final CloseableHttpClient httpClient;
    private final HttpRequestBase httpRequestBase;
    
    
	public DownLoadThread(StatBean statBean, CloseableHttpClient httpClient,
			HttpRequestBase httpRequestBase) {
		super();
		this.statBean = statBean;
		this.httpClient = httpClient;
		this.httpRequestBase = httpRequestBase;
		fileName = DownLoadStat.filePath+UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
	}


	@Override
	public StatBean call() throws Exception {
		statBean.setBenginTime(System.currentTimeMillis());
		return httpClient.execute(httpRequestBase, new ResponseHandler<StatBean>() {
			@Override
			public StatBean handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				statBean.setEndTime(System.currentTimeMillis());
				int status = response.getStatusLine().getStatusCode();
                if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                	statBean.setSuccess(true);
                	//BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName), 1024);
                	statBean.setFileLength(response.getEntity().getContentLength());
                	//IOUtils.copy(response.getEntity().getContent(), bufferedOutputStream);
                	EntityUtils.consume(response.getEntity());
                	statBean.setDownloadTime(System.currentTimeMillis());
                	/*File file = new File(fileName);
                	if(file.exists()){
                		file.delete();
                	}*/
                }
				return statBean;
			}
		});
		
	}

}
