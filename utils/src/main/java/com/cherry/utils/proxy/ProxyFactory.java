package com.cherry.utils.proxy;

import com.cherry.utils.proxy.cglib.CGlibProxyFactory;
import com.cherry.utils.proxy.jdk.JDKProxyFactory;




public class ProxyFactory {
	
	private ProxyFactory(){}

	/**
	 * 获取JDK的代理
	 * 
	 * @param targetObject 需要被代理的对象
	 * @param ProxyProcessInvoker 回调函数
	 * @return
	 */
	public static <T> T getJDKProxy(T targetObject,ProxyProcessInvoker invoker) {
		JDKProxyFactory<T> proxy = new JDKProxyFactory<T>(invoker);
		return  proxy.createJDKProxyInstance(targetObject);
	}
	
	/**
	 * 获取CGlib的代理
	 * @param targetObject 需要被代理的对象
	 * @param ProxyProcessInvoker 回调函数
	 * @return 代理对象
	 */
	public static <T> T getCGlibProxy(T targetObject,ProxyProcessInvoker invoker){
		
		CGlibProxyFactory<T> proxy = new CGlibProxyFactory<T>(invoker);
		return proxy.createCGlibProxyInstance(targetObject);
	}

}
