package com.cherry.utils.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonJsontestUtil {

	  private static ComBean comBean;

	    static {
	        String stringValue = "测试字符串";
	        int intValue = 123456;
	        double doubleValue = 123456.134456;
	        long longValue = 1234567890L;
	        boolean booleanValue = true;
	        Map<String, Object> mapValue = new HashMap<String, Object>();
	        List<Object> listValue = new ArrayList<Object>();

	        mapValue.put("key1", "value1");
	        mapValue.put("key2", "value2");
	        mapValue.put("key3", new ComBean("a string", 123, 123.123, 123L, false,
	                new HashMap<String, Object>(), new ArrayList<Object>()));

	        listValue.add("value1");
	        listValue.add("value2");
	        listValue.add(mapValue.get("key3"));

	        comBean = new ComBean(stringValue, intValue, doubleValue, longValue,
	                booleanValue, mapValue, listValue);
	    }

	    public static ComBean getTestDatas() {
	        return comBean;
	    }
	    
}
