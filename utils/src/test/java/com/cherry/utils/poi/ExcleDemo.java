package com.cherry.utils.poi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.cherry.utils.export.excle.ExportExcel;
import com.cherry.utils.export.excle.ExportExcel.Header;

public class ExcleDemo {
	 public static void main(String[] args) throws Exception {  
		 long currentTimeMillis = System.currentTimeMillis();
		  Map<String,Map<String,Integer>> map = new HashMap<String, Map<String,Integer>>();
	        IRowReader reader = new RowReader(map);  
	        ExcelReaderUtil.readExcel(reader, "c:/1.xlsx");  
	        
			System.out.println("读取数据结束，耗时:"+(System.currentTimeMillis()-currentTimeMillis)/1000+"s"+"总条数:"+map.size());
			 ExportExcel excle = new ExportExcel("c:\\result.xls");
			 List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			 Map<String,String> map2 = null;
			 currentTimeMillis = System.currentTimeMillis();
			 for (Map.Entry<String,Map<String,Integer>> entry : map.entrySet()) {
				String url2 = entry.getKey();
				 for (Map.Entry<String,Integer> entry2 :entry.getValue().entrySet()) {
					 System.out.println(entry2);
					 if(entry2.getValue() >1){
						 map2 = new HashMap<String, String>();
						 map2.put("shuff", url2);
						 map2.put("date", entry2.getKey());
						 map2.put("count", entry2.getValue()+"");
						 list.add(map2);
					 }
					
				 }
			}
			 
			 map.clear();
			 try {
			    	System.runFinalization();
			    	System.gc();
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 
			 Header header=excle.createHeader()
						.appendColumn("前缀", "shuff")
						.appendColumn("时间", "date")
						.appendColumn("次数", "count");
			 System.out.println("数据数据组装结束，耗时:"+(System.currentTimeMillis()-currentTimeMillis)/1000+"s"+"个数:"+list.size());			
			 excle.exportListToExcel(list, header);
	    } 
}
