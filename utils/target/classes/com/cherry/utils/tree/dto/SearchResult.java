package com.cherry.utils.tree.dto;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.cherry.utils.CommonMethod;

public class SearchResult {
	/**
	 * 分页的大小
	 */
	private int pageSize;
	/**
	 * 起始位置
	 */
	private int start;
	/**
	 * 偏移量
	 */
	private int offset;
	/**
	 * 返回的最终值(搜索树中存的可以被找到的值，一般是主键)
	 */
	public Set<String> set = new LinkedHashSet<String>();
	
	/**
	 * 需要过滤的属性
	 */
	private Set<String> filterFields;
	
	/**
	 * 过滤的树形映射
	 */
	private Map<String,Set<String>> filterMap;
	
	public SearchResult(int pageSize, int start) {
		super();
		this.pageSize = pageSize;
		this.start = start;
	}
	
	public SearchResult(int pageSize, int start, Set<String> filterFields,Map<String,Set<String>> filterMap) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.filterFields = filterFields;
		this.filterMap = filterMap;
	}

	public boolean start(){
		return offset >= start;
	}
	public void next(){
		offset++;
	}
	
	public boolean isFull(){
		return this.set.size()>= pageSize;
	}
	
	public void addWord(String word){
		if(CommonMethod.isCollectionNotEmpty(filterFields)){//需要过滤额外属性
			if(CommonMethod.isMapNotEmpty(filterMap)){
				Set<String> filterSet = filterMap.get(word);//可以保证不为null
				if(filterSet.size() >= filterFields.size()){
					if(filterSet.containsAll(filterFields))
						set.add(word);
				}
			}
		}else
			set.add(word);
	}
}
