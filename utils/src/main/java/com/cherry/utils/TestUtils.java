package com.cherry.utils;

import com.cherry.utils.testutil.CommonTester;


/**
 * 测试工具类，负责多个线程的测试
 * @author:Cherry
 * @version:1.0
 * @date:Mar 26, 2013
 */
public class TestUtils {
	
	private TestUtils(){}
	
	/**
	 * 指定测试任务测试，线程个数和测试次数默认为都为1，测试说明为空字符串
	 * @param task 测试的任务
	 */
	public static void createTest(Runnable task){
		createTest(1,1,task);
	}
	
	/**
	 * 指定测试的说明和测试的任务测试，线程个数和测试次数默认为都为1
	 * @param testContent 测试说明
	 * @param task 测试的任务
	 */
	public static void createTest(String testContent,Runnable task){
		createTest(1,1,testContent,task);
	}
	
	/**
	 * 指定线程个数和测试次数和任务测试，测试说明为空字符串
	 * @param testCount 测试次数
	 * @param threadCount 测试的线程数量
	 * @param task 测试的任务
	 */
	public static void createTest(int testCount,int threadCount,Runnable task){
		createTest(testCount,threadCount,"",task);
	}
	
	/**
	 * 指定测试循环次数、线程个数、测试说明和测试任务进行测试
	 * @param testCount 测试循环次数
	 * @param threadCount 线程的个数
	 * @param testContent 测试说明
	 * @param task 测试的任务
	 */
	public static void createTest(int testCount,int threadCount,String testContent,Runnable task){
		CommonTester.initCommonTester().setTestCount(testCount)
					.setThreadCount(threadCount).measure(testContent, task);
	}

}
