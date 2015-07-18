package com.cherry.utils.tree;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cherry.utils.LogUtils;
import com.cherry.utils.tree.search.SearchTree;
import com.cherry.utils.tree.search.SearchTreeNode;


public class SearchTreeTest {
	
	private  SearchTree<String> buildTreee(){
		SearchTree<String> searchTree = new SearchTree<String>("全国");
		
		
		List<SearchTreeNode<String>> jiangsuRoot = new ArrayList<SearchTreeNode<String>>();
		SearchTreeNode<String> jiangsuNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320000", "江苏省", 1), searchTree.getRoot());
		jiangsuRoot.add(jiangsuNode);
		
		SearchTreeNode<String> nanjingNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320100","南京市",2), jiangsuNode);
		jiangsuRoot.add(nanjingNode);
		
		SearchTreeNode<String> shixiaNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320101","市辖区",3),nanjingNode);
		jiangsuRoot.add(shixiaNode);
		SearchTreeNode<String> xuanwuNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320102","玄武区",3), nanjingNode);
		jiangsuRoot.add(xuanwuNode);
		
		SearchTreeNode<String> suzhouNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320500","苏州市",2), jiangsuNode);
		jiangsuRoot.add(suzhouNode);
		
		SearchTreeNode<String> shixiaquNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320501","市辖区",3), suzhouNode);
		jiangsuRoot.add(shixiaquNode);
		SearchTreeNode<String> canglangNode = new SearchTreeNode<String>(new SearchTreeNode<String>("320502", "沧浪区",3), suzhouNode);
		jiangsuRoot.add(canglangNode);
		
		searchTree.addAll(jiangsuRoot);
		
		
		
		List<SearchTreeNode<String>> zhejiangRoot = new ArrayList<SearchTreeNode<String>>();
		SearchTreeNode<String> zhejiangNode = new SearchTreeNode<String>("330000", "浙江省", searchTree.getRoot());
		zhejiangRoot.add(zhejiangNode);
		
		SearchTreeNode<String> hangzhouNode = new SearchTreeNode<String>("330100", "杭州市", zhejiangNode);
		zhejiangRoot.add(hangzhouNode);
		
		SearchTreeNode<String> shangchengNode = new SearchTreeNode<String>("330102", "上城区", hangzhouNode);
		zhejiangRoot.add(shangchengNode);
		SearchTreeNode<String> xiachengNode = new SearchTreeNode<String>("330103", "下城区", hangzhouNode);
		zhejiangRoot.add(xiachengNode);
		
		SearchTreeNode<String> ningboNode = new SearchTreeNode<String>("330200", "宁波市", zhejiangNode);
		zhejiangRoot.add(ningboNode);
		
		SearchTreeNode<String> jisngdongNode = new SearchTreeNode<String>("330204", "江东区", ningboNode);
		zhejiangRoot.add(jisngdongNode);
		SearchTreeNode<String> jiangbeiNode = new SearchTreeNode<String>("320502", "江北区", ningboNode);
		zhejiangRoot.add(jiangbeiNode);
		
		searchTree.addAll(zhejiangRoot);
		
		
		return searchTree;
	}
	
	@Test
	public void testTree(){
		SearchTree<String> tree = buildTreee();
		LogUtils.debug(tree);
		
		List<SearchTreeNode<String>> childNodes = tree.getChildNodes("320000");
		LogUtils.debug(childNodes);
		List<SearchTreeNode<String>> recursionChildNodes = tree.getRecursionChildNodes("320000");
		LogUtils.debug(recursionChildNodes);
		
		SearchTreeNode<String> treeNode = tree.getTreeNodeById("330204");
		LogUtils.debug(treeNode);
		List<SearchTreeNode<String>> recursionParentNodes = tree.getRecursionParentNodes(treeNode);
		LogUtils.debug(recursionParentNodes);
		
	
		tree.updateTreeNode(new SearchTreeNode<String>(treeNode.getNodeId(), treeNode.getNodeValue()+"xxxx", treeNode.getParentNode()));
		LogUtils.debug( tree.getTreeNodeById("330204"));
	}
	

}
