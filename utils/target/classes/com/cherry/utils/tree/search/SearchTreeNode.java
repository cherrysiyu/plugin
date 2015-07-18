package com.cherry.utils.tree.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchTreeNode<E> {
	/**
	 * 节点id
	 */
	private String nodeId = "-1";
	/**
	 * 节点值
	 */
	private E nodeValue;
	/**
	 * 父节点
	 */
	private SearchTreeNode<E> parentNode;
	
	/**
	 * 层级
	 */
	private int level;
	
	/**
     * 当前节点的第一层子节点
     */
    protected Map<String,SearchTreeNode<E>> childMap = new HashMap<String,SearchTreeNode<E>>(); 
    
    public SearchTreeNode(String nodeId,E nodeValue,int level) {
		this.nodeId = nodeId;
		this.nodeValue = nodeValue;
		this.level = level;
	}
    
    public SearchTreeNode(SearchTreeNode<E> node, SearchTreeNode<E> parentNode) {
		this.nodeId = node.nodeId;
		this.nodeValue = node.nodeValue;
		this.level = node.level;
		this.parentNode = parentNode;
	}
    public SearchTreeNode(String nodeId,E nodeValue,SearchTreeNode<E> parentNode) {
		this.nodeId = nodeId;
		this.nodeValue = nodeValue;
		this.parentNode = parentNode;
		this.level = parentNode.level + 1;
	}
   /**
    * 插入一个child节点到当前节点中
    * @param searchTreeNode
    */
	public void addChildNode(SearchTreeNode<E> searchTreeNode){
		 childMap.put(searchTreeNode.getNodeId(), searchTreeNode);
	}
	
	
	/**
	 * 返回当前节点的父辈节点集合（递归）
	 * @return
	 */
	public  List<SearchTreeNode<E>> getParentNodes(){
		List<SearchTreeNode<E>> list = new ArrayList<SearchTreeNode<E>>();  
		/*递归
		if(!SearchTree.rootNodeId.equals(parentNode.nodeId))
			list.add(parentNode);
		list.addAll(parentNode.getParentNodes());*/
		//非递归
		/*for (SearchTreeNode<E> node = parentNode; node != null; node = node.parentNode) {
			if(!SearchTree.rootNodeId.equals(node.nodeId))
				list.add(node);
		}*/
		SearchTreeNode<E> ancestor = this;
        while((ancestor = ancestor.parentNode) != null){
        	if(!SearchTree.rootNodeId.equals(ancestor.nodeId))
				list.add(ancestor);
        }
		return list;
	}
	/**
	 * 返回当前节点的晚辈集合
	 * @param isRecursion 是否需要递归
	 * @return
	 */
	public  List<SearchTreeNode<E>> getChildNodes(boolean isRecursion){
		List<SearchTreeNode<E>> list = new ArrayList<SearchTreeNode<E>>();  
		Collection<SearchTreeNode<E>> childList = this.getChildList();  
		if(childList.isEmpty())
			return list;
		else{
			for (SearchTreeNode<E> SearchTreeNode : childList) {
				list.add(SearchTreeNode);
				if(isRecursion)
					list.addAll(SearchTreeNode.getChildNodes(isRecursion));
			}
			return list;
		}
	}
	
   /**
    *  
    *  删除节点和它下面的晚辈节点，递归删除
    * @param childId
    * @return
    */
    public boolean deleteRecursionChildNodes(String childId) {  
    	if(childMap.containsKey(childId)){
    		childMap.remove(childId);
    		return true;
    	}else{
    		for (SearchTreeNode<E> child : getChildList()) {
    			return child.deleteRecursionChildNodes(childId);
			}
    	}
    	return false;
    }
	
	
	/**
	 * 返回当前节点的孩子集合
	 * @return
	 */
    public Collection<SearchTreeNode<E>> getChildList() {  
        return childMap.values();  
    }  
	
    
   /**
    * 动态的插入一个新的节点到当前树中,添加成功后返回其父节点信息
    * @param treeNode
    * @return
    */
    public SearchTreeNode<E> insertNode(SearchTreeNode<E> treeNode) {  
        String parentId = treeNode.parentNode.nodeId;  
        if (this.nodeId == parentId) {  
            addChildNode(treeNode);  
            return this;  
        } else { //子节点 
        	for (SearchTreeNode<E> child : childMap.values()) {
        		 SearchTreeNode<E> insertNode = child.insertNode(treeNode);  
                 if (insertNode != null)  
                     return insertNode;  
			}
            return null;  
        }  
    }  
    
   /**
    * 找到一颗树中某个节点
    * @param nodeId
    * @return
    */
    public SearchTreeNode<E> findTreeNodeById(String nodeId) {  
        if (this.nodeId.equals(nodeId))  
            return this;  
        if (childMap.isEmpty()) {  
            return null;  
        } else {  
        	if(childMap.containsKey(nodeId))
        		return childMap.get(nodeId);
        	else{
        		for (SearchTreeNode<E> child : childMap.values()) {
        			SearchTreeNode<E> resultNode = child.findTreeNodeById(nodeId);  
	                if (resultNode != null) {  
	                    return resultNode;  
	                } 
				}
        	}
            return null;  
        }  
    }  
    
	public String getNodeId() {
		return nodeId;
	}

	public E getNodeValue() {
		return nodeValue;
	}

	public SearchTreeNode<E> getParentNode() {
		return parentNode;
	}

	public void setParentNode(SearchTreeNode<E> parentNode) {
		this.parentNode = parentNode;
	}

	public boolean isLeaf(){
		return this.childMap.isEmpty();
	}
	public int getLevel(){
		/*SearchTreeNode<E> ancestor;
	    int levels = 0;
        ancestor = this;
        while((ancestor = ancestor.parentNode) != null){
            levels++;
        }
        return levels;*/
		return this.level;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchTreeNode<?> other = (SearchTreeNode<?>) obj;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder().append("nodeId:").append(nodeId).append(",nodeValue:").append(nodeValue).append(",parentNode:").append(parentNode).toString();
	}
	
}
