package com.cherry.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestBaseMethod {
	
	@Test
	public void testSeprator() {
		List<String> list = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();
		List<Double> list3 = new ArrayList<Double>();
		for (int i = 0; i < 10; i++) {
			list.add(new Random().nextFloat()+"");
			list2.add(i);
			list3.add(new Random().nextDouble());
		}
		
		System.out.println(CommonMethod.getSeparaterString(",", list));
		System.out.println(CommonMethod.getSeparaterString(";", list2));
		System.out.println(CommonMethod.getSQLSeparaterString(list3.toArray(new String[]{})));
	}

}
