/**
 * 
 */
package com.cherry.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
/**
 * <pre>log4j文件中写入文件到制定的文件中</pre>
 * @description:日志操作公用方法 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class Log {

    public static Logger logger = Logger.getLogger(Log.class );

   /**
    * 此方法用来被其他logger调用
    * @param inputLogger
    */
    public void setLogger(Logger inputLogger)
    {
      if (inputLogger == null)
        return;
      Log.logger = inputLogger;
      Log.info("Logger is from other system!!!====================================");
    }
	/**
     * 记录消息
     * @param logContent
     */
    public static void info( Object logContent ) {
        logger.info( "[" + getFileLineInfo( new Throwable() ) + "] "    
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 记录消息
     * @param logContent
     */
    public static void info( Object logContent , Throwable e ) {
        logger.info( "[" + getFileLineInfo( e ) + "] "
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 记录调试消息
     * @param logContent
     */
    public static void debug( Object logContent ) {
        logger.debug( "[" + getFileLineInfo( new Throwable() ) + "] "
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 记录调试消息
     * @param logContent
     */
    public static void debug( Object logContent , Throwable e ) {
        logger.debug( "[" + getFileLineInfo( e ) + "] "
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 记录错误消息
     * @param logContent
     */
    public static void error( Object logContent ) {
        logger.error( "[" + getFileLineInfo( new Throwable() ) + "] "
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 记录错误消息
     * @param logContent
     */
    public static void error( Object logContent , Throwable e ) {
        logger.error( "[" + getFileLineInfo( e ) + "] "
                + ( logContent == null ? "null" : logContent.toString() ) );
    }

    /**
     * 获取Throwable对象所有抛出的信息
     * @param t
     *            Throwable 异常
     * @return String
     */
    private static String getFileLineInfo( Throwable t ) {
        String s = getStackTraceMessage( t );
        int ibegin = s.indexOf( ')' ) + 1;
        s = s.substring( ibegin );
        ibegin = s.indexOf( '(' ) + 1;
        int iend = s.indexOf( ')' );
        return s.substring( ibegin , iend );
    }

    /**
     * 获取Throwable对象所有抛出的信息
     * @param t
     *            Throwable 异常
     * @return String
     */
    private static String getStackTraceMessage( Throwable t ) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter( sw );
        t.printStackTrace( pw );
        return sw.toString();
    }
}

