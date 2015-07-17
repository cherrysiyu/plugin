package com.cherry.utils.testutil.listener;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

import com.cherry.utils.LogUtils;
import com.cherry.utils.testutil.MemoryUtils;
import com.cherry.utils.testutil.TestBean;

public class MemoryUsage implements TestListener{
	/**
	 * 数据格式化,保留3位小数
	 */
	private static final DecimalFormat integerFormat = new DecimalFormat("#,##0.000");
	/**
	 * 测试次数
	 */
	private AtomicInteger count = new AtomicInteger();
	
	@Override
	public void onMeasure(TestBean testBean) {
		 this.count.getAndIncrement();
		   outputMeasureInfo(testBean);
	}

	private void outputMeasureInfo(TestBean testBean) {
		 //如果测试结束，测把测试中内存的使用及测试时间等输出
		 if (isEnd(testBean)) {
		      StringBuffer sb = new StringBuffer("\n");
		      sb.append("memory-usage: ")
		      	 .append(testBean.getLabel())
		      	 .append("\t")
		      	 .append(format(MemoryUtils.memoryUsed() / 1000.0D))
		      	 .append(" Kb\n");
		      //计数器重置
		      this.count.set(0);
		      //输出测试情况
		      if (!(testBean.getLabel().equals("_warmup_"))) {
		        LogUtils.info(sb.toString());
		        return;
		      }
		      //如果日志是debug的状态
		      LogUtils.debug(sb.toString());
		    }
		
	}
	
	
	private String format(double value){
	    return integerFormat.format(value);
	 }
	/**
	 * 是否测试结束
	 * @param state
	 * @return
	 */
	private boolean isEnd(TestBean state) {
	    return (this.count.get() == state.getMeasurements());
	}
}
