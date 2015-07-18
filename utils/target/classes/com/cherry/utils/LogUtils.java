package com.cherry.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 专门用来记录日志的
 * @description:
 * @author:Cherry
 * @version:1.0
 * @date:Oct 27, 2012
 */
public class LogUtils {
	
	//public static Logger logger = initLog4j();
	private static  Logger logger = LoggerFactory.getLogger(LogUtils.class.getName());
	private LogUtils(){}
	
	public static void initLog4OtherSystem(String name){
		if(CommonMethod.isNotEmpty(name)){
			logger = LoggerFactory.getLogger(name);
			logger.info("Logger for Other System ["+name+"] !!!");
		}
		
	}
	
	/*private static Logger initLog4j() {
		 Logger log = null;
		 Properties props = new Properties();
		try {
			props.load(LogUtils.class.getClassLoader().getResourceAsStream("com/Cherry/common/resource/log4util.properties"));
			BasicConfigurator.resetConfiguration();
			PropertyConfigurator.configure(props);
			LogUtils.debug("===========加载系统内部log4util.properties文件成功=============");
			log =  Logger.getLogger("CommonLog");
		} catch (IOException e) {
			LogUtils.error(e);
			System.err.println("===========加载系统内部log4util.properties文件失败!!!,采用日志记录到工作台===========");
			log = Logger.getLogger(LogUtils.class);
		}
		return log;
	}*/


	public static void info(Object logContent) {
		logger.info("[" + getFileLineInfo(new Throwable()) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	public static void info(Object logContent, Throwable e) {
		logger.info("[" + getFileLineInfo(e) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	public static void debug(Object logContent) {
		logger.debug("[" + getFileLineInfo(new Throwable()) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	public static void debug(Object logContent, Throwable e) {
		logger.debug("[" + getFileLineInfo(e) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	public static void error(Object logContent) {
		logger.error("[" + getFileLineInfo(new Throwable()) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	public static void error(Object logContent, Throwable e) {
		logger.error("[" + getFileLineInfo(e) + "] "
				+ ((logContent == null) ? "null" : logContent.toString()));
	}

	private static String getFileLineInfo(Throwable t) {
		String s = getStackTraceMessage(t);
		int ibegin = s.indexOf(')') + 1;
		s = s.substring(ibegin);
		ibegin = s.indexOf('(') + 1;
		int iend = s.indexOf(')');
		return s.substring(ibegin, iend);
	}

	private static String getStackTraceMessage(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	

}
