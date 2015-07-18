package com.cherry.utils.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.cherry.utils.LogUtils;
import com.cherry.utils.tree.dto.SearchInsertTerm;
import com.cherry.utils.tree.fuzzy.Matcher;

public class TestMatch {
	
	@Test
	public  void testMatch() {
		
		Matcher matcher = new Matcher();
		
		List<SearchInsertTerm> list = new ArrayList<SearchInsertTerm>();
		list.add(new SearchInsertTerm(Arrays.asList("中国","zg"), "001",new HashSet<String>(Arrays.asList("我的"))));
		list.add(new SearchInsertTerm(Arrays.asList("中国人民","zgrm"), "002",new HashSet<String>(Arrays.asList("你的"))));
		list.add(new SearchInsertTerm(Arrays.asList("中国人民共和国","zgrmghg"), "003",new HashSet<String>(Arrays.asList("他的"))));
		
		matcher.addBatch(list);
		int start = 0;
		int pageSize = 10;
		LogUtils.debug(matcher.match("人民", start,pageSize,null));
		LogUtils.debug(matcher.match("z", start,pageSize,null));
		LogUtils.debug(matcher.match("02", start,pageSize,null));
		LogUtils.debug(matcher.match("RM", start,pageSize,null));//不区分大小写
		LogUtils.debug(matcher.match("z", start,pageSize,new HashSet<String>(Arrays.asList("你的"))));
		
		
	}

}
