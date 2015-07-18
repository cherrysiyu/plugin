package com.cherry.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class TestMap {
	private static int testCount = 1000;
	private static int threadsCount = 3;

	@Test
	public void testHashTable() {
		Hashtable<Integer, Integer> map = new Hashtable<Integer, Integer>();
		TestUtils.createTest(testCount, threadsCount, "[Hashtable]", new MapThread(map));
	}

	@Test
	public void testSyncHashMap() {
		Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		TestUtils.createTest(testCount, threadsCount, "[SyncHashMap]", new MapThread(map));
	}

	@Test
	public void testCurrentHashMap() {
		Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
		TestUtils.createTest(testCount, threadsCount, "[ConcurrentHashMap]", new MapThread(map));
	}

}

class MapThread implements Runnable {

	private Map<Integer, Integer> map = null;

	public MapThread(Map<Integer, Integer> map) {
		super();
		this.map = map;
	}

	@Override
	public void run() {
		Random random = new Random(10000L);
		for (int i = 0; i < 1000; ++i) {
			map.put(Integer.valueOf(i), Integer.valueOf(i));
			if (i % 2 == 0)
				for (int j = 0; j < 3; ++j)
					map.get(Integer.valueOf(random.nextInt()));
		}
	}
}
