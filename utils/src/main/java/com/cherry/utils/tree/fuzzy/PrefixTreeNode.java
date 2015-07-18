package com.cherry.utils.tree.fuzzy;


public class PrefixTreeNode /*implements Sortable<PrefixTreeNode>*/{
	/**
	 * 节点id
	 *//*
	private char id;
	*//**
	 * 子节点
	 *//*
	private PrefixTreeNode[] children;
	*//**
	 * 类容
	 *//*
	private Object[] dic;
	
	public PrefixTreeNode(char id) {
		super();
		this.id = id;
	}
	
	public SearchResult searchValue(SearchResult result,char[] key){
		if(key == null || key.length == 0)
			return result;
		int cur = 0;
		int index = indexOf(key[cur]);
		PrefixTreeNode temp = this;
		while(index >= 0&& cur++ < key.length){
			temp = temp.children[index];
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
		PrefixTreeNode temp = this;
		while(index >=0 && offset++ <key.length){
			temp = temp.children[index];
			if(temp.dic != null && temp.dic.length>0){
				for(int i = 0;i<temp.dic.length && !result.isFull();i++){
					if(result.start())
						result.addWord(temp.dic[i]);
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
		if(dic != null && dic.length>0){
			for (int i = 0; i < dic.length && !result.isFull(); i++) {
				if(result.start())
					result.addWord(dic[i]);
				result.next();
			}
		}
		if(children != null && children.length>0){
			for (int i = 0; i < children.length && !result.isFull(); i++) {
				children[i].getValues(result);
			}
		}
		return result;
	}

	private int indexOf(char key) {
		int low = 0;
		int high = children == null ? -1 : (children.length - 1);
		while (low <= high) {
		    int mid = (low + high) >>> 1;
			int cmp = children[mid].compareKey(key);
			if(cmp <0)
				low = mid +1;
			else if(cmp >0)
				high = mid -1;
			else
				return mid; // key found
		}
		return -(low + 1);  // key not found.
	}
	
	public void addNode(char[]key,Object value){
		this.insertNode(new Entry(key,value));
	}
	
	
	private int insertNode(Entry entry) {
		PrefixTreeNode node = null;
		char key = entry.nextKey();
		int index = insertSearchNode(key);
		if(index >= 0){
			node = new PrefixTreeNode(key);
			PrefixTreeNode[] newChildren = new PrefixTreeNode[(children == null?0:children.length)+1];
			if(children != null){
				System.arraycopy(children, 0, newChildren, 0, index);
				System.arraycopy(children, index, newChildren, index+1, children.length-index);
			}
			newChildren[index] = node;
			this.children = newChildren;
		}else//该节点已经存在
			node = this.children[-index -1];
		
		if(!entry.hasNextKey())
			node.insertTerm(entry.value());
		else
			node.insertNode(entry);
		
		return index;
	}
	
	
	private int insertSearchNode(char key){
		
		int low = 0;
		int high = children == null ? -1 : (children.length - 1);
		while (low <= high) {
			 int mid = (low + high) >>> 1;
			 int cmp = children[mid].compareKey(key);
			 if(cmp <0)
					low = mid +1;
				else if(cmp >0)
					high = mid -1;
				else
					return -(mid +1);//key found
		}
		return low;//key not found;
	}
	
	private int insertTerm(Object word){
		int index = insertSearchTerm(word);
		if(index >= 0){
			Object[] newdic = new Object[(dic == null?0:dic.length)+1];
			if(newdic.length == 1){
				newdic[0] = word;
				this.dic = newdic;
			}else{
				System.arraycopy(dic, 0, newdic, 0, index);
				System.arraycopy(dic, index, newdic,index+1, dic.length-index);
				newdic[index] = word;
				this.dic = newdic;
			}
		}
		return index;
	}
	
	private int insertSearchTerm(Object word) {
		int low = 0;
		int high = (dic == null ? -1 : (dic.length - 1));
		while (low <= high) {
			 int mid = (low + high) >>> 1;
			 int cmp = compare(dic[mid],word);
			 if(cmp <0)
					low = mid +1;
				else if(cmp >0)
					high = mid -1;
				else
					return -(mid +1);//key found
		}
		return low;//key not found;
	}

	@SuppressWarnings("unchecked")
	private int compare(Object object, Object object2) {
		if(object instanceof char[]){
			char[] c1 = String.valueOf(object).toCharArray();
			char[] c2 = String.valueOf(object2).toCharArray();
			return compare(c1, c2);
		}else
		return ((Comparable<Object>)object).compareTo((Comparable<Object>)object2);
	}

	private int compare(char[] c1, char[] c2) {
		int s1 = c1.length;
		int s2 = c2.length;
		for (int i = 0,i2=0; i < s1 && i2 <s2; i++,i2++) {
			char cc1 = c1[i];
			char cc2 = c2[i2];
			if(cc1 != cc2){
				cc1 = Character.toUpperCase(cc1);
				cc2 = Character.toUpperCase(cc2);
				if(cc1 != cc2)
					return cc1-cc2;
			}
		}
		return s1 - s2;
	}
	
	
	
	@Override
	public int compareTo(PrefixTreeNode node) {
		return compareKey(node.id);
	}

	public int compareKey(PrefixTreeNode node) {
		char u1 = Character.toUpperCase(this.id);
		char u2 = Character.toUpperCase(node.id);
		if(u1>u2)
			return 1;
		else if(u1 <u2)
			return -1;
		else
			return 0;
	}
	
	@Override
	public int compareKey(char key) {
		char u1 = Character.toUpperCase(this.id);
		char u2 = Character.toUpperCase(key);
		if(u1>u2)
			return 1;
		else if(u1 <u2)
			return -1;
		else
			return 0;
	}
	
	
	*/

}
