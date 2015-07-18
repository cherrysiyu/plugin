package com.cherry.utils.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.cherry.utils.http.sync.HttpConnectionManager;

public class FileUpload {
	public static final String requestUrl = "http://192.168.10.38/upload/fud/upload";
	public static void main(String[] args) throws Exception {
		List<File> files = new ArrayList<File>();
		//files.add(new File("c:/1.png"));
		//files.add(new File("c:/NewDfaAlgorithm.java"));
		files.add(new File("c:/excle.zip"));
		long currentTimeMillis = System.currentTimeMillis();
		String uploadFile = uploadFile(files);
		System.out.println("花费的时间:"+(System.currentTimeMillis()-currentTimeMillis)+"ms");
		System.out.println(uploadFile);
	}
	
	
	public static String uploadFile(List<File> files) throws Exception{
		HttpPost post = new HttpPost(requestUrl);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (File file : files) {
			builder.addBinaryBody("files", new FileInputStream(file),ContentType.DEFAULT_BINARY, file.getName());
		}
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
		byte[] result = httpClient.execute(post, new ResponseHandler<byte[]>() {
			@Override
			public byte[] handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				 int status = response.getStatusLine().getStatusCode();
                 if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                    return EntityUtils.toByteArray(response.getEntity());
                } else {
                	System.out.println("Unexpected response status: " + status);
                    return null;
                }
			}
		});
		String responseString = HttpCommon.getResponseString(result);
		return responseString;
	}

}
