package com.cherry.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 语言切换过滤器
  @description:
  @version:0.1
  @author:Cherry
  @date:Sep 9, 2013
 */
public class LocaleFilter implements Filter {       
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
     if (request instanceof HttpServletRequest &&  ((HttpServletRequest)request).getSession().getAttribute("localeValue") != null) {  
            chain.doFilter(new LocaleRequestWrapper((HttpServletRequest)request), response);  
        } else {  
            chain.doFilter(request, response);  
        }  
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}  
  
}  
