package com.cherry.utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;


public class TestList {
	private static int testCount = 10;
	private static int threadsCount =2;
	
	@Test
	public void testVector(){
		List<Integer> vector = new Vector<Integer>();
		TestUtils.createTest(testCount, threadsCount, "[Vector]", new ListThread(vector));
	}
	@Test
	public void testArrayList(){
		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
		TestUtils.createTest(testCount, threadsCount, "[ArrayList]", new ListThread(list));
	}
	@Test
	public void testCopyOnWriteArrayList(){
		List<Integer> list = new CopyOnWriteArrayList<Integer>();
		TestUtils.createTest(testCount, threadsCount, "[CopyOnWriteArrayList]", new ListThread(list));
	}
	
	

}
class ListThread implements Runnable {

	private List<Integer> list ;
	
	public ListThread(List<Integer> list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000; ++i) {
			list.add(Integer.valueOf(i));
			if (i % 2 == 0){
				for (int j = 0; j < 3; ++j)
					list.get(0);
			}
		}
		
		
	}
	
	
	
}