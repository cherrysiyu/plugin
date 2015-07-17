package com.cherry.utils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  
public class ExcelReader {  
	public static int[] indexArray = new int[]{0,1,8,9};
	public  Map<String,Map<Long,ExcleSateBean>> map = new 	HashMap<String, Map<Long,ExcleSateBean>>();
	private static final String seprator="|";
	XSSFWorkbook   wb = null;  
  public ExcelReader(String path){  
    try {  
      InputStream inp =new BufferedInputStream(new FileInputStream(path)); 
      wb = new XSSFWorkbook(inp); 
      System.out.println("创建WorkbookFactory 成功,路径:"+path);
    } catch (FileNotFoundException e) {  
      e.printStackTrace();  
    }catch (IOException e) {  
      e.printStackTrace();  
    }  
  }    
  public static boolean isNeedIndex(int index){
	  return Arrays.binarySearch(indexArray, index)>=0;
  }
    
  /** 
   * 取Excel所有数据，包含header 
   * @return  List<String[]> 
   */  
 public Map<String,Map<Long,ExcleSateBean>> getAllData(int sheetIndex){  
	 int totalCount = 0;
    int columnNum = 0;  
    Sheet sheet = wb.getSheetAt(sheetIndex);  
    if(sheet.getRow(0)!=null){  
        columnNum = sheet.getRow(0).getLastCellNum()-sheet.getRow(0).getFirstCellNum();  
    }  
    List<Object> singleRow = new ArrayList<Object>(columnNum);
    if(columnNum>0){  
      for(Row row:sheet){  
    	  totalCount++;
          for(int i=0;i<columnNum;i++){  
        	  if(!isNeedIndex(i))//不需要的数据不读取
        		  continue;
             Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);  
             switch(cell.getCellType()){  
               case Cell.CELL_TYPE_BLANK:  
            	   singleRow.add("");
                 break;  
               case Cell.CELL_TYPE_BOOLEAN:  
            	   singleRow.add(Boolean.toString(cell.getBooleanCellValue()));  
                 break;  
                //数值  
               case Cell.CELL_TYPE_NUMERIC:                 
                 if(DateUtil.isCellDateFormatted(cell)){  
                	 singleRow.add(cell.getDateCellValue());  
                 }else{   
                   cell.setCellType(Cell.CELL_TYPE_STRING);  
                   String temp = cell.getStringCellValue();  
                   //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串  
                   if(temp.indexOf(".")>-1){  
                	   singleRow.add( String.valueOf(new Double(temp)).trim());  
                   }else{  
                	   singleRow.add(temp.trim());  
                   }  
                 }  
                 break;  
               case Cell.CELL_TYPE_STRING:  
            	   singleRow.add(cell.getStringCellValue().trim());  
                 break;  
               case Cell.CELL_TYPE_ERROR:  
            	   singleRow.add("");  
                 break;    
               case Cell.CELL_TYPE_FORMULA:  
                 cell.setCellType(Cell.CELL_TYPE_STRING);  
                 String s  = cell.getStringCellValue();  
                 if(s!=null){  
                   s = s.replaceAll("#N/A","").trim();  
                 } 
                 singleRow.add(s);
                 break;    
               default:  
            	   singleRow.add("");  
                 break;  
             }  
          }   
          if("".equals(singleRow.get(0))){continue;}//如果第一行为空，跳过  
          if(singleRow.size()>=4){
        	  add2Map(singleRow);
          }else{
        	  System.out.println("数据不全："+singleRow);
          }
          singleRow.clear();
      }  
    } 
    System.out.println("本次读取excle结束，总条数:"+totalCount);
	System.runFinalization();
	System.gc();
    return map;  
  }    
 
	public void add2Map(List<Object> singleRow) {
		Date date = null;
		StringBuilder key = new StringBuilder();
		String keyTemp = null;
		date = (Date)singleRow.get(1);
		Long stateTime = DateUtils.convertTimeToLong(DateUtils.convertTimeToString(date.getTime()/1000,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm") ;
		keyTemp = key.append(singleRow.get(0)).append(seprator)
				.append(singleRow.get(3)).append(seprator).append(singleRow.get(2))
				.toString();
		key.setLength(0);
		if (map.containsKey(keyTemp)) {
			Map<Long, ExcleSateBean> map2 = map.get(keyTemp);
			if (map2.containsKey(stateTime)) {
				map2.put(stateTime, map2.get(stateTime).incresment().addOldTime(date));
			} else {
				map2.put(stateTime,new ExcleSateBean(date).addMobile(singleRow.get(0)).addIp(singleRow.get(3)).addUa(singleRow.get(2)));
			}
			map.put(keyTemp, map2);
		} else {
			TreeMap<Long, ExcleSateBean> hashMap = new TreeMap<Long, ExcleSateBean>();
			hashMap.put(stateTime,new ExcleSateBean(date).addMobile(singleRow.get(0)).addIp(singleRow.get(3)).addUa(singleRow.get(2)));
			map.put(keyTemp, hashMap);
		}
	}
 
 
  /** 
   * 返回Excel最大行index值，实际行数要加1 
   * @return 
   */  
  public int getRowNum(int sheetIndex){  
    Sheet sheet = wb.getSheetAt(sheetIndex);  
    return sheet.getLastRowNum();  
  }  
    
  /** 
   * 返回数据的列数 
   * @return  
   */  
  public int getColumnNum(int sheetIndex){  
    Sheet sheet = wb.getSheetAt(sheetIndex);  
    Row row = sheet.getRow(0);  
    if(row!=null&&row.getLastCellNum()>0){  
       return row.getLastCellNum();  
    }  
    return 0;  
  }  
    
  public static void main(String[] args) {
	System.out.println(isNeedIndex(0));
	System.out.println(isNeedIndex(-1));
	System.out.println(isNeedIndex(2));
	System.out.println(isNeedIndex(8));
	System.out.println(isNeedIndex(1));
	System.out.println(isNeedIndex(9));
  }
    
 } 