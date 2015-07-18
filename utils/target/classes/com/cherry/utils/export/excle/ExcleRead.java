package com.cherry.utils.export.excle;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cherry.utils.CommonMethod;


public class ExcleRead {
	
	/**
	 * 工作薄
	 */
	private Workbook wb;
	/**
	 * Sheet
	 */
	private Sheet sheet;
	
	
	public ExcleRead(String filePath){
		this(filePath,0);
	}
	
	public ExcleRead(String filePath,int sheetIndex){
		File file = new File(filePath);
		if(!file.exists()){
			throw new IllegalArgumentException("文件找不到，请检查,path"+filePath);
		}
		if(sheetIndex < 0){
			throw new IllegalArgumentException("Excle文件的Sheet从0开始,sheetIndex >= 0");
		}
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if(!("xls".equals(fileType) || "xlsx".equals(fileType)))
			throw new IllegalArgumentException("文件不是excle合法类型，不支持此文件类型");
		try {
			this.wb  = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sheet = wb.getSheetAt(sheetIndex);
	}
	
	public ExcleRead(File file) throws InvalidFormatException, IOException{
		this(file,0);
	}
	
	public ExcleRead(File file,int sheetIndex) throws InvalidFormatException, IOException{
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if(!("xls".equals(fileType) || "xlsx".equals(fileType)))
			throw new IllegalArgumentException("文件不是excle合法类型，不支持此文件类型");
		this.wb  = WorkbookFactory.create(file);
		this.sheet = wb.getSheetAt(sheetIndex);
	}
	
	
	/**
	 * 
	 * @param strKeys	 Map的Key列表，注意key的顺序必须要和excle文件中的列一一对应
	 * @param beginRowNum 从第几行开始读取
	 * @return
	 */
	public List<Map<String,String>> readExcle2Map(LinkedList<String> strKeys,int beginRowNum,int lastRowNum){
		
		if(!CommonMethod.isCollectionNotEmpty(strKeys))
			throw new NullPointerException(" Map的Key列表不能为空");
			
		if (beginRowNum < 0 || lastRowNum < beginRowNum)
			throw new IllegalArgumentException("beginRowNum 或者lastRowNum 不合法，beginRowNum 必须>0 并且lastRowNum必须 >= beginRowNum");
			
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		Row row = null;
		int strKeysSize = strKeys.size();
		for (int i = beginRowNum; i <= lastRowNum; i++) {
			row = sheet.getRow(i);
			if (row == null)
				continue;
			map = new LinkedHashMap<String, String>();
			// 获取一行多少列
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum() && j <strKeysSize; j++) {
				String key = strKeys.get(j);
				if(CommonMethod.isNotEmpty(key)){
					map.put(key, getCellValue(row.getCell(j)));
				}
			}
			listMap.add(map);
		}
		return listMap;
	}
	
	/**
	 * 此方法会把Excle中每一列每一行的数据都读入到集合中
	 * @param strKeys  Map的Key列表，注意key的顺序必须要和excle文件中的列一一对应
	 * @return
	 */
	public List<Map<String,String>> readExcle2Map(LinkedList<String> strKeys){
		return readExcle2Map(strKeys,0, sheet.getLastRowNum());
	}
	
	
	private String getCellValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			 break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue()); break;
		case Cell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getCellFormula()); break;
		case Cell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value = sdf.format(cell.getDateCellValue());
			}else{
				 cell.setCellType(Cell.CELL_TYPE_STRING);  
                 String temp = cell.getStringCellValue();  
                 //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串  
                 if(temp.indexOf(".")>-1){  
                	 temp = String.valueOf(new Double(temp)).trim();  
                 } 
				value = temp; 
			}
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue(); break;
		case Cell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		if(CommonMethod.isNotEmpty(value))
			value = value.trim().replace("'", "").replace("‘", "");
		return value;
	}
}
