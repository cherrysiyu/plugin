package com.cherry.utils.poi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cherry.utils.export.excle.ExportExcel;
import com.cherry.utils.export.excle.ExportExcel.Header;
;


/**ExportExcel
 * Test
 *
 */
public class ExportExcelTest {
	

	/**
	 * @throws IOException 
	 */
	@Test
	public void testExportListToExcel() throws IOException {
		List<String> sheetNames = new ArrayList<String>();
		//要导出的结果集
		List<List<Map<String,String>>> lists=new ArrayList<List<Map<String,String>>>();
		for (int j = 0; j < 10; j++) {
			sheetNames.add("sheet"+(j+1));
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			for(int i=0;i<10;i++)
			{
				Map<String,String> m=new HashMap<String,String>();
				m.put("id",String.valueOf(i)+"id"+j);
				m.put("name", String.valueOf(i)+"name"+j);
				m.put("sex", i+"male"+j);
				
				list.add(m);
			}
			lists.add(list);
		}
		 ExportExcel e=new ExportExcel("c:\\4.xlsx",sheetNames);
		
		
		Header header=e.createHeader()
		.appendColumn("序号", "id")
		.appendColumn("姓名", "name")
		.appendColumn("性别", "sex");
		e.batchExportListsToExcel(lists, header);
		
	}


}
