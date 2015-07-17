package com.cherry.utils.threadPool.custom;

public interface Submitter {
	/**
	 * 提交任务
	 * @param task
	 * @param executor
	 */
	void submit(Runnable task, CentralExecutor executor);
	/**
	 * 是否还有配额
	 * @return
	 */
	boolean isHasQuota();
}
