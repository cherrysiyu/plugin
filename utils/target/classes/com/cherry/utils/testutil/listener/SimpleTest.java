package com.cherry.utils.testutil.listener;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.cherry.utils.LogUtils;
import com.cherry.utils.testutil.TestBean;

public class SimpleTest implements TestListener {

	private static final DecimalFormat integerFormat = new DecimalFormat("#,##0.00");
	private List<TestBean> timesList = new ArrayList<TestBean>();
	private AtomicInteger count = new AtomicInteger();
	private AtomicLong startTime = new AtomicLong();

	@Override
	public void onMeasure(TestBean testBean) {
		this.count.getAndIncrement();
		this.startTime.compareAndSet(0L, System.nanoTime());
		outputMeasureInfo(testBean);
	}

	private void outputMeasureInfo(TestBean testBean) {
		synchronized (this.timesList) {
			if (this.timesList.size() % 50 == 0) 
				LogUtils.debug("");
			LogUtils.debug(testBean.getIndex() + ".");
		}

		if (isEnd(testBean)) {
			long total = System.nanoTime() - this.startTime.get();
			this.count.set(0);
			this.startTime.set(0L);
			StringBuffer sb = new StringBuffer("\n");
		      sb.append(testBean.getLabel() + "\t").append("avg: ")
		      .append(format(total / testBean.getMeasurements() / 1000000.0D))
					.append(" ms\t").append("total: ")
					.append(format(total / 1000000.0D)).append(" ms\t")
					.append("tps: ")
					.append(format(testBean.getMeasurements() / (total/ 1000000000.0D)))
					.append("\t").append("running: ").append(testBean.getMeasurements())
					.append(" times\t").append("in ")
					.append(testBean.getThreadCount()).append(" Threads");

			if (!(testBean.getLabel().equals("_warmup_"))) {
				LogUtils.info(sb.toString());
				return;
			}
			LogUtils.debug(sb.toString());
		}
	}

	private String format(double value) {
		return integerFormat.format(value);
	}

	private boolean isEnd(TestBean state) {
		return (this.count.get() == state.getMeasurements());
	}

}
