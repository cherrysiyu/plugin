package com.cherry.utils.tree.dto;

public class TreeEntry {

	int cur;
	private char[] key;
	
	private String value;

	public TreeEntry(char[] key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public void resetCur(){
		this.cur=0;
	}
	public boolean hasNextKey(){
		return this.cur <key.length;
	}
	
	public char nextKey(){
		return key[cur++];
	}
	
	public String value(){
		return value;
	}
	
	@Override
	public String toString() {
		return "key:"+new String(key)+",value:"+value;
	}
	
}
