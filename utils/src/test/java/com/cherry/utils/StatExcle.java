package com.cherry.utils;

import java.util.Map;

public class StatExcle{
		private int adPositionId;
		private int adId;
		private int days;
		private double totalMoney;
		private double avgMoney;
		private String totalMoneyStr;
		private String avgMoneyStr;
		private String zhl;
		public StatExcle() {
			super();
		}
		
		public StatExcle(int adPositionId, int adId, int days) {
			super();
			this.adPositionId = adPositionId;
			this.adId = adId;
			setDays(days);
		}

		public int getAdPositionId() {
			return adPositionId;
		}
		public void setAdPositionId(int adPositionId) {
			this.adPositionId = adPositionId;
		}
		public int getAdId() {
			return adId;
		}
		public void setAdId(int adId) {
			this.adId = adId;
		}
		public int getDays() {
			return days;
		}
		public void setDays(int days) {
			this.days = days;
			double d = (days*100.0d/23.0d);
			this.zhl= DoubleUtils.getDoubleValue(d, 2)+"";
		}
		public double getTotalMoney() {
			return totalMoney;
		}
		public void setTotalMoney(double totalMoney) {
			this.totalMoney += totalMoney;
		}
		public double getAvgMoney() {
			return avgMoney;
		}
		public void setAvgMoney(double avgMoney) {
			this.avgMoney += avgMoney;
		}
		
		public  StatExcle addTotalMoney(double money){
			setTotalMoney(money);
			setAvgMoney(money/days);
			return this;
		}
		
		@Override
		public String toString() {
			return adPositionId+"--->"+adId+"---->"+days;
		}
		
		public Map<String,String> toMap(){
			return CommonMethod.convertBean2Map(this);
		}

		public String getZhl() {
			return zhl;
		}

		public void setZhl(String zhl) {
			this.zhl = zhl;
		}

		public String getTotalMoneyStr() {
			return DoubleUtils.getDoubleValue(totalMoney, 2)+"";
		}

		public void setTotalMoneyStr(String totalMoneyStr) {
			this.totalMoneyStr = totalMoneyStr;
		}

		public String getAvgMoneyStr() {
			return DoubleUtils.getDoubleValue(totalMoney/days, 2)+"";
		}

		public void setAvgMoneyStr(String avgMoneyStr) {
			this.avgMoneyStr = avgMoneyStr;
		}
		
		
		
	}