package com.cherry.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcleSateBean {
	
	private Long stateTime;
	
	private List<String> oldTime = new ArrayList<String>();
	private List<Long> statOldTime = new ArrayList<Long>();
	
	private Date  oldDate;
	
	private String mobile="";
	
	private String ip="";
	
	private String ua="";
	
	private int count = 1;
	
	public ExcleSateBean(Date oldDate) {
		super();
		setOldDate(oldDate);
	}

	public Long getStateTime() {
		return stateTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public Date getOldDate() {
		return oldDate;
	}

	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
		if(oldDate != null){
			oldTime.add(DateUtils.convertTimeToString(oldDate.getTime()/1000));
			statOldTime.add(oldDate.getTime()/1000);
			stateTime =DateUtils.convertTimeToLong(DateUtils.convertTimeToString(oldDate.getTime()/1000,"yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm") ;
		}
	}

	public ExcleSateBean incresment(){
		count++;
		return this;
	}
	public ExcleSateBean addOldTime(Date oldDate){
		oldTime.add(DateUtils.convertTimeToString(oldDate.getTime()/1000));
		statOldTime.add(oldDate.getTime()/1000);
		return this;
	}
	
	public ExcleSateBean addMobile(Object mobile){
		if(mobile != null)
			this.mobile = mobile.toString();
		return this;
	}
	
	public String getMobile() {
		return mobile==null?"":mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIp() {
		return ip==null?"":ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public ExcleSateBean addIp(Object ip){
		if(ip != null)
			this.ip = ip.toString();
		return this;
	}
	
	public String getUa() {
		return ua==null?"":ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
		
	}
	public ExcleSateBean addUa(Object ua){
		if(ua != null)
			this.ua = ua.toString();
		return this;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stateTime == null) ? 0 : stateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExcleSateBean other = (ExcleSateBean) obj;
		if (stateTime == null) {
			if (other.stateTime != null)
				return false;
		} else if (!stateTime.equals(other.stateTime))
			return false;
		return true;
	}
	public String getAllOldTimes(){
		Collections.sort(oldTime);
		return CommonMethod.getSeparaterString(",", oldTime);
	}
	public Map<String,String> getTimeRangeStat(){
		Map<String,String> map = new HashMap<String, String>();
		int size1 = 0,size2=0,size3 = 0;
		if(count >=2){
			Long time1 = statOldTime.get(0);
			for (int i = 1; i < statOldTime.size(); i++) {
				Long time2 = statOldTime.get(i);
				if(time2 - time1 <=10){
					size1++;
				}else if(time2 - time1 <=20){
					size2++;
				}else if(time2 - time1 <=60){
					size3++;
				}
				time1 = time2;
			}
			map.put("[0-10]", size1==0?"":(size1+""));	
			map.put("(10-20]", size2==0?"":size2+"");
			map.put("(20-60]", size3==0?"":size3+"");
		}else{
			map.put("[0-10]", "");	
			map.put("(10-20]", "");
			map.put("(20-60]", "");
		}
		return map;
	}

}
