package com.cherry.utils.threadPool.custom;

import static java.lang.Thread.sleep;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

import com.cherry.utils.LogUtils;

/**
 * CentralExecutorTest
 * 
 * 
 */
public class CentralExecutorTest {
	private CentralExecutor executor;

	@After
	public void tearDown() throws Exception {
		executor.shutdown();
		if (!executor.awaitTermination(1L, TimeUnit.SECONDS))
			executor.shutdownNow();
	}

	@Test
	public void reserveInPessimism() throws Exception {
		executor = new CentralExecutor(2, Policy.PESSIMISM);
		executor.quota(Placeholder.class, CentralExecutor.reserve(1), CentralExecutor.nil());

		final Placeholder ph1 = new Placeholder();
		final Placeholder ph2 = new Placeholder();

		executor.execute(ph1);
		executor.execute(ph2);

		sleep(100L);

		ph1.running = false;
		sleep(100L);

		ph2.running = false;
	}

	@Test
	public void elasticInOptimism() throws Exception {
		executor = new CentralExecutor(3, Policy.OPTIMISM);
		executor.quota(Placeholder.class, CentralExecutor.reserve(1), CentralExecutor.elastic(1));

		final Placeholder ph1 = new Placeholder();
		final Placeholder ph2 = new Placeholder();
		final Placeholder ph3 = new Placeholder();

		executor.execute(ph1);
		executor.execute(ph2);
		executor.execute(ph3);

		sleep(100L);

		ph1.running = false;
		sleep(100L);

		ph2.running = false;
		ph3.running = false;
	}
	
	@Test
	public void priorityThread() throws Exception {
		executor = new CentralExecutor(5, Policy.OPTIMISM);
		executor.quota(Placeholder.class, CentralExecutor.reserve(1), CentralExecutor.elastic(1));
		executor.quota(PPlaceholder.class, CentralExecutor.reserve(2), CentralExecutor.elastic(1));

		final Placeholder ph1 = new Placeholder();
		ph1.setMsg("priority1 Placeholder1......");
		final Placeholder ph2 = new Placeholder();
		ph2.setMsg("priority1 Placeholder2......");
		final Placeholder ph3 = new Placeholder();
		ph3.setMsg("priority1 Placeholder3......");
		final Placeholder ph4 = new Placeholder();
		ph4.setMsg("priority1 Placeholder4......");
		final Placeholder ph5 = new Placeholder();
		ph5.setMsg("priority1 Placeholder5......");

		executor.execute(ph1);		
		executor.execute(ph2);
		executor.execute(ph3);
		executor.execute(ph4);
		executor.execute(ph5);
				
		final PPlaceholder pph1 = new PPlaceholder();
		pph1.setMsg("priority2 PPlaceholder1......");
		final PPlaceholder pph2 = new PPlaceholder();
		pph2.setMsg("priority2 PPlaceholder2......");
		final PPlaceholder pph3 = new PPlaceholder();
		pph3.setMsg("priority2 PPlaceholder3......");
		final PPlaceholder pph4 = new PPlaceholder();
		pph4.setMsg("priority2 PPlaceholder4......");
		final PPlaceholder pph5 = new PPlaceholder();
		pph5.setMsg("priority2 PPlaceholder5......");
		

		executor.execute(pph1);
		executor.execute(pph2);
		executor.execute(pph3);
		executor.execute(pph4);
		executor.execute(pph5);
		
		sleep(5000);

		LogUtils.debug("begin stop ph1");
		ph1.running = false;
		sleep(5000);
		
		LogUtils.debug("begin stop pph1");
		pph1.running = false;
		sleep(5000);
				
		LogUtils.debug("begin stop ph2,ph3");
		ph2.running = false;
		ph3.running = false;
		sleep(5000);
				
		LogUtils.debug("begin stop pph2,pph3");
		pph2.running = false;
		pph3.running = false;
		sleep(5000);
				
		LogUtils.debug("begin stop pph4,pph5");
		pph4.running = false;
		pph5.running = false;
		sleep(5000);
		LogUtils.debug("Placeholder isHasQuota:" + executor.getQuotas().get(Placeholder.class).isHasQuota());
		LogUtils.debug("PPlaceholder isHasQuota:" + executor.getQuotas().get(PPlaceholder.class).isHasQuota());
		LogUtils.debug("executor.hasUnreserved:" + executor.hasUnreserved());
		
		LogUtils.debug("begin stop ph4,ph5");
		ph4.running = false;
		ph5.running = false;	
		LogUtils.debug("Placeholder isHasQuota:" + executor.getQuotas().get(Placeholder.class).isHasQuota());
		LogUtils.debug("PPlaceholder isHasQuota:" + executor.getQuotas().get(PPlaceholder.class).isHasQuota());
		LogUtils.debug("executor.hasUnreserved:" + executor.hasUnreserved());
		sleep(30000);
		LogUtils.debug("Placeholder isHasQuota:" + executor.getQuotas().get(Placeholder.class).isHasQuota());
		LogUtils.debug("PPlaceholder isHasQuota:" + executor.getQuotas().get(PPlaceholder.class).isHasQuota());
		LogUtils.debug("executor.hasUnreserved:" + executor.hasUnreserved());
	}

	@Test(expected = RejectedExecutionException.class)
	public void unquotaedTaskCantBeExecutedInPessimism() throws Exception {
		(executor = new CentralExecutor(1, Policy.PESSIMISM)).execute(new Placeholder());
	}

	@Test(expected = IllegalArgumentException.class)
	public void noResourceForReserve() throws Exception {
		(executor = new CentralExecutor(1)).quota(Runnable.class, CentralExecutor.reserve(3), CentralExecutor
				.elastic(5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void quotaShouldNotLessThanZero() throws Exception {
		(executor = new CentralExecutor(1)).quota(Runnable.class, CentralExecutor.reserve(-1), CentralExecutor
				.elastic(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void noneReserveTaskWillNeverBeExecutedInPessimism() throws Exception {
		(executor = new CentralExecutor(1)).quota(Runnable.class, CentralExecutor.reserve(0), CentralExecutor
				.elastic(1));
	}

	private class Placeholder implements Runnable {

		public volatile boolean running = false;
		
		private String msg;

		@Override
		public void run() {
			running = true;
			try {
				while (running) {
					LogUtils.debug(msg);
					sleep(500L);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
	
	private class PPlaceholder implements Runnable {

		public volatile boolean running = false;
		
		private String msg;

		@Override
		public void run() {
			running = true;
			try {
				while (running) {
					LogUtils.debug(msg);
					sleep(500L);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}