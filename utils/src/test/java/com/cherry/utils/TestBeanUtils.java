package com.cherry.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.cherry.utils.threadPool.dto.TimerTask;

public class TestBeanUtils {

	@SuppressWarnings("unchecked")
	@Test
	public void testBeanUtils() throws Exception {
		Map<String,Object> describe = (Map<String,Object>)BeanUtils.describe(new TimerTask());
		describe.remove("class");
		LogUtils.debug(describe);
		
		LogUtils.debug(BeanUtils.describe(null));
		
	}
}
