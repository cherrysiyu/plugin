package com.cherry.utils.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.cherry.utils.JacksonUtils;
import com.cherry.utils.LogUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class JackUtilsTest {
	
	private static JacksonUtils utils = JacksonUtils.getInstance();
	
	@SuppressWarnings("unchecked")
	@Test
	public void test1() throws Exception{
		LogUtils.debug("====================test1=============================");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 5; i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("boolean", true);
			map.put("Integer", new Integer(i+1));
			map.put("String", "fs"+i);
			map.put("double", i+0.20);
			map.put("float", i+0.25f);
			list.add(map);
		}
		
		String jsonString = utils.writerJavaObject2JSON(list);
		LogUtils.debug("jsonString:"+jsonString);
		LogUtils.debug(utils.writerJavaObject2JSON(list));
		List<Map<String,Object>> list2 = (List<Map<String,Object>>)utils.readJSON2Bean(List.class, jsonString);
		
		LogUtils.debug(list2);
		
		List<Map<String,Object>> ll = utils.readJSON2ListMap(jsonString);
		LogUtils.debug(ll);
		
		List<Map<String,Object>> ll2 =  utils.readJSON2Genric(jsonString, new TypeReference<List<Map<String,Object>>>(){});
		LogUtils.debug(ll2);
	}
	
	@Test
	public void test2()throws Exception{
		LogUtils.debug("====================test2=============================");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("boolean", true);
		map.put("Integer", new Integer(1));
		map.put("String", "fs");
		map.put("double", 0.20);
		map.put("float", 0.25f);
		
		String jsonString = utils.writerJavaObject2JSON(map);
		LogUtils.debug("jsonString:"+jsonString);
		LogUtils.debug(utils.writerJavaObject2JSON(map));
		
		LogUtils.debug(utils.readJSON2Bean(Map.class, jsonString));
		
		Map<String,Object> map2 = utils.readJSON2Map(jsonString);
		LogUtils.debug(map2);
		
		Map<String,Object> map3 = utils.readJSON2Genric(jsonString, new TypeReference<Map<String,Object>>(){});
		LogUtils.debug(map3);
	}
	
	
	@Test
	public void test3()throws Exception{
		LogUtils.debug("====================test3=============================");
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);set.add(2);set.add(null);set.add(5);
		
		String jsonString = utils.writerJavaObject2JSON(set);
		LogUtils.debug(jsonString);
		LogUtils.debug(utils.readJSON2Bean(Set.class, jsonString));
		LogUtils.debug(utils.readJSON2Genric(jsonString,  new TypeReference<Set<Integer>>(){}));
	}
	
	
	
	@Test
	public void test5()throws Exception{
		String jsonString = "{\"name\" : { \"first\" : \"Joe\", \"last\" : \"Sixpack\" }, \"gender\" : \"MALE\",\"verified\" : false,\"userImage\" : \"Rm9vYmFyIQ==\"}";
		Map<String,Object> map = utils.readJSON2Map(jsonString);
		LogUtils.debug(map.get("name"));
		LogUtils.debug(map.get("gender"));
		LogUtils.debug(map.get("verified"));
		LogUtils.debug(map.get("userImage"));
		LogUtils.debug(((Map<String,Object>)map.get("name")).get("first"));
	}
	
	@Test
	public void test6()throws Exception{
		String jsonString = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
			"{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
		List<Map<String,String>> list = utils.readJSON2Genric(jsonString, new TypeReference<List<Map<String,String>>>(){});
		LogUtils.debug(list);
		for (Map<String, String> map : list) {
			LogUtils.debug(map.get("name"));
		}
	}
	
	@Test
	public void test7()throws Exception{
		String jsonString ="{'address': 'address2','name':'haha2','id':2,'email':'email2'}}";
		Map<String,Object> map = utils.readJSON2Map(jsonString);
		LogUtils.debug(map);
	}
	
}
