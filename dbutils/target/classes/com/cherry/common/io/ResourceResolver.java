package com.cherry.common.io;

import java.net.URL;
import java.util.Set;

/**
 * 资源解析器
 *
 * @author: Cherry
 * @version:1.0
 * @data:Mar 16, 2012
 */
public interface ResourceResolver
{
	/**
	 * 根据文件路径获取多个资源
	 * 
	 * @param location
	 * @return
	 */
	public Set<URL> getResources( String location );
}
