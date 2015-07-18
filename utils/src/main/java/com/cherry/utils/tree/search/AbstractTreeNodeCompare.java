package com.cherry.utils.tree.search;

import java.util.Comparator;

public abstract class AbstractTreeNodeCompare implements  Comparator<SearchTreeNode<?>>{

	@Override
	public abstract int compare(SearchTreeNode<?> o1, SearchTreeNode<?> o2) ;
	
	public static Comparator<SearchTreeNode<?>> nodeIdComparator = new AbstractTreeNodeCompare() {
		@Override
		public int compare(SearchTreeNode<?> o1, SearchTreeNode<?> o2) {
			return o1.getNodeId().compareTo(o2.getNodeId());
		}
	};
	
	public static Comparator<SearchTreeNode<?>> nodeLevelComparator = new AbstractTreeNodeCompare() {
		@Override
		public int compare(SearchTreeNode<?> o1, SearchTreeNode<?> o2) {
			return o1.getLevel()- o2.getLevel();
		}
	};
	
	
	
}
