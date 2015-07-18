/**
 * 
 */
package com.cherry.utils.export.excle;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出Excel 使用POI 3.8
 * 
 */
public class ExportExcel {
	
	/**
	 * 工作薄
	 */
	private Workbook wb;
	/**
	 * Sheet
	 */
	private List<Sheet> sheets = new ArrayList<Sheet>();
	/**
	 * 表头单元格样式
	 */
	private CellStyle hcStyle;
	/**
	 * 内容单元格样式
	 */
	private CellStyle cellStyle;
	/**
	 * 行索引
	 */
	private int rowIndex = 0;
	/**
	 * 写入的sheet索引
	 */
	private int sheetIndex = 0;
	
	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 是否已经被写过
	 */
	private boolean isAlreadyWrited; 

	/**
	 * @param fileName 导出的excle文件名称,此种构造只能导出一个Sheet
	 */
	public ExportExcel(String fileName) {
		this.fileName = fileName;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if ("xls".equals(fileType))
			// 创建工作薄
			this.wb = new HSSFWorkbook();
		else if ("xlsx".equals(fileType))
			// 创建工作薄
			this.wb = new XSSFWorkbook();
		else
			throw new UnsupportedOperationException("您导出的不是excle合法类型，不支持此文件类型");
		// 创建sheet
		this.sheets.add(wb.createSheet());
		// 定义表头单元格样式
		setHcStyle();
		// 定义内容单元格样式
		setCellStyle();
	}
	/**
	 * 导出的excle文件名称,此种构造可以导出多个Sheet
	 * @param fileName
	 * @param sheetNames
	 */
	public ExportExcel(String fileName,List<String> sheetNames) {
		this.fileName = fileName;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if ("xls".equals(fileType))
			// 创建工作薄
			this.wb = new HSSFWorkbook();
		else if ("xlsx".equals(fileType))
			// 创建工作薄
			this.wb = new XSSFWorkbook();
		else
			throw new UnsupportedOperationException("您导出的不是excle合法类型，不支持此文件类型");
		if(sheetNames != null){
			for (String sheetName : sheetNames) {
				this.sheets.add(wb.createSheet(sheetName));
			}
		}
		// 定义表头单元格样式
		setHcStyle();
		// 定义内容单元格样式
		setCellStyle();
	}
	
	/**
	 * 创建表头
	 * 
	 * @return
	 */
	public Header createHeader() {
		return new Header();
	}

	
	
	
	/**
	 * 将数据列表导出到excel中，<span style="font:bold">一次导入只能写入一次</span>
	 * @param result 数据列表
	 * @param header  表头对象,只有一个header，则所有的sheet都一样的
	 * @throws IOException
	 */
	public void exportListToExcel(List<Map<String, String>> result, Header header) throws IOException {
		if (!isAlreadyWrited && result != null && header != null && header.columns != null) {
			isAlreadyWrited = true;
			OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(fileName));
			exportListToExcel(result, header, fileOut);
			wb.write(fileOut);
			fileOut.close();
		}
	}

	/**
	 * 写入的是多个Sheet
	 * @param result
	 * @param header
	 * @throws Exception
	 */
	public void batchExportListsToExcel(List<List<Map<String, String>>> result,Header header) throws IOException {
		if (!isAlreadyWrited && fileName != null && result != null && header != null && header.columns != null) {
			isAlreadyWrited = true;
			OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(fileName));
			for (List<Map<String, String>> list : result) {
				if(sheetIndex < sheets.size()){
					exportListToExcel(list,header,fileOut);
				}else{
					break;
				}
			}
			wb.write(fileOut);
			fileOut.close();
		}
	}
	
	/**
	 * 将数据列表写入写入excel文件,写入的是多个Sheet
	 * @param fileName 文件名
	 * @param result 数据列表
	 * @throws IOException
	 */
	public void batchExportListsToExcel(List<List<Map<String, String>>> result,List<Header> excelHeaders) throws IOException {
		if (!isAlreadyWrited && fileName != null && result != null && excelHeaders != null && !excelHeaders.isEmpty()) {
			isAlreadyWrited = true;
			int index = 0;
			OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(fileName));
			Header header = excelHeaders.get(0);
			int headerSize = excelHeaders.size();
			for (List<Map<String, String>> list : result) {
				if(sheetIndex < sheets.size()){
					index++;
					if(headerSize >= index )
						exportListToExcel(list,excelHeaders.get(index-1),fileOut);
					else
						exportListToExcel(list,header,fileOut);
				}else{
					break;
				}
			}
			wb.write(fileOut);
			fileOut.close();
		}
	}
	
	
	private void exportListToExcel(List<Map<String, String>> result, Header header,OutputStream fileOut) throws IOException {
		if (result != null && header != null && header.columns != null) {
			Row r = null;
			Cell c = null;
			Sheet sheet = sheets.get(sheetIndex);
			// 添加表头行
			r = sheet.createRow(rowIndex++);
			for (int i = 0; i < header.columns.size(); i++) {
				c = r.createCell(i);
				// 取出中文列名
				c.setCellValue(header.columns.get(i).cn);
				c.setCellStyle(hcStyle);
			}
			// 添加数据行
			for (Map<String, String> m : result) {
				r = sheet.createRow(rowIndex++);
				for (int i = 0; i < header.columns.size(); i++) {
					c = r.createCell(i);
					c.setCellValue(m.get(header.columns.get(i).en) == null ? "": m.get(header.columns.get(i).en));
					c.setCellStyle(cellStyle);
				}

			}
			sheetIndex++;
			rowIndex = 0;
		}
	}
	
	
	/**
	 * Excel表头
	 */
	public class Header {
		/**
		 * 字段集合
		 */
		private List<Column> columns = new ArrayList<Column>();

		private Header() {}

		/**
		 * 添加column
		 * @param cn 中文列名
		 * @param en 英文列名
		 * @return
		 */
		public Header appendColumn(String cn, String en) {
			this.columns.add(new Column(cn, en));
			return this;
		}

		/**
		 * Column
		 * 
		 */
		protected class Column {
			/**
			 * 中文列名
			 */
			private String cn;
			/**
			 * 英文列名
			 */
			private String en;

			private Column(String cn, String en) {
				this.cn = cn;
				this.en = en;
			}
		}
	}

	/**
	 * 设置工作薄 当需要在一个<span style="font:bold">已存在的excel文件中</span>写入内容时使用这个方法
	 * @param inputStream输入流
	 * @param sheetIndex 要写入内容的sheet索引
	 * @throws Exception
	 */
	public void setWb(FileInputStream inputStream)
			throws Exception {
		if (inputStream != null && sheetIndex > -1) {
			// 获取工作薄
			this.wb = WorkbookFactory.create(inputStream);
			// 定义表头单元格样式
			setHcStyle();
			// 定义内容单元格样式
			setCellStyle();
		}
	}

	/**
	 * 定义表格表头单元格样式
	 */
	@SuppressWarnings("static-access")
	protected void setHcStyle() {
		this.hcStyle = wb.createCellStyle();

		// 设置border
		hcStyle.setBorderBottom(hcStyle.BORDER_THIN);
		hcStyle.setBorderLeft(hcStyle.BORDER_THIN);
		hcStyle.setBorderRight(hcStyle.BORDER_THIN);
		hcStyle.setBorderTop(hcStyle.BORDER_THIN);
		// 设置水平对齐
		hcStyle.setAlignment(hcStyle.ALIGN_CENTER);
		// 设置字体
		Font f = wb.createFont();
		f.setBoldweight(f.BOLDWEIGHT_BOLD);
		hcStyle.setFont(f);
	}

	/**
	 * 定义表格内容单元格样式
	 */
	@SuppressWarnings("static-access")
	protected void setCellStyle() {
		this.cellStyle = wb.createCellStyle();

		// 设置border
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);
		// 设置水平对齐
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		// 设置垂直对齐
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);
		// 设置是否自动换行
		cellStyle.setWrapText(false);
	}

}
