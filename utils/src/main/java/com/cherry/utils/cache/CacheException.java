package com.cherry.utils.cache;
/**
 * Cache的异常处理
 * @author:Cherry
 * @version:1.0
 * @date:May 10, 2013
 */
public class CacheException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6827389506048068830L;

	public CacheException(String s) {
		super(s);
	}

	public CacheException(String s, Throwable e) {
		super(s, e);
	}

	public CacheException(Throwable e) {
		super(e);
	}
}
