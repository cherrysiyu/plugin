package com.cherry.utils;import static org.junit.Assert.fail;

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
	private ExportExcel e=new ExportExcel("c:\\1.xls");
	@Test
	public void testExportExcel() {
		fail("Not yet implemented");
	}

	/**
	 * @throws IOException 
	 */
	@Test
	public void testExportListToExcel() throws IOException {
		//要导出的结果集
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		
		for(int i=0;i<10;i++)
		{
			Map<String,String> m=new HashMap<String,String>();
			m.put("id",String.valueOf(i));
			m.put("name", String.valueOf(i)+"name");
			m.put("sex", "male");
			
			list.add(m);
		}
		
		Header header=e.createHeader()
		.appendColumn("序号", "id")
		.appendColumn("姓名", "name")
		.appendColumn("性别", "sex");
		
		e.exportListToExcel(list, header);
	}

	@Test
	public void testCreateHeader() {
		fail("Not yet implemented");
	}

}
