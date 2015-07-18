package com.cherry.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 负责Long类型的时间和String类型的时间的转换,以及获取一些常用的制定格式的时间
	@version:0.1
	@author :Cherry
    @date:2013-6-21
 */
public class DateUtils {

	/**
	 * 时间长类型
	 */
	public static final String  FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 时间短类型
	 */
	public static final String  FORMAT_SHORT = "yyyy-MM-dd";
	
	
	
	
	/**
	 * 转换long型(秒数)日期格式为字符型日期，精确到时分秒，如：convertTimeToString( 1264834129 ) -->2010-01-30 14:48:49
	 * @param longTime 需要转换的long型日期
	 * @return 格式为yyyy-MM-dd HH:mm:ss的字符型日期
	 */
	public static String convertTimeToString(long longTime) {
		return convertTimeToString(longTime, FORMAT_LONG);
	}

	/**
	 * 转换long型(秒数)日期格式为字符型日期，日期格式通过 format 定义，如：convertTimeToString( 1264834129 ,FORMAT_SHORT ) --> 2010-01-30
	 * @param longTime  需要转换的long型日期
	 * @param format 日期格式 年：yyyy 月：MM 日：dd 小时：HH 分钟：mm 秒：ss
	 * @return 格式为yyyy-MM-dd HH:mm:ss的字符型日期
	 */
	public static String convertTimeToString(long longTime, String format) {
		try {
			Timestamp t = new Timestamp(longTime * 1000);
			SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
			return sDateFormat.format(t);
		} catch (Exception ex) {
			throw new RuntimeException("Can't format the time by format["+format+"]!");
		}

	}

	/**
	 * 转换字符型日期(精确到时分秒)为long型日期(秒数)，如：convertTimeToLong( "2010-01-30 14:48:49" )--> 1264834129
	 * @param dateTime 需要转换的Str型日期
	 * @return long型日期
	 */
	public static long convertTimeToLong(String dateTime) {
		return convertTimeToLong(dateTime, FORMAT_LONG);
	}

	/**
	 * 转换字符型日期(日期格式通过format定义)为long型日期(秒数)，如：convertTimeToLong( "2010-01-30",FORMAT_SHORT ) --> 1264780800
	 * @param dateTime 需要转换的Str型日期
	 * @param format 日期格式 年：yyyy 月：MM 日：dd 小时：HH 分钟：mm 秒：ss
	 * @return long型日期
	 */
	public static long convertTimeToLong(String dateTime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long date = 0;
		try {
			Date d = sdf.parse(dateTime);
			date = d.getTime() / 1000;
		} catch (Exception ex) {
			throw new RuntimeException("Can't format the time by format["+format+"]!");
		}
		return date;
	}

	/**
	 * 获取当前时间的long型值(秒数)。
	 * @return long型日期
	 */
	public static long getCurrentLongTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间字符串表示，精确到时分秒，如：2010-01-30 14:48:49
	 * @return 字符型日期
	 */
	public static String getCurrentTime() {
		return getCurrentTime(FORMAT_LONG);
	}

	/**
	 * 获取当前时间字符串表示，日期格式由format定义，如：getCurrentTime(FORMAT_SHORT) --> 2010-01-30
	 * @param format 日期格式 年：yyyy 月：MM 日：dd 小时：HH 分钟：mm 秒：ss";
	 * @return 日期字符串
	 */
	public static String getCurrentTime(String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(new Date());
		} catch (Exception e) {
			throw new RuntimeException("Can't format the time by format["+format+"]!");
		}
	}

	/**
	 * 获取2个日期之间间隔的天数，如：getDayByDateToDate("2009-12-1","2009-9-29"); --> -63
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return 间隔天数（long型）
	 */
	public static long getDayBetweenDateAndDate(String startDate, String endDate) {

		SimpleDateFormat myFormatter = new SimpleDateFormat(FORMAT_SHORT);
		long day = 0;
		try {
			Date date = myFormatter.parse(endDate);
			Date mydate = myFormatter.parse(startDate);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception ex) {
			throw new RuntimeException("Time format["+startDate+"]["+endDate+"] is error ! format must be 'yyyy-MM-dd'！");
		}
		return day;
	}

	/**
	 * 根据日期获取是星期几 0,1,2,3,4,5,6 分别对应 礼拜日,礼拜一，礼拜二，礼拜三，礼拜四，礼拜五，礼拜六
	 * @return 0-6 
	 */
	public static int getWeekday(String dateTime) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(FORMAT_SHORT);
		try {
			Date date = myFormatter.parse(dateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_WEEK) - 1< 0 ? 0 : cal.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (ParseException e) {
			throw new RuntimeException("Time format["+dateTime+"] is error ! format must be 'yyyy-MM-dd'！");
		}
		
	}

	/**
	 * 获得上周一的日期字符串
	 * @return 上周星期一的日期字符串
	 */
	public static String getPreWeekFirstDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获得上周日的日期字符串
	 * @return 上周星期日的日期字符串
	 */
	public static String getPreWeekLastDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus -1);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获得下周一的日期字符串
	 * @return 下周星期一的日期字符串
	 */
	public static String getNextWeekFirstDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获得下周日的日期字符串
	 * @return 下周星期日的日期字符串
	 */
	public static String getNextWeekLastDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获得本周一的日期字符串
	 * @return 本周星期一的日期字符串
	 */
	public static String getWeekFirstDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获得本周日的日期字符串
	 * @return 本周星期日的日期字符串
	 */
	public static String getWeekLastDay() {
		Calendar cd = Calendar.getInstance();
		int week= cd.get(Calendar.DAY_OF_WEEK) == 1 ? 8 : cd.get(Calendar.DAY_OF_WEEK);
		int mondayPlus = week - 1 == 1 ? 0 : 1 - (week - 1);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(monday);
	}

	/**
	 * 获取上个月第一天日期字符串
	 * @return 上个月第一天日期字符串
	 */
	public static String getPreMonthFirstDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取上个月最后一天日期字符串
	 * @return 上个月最后一天日期字符串
	 */
	public static String getPreMonthLastDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.roll(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取本月第一天日期字符串
	 * @return 本月第一天日期字符串
	 */
	public static String getMonthFirstDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取本月最后一天日期字符串
	 * @return 本月最后一天日期字符串
	 */
	public static String getMonthLastDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取下个月第一天日期字符串
	 * @return 下个月第一天日期字符串
	 */
	public static String getNextMonthFirstDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取下个月最后一天日期字符串
	 * @return 下个月最后一天日期字符串
	 */
	public static String getNextMonthLastDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);
		lastDate.set(Calendar.DATE, 1);
		lastDate.roll(Calendar.DATE, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 换取指定日期前多少天后多少天的日期，如DateUtils.getBeforeAfterDate("2010-02-08",-1)-->2010-02-07
	 * @param dateTime 日期字符串
	 * @param day 相对天数，为正数表示之后，为负数表示之前
	 * @return 指定日期字符串n天之前或者之后的日期
	 */
	public static String getBeforeAfterDate(String dateTime, int day) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_SHORT);
		Date oldDate = null;
		try {
			df.setLenient(false);
			oldDate = new Date(df.parse(dateTime).getTime());
		} catch (ParseException e) {
			throw new RuntimeException("Time format["+dateTime+"] is error ! format must be 'yyyy-MM-dd'！");
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(oldDate);
		int Year = cal.get(Calendar.YEAR);
		int Month = cal.get(Calendar.MONTH);
		int Day = cal.get(Calendar.DAY_OF_MONTH);
		int NewDay = Day + day;
		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, Month);
		cal.set(Calendar.DAY_OF_MONTH, NewDay);
		Date date = new Date(cal.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);
		return sdf.format(date);
	}
	
	
	/**
	   * 获取本年的第一天日期
	   * @return
	   */
	  public static String getCurrentYearFirst(){
		  	 return getBeforeAfterDate(getCurrentTime(FORMAT_SHORT),getYearPlus());
	  }
	  
	  
	  /**
	   * 获取本年最后一天日期
	   * @return
	   */
	  public static String getCurrentYearEnd(){
		  
		  	Date date = new Date();  
	        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式     
	        String  years  = dateFormat.format(date);     
	        return years+"-12-31";  
	  }
	  
	  
	  /**
	   * 获取去年的第一天日期
	   * @return
	   */
	  public static String getPreviousYearFirst(){
		  
		  	Date date = new Date();  
	        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式     
	        String  years  = dateFormat.format(date); int years_value = Integer.parseInt(years);    
	        years_value--;  
	        return years_value+"-01-01"; 
	  }
	  
	  /**
	   * 获取去年的最后一天日期
	   * @return
	   */
	  public static String getPreviousYearEnd(){
		//用来全局控制 上一周，本周，下一周的周数变化  
		    int weeks = 0;  
		    int MaxYear = 0;//一年最大天数
		  	weeks--;  
	        int yearPlus = getYearPlus();  
	        GregorianCalendar currentDate = new GregorianCalendar();  
	        currentDate.add(GregorianCalendar.DATE,yearPlus+MaxYear*weeks+(MaxYear-1));  
	        Date yearDay = currentDate.getTime();  
	        DateFormat df = DateFormat.getDateInstance();  
	        String preYearDay = df.format(yearDay);  
	        getSeasonTime(11);  
	        return preYearDay;  
		  
		  
	  }
	  
	  /**
	   * 获取明年第一天日期
	   * @return
	   */
	  public static String getNextYearFirst(){
		  
		  String str = "";  
	      SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_SHORT);      
	  
	      Calendar lastDate = Calendar.getInstance();  
	      lastDate.add(Calendar.YEAR,1);//加一个年  
	      lastDate.set(Calendar.DAY_OF_YEAR, 1);  
	      str=sdf.format(lastDate.getTime());  
	      return str; 
		  
		  
	  }
	  
	  /**
	   * 获取明年最后一天日期
	   * @return
	   */
	  public static String getNextYearEnd(){
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_SHORT);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;    
		  
		  
		  
	  }
	  
	  /**
	   * 获取当前日期所在的季度的第一天和最后一天的日期
	   * @return String[]   String[0]表示当前时间所季度在的第一天的日期，String[1]表示当前时间所在季度的最后一天的日期
	   */
	  public static String[] getThisSeasonTime(){
		  Calendar calendar = new GregorianCalendar();
	       Date trialTime = new Date();
	       calendar.setTime(trialTime);
	       
		  return getSeasonTime(calendar.get(Calendar.MONTH)+1);
	  }
	  
	  /**
	   * 返回指定月所在的季度的第一天日期和最后一天的日期
	   * @param month 指定的月 取值是1-12
	   * @return @return String[]   String[0]表示当前月所季度在的第一天的日期，String[1]表示当前月所在季度的最后一天的日期
	   */
	  public static String[] getSeasonTime(int month){  
	        int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};  
	        int season = 1;  
	        if(month>=1&&month<=3){  
	            season = 1;  
	        }  
	        if(month>=4&&month<=6){  
	            season = 2;  
	        }  
	        if(month>=7&&month<=9){  
	            season = 3;  
	        }  
	        if(month>=10&&month<=12){  
	            season = 4;  
	        }  
	        int start_month = array[season-1][0];  
	        int end_month = array[season-1][2];  
	          
	        Date date = new Date();  
	        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式     
	        String  years  = dateFormat.format(date);     
	        int years_value = Integer.parseInt(years);  
	          
	        int start_days =1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);  
	        int end_days = getLastDayOfMonth(years_value,end_month);  
	        String[] result = new String[2];
	        result[0] = years_value+getSplit(start_month)+start_month+getSplit(start_days)+start_days;
	        result[1] = years_value+getSplit(end_month)+end_month+getSplit(end_days)+end_days;
	        return result;  
	          
	    }
	  	private static String  getSplit(int number){
	  		
	  		return number >9?"-":"-0";
	  	}
	  
	  	private static int getYearPlus(){  
	        Calendar cd = Calendar.getInstance();  
	        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天  
	        cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天  
	        cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。  
	        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);  
	        if(yearOfNumber == 1){  
	            return -MaxYear;  
	        }else{  
	            return 1-yearOfNumber;  
	        }  
	    } 
	  
	  /** 
	     * 获取某年某月的最后一天 
	     * @param year 年 
	     * @param month 月 
	     * @return 最后一天 
	     */  
	   private static int getLastDayOfMonth(int year, int month) {  
	         if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
	                   || month == 10 || month == 12) {  
	               return 31;  
	         }  
	         if (month == 4 || month == 6 || month == 9 || month == 11) {  
	               return 30;  
	         }  
	         if (month == 2) {  
	               if (isLeapYear(year)) {  
	                   return 29;  
	               } else {  
	                   return 28;  
	               }  
	         }  
	         return 0;  
	   }  
	   /** 
	    * 是否闰年 
	    * @param year 年 
	    * @return  
	    */  
	  public static boolean isLeapYear(int year) {  
	        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);  
	  } 


}
