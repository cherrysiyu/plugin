package com.cherry.web.filter;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 设置request 里面的getLocale()
  @description:
  @version:0.1
  @author:Cherry
  @date:Sep 9, 2013
 */
public class LocaleRequestWrapper extends HttpServletRequestWrapper {  
  
        public LocaleRequestWrapper(HttpServletRequest req) {  
            super(req);  
        }  
        
        /**
         * 得到语言集合
         */
        @Override
        public Enumeration<Locale> getLocales() {  
            Vector<Locale> v = new Vector<Locale>(1);  
            v.add(getLocale());
            return v.elements();  
        }  
        /**
         * 从request中获取Locale对象
         */
        @Override
        public Locale getLocale() {  
            return new Locale(((HttpServletRequest)getRequest()).getSession().getAttribute("localeValue").toString());  
        }  
    }  
