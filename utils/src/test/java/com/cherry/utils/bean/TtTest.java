package com.cherry.utils.bean;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.cherry.utils.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class TtTest {
	public static void main(String[] args) throws Exception {
		JacksonUtils instance = JacksonUtils.getInstance();
		FileInputStream fileInputStream = new FileInputStream(new File("D:/workspace/plugin/utils/1.json"));
		List readValue = instance.getObjectMapper().readValue(fileInputStream, List.class);
		System.out.println(readValue);
		System.out.println(instance.writerJavaObject2JSON(readValue));
		List<Object> readFromFile = instance.getObjectMapper().readValue(fileInputStream, new TypeReference<List<Object>>() {});
		System.out.println(readFromFile);
		System.out.println(instance.writerJavaObject2JSON(readValue));
		
		
	}

}
