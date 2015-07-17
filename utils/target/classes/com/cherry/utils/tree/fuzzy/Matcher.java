package com.cherry.utils.tree.fuzzy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.tree.dto.SearchInsertTerm;
import com.cherry.utils.tree.dto.SearchResult;


public class Matcher {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 用来查找
	 */
	private TreeNode[] nodes = null;
	/**
	 * 用来构建一棵树
	 */
	private TreeNode analyzer = new TreeNode((char)0);
	/**
	 * 数组的最大长度
	 */
	private static final int maxLength =64;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	/**
	 * 用来过滤的属性集合
	 */
	private Map<String,Set<String>> filterMap = new ConcurrentHashMap<String, Set<String>>(16,0.8f);
	public Matcher() {
		nodes = new TreeNode[maxLength];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new TreeNode((char)0);
		}
	}
	
	public  void addBatch(List<SearchInsertTerm> list){
		if(list != null && !list.isEmpty()){
			for (SearchInsertTerm searchInsertBean : list) {
				if(CommonMethod.isCollectionNotEmpty(searchInsertBean.getFilterFields()))
					filterMap.put(searchInsertBean.getValue(),searchInsertBean.getFilterFields());
				List<String> searchFileds = searchInsertBean.getSearchFileds();
				for (String key : searchFileds) {
					add(key,searchInsertBean.getValue());
				}
			}
		}
	}
	
	
	public void add(SearchInsertTerm bean) {
		if(bean != null){
			if(CommonMethod.isCollectionNotEmpty(bean.getFilterFields()))
				filterMap.put(bean.getValue(),bean.getFilterFields());
			for (String key : bean.getSearchFileds()) {
				add(key,bean.getValue());
			}
		}
	}

	public void add(String key, String value) {
		if(key.length()>maxLength){
			logger.info(new StringBuilder("搜索的key[").append(key).append("]超度超过").append(maxLength).append("个字符,value:").append(value).toString());
			key = StringUtils.substring(value, 0, maxLength);
		}else if(key.trim().length() == 0){
			logger.info("搜索的key长度位0，value:"+value);
			return;
		}else{
			key = key.trim().toLowerCase();
			char[] array0 = key.toCharArray();
			lock.writeLock().lock();
			try{
				analyzer.addNode(array0, value);
				addKV(array0,value);
			}finally{
				lock.writeLock().unlock();
			}
		}
		
	}

	private void addKV(char[] key, String data) {
		if(key.length>maxLength){
			logger.info(new StringBuilder("搜索的key[").append(key).append("]超度超过").append(maxLength).append("个字符,value:").append(data).toString());
			return;
		}
		for (int i = 0; i < key.length && i<maxLength; i++) {
			char[] temp = new char[key.length-i];
			System.arraycopy(key, i, temp, 0, temp.length);
			nodes[i].addNode(temp, data);
		}
	}
	
	public Set<String> match(String strKey,int start,int pageSize,Set<String> filterFields){
		SearchResult result = new SearchResult(pageSize, start,filterFields,filterMap);
		char[] key = strKey.trim().toCharArray();
		lock.readLock().lock();
		try{
			for (int i = 0; i < nodes.length && !result.isFull(); i++) {
				nodes[i].searchValue(result, key);
			}
			for (int i = 0; i < key.length && !result.isFull(); i++) {
				analyzer.searchCValue(result, key, i);
			}
		}finally{
			lock.readLock().unlock();
		}
		return result.set;
	}
	
	public Set<String> matcherWithFields(List<String> keys,int start,int pageSize,Set<String> filterFields){
		SearchResult result = new SearchResult(pageSize, start,filterFields,filterMap);
		char[] key;
		lock.readLock().lock();
		try{
			for (String keyStr : keys) {
				key = keyStr.trim().toCharArray();
				for (int i = 0; i < nodes.length && !result.isFull(); i++) {
					nodes[i].searchValue(result, key);
				}
				for (int i = 0; i < key.length && !result.isFull(); i++) {
					analyzer.searchCValue(result, key, i);
				}
			}
		}finally{
			lock.readLock().unlock();
		}
		return result.set;
		
	}
	
}
