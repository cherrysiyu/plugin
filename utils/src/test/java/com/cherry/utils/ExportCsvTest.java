package com.cherry.utils;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.cherry.utils.export.excle.ExportCsv;
;


/**
 * ExportCsv测试类
 *
 */
public class ExportCsvTest {
	ExportCsv exportCsv;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		exportCsv=new ExportCsv("123.csv");
	}


	@Test
	public void testHandleValue() {
		assertTrue("\t".equals(exportCsv.handleValue("")));
		assertTrue("\t".equals(exportCsv.handleValue(null)));
		assertTrue("\"1,2,3\"\t".equals(exportCsv.handleValue("1,2,3")));
	}


	@Test
	public void testAddWrapToLine() {
		StringBuilder sb=new StringBuilder("");
		exportCsv.addWrapToLine(sb);
		
		assertTrue("\r\n".equals(sb.toString()));
	}


	@Test
	public void testExportContent() throws IOException {
		exportCsv.exportContent("123");
	}
	
	@Test
	public void testCloaseStream() throws IOException {
		exportCsv.closeStream();
	}
}
