package com.cherry.utils.testutil;


/**
 * 测试状态
 * @author:Cherry
 * @version:1.0
 * @date:Feb 2, 2013
 */
public class TestBean implements Comparable<TestBean>{
	
	  /**
	   * 测试说明
	   */
	  private String label;
	  /**
	   * 起始时间
	   */
	  private long startTime;
	  /**
	   * 结束时间
	   */
	  private long endTime;
	  
	  /**
	   * 
	   */
	  private long index;
	  /**
	   * 测试次数
	   */
	  private int testCount;
	  /**
	   * 线程数量
	   */
	  private int threadCount;

	  public TestBean(String label, long index, int testCount, int threadCount){
	    this.label = label;
	    this.testCount = testCount;
	    this.index = index;
	    this.threadCount = threadCount;
	  }

	  public long getIndex() {
	    return this.index;
	  }

	  public String getLabel() {
	    return this.label;
	  }

	  public long getStartTime() {
	    return this.startTime;
	  }

	  public long getEndTime() {
	    return this.endTime;
	  }

	  public long getMeasurements() {
	    return this.testCount;
	  }

	  public long getMeasureTime() {
	    return (this.endTime - this.startTime);
	  }

	  public int getThreadCount() {
	    return this.threadCount;
	  }

	  public void startNow() {
	    this.startTime = System.nanoTime();
	  }

	  public void endNow() {
	    this.endTime = System.nanoTime();
	  }
	  
	  @Override
	  public int compareTo(TestBean another) {
	    if (this.startTime > another.startTime)
	      return -1;
	    if (this.startTime < another.startTime) {
	      return 1;
	    }
	    return 0;
	  }

}
