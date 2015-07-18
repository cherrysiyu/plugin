package com.cherry.utils.tree.dto;

import java.util.List;
import java.util.Set;

public class SearchInsertTerm {
	/**
	 * 可以被搜索到的值(通过条件可以定位到的value值)
	 */
	private List<String> searchFileds;
	/**
	 * 需要被搜索出来的值
	 */
	private String value;
	
	/**
	 * 需要过滤的字段
	 */
	private Set<String> filterFields;
	
	public SearchInsertTerm(List<String> searchFileds, String value) {
		super();
		if(searchFileds == null || searchFileds.isEmpty())
			throw new IllegalArgumentException("searchFileds is empty");
		this.searchFileds = searchFileds;
		if(value == null || "".equals(value.trim()))
			throw new IllegalArgumentException("value is empty");
		this.value = value;
	}
	
	
	public SearchInsertTerm(List<String> searchFileds, String value,
			Set<String> filterFields) {
		this(searchFileds, value);
		this.filterFields = filterFields;
	}


	public List<String> getSearchFileds() {
		return searchFileds;
	}
	public String getValue() {
		return value;
	}
	public Set<String> getFilterFields() {
		return filterFields;
	}
	public void setFilterFields(Set<String> filterFields) {
		this.filterFields = filterFields;
	}
	
	
}
