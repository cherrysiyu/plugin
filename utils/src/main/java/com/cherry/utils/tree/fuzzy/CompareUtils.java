package com.cherry.utils.tree.fuzzy;

import java.util.Comparator;

public  class CompareUtils {

	public static Comparator<TreeNode> keyComparator = new Comparator<TreeNode>() {
		@Override
		public int compare(TreeNode o1, TreeNode o2) {
			char u1 = Character.toUpperCase(o1.id);
			char u2 = Character.toUpperCase(o2.id);
			if(u1>u2)
				return 1;
			else if(u1 <u2)
				return -1;
			else
				return 0;
		}
	};
	
	public static Comparator<char[]> charsIgnoreCaseComparator = new Comparator<char[]>() {
		@Override
		public int compare(char[] c1, char[] c2) {
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
	};
	
	public static Comparator<String> stringValueComparator = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			return s1.compareTo(s2);
		}
	};
}
