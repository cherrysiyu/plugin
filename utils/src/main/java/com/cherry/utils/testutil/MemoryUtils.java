package com.cherry.utils.testutil;

import java.lang.management.ManagementFactory;

/**
 * 内存使用情况工具类
 * @author:Cherry
 * @version:1.0
 * @date:Feb 2, 2013
 */
public class MemoryUtils {
	
	/**
	 * 修复重启JVM
	 */
	public static void restoreJVM(){
	    int maxRestoreJvmLoops = 10;
	    long memUsedPrev = memoryUsed();
	    for (int i = 0; i < maxRestoreJvmLoops; ++i) {
	      System.runFinalization();
	      System.gc();
	      long memUsedNow = memoryUsed();
	      if ((ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount() == 0) &&(memUsedNow >= memUsedPrev))
	        return;

	      memUsedPrev = memUsedNow;
	    }
	  }
	  /**
	   * 当前jvm中内存的使用
	   * @return
	   */
	  public static long memoryUsed(){
	    Runtime rt = Runtime.getRuntime();
	    return (rt.totalMemory() - rt.freeMemory());
	  }
}
