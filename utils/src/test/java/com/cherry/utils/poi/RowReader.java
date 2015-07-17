package com.cherry.utils.poi;import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
;

public class RowReader implements IRowReader{  
	public  Map<String,Map<String,Integer>> map;
	private int count = 0;
	private int exCount = 0;
	private static final String seprator="|";
    public RowReader(Map<String, Map<String, Integer>> map) {
		super();
		this.map = map;
	}
    

	/* 业务逻辑实现方法 
     * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List) 
     */  
    public void getRows(int sheetIndex, int curRow, List<String> rowlist) { 
    	count++;
    	if(count % 100 == 0){
    		System.out.println(count+"---->"+rowlist);
    	}
    	try{
    		 add2Map(rowlist.toArray(new String[]{}));
    	}catch(Exception e){
    		exCount++;
    		e.printStackTrace();
    		System.out.println(count+"----->"+rowlist +"--->exCount:"+exCount);
    	}
       
    }  
    
    public void add2Map(String[] singleRow) {
		String date = null;
		StringBuilder key = new StringBuilder();
		String keyTemp = null;
		date = singleRow[1];
		keyTemp = key.append(singleRow[0]).append(seprator)
				.append(singleRow[3]).append(seprator).append(singleRow[2])
				.toString();
		key.setLength(0);
		if (map.containsKey(keyTemp)) {
			Map<String, Integer> map2 = map.get(keyTemp);
			if (map2.containsKey(date)) {
				map2.put(date, map2.get(date) + 1);
			} else {
				map2.put(date, 1);
			}
			map.put(keyTemp, map2);
		} else {
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			hashMap.put(date, 1);
			map.put(keyTemp, hashMap);
		}
		Arrays.fill(singleRow, null);
		singleRow = null;
	}
  
    
}  