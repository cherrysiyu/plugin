package com.cherry.utils.tree.fuzzy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cherry.utils.tree.dto.SearchResult;
import com.cherry.utils.tree.dto.TreeEntry;

public class TreeNode implements Comparable<TreeNode>{
	/**
	 * 节点id
	 */
	public char id;
	/**
	 * 子节点
	 */
	public List<TreeNode> children;
	/**
	 * 内容
	 */
	public List<String> dic;
	
	public TreeNode(char id) {
		super();
		this.id = id;
	}
	
	
	
	public SearchResult searchValue(SearchResult result,char[] key){
		if(key == null || key.length == 0)
			return result;
		int cur = 0;
		int index = indexOf(key[cur]);
		TreeNode temp = this;
		while(index >= 0&& cur++ < key.length){
			temp = temp.children.get(index);
			if(cur == key.length)
				return temp.getValues(result);
			else
				index = temp.indexOf(key[cur]);
		}
		return result;
	}
	
	public SearchResult searchCValue(SearchResult result,char[] key,int offset){
		if(key == null || key.length == 0)
			return result;
		int index = indexOf(key[offset]);
		TreeNode temp = this;
		while(index >=0 && offset++ <key.length){
			temp = temp.children.get(index);
			if(temp.dic != null && temp.dic.size()>0){
				for(int i = 0;i<temp.dic.size() && !result.isFull();i++){
					if(result.start())
						result.addWord(temp.dic.get(i));
					result.next();
				}
			}
			if(offset == key.length)
				return result;
			else
				index = temp.indexOf(key[offset]);
		}
		return result;
	}
	
	
	
	public SearchResult getValues(SearchResult result) {
		if(dic != null && dic.size()>0){
			for (int i = 0; i < dic.size() && !result.isFull(); i++) {
				if(result.start())
					result.addWord(dic.get(i));
				result.next();
			}
		}
		if(children != null && children.size()>0){
			for (int i = 0; i < children.size() && !result.isFull(); i++) {
				children.get(i).getValues(result);
			}
		}
		return result;
	}
	
	public void addNode(char[]key,String value){
		this.insertNode(new TreeEntry(key,value));
	}
	
	
	private int insertNode(TreeEntry entry) {
		TreeNode node = null;
		char key = entry.nextKey();
		int index = insertSearchNode(key);
		if(index >= 0){
			node = new TreeNode(key);
			if(children == null)
				children = new ArrayList<TreeNode>();
			children.add(index, node);
		}else//该节点已经存在
			node = this.children.get(-index -1);
		
		if(!entry.hasNextKey())
			node.insertTerm(entry.value());
		else
			node.insertNode(entry);
		
		return index;
	}
	
	
	
	
	
	
	@Override
	public int compareTo(TreeNode o) {
		return compareKey(o.id);
	}
	
	private int compareKey(char key) {
		char u1 = Character.toUpperCase(this.id);
		char u2 = Character.toUpperCase(key);
		if(u1>u2)
			return 1;
		else if(u1 <u2)
			return -1;
		else
			return 0;
	}
	
	
	private int indexOf(char key) {
		if(children == null)
			return -1;
		else
			return Collections.binarySearch(children, new TreeNode(key));
	}
	
	
	private int insertSearchNode(char key){
		int low = 0;
		int high = children == null ? -1 : (children.size() - 1);
		while (low <= high) {
			 int mid = (low + high) >>> 1;
			 int cmp = children.get(mid).compareKey(key);
			 if(cmp <0)
					low = mid +1;
				else if(cmp >0)
					high = mid -1;
				else
					return -(mid +1);//key found
		}
		return low;//key not found;
	}
	
	private int insertSearchTerm(String word) {
		int low = 0;
		int high = (dic == null ? -1 : (dic.size() - 1));
		while (low <= high) {
			 int mid = (low + high) >>> 1;
			 int cmp =  CompareUtils.stringValueComparator.compare(dic.get(mid),word);
			 if(cmp <0)
					low = mid +1;
				else if(cmp >0)
					high = mid -1;
				else
					return -(mid +1);//key found
		}
		return low;//key not found;
	}
	
	private int insertTerm(String word){
		int index = insertSearchTerm(word);
		if(index >= 0){
			if(dic == null){
				dic = new ArrayList<String>();
			}
			dic.add(index,word);
		}
		return index;
	}
	
	
}
