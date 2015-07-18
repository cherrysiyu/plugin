package com.cherry.utils.poi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/** 
 * 抽象Excel2007读取器，excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析 
 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低 
 * 内存的耗费，特别使用于大数据量的文件。 
 * 
 */  
public class Excel2007Reader extends DefaultHandler {  
    //共享字符串表  
    private SharedStringsTable sst;  
    //上一次的内容  
    private String lastContents;  
    private boolean nextIsString;  
  
    private int sheetIndex = -1;  
    private List<String> rowlist = new ArrayList<String>();  
    //当前行  
    private int curRow = 0;  
      
    private IRowReader rowReader;  
      
    public void setRowReader(IRowReader rowReader){  
        this.rowReader = rowReader;  
    }  
      
    /**只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3 
     * @param filename 
     * @param sheetId 
     * @throws Exception 
     */  
    public void processOneSheet(String filename,int sheetId) throws Exception {  
        OPCPackage pkg = OPCPackage.open(filename);  
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
        XMLReader parser = fetchSheetParser(sst);  
          
        // 根据 rId# 或 rSheet# 查找sheet  
        InputStream sheet2 = r.getSheet("rId"+sheetId);  
        sheetIndex++;  
        InputSource sheetSource = new InputSource(sheet2);  
        parser.parse(sheetSource);  
        sheet2.close();  
    }  
  
    /** 
     * 遍历工作簿中所有的电子表格 
     * @param filename 
     * @throws Exception 
     */  
    public void process(String filename) throws Exception {  
        OPCPackage pkg = OPCPackage.open(filename);  
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
        XMLReader parser = fetchSheetParser(sst);  
        Iterator<InputStream> sheets = r.getSheetsData();  
        while (sheets.hasNext()) {  
            curRow = 0;  
            sheetIndex++;  
            InputStream sheet = sheets.next();  
            InputSource sheetSource = new InputSource(sheet);  
            parser.parse(sheetSource);  
            sheet.close();  
        }  
    }  
  
    public XMLReader fetchSheetParser(SharedStringsTable sst)  
            throws SAXException {  
        XMLReader parser = XMLReaderFactory  
                .createXMLReader("org.apache.xerces.parsers.SAXParser");  
        this.sst = sst;   
        parser.setContentHandler(this);  
        return parser;  
    }  
  
    public void startElement(String uri, String localName, String name,  
            Attributes attributes) throws SAXException {  
          
    	if(name.equals("c")) {
			// Figure out if the value is an index in the SST
			String cellType = attributes.getValue("t");
			if(cellType != null && cellType.equals("s")) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		// Clear contents cache
		lastContents = "";
    }  
  
    public void endElement(String uri, String localName, String name)  
            throws SAXException {  
          
    	// Process the last contents as required.
    				// Do now, as characters() may be called more than once
			if(nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}

			// v => contents of a cell
			// Output after we've seen the string contents
			if(name.equals("v")) {
				if(StringUtils.isNotBlank(lastContents))
					rowlist.add(lastContents.trim());
			}
			//如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法  
            if (name.equals("row")) {  
            	if(rowlist.size()>=4){
	                rowReader.getRows(sheetIndex,curRow,rowlist);  
	                rowlist.clear();  
	                curRow++;  
            	}
            } 
          
    }  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        //得到单元格内容的值  
        lastContents += new String(ch, start, length);  
    }  
}  