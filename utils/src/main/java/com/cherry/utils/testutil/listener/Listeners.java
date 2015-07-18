package com.cherry.utils.testutil.listener;



public final class Listeners {
	
	/**
	 * 测试和内存都包含
	 * @return
	 */
	public static TestListener[] simple(){
		return new TestListener[]{ new SimpleTest(), new MemoryUsage()};
   }
	/**
	 * 简单测试情况
	 * @return
	 */
	public static SimpleTest simpleMeasure() {
		return new SimpleTest();
	}
	/**
	 * 内存使用情况
	 * @return
	 */
	public static MemoryUsage memoryUsage() {
		return new MemoryUsage();
	}
}
