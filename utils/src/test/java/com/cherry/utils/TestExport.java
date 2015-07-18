package com.cherry.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cherry.utils.export.DataBean;
import com.cherry.utils.export.FieldBean;


public class TestExport {
	
	@Test
	public  void testExport() {
		
		exportTxt(buildDate("1.txt"));
		exportExcle(buildDate("1.xlsx"));
		exportExcle(buildDate("1.xls"));
		exportCSV(buildDate("1.csv"));
	}
	
	
	public static DataBean  buildDate(String fileName){
		
		DataBean data = new DataBean();
		data.setFileName(fileName);
		
		List<FieldBean> exportFields = new ArrayList<FieldBean>();
		
		exportFields.add(new FieldBean("username","用户名"));
		exportFields.add(new FieldBean("password","密码"));
		exportFields.add(new FieldBean("sex","性别"));
		exportFields.add(new FieldBean("age","年龄"));
		exportFields.add(new FieldBean("birthday","出生日期"));
		exportFields.add(new FieldBean("address","地址"));
		
		data.setExportFields(exportFields);
		
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for (int i = 0; i < 10; i++) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("username", "cherry"+i);
			map.put("password", Math.random()*100+"");
			map.put("sex", "dffsd"+i);
			map.put("age", Math.random()*100+"");
			map.put("birthday", "2012-11-12"+i);
			map.put("address", "fsfsf"+i);
			
			datas.add(map);
		}
		
		data.setDatas(datas);
		
		return data;
	}
	
	
	public static boolean exportTxt(DataBean dataBean){
		return DataExportUtils.exportDatas2Txt(dataBean);
	}
	
	public static boolean exportExcle(DataBean dataBean) {
		try {
			return DataExportUtils.exportDatas2Excel(dataBean);
		} catch (Exception e) {
			LogUtils.error(e);
		}
		return true;
	}

	public static boolean exportCSV(DataBean dataBean){
		return DataExportUtils.exportDatas2CSV(dataBean);
	}

}
