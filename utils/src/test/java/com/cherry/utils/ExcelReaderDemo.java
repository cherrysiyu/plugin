package com.cherry.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.cherry.utils.export.excle.ExportExcel;
import com.cherry.utils.export.excle.ExportExcel.Header;

public class ExcelReaderDemo {
	
	static class EqualIpBean{
		private String ip;
		private String prov;
		private String city;
		private String type;
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getProv() {
			return prov;
		}
		public void setProv(String prov) {
			this.prov = prov;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public EqualIpBean addIp(String ip){
			setIp(ip);
			return this;
		}
		public EqualIpBean addProv(String prov){
			setProv(prov);
			return this;
		}
		public EqualIpBean addCity(String city){
			setCity(city);
			return this;
		}
		public EqualIpBean addType(String type){
			setType(type);
			return this;
		}
	}
	
	
	static class RangeIpBean{
		private long beginIp;
		private long endIp;
		private String prov;
		private String city;
		
		public long getBeginIp() {
			return beginIp;
		}
		public void setBeginIp(long beginIp) {
			this.beginIp = beginIp;
		}
		public long getEndIp() {
			return endIp;
		}
		public void setEndIp(long endIp) {
			this.endIp = endIp;
		}
		public String getProv() {
			return prov;
		}
		public void setProv(String prov) {
			this.prov = prov;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		
		public RangeIpBean addBeginIp(long beginIp){
			setBeginIp(beginIp);
			return this;
		}
		public RangeIpBean addEndIp(long endIp){
			setEndIp(endIp);
			return this;
		}
		public RangeIpBean addProv(String prov){
			setProv(prov);
			return this;
		}
		public RangeIpBean addCity(String city){
			setCity(city);
			return this;
		}
		/**
		 * 判断ip是否在区间内
		 * @param ip
		 * @return
		 */
		public boolean isIpIn(long ip){
			return (beginIp <= ip && ip <= endIp);
		}
		
	}
	/**
	 * 将ip字段转化为16进制的数
	 * 
	 * @param ip
	 *            传进来的ip地址
	 * @return
	 */
	private static String toHexString(String ip) {
		// ip分隔而成的数组
		String[] ips = ip.split("\\.");
		// 返回的数组
		String returnStr = "0";
		for (int i = 0; i < ips.length; i++) {
			double num = Math.pow(256, 3 - i);
			BigDecimal str = new BigDecimal(0);
			if (NumberUtils.isNumber(ips[i])) {
				str = new BigDecimal(ips[i]).multiply(new BigDecimal(num));
			}
			returnStr = new BigDecimal(returnStr).add(str).toString();
		}
		return returnStr;
	}
	private static Map<String,EqualIpBean> equalIpMap = new HashMap<String,EqualIpBean>();
	private static List<RangeIpBean> rangeIpList = new ArrayList<ExcelReaderDemo.RangeIpBean>();
	
	
	public static void addFilse(List<File> files,File file){
			if(file.isDirectory()){
				for (File file2 : file.listFiles()) {
					addFilse(files,file2);
				}
			}else{
				files.add(file);
			}
	}
	
	private static void initIpDatas() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("1.txt"));
		String line = null;
		while((line = br.readLine()) != null){
			if(StringUtils.isNotBlank(line)){
				String[] split = StringUtils.split(line, ",");
				if(split != null && split.length>=4){
					String ip = split[0];
					if(StringUtils.isNotBlank(ip)){
						String[] ipSplit = StringUtils.split(ip, "\\.");
						if(ipSplit.length==3){
							for (int i = 0; i < 256; i++) {
								equalIpMap.put(ip+"."+i, new EqualIpBean().addIp(ip+"."+i).addProv(split[1]).addType(split[2]).addCity(split[3]));
							}
						}else{
							equalIpMap.put(split[0], new EqualIpBean().addIp(split[0]).addProv(split[1]).addType(split[2]).addCity(split[3]));
						}
					}
				}
			}
		}
		br.close();
		
		br = new BufferedReader(new FileReader("2.txt"));
		while((line = br.readLine()) != null){
			if(StringUtils.isNotBlank(line)){
				String[] split = StringUtils.split(line, ",");
				if(split != null && split.length>=4){
					rangeIpList.add(new RangeIpBean().addBeginIp(Long.parseLong(toHexString(split[0]))).addEndIp(Long.parseLong(toHexString(split[1]))).addProv(split[2]).addCity(split[3]));
				}
			}
		}
		br.close();
		System.out.println("初始化ip数据成功:equalIpMap:"+equalIpMap.size()+",rangeIpList:"+rangeIpList.size());
	}
	
	
	public static void main(String[] args) throws IOException {
		initIpDatas();
		
		File file = new File("C:/txt");
		if(!file.exists()){
			System.out.println("文件不存在，位置："+file.getAbsolutePath());
			System.exit(1);
		}
		List<File> files = new ArrayList<File>();
		addFilse(files,file);
		
		for (File file2 : files) {
			TreeMap<Integer,Integer> statMap = new TreeMap<Integer, Integer>();
			
			List<Integer> allCounts = new ArrayList<Integer>();
			
			String path = file2.getAbsolutePath();
			String fileName =  file2.getName();
			String forderPath = path.substring(0, path.length()-fileName.length());
			
			
			ExcelReader excelReader = new ExcelReader(path);
			long currentTimeMillis = System.currentTimeMillis();
			Map<String,Map<Long,ExcleSateBean>> map  = excelReader.getAllData(0);
			System.out.println("读取数据结束，耗时:"+(System.currentTimeMillis()-currentTimeMillis)/1000+"s"+"总条数:"+map.size());
			 List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			 List<Map<String,String>> list2=new ArrayList<Map<String,String>>();
			 Map<String,String> map2 = null;
			 currentTimeMillis = System.currentTimeMillis();
			 int totalCount = 0;
			List<String> ipList = new ArrayList<String>();
			 for (Map.Entry<String,Map<Long,ExcleSateBean>> entry : map.entrySet()) {
				 String url = entry.getKey();
				 int countIncresment = 0;
				Map<Long, ExcleSateBean> value2 = entry.getValue();
				int size = value2.size();
				for (Map.Entry<Long, ExcleSateBean> entry2 : value2.entrySet()) {
					ExcleSateBean value = entry2.getValue();
					int count = value.getCount();
					ipList.add(value.getIp());
					if(count >1 || (count==1 && size>1)){//条件是 统一手机号 统一ip 同一ua 一秒内的统计
						map2 = new LinkedHashMap<String, String>();
						map2.put("mobile",value.getMobile());
						map2.put("ip",value.getIp());
						map2.put("ua",value.getUa());
						map2.put("stateTime",DateUtils.convertTimeToString(value.getStateTime(),"yyyy-MM-dd HH:mm") );
						map2.put("oldTime", value.getAllOldTimes());
						map2.put("count",count + "");
						map2.putAll(value.getTimeRangeStat());
						list.add(map2);
						
						countIncresment += count;
					}
				}
				if(countIncresment >1){
					totalCount += countIncresment;
					Map<String, String> hashMap = new LinkedHashMap<String, String>();
					hashMap.put("mobile", url.split("\\|")[0]);
					hashMap.put("count",countIncresment+"");
					hashMap.put("shuff", url);
					
					list2.add(hashMap);
					allCounts.add(countIncresment);
				}
			}
			 map.clear();
			 
	    	List<String> fileNames = new ArrayList<String>();
	    	fileNames.add("结果");
	    	fileNames.add("统计");
	    	fileNames.add("累计统计");
	    	//fileNames.add("ip统计");
	    	
	    	ExportExcel excle = new ExportExcel(forderPath+"统计"+fileName,fileNames);
	    	List<List<Map<String,String>>> results = new ArrayList<List<Map<String,String>>>();
	    	List<Header> headers = new ArrayList<ExportExcel.Header>();
	    	
			 Header header=excle.createHeader()
						.appendColumn("手机号", "mobile")
						.appendColumn("ip地址", "ip")
						.appendColumn("ua", "ua")
						.appendColumn("统计时间", "stateTime")
						.appendColumn("原始时间", "oldTime")
						.appendColumn("次数", "count")
						.appendColumn("[0-10]", "[0-10]")
						.appendColumn("(10-20]", "(10-20]")
						.appendColumn("(20-60]", "(20-60]")
						;
			 System.out.println("数据数据组装结束，耗时:"+(System.currentTimeMillis()-currentTimeMillis)/1000+"s"+"个数:"+list.size());			
			 headers.add(header);
			 results.add(list);
			 
			 Header header2=excle.createHeader()
					 .appendColumn("手机号", "mobile")
					 .appendColumn("出现次数", "count")
					 .appendColumn("前缀", "shuff");
			 headers.add(header2);
			 results.add(list2);
			System.out.println(forderPath+"统计"+fileName +"总次数:"+totalCount);
			
			
			for (Integer count : allCounts) {
				if(statMap.containsKey(count)){
					statMap.put(count, statMap.get(count)+1);
				}else{
					statMap.put(count, 1);
				}
			}
			System.out.println(statMap);
			List<Map<String,String>> list3=new ArrayList<Map<String,String>>();
			LinkedHashMap<String,String> finalStatMap = null;
			for (Map.Entry<Integer,Integer> entry : statMap.entrySet()) {
				 finalStatMap = new LinkedHashMap<String, String>();
				Integer key = entry.getKey();
				int value = 0;
				Collection<Integer> tailMap = statMap.tailMap(key).values();
				for (Integer integer : tailMap) {
					value += integer; 
				}
				finalStatMap.put("key", key+"");
				finalStatMap.put("oldvalue", entry.getValue()+"");
				finalStatMap.put("value", value+"");
				list3.add(finalStatMap);
			}
			System.out.println(list3);
			
			 Header header3 = excle.createHeader()
					 .appendColumn("出现次数值", "key") //大于2次的出现次数
					 .appendColumn("原始次数", "oldvalue")//
					 .appendColumn("累计", "value");//大于当前列出现次数总个数
			 headers.add(header3);
			 results.add(list3);
			 
			 
			 List<Map<String,String>> ipLists = getIpInfo(ipList);
			// ip地址
			 Header header4 = excle.createHeader()
					 .appendColumn("ip", "ip")
					 .appendColumn("省", "prov")
					 .appendColumn("市", "city");
			 
			 
			 
			 excle.batchExportListsToExcel(results, headers);
			 System.runFinalization();
		     System.gc();
		     
		     ExportExcel excleIp = new ExportExcel(forderPath+"IP统计"+fileName);
		     excleIp.exportListToExcel(ipLists, header4);
		     
		}
		
		
	}
	
	private static List<Map<String,String>> getIpInfo(List<String> ips) throws IOException{
		List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
		System.out.println("处理前:"+ips.size());
		for (Iterator<String> iterator = ips.iterator(); iterator.hasNext();) {
			String ip = iterator.next();
			if(equalIpMap.containsKey(ip)){
				HashMap<String, String> hashMap = new HashMap<String, String>();
				EqualIpBean equalIpBean = equalIpMap.get(ip);
				hashMap.put("prov",equalIpBean.getProv());
				hashMap.put("city", equalIpBean.getCity());
				hashMap.put("ip",ip);
				maps.add(hashMap);
				iterator.remove();
			}
		}
		if(ips.size()>0){
			System.out.println("剩余未被解析出来的ip 个数:"+ips.size()+"讲采用范围匹配");
			for (RangeIpBean bean : rangeIpList) {
				for (Iterator<String> iterator = ips.iterator(); iterator.hasNext();) {
					String next = iterator.next();
					long longIp = Long.parseLong(toHexString(next));
					if(bean.isIpIn(longIp)){
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("prov",bean.getProv());
						hashMap.put("city", bean.getCity());
						hashMap.put("ip",next);
						maps.add(hashMap);
						iterator.remove();
						break;
					}
				}
			}
		}
		if(ips.size()>0){
			System.out.println("剩余未被解析出来的ip 个数:"+ips.size());
			for (String ip : ips) {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("prov","");
				hashMap.put("city","");
				hashMap.put("ip",ip);
				maps.add(hashMap);
			}
		}
		
		
		System.out.println("处理后:"+ips.size());
		return maps;
	}

}
