/**
 * 
 */
package com.cherry.common.utils;

/**
 * 
 * @description:定义用指定分隔符分隔对象规则接口
 * @author:Cherry
 * @vertion:1.0
 * @data:2011-11-23
 */
public interface SeparaterRule<T> {
	public String getString( T object );
}
