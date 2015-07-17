package com.cherry.utils.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.cherry.utils.LogUtils;
import com.cherry.utils.proxy.ProxyProcessInvoker;



public class JDKProxyFactory<T> implements InvocationHandler {

	private Object targetObject;
	private ProxyProcessInvoker invoker;
	
	public JDKProxyFactory(ProxyProcessInvoker invoker) {
		this.invoker = invoker;
	}
	
	public JDKProxyFactory(Object targetObject,ProxyProcessInvoker invoker) {
		this.targetObject = targetObject;
		this.invoker = invoker;
	}
	
	public Object getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}
	
	public ProxyProcessInvoker getInvoker() {
		return invoker;
	}

	public void setInvoker(ProxyProcessInvoker invoker) {
		this.invoker = invoker;
	}

	/**
	 * 获取JDK的代理
	 * 
	 * @param targetObject 需要被代理的对象
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public  <T> T createJDKProxyInstance(T targetObject) {
		 this.targetObject = targetObject;
		return (T)Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject
				.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		
		if(invoker != null){
			invoker.doBefore(method, args);
		}
		Object result = null;
		try {
			result = method.invoke(getTargetObject(), args);
		} catch (Exception e) {
			LogUtils.error(e);
			throw new RuntimeException(e);
		}
		if(invoker != null){
			invoker.doAfter(method, args);
		}
		return result;
	}

}
