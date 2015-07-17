package com.cherry.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.cherry.utils.export.excle.ExcleRead;
import com.cherry.utils.export.excle.ExportExcel;
import com.cherry.utils.export.excle.ExportExcel.Header;

public class DemoRead {
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
		ExcleRead read1 = new ExcleRead("C:/1/1.xlsx");
		LinkedList<String> strKeys = new LinkedList<String>();
		strKeys.add("adPositionId");strKeys.add("adId");strKeys.add("days");
		List<Map<String, String>> readExcle2Map = read1.readExcle2Map(strKeys);
		System.out.println("共"+readExcle2Map.size());
		
		ExcleRead read2 = new ExcleRead("C:/1/2.xlsx");
		ExcleRead read3 = new ExcleRead("C:/1/3.xlsx");
		LinkedList<String> strKeys2 = new LinkedList<String>();
		strKeys2.add("adPositionId");strKeys2.add("money");
		List<Map<String, String>> map2 = read2.readExcle2Map(strKeys2);
		System.out.println("3月到11月标比程序共读到数据"+map2.size());
		List<Map<String, String>> list3 = read3.readExcle2Map(strKeys2);
		System.out.println("8到11月正邦程序共读到数据"+list3.size());
		map2.addAll(list3);
		Map<Integer,Double> queryMap = new HashMap<Integer, Double>();
		for (Map<String, String> map : map2) {
			String adPositionId = map.get("adPositionId");
			String money = map.get("money");
			if(StringUtils.isBlank(adPositionId) || !NumberUtils.isNumber(adPositionId)){
				System.out.println("adPositionId是空，忽略"+adPositionId+"|"+money);
				continue;
			}
			if(StringUtils.isBlank(money)){
				money = "0";
			}
			Integer key = Integer.valueOf(adPositionId);
			if(queryMap.containsKey(key)){
				queryMap.put(key, queryMap.get(key)+Double.valueOf(money));
			}else{
				queryMap.put(key,Double.valueOf(money));
			}
		}
		System.out.println("可查询的数量queryMap："+queryMap.size());
		
		List<StatExcle> list = new ArrayList<StatExcle>();
		int size = 0;
		for (Map<String, String> map : readExcle2Map) {
			int adPositionId = Integer.parseInt(map.get("adPositionId"));
			int adId = Integer.parseInt(map.get("adId"));
			int days = Integer.parseInt(map.get("days"));
			StatExcle statExcle = new StatExcle(adPositionId, adId, days);
			if(queryMap.containsKey(adId)){
				statExcle.addTotalMoney(queryMap.get(adId));
			}else{
				size++;
				System.out.println("没有查询到此数据:"+statExcle);
			}
			list.add(statExcle);
		}
		System.out.println(size);
		
		ExportExcel excle = new ExportExcel("C:/1/result.xlsx");
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<Integer,StatExcle> mapSort = new LinkedHashMap<Integer, StatExcle>();
		for (StatExcle statExcle : list) {
			int adPositionId = statExcle.getAdPositionId();
			if(mapSort.containsKey(adPositionId)){
				StatExcle statExcle2 = mapSort.get(adPositionId);
				statExcle2.addTotalMoney(statExcle.getTotalMoney());
			}else{
				mapSort.put(adPositionId, statExcle);
			}
		}
		
		for (StatExcle statExcle : mapSort.values()) {
			results.add(statExcle.toMap());
		}
		
		Header header = excle.createHeader()
			.appendColumn("广告位id", "adPositionId")
			.appendColumn("投放天数", "days")
			.appendColumn("总收入", "totalMoneyStr")
			.appendColumn("平均收入", "avgMoneyStr")
			.appendColumn("转化率", "zhl");
		
		excle.exportListToExcel(results, header);
		
		
		
		
		
	}

}
