package com.cherry.utils.tree.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTree<E> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 根节点的nodeId
	 */
	public static final String rootNodeId = "0";
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * Read lock 
     */
    final Lock readlock = lock.readLock(); // read lock
    /**
     * Write lock 
     */
    final Lock writeLock = lock.writeLock(); // write lock
    //根节点
    private SearchTreeNode<E> root;
    
    public SearchTree() {
		this(null);
	}
	public SearchTree(E rootValue) {
		root = new SearchTreeNode<E>(rootNodeId,rootValue,0);
	}

	/**
     * 插入节点信息
     * @param treeNodes
     * @return 插入成功的个数
     */
    public int addAll(List<SearchTreeNode<E>> treeNodes){
    	writeLock.lock();
    	int totalSize = treeNodes.size();
    	Collections.sort(treeNodes, AbstractTreeNodeCompare.nodeLevelComparator);
		try {
			 Queue<SearchTreeNode<E>> queue = new LinkedList<SearchTreeNode<E>>(treeNodes);
			 int size = 0;
			 while(!queue.isEmpty() && size != queue.size()){//防止死循环
				 size = queue.size();
				 SearchTreeNode<E> element = queue.element();
				 SearchTreeNode<E> parentNode = root.insertNode(element);
				 if (parentNode != null) { //删除添加成功的节点
						 queue.remove();
				}else{
					//将添加失败的节点重新放入节点队列的队尾
		                queue.remove();
		                queue.add(element);
				}
			 }
			 logger.debug(new StringBuilder().append("总节点数:").append(totalSize).append(",失败的节点数:").append(size).append(",失败节点信息:").append(queue.toString()).toString());
			 /*释放内存，其实不需要关心，JVM会垃圾释放，即使这里clear也未必有用
			 queue.clear();
			 treeNodes.clear();*/
			 return totalSize-size;
		} finally {
			writeLock.unlock();
		}
    }
    
    /**
	 * 通过nodeId找到节点信息
	 * @param nodeId
	 * @return
	 */
	public SearchTreeNode<E> getTreeNodeById(String nodeId) {
		readlock.lock();
		try{
			return root.findTreeNodeById(nodeId);
		}finally{
			readlock.unlock();
		}
	}
    
	/**
	 * 通过父节点和nodeid查询node信息
	 * @param tree
	 * @param id
	 * @return
	 */
	public SearchTreeNode<E> getTreeNodeById(SearchTreeNode<E> parentNode, String nodeId) {
		if(parentNode == null)
			return getTreeNodeById(nodeId);
		readlock.lock();
		try{
			return parentNode.findTreeNodeById(nodeId);
		}finally{
			readlock.unlock();
		}
	}
    
	 /**
     * 插入一个节点
     * @param treeNode
     * @return
     */
    public boolean insertTreeNode(SearchTreeNode<E> treeNode) {
    	writeLock.lock();
		try {
			if (treeNode != null) {
				if (root.insertNode(treeNode) != null) {
					return true;
				} else
					return false;
			} else
				return false;
		} finally {
			writeLock.unlock();
		}
    }
    
    /**
     * 更新节点信息
     * @param treeNode
     * @return
     */
    public boolean updateTreeNode(SearchTreeNode<E> treeNode) {
    	writeLock.lock();
		try {
			boolean isSuccess = deleteTreeNodeById(treeNode.getNodeId());
			if (isSuccess) {
				if (root.insertNode(treeNode) != null) {
					return true;
				} else
					return false;
			} else
				return false;
		} finally {
			writeLock.unlock();
		}
    }
   
    /**
     * 通过nodeId删除节点信息(会递归向下删除掉子树)
     * @param nodeId
     */
    public boolean deleteTreeNodeById(String nodeId){
    	writeLock.lock();
		try {
				return root.deleteRecursionChildNodes(nodeId);
		} finally {
			writeLock.unlock();
		}
    }
    /**
     * 删除一个节点信息(会递归向下删除掉子树)
     * @param parentTreeNode
     * @param nodeId
     */
    public boolean deleteTreeNode(SearchTreeNode<E> parentTreeNode,String nodeId){
    	if(parentTreeNode == null)
    		return deleteTreeNodeById(nodeId);
    	writeLock.lock();
		try {
			return parentTreeNode.deleteRecursionChildNodes(nodeId);
		} finally {
			writeLock.unlock();
		}
    }
    
    /**
     * 递归得到一个节点的所有子节点
     * @param treeNode
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionChildNodes(SearchTreeNode<E> treeNode){
    	readlock.lock();
    	try{
	    	if(treeNode != null)
	    		return treeNode.getChildNodes(true);
	    	else
	    		return new ArrayList<SearchTreeNode<E>>();
	    }finally{
			readlock.unlock();
		}
    }
    /**
     * 递归得到一个节点的所有子节点
     * @param nodeId
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionChildNodes(String nodeId){
		readlock.lock();
    	try{
    		 SearchTreeNode<E> findTreeNodeById = root.findTreeNodeById(nodeId);
    		 if(findTreeNodeById == null)
    			 return new ArrayList<SearchTreeNode<E>>();
    		 else
    			 return findTreeNodeById.getChildNodes(true);
	    }finally{
			readlock.unlock();
		}
    	
    }
    /**
     * 按照指定的排序递归得到一个节点的所有子节点
     * @param treeNode
     * @param comparator
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionChildNodes(SearchTreeNode<E> treeNode,AbstractTreeNodeCompare comparator){
    	readlock.lock();
    	try{
	    	List<SearchTreeNode<E>> list = getRecursionChildNodes(treeNode);
	    	if(comparator !=null)
	    		Collections.sort(list,comparator);
	    	return list;
    	}finally{
			readlock.unlock();
		}
    }
    /**
     * 
     * @param nodeId
     * @param comparator
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionChildNodes(String nodeId,AbstractTreeNodeCompare comparator){
    	List<SearchTreeNode<E>> list = getRecursionChildNodes(nodeId);
    	readlock.lock();
    	try{
	    	if(comparator !=null)
	    		Collections.sort(list,comparator);
	    	return list;
    	}finally{
			readlock.unlock();
		}
    }
    /**
     * 得到一个节点的下一层子节点信息
     * @param treeNode
     * @return
     */
    public List<SearchTreeNode<E>> getChildNodes(SearchTreeNode<E> treeNode){
    	readlock.lock();
    	try{
	    	if(treeNode != null)
	    		return treeNode.getChildNodes(false);
	    	return new ArrayList<SearchTreeNode<E>>();
    	}finally{
			readlock.unlock();
		}
    }
    /**
     * 得到一个节点的下一层子节点信息
     * @param nodeId
     * @return
     */
    public List<SearchTreeNode<E>> getChildNodes(String nodeId){
		readlock.lock();
    	try{
    		SearchTreeNode<E> findTreeNodeById = root.findTreeNodeById(nodeId);
    		if(findTreeNodeById == null)
    			return new ArrayList<SearchTreeNode<E>>();
    		else
    		return findTreeNodeById.getChildNodes(false);
    	}finally{
			readlock.unlock();
		}
    }
    /**
     * 按照指定的排序得到一个节点的下一层子节点信息
     * @param treeNode
     * @param comparator
     * @return
     */
    public List<SearchTreeNode<E>> getChildNodes(SearchTreeNode<E> treeNode,AbstractTreeNodeCompare comparator){
    	List<SearchTreeNode<E>> list = getChildNodes(treeNode);
    	readlock.lock();
    	try{
	    	if(comparator != null)
	    		Collections.sort(list,comparator);
	    	return list;
    	}finally{
			readlock.unlock();
		}
    }
    public List<SearchTreeNode<E>> getChildNodes(String nodeId,AbstractTreeNodeCompare comparator){
    	List<SearchTreeNode<E>> list = getChildNodes(nodeId);
		readlock.lock();
    	try{
    		if(comparator != null)
    			Collections.sort(list,comparator);
    		return list;
    	}finally{
			readlock.unlock();
		}
    	
    }
    /**
     * 递归得到一个节点的所有父节点
     * @param treeNode
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionParentNodes(SearchTreeNode<E> treeNode){
    	readlock.lock();
    	try{
	    	if(treeNode != null)
	    		return treeNode.getParentNodes();
	    	else
	    		return new ArrayList<SearchTreeNode<E>>();
    	}finally{
			readlock.unlock();
		}
    }
    /**
     * 按照指定的排序递归得到一个节点的所有父节点
     * @param treeNode
     * @param comparator
     * @return
     */
    public List<SearchTreeNode<E>> getRecursionParentNodes(SearchTreeNode<E> treeNode,AbstractTreeNodeCompare comparator){
    	List<SearchTreeNode<E>> list = getRecursionParentNodes(treeNode);
    	readlock.lock();
    	try{
	    	if(comparator !=null)
	    		Collections.sort(list,comparator);
	    	return list;
    	}finally{
			readlock.unlock();
		}
    }
	public SearchTreeNode<E> getRoot() {
		return root;
	}
    
    
    
}
