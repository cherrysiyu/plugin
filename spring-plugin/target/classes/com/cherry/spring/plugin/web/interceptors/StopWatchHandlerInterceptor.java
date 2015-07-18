package com.cherry.spring.plugin.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 性能监控,此拦截器应该放在拦截器链的第一个，这样得到的时间才最准确
  @description:拦截器是单例的，因此不管用户请求多少次都只有一个拦截器实现，即线程不安全.
  				采用ThreadLocal解决此问题.
  @version:0.1
  @author:Cherry
  @date:Aug 2, 2013
 */
public class StopWatchHandlerInterceptor extends HandlerInterceptorAdapter{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	private NamedThreadLocal<Long>  startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();//开始时间
	    startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）
	     return true;//继续流程
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;//3、消耗的时间
        if(consumeTime > 1000) {//此处认为处理时间超过1000毫秒的请求为慢请求
            //TODO 记录到日志文件
        	logger.info(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));
        }
	}
	
}
