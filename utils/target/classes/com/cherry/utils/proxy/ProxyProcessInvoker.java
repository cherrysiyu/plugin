package com.cherry.utils.proxy;

import java.lang.reflect.Method;

/**
 * 代理目标对象中代理需要做的事，需要具体的实现类来实现此接口
 * @description:
 * @author:Cherry
 * @version:1.0
 * @date:Jan 10, 2013
 */
public interface ProxyProcessInvoker {
	
	/**
	 * 代理类执行方法前调用的回调方法
	 * @param method 当前代理类调用的方法
	 * @param args 当前代理类调用的方法的参数
	 */
	public void doBefore(Method method,Object... args);
	
	/**
	 * 代理类执行方法后调用的回调方法
	 * @param method 当前代理类调用的方法
	 * @param args 当前代理类调用的方法的参数
	 */
	public void doAfter(Method method,Object... args);

}
