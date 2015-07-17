package com.cherry.utils.proxy;

import java.lang.reflect.Method;

import org.junit.Test;

import com.cherry.utils.LogUtils;

public class ProxyFactoryTest {
	
	@Test
	public void testProxy() {
		
		UserService service1 = ProxyFactory.getJDKProxy(new UserServiceImpl(),new JDKProxyProcessInveoker());
		service1.add("username","password");
		service1.delete("username");
		service1.update("username","username2");
		
		LogUtils.debug("=================================");
		UserService service2 = ProxyFactory.getCGlibProxy(new UserServiceImpl(),new CglibProxyProcessInveoker());
		service2.add("username","password");
		service2.delete("username");
		service2.update("username","username2");
		
		
	}
	

}
class JDKProxyProcessInveoker implements ProxyProcessInvoker{

	@Override
	public void doAfter(Method method, Object... args) {
		LogUtils.debug("调用方法结束了....");
		
	}

	@Override
	public void doBefore(Method method, Object... args) {
		LogUtils.debug(method.getName()+"------checkSecuerity------");
	}
}
class CglibProxyProcessInveoker implements ProxyProcessInvoker{

	@Override
	public void doBefore(Method method, Object... args) {
		LogUtils.debug("执行方法：" + method.getName());
		if (args != null && args.length != 0) {
			for (int i = 0; i < args.length; i++)
				LogUtils.debug("参数" + (i+1) + "：" + args[i].toString());
		}
	}
	
	@Override
	public void doAfter(Method method, Object... args) {
		LogUtils.debug("执行方法：" + method.getName()+"记录日志结束");
	}
}