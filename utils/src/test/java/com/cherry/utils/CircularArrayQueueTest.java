package com.cherry.utils;

import java.util.Queue;

public class CircularArrayQueueTest {

	public static void main(String[] args) {
		Queue<String> q = new CircularArrayQueue<String>(5);
		q.add("Amy");
		q.add("Bob");
		q.add("Carl");
		q.add("Deedee");
		q.add("Emile");
		q.remove();
		q.add("Fifi");
		q.remove();
		for (String s : q)
			LogUtils.debug(s);
	}
}
