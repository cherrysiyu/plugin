/**
 * 
 */
package com.cherry.utils.export.excle;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出Excel support
 * 代理导出excel时要完成的一些初始化动作和一些共用操作
 *
 */
public class ExportExcelSuppport {
	/**
	 * 工作薄
	 */
	private Workbook wb = new XSSFWorkbook();
	/**得到 工作薄
	 * @return
	 */
	public Workbook getWb() {
		return wb;
	}
	/**设置 工作薄
	 * @param wb
	 */
	public void setWb(Workbook wb) {
		this.wb = wb;
	}
	
	/**
	 * 表格头样式
	 */
	private CellStyle hcStyle = wb.createCellStyle();
	/**得到表格头样式
	 * @return
	 */
	public CellStyle getHcStyle() {
		return hcStyle;
	}
	/**设置	表格头样式
	 * @param hcStyle
	 */
	public void setHcStyle(CellStyle hcStyle) {
		this.hcStyle = hcStyle;
	}
	
	/**
	 * 单元格样式
	 */
	private CellStyle cellStyle = wb.createCellStyle();
	/**得到 单元格样式
	 * @return
	 */
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	/**设置 单元格样式
	 * @param cellStyle
	 */
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
	{
		//初始化单元格样式
		initCellStyle();
	}
	
	/**将工作薄写入文件
	 * @param fileName 文件名
	 * @throws IOException 
	 */
	public void writeWbToFile(String fileName) throws IOException
	{
		if(StringUtils.isNotBlank(fileName))
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
		}
	}
	
	/**
	 * 初始化单元格样式
	 */
	@SuppressWarnings("static-access")
	private void initCellStyle()
	{
		//初始化表格头样式
		//设置border
		hcStyle.setBorderBottom(hcStyle.BORDER_THIN);
		hcStyle.setBorderLeft(hcStyle.BORDER_THIN);
		hcStyle.setBorderRight(hcStyle.BORDER_THIN);
		hcStyle.setBorderTop(hcStyle.BORDER_THIN);
		//设置水平对齐
		hcStyle.setAlignment(hcStyle.ALIGN_CENTER);
		//设置字体
		Font f = wb.createFont();
		f.setBoldweight(f.BOLDWEIGHT_BOLD);
		hcStyle.setFont(f);
		//设置是否自动换行
		hcStyle.setWrapText(true);
		
		//初始化单元格样式
		//设置border
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);
		//设置水平对齐
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		//设置垂直对齐
		cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);
		//设置是否自动换行
		cellStyle.setWrapText(false);
	}
	
}
