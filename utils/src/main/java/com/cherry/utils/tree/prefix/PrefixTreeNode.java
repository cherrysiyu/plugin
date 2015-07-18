package com.cherry.utils.tree.prefix;

import java.util.HashMap;
import java.util.Map;

public class PrefixTreeNode implements Comparable<PrefixTreeNode>{

	//公用字典表，存储字
		private static final Map<Character , Character> charMap = new HashMap<Character , Character>(16 , 0.95f);
		//数组大小上限
		private static final int ARRAY_LENGTH_LIMIT = 3;
		//Map存储结构
		private Map<Character , PrefixTreeNode> childrenMap;
		//数组方式存储结构
		private PrefixTreeNode[] childrenArray;
		
		//当前节点上存储的字符
		private Character nodeChar;
		//当前节点存储的Segment数目
		//storeSize <=ARRAY_LENGTH_LIMIT ，使用数组存储， storeSize >ARRAY_LENGTH_LIMIT ,则使用Map存储
		private int storeSize = 0;
		//当前DictSegment状态 ,默认 0 , 1表示从根节点到当前节点的路径表示一个词
		private int nodeState = 0;	
	
		PrefixTreeNode(Character nodeChar){
			if(nodeChar == null){
				throw new IllegalArgumentException("参数为空异常，字符不能为空");
			}
			this.nodeChar = nodeChar;
		}

		Character getNodeChar() {
			return nodeChar;
		}
	
		
		/**
		 * 判断是否有下一个节点
		 * @return
		 */
		boolean hasNextNode(){
			return  this.storeSize > 0;
		}
		
		
		
		
		
		
		
		
		
		
		
		
	
	
		/**
		 * 获取数组容器
		 * 线程同步方法
		 */
		private PrefixTreeNode[] getChildrenArray(){
			if(this.childrenArray == null){
				synchronized(this){
					if(this.childrenArray == null){
						this.childrenArray = new PrefixTreeNode[ARRAY_LENGTH_LIMIT];
					}
				}
			}
			return this.childrenArray;
		}
		
		/**
		 * 获取Map容器
		 * 线程同步方法
		 */	
		private Map<Character , PrefixTreeNode> getChildrenMap(){
			if(this.childrenMap == null){
				synchronized(this){
					if(this.childrenMap == null){
						this.childrenMap = new HashMap<Character , PrefixTreeNode>(ARRAY_LENGTH_LIMIT * 2,0.8f);
					}
				}
			}
			return this.childrenMap;
		}
		
		/**
		 * 将数组中的segment迁移到Map中
		 * @param segmentArray
		 */
		private void migrate(PrefixTreeNode[] segmentArray , Map<Character , PrefixTreeNode> segmentMap){
			for(PrefixTreeNode segment : segmentArray){
				if(segment != null){
					segmentMap.put(segment.nodeChar, segment);
				}
			}
		}
	
	@Override
	public int compareTo(PrefixTreeNode o) {
		return this.nodeChar.compareTo(nodeChar);
	}
	
	
	

}
