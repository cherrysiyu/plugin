package com.cherry.utils.tree;

/**
 * 排序
 * @author Cherry
 * @version 0.1
 * @Desc
 * 2014年8月8日 下午8:26:18
 */
public interface Sortable<T> {
	
	
	
	public int compareTo(T object) ;
	
	public int compareKey(T object);
	
	public int compareKey(char key);

}
