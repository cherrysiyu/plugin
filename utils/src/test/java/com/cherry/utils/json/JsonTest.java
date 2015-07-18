package com.cherry.utils.json;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.cherry.utils.LogUtils;
import com.cherry.utils.TestUtils;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {

	private static int  measurements = 100;  // 测量次数
    private static int threads      = 10;   // 线程数
    private static int SerialTimes  = 1000; // 每个线程执行序列化次数
    
    private static ComBean data  = CommonJsontestUtil.getTestDatas();
	
	
	
	
	 /*
   * 在缓存重用ObjectMapper的情况下使用jackson进行json序列化
   */
  @Test
  public void testJackson() {
	  TestUtils.createTest(measurements, threads,
              "[jackson（ObjectMapper）]", new Runnable() {
                  public void run() {
                      // ObjectMapper重用方式
                      ObjectMapper mapper = new ObjectMapper();
                      StringWriter writer = null;

                      for (int i = 0; i < SerialTimes; i++) {
                          writer = new StringWriter();
                          try {
                              mapper.writeValue(writer, data);
                          } catch (JsonGenerationException e) {
                              LogUtils.error(e);
                          } catch (JsonMappingException e) {
                              LogUtils.error(e);
                          } catch (IOException e) {
                              LogUtils.error(e);
                          }
                      }
                  }
              });
  }

  
  /*@Test
  public void testJackson2() {
     TestUtils.createTest(measurements, threads,
              "[jackson（ObjectMapper）]", new Runnable() {
                  public void run() {
                  	JsonGenerator jsonGenerator = null;
                      try {
                      	jsonGenerator = new ObjectMapper().getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
							 for (int i = 0; i < SerialTimes; i++) {
								 jsonGenerator.writeObject(data);
		                      }
							 if (jsonGenerator != null) {
				                jsonGenerator.flush();
							 }
				             if (!jsonGenerator.isClosed()) {
				                jsonGenerator.close();
				             }
                      } catch (IOException e) {
							LogUtils.error(e);
						}
                     
                     
                  }
              });
  }*/

  
  
  /*
   * 直接使用StringBuffer的append方法拼接json字符串
   */
  @Test
  public void testStringBuffer() {
	  TestUtils.createTest(measurements, threads,"[StringBuffer]",
              new Runnable() {
                  public void run() {
                  	for(int i = 0; i < SerialTimes; i++){
                  		data.toJson();
                  	}
                  }
              });
  }
    
    
}
