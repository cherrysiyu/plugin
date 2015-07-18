package com.cherry.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestMethods {
	
	@Test
	public  void testMethods() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++) {
			list.add(i+1);
		}
		
		String separaterString = CommonMethod.getSeparaterString(",", list);
		LogUtils.debug(separaterString);
		
		
		//LogUtils.debug(CommonMethod.isArrayEmpty(null));
	//	LogUtils.debug(CommonMethod.isArrayEmpty(1));
		///LogUtils.debug(CommonMethod.isArrayEmpty(new String[]{}));
		//LogUtils.debug(CommonMethod.isArrayEmpty(new String[]{"1","2"}));
		
		LogUtils.debug(CommonMethod.isArrayNotEmpty(null));
		LogUtils.debug(CommonMethod.isArrayNotEmpty(1));
		LogUtils.debug(CommonMethod.isArrayNotEmpty(new String[]{}));
		LogUtils.debug(CommonMethod.isArrayNotEmpty(new String[]{"1","2"}));
	}

}
