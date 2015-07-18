package com.cherry.utils.testutil;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.cherry.utils.testutil.listener.Listeners;
import com.cherry.utils.testutil.listener.TestListener;


public class CommonTester implements TestInterface {

	private int testCount = 20;
	private int numberOfWarmUp = 10;
	private int threadCount = 1;

	private CountDownLatch measureLatch;
	private CountDownLatch warmUpLatch;
	private List<TestListener> listeners;

	public static CommonTester initCommonTester() {
		return new CommonTester();
	}

	public CommonTester setTestCount(int testCount) {
		this.testCount = testCount;
		return this;
	}

	public CommonTester setThreadCount(int threadCount) {
		this.threadCount = threadCount;
		return this;
	}

	public void measure(Runnable task) {
		measure("", task);
	}

	public void measure(String content, Runnable task) {
		this.warmUpLatch = new CountDownLatch(this.numberOfWarmUp);
		this.measureLatch = new CountDownLatch(this.testCount);
		MemoryUtils.restoreJVM();
		doWarmup(task);
		MemoryUtils.restoreJVM();
		doMeasure(content, task);
	}

	private void doMeasure(String content, Runnable task) {
		Executor executor = Executors.newFixedThreadPool(this.threadCount);
		for (int i = 0; i < this.testCount; ++i) {
			executor.execute(new TimeMeasureProxy(new TestBean(content, i,
					this.testCount, this.threadCount), task, getListeners(),
					this.measureLatch));
		}

		try {
			this.measureLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void doWarmup(Runnable task) {
		Executor executor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < this.numberOfWarmUp; ++i)
			executor.execute(new TimeMeasureProxy(new TestBean("_warmup_", i,
					this.numberOfWarmUp, 1), task, getListeners(),
					this.warmUpLatch));
		try {
			this.warmUpLatch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized List<TestListener> getListeners() {
		if (this.listeners == null)
			this.listeners = Arrays.asList(Listeners.simple());

		return this.listeners;
	}

	public void addListener(TestListener listener) {
		this.listeners.add(listener);
	}

	private static class TimeMeasureProxy implements Runnable {
		private TestBean state;
		private Runnable runnable;
		private List<TestListener> listeners;
		private CountDownLatch measureLatch;

		public TimeMeasureProxy(TestBean state, Runnable runnable,
				List<TestListener> listeners, CountDownLatch measureLatch) {
			this.state = state;
			this.runnable = runnable;
			this.listeners = listeners;
			this.measureLatch = measureLatch;
		}

		public void run() {
			this.state.startNow();
			this.runnable.run();
			this.state.endNow();
			notifyMeasurement(this.state);
			this.measureLatch.countDown();
		}

		private void notifyMeasurement(TestBean times) {
			for (Iterator<TestListener> iter = this.listeners.iterator(); iter.hasNext();) {
				TestListener listener = iter.next();
				listener.onMeasure(times);
			}
		}
	}

}
