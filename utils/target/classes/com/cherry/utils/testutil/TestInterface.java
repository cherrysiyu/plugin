package com.cherry.utils.testutil;


public interface TestInterface {
	
	public abstract void measure(Runnable paramRunnable);

	public abstract void measure(String paramString, Runnable paramRunnable);
}
