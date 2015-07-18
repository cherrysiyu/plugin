package com.cherry.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * 过滤url中的sessionId
 */
public class URLSessionIdFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 跳过非http请求
		if ( ! (request instanceof HttpServletRequest) ) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 如果url中存在sessionId，清除会话
		if ( httpRequest.isRequestedSessionIdFromURL() ) {
			HttpSession session = httpRequest.getSession();
			if (null != session) {
				session.invalidate();
			}
		}
		// 重写httpResponse encodeURL、encodeRedirectURL等方法
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpResponse){
			@Override
			public String encodeRedirectURL(String url) {
				return url;
			}
			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}
			@Override
			public String encodeURL(String url) {
				return url;
			}
			@Override
			public String encodeUrl(String url) {
				return url;
			}
		};
		// 执行过程链中的下一个请求
		chain.doFilter(request, responseWrapper);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
