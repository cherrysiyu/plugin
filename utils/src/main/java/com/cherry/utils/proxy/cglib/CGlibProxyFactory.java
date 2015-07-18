package com.cherry.utils.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.cherry.utils.LogUtils;
import com.cherry.utils.proxy.ProxyProcessInvoker;


public class CGlibProxyFactory<T> implements MethodInterceptor {

	private Object targetObject;
	private ProxyProcessInvoker invoker;
	
	public CGlibProxyFactory(ProxyProcessInvoker invoker) {
		this.invoker = invoker;
	}
	
	public CGlibProxyFactory(Object targetObject,ProxyProcessInvoker invoker) {
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
	 * 获取CGlib的代理
	 * 
	 * @param targetObject 需要被代理的对象
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public  <T> T createCGlibProxyInstance(T targetObject) {
		 this.targetObject = targetObject;
		 Enhancer enhancer = new Enhancer(); 
		 enhancer.setSuperclass(this.getTargetObject().getClass());//不能是final的类
		 enhancer.setCallback(this);
		 return (T)enhancer.create(); 
	}
	
	/**
	 * 当代理对象的业务方法被调用的时候，会回调这个方法
	 * @proxy :代理对象本身
	 * @method:被拦截到的方法
	 * @args:方法的参数
	 * @methodProxy:方法的代理对象
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		
		//做需要做的事，调用回调函数执行
		if(invoker != null){
			invoker.doBefore(method, args);
		}
		Object result = null;
		try {
			result = methodProxy.invoke(getTargetObject(), args);
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
