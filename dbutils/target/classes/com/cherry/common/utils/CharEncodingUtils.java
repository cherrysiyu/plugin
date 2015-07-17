/**
 * 
 */
package com.cherry.common.utils;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @description:字符编码转换
 * @author: Cherry
 * @version:1.0
 * @data:Sep 23, 2011
 */
public class CharEncodingUtils {

	/**
	 * 把一个字符串从GBK编码码转化成ISO编码的字符串
	 * @param str
	 * @return
	 */
	public static String convertGBToISO(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("GB2312"), "ISO-8859-1");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }

	/**
	 * 把一个字符串从GBK编码码转化成UTF-8编码的字符串
	 * @param str
	 * @return
	 */
	  public static String convertGBToUTF8(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("GB2312"), "UTF-8");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }

	  /**
	   * 把一个字符串从ISO编码码转化成UTF-8编码的字符串
	   * @param str
	   * @return
	   */
	  public static String convertISOToUTF8(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }

	  /**
	   * 把一个字符串从ISO编码码转化成GBK编码的字符串
	   * @param str
	   * @return
	   */
	  public static String convertISOToGB(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("ISO-8859-1"), "GB2312");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }

	  /**
	   * 把一个字符串从UTF-8编码转化成ISO编码的字符串
	   * @param str
	   * @return
	   */
	  public static String convertUTF8ToISO(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("UTF-8"), "ISO-8859-1");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }

	  /**
	   * 把一个字符串从UTF-8编码转化成GBK编码的字符串
	   * @param str
	   * @return
	   */
	  public static String convertUTF8ToGB(String str)
	  {
	    if (str == null)
	      return "";
	    try
	    {
	      return new String(str.getBytes("UTF-8"), "GB2312");
	    } catch (Exception uee) {
	      uee.printStackTrace();
	    }return "";
	  }
	  
	  /**
	     * 根据指定的原始字符集和转换后字符集,转换字符串
	     * @param str 需要转换的字符串
	     * @param fromCharSet 原始的字符集
	     * @param toCharSet 转换后的字符集
	     * @return 根据指定的原始字符集和转换后字符集,转换字符串
	     */
	    public static String convertCharSet(String str,String fromCharSet,
	            String toCharSet){
	        if(str == null)
	            return "";
	        if(fromCharSet == null || fromCharSet.equals("") || toCharSet == null
	                || toCharSet.equals(""))
	            return str;
	        try{
	            return new String(str.getBytes(fromCharSet),toCharSet);
	        }catch(Exception e){
	            Log.error(getStackTraceMessage(e));
	            return e.getMessage();
	        }
	    }

	    public static String getStackTraceMessage(Throwable t){
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        t.printStackTrace(pw);
	        return sw.toString();
	    }

	    /**
	     * 把中文字符串转换为UTf-8的字符串
	     * @param param 需要转换的字符集
	     * @return 把中文字符串转换为UTf-8的字符串
	     */
	    public static String GB2UTF(String param){
	        try{
	            param = new String(param.getBytes("ISO-8859-1"),"GB2312");
	            byte[] bytes = param.getBytes("GB2312");
	            param = UTF2GBByByte(bytes);
	            return param;
	        }catch(Exception e){
	            return null;
	        }
	    }

	    /**
	     * 把字符集由UTF-8转换到GB2312
	     * @param param 字符串
	     * @return UTF-8的字符集转换到GB2312
	     */
	    public static String UTF2GB(String param){
	        try{
	            param = new String(param.getBytes("ISO-8859-1"),"UTF-8");
	            byte[] bytes = param.getBytes("UTF-8");
	            param = UTF2GBByByte(bytes);
	            return param;
	        }catch(Exception e){
	            return null;
	        }
	    }

	    /**
	     * 根据bytes数组,把UTF字符转换为GB2312
	     * @param bytes bytes数组
	     * @return 根据bytes数组内容,把UTF字符转换为GB2312
	     */
	    private static String UTF2GBByByte(byte bytes[]){
	        int len = bytes.length;
	        StringBuffer sb = new StringBuffer(len / 2);
	        for(int i = 0 ; i < len ; i++){
	            if(by2int(bytes[i]) <= 0x7F)
	                sb.append((char)bytes[i]);
	            else if(by2int(bytes[i]) <= 0xDF && by2int(bytes[i]) >= 0xC0){
	                int bh = by2int(bytes[i] & 0x1F);
	                int bl = by2int(bytes[++i] & 0x3F);
	                bl = by2int(bh << 6 | bl);
	                bh = by2int(bh >> 2);
	                int c = bh << 8 | bl;
	                sb.append((char)c);
	            }else if(by2int(bytes[i]) <= 0xEF && by2int(bytes[i]) >= 0xE0){
	                int bh = by2int(bytes[i] & 0x0F);
	                int bl = by2int(bytes[++i] & 0x3F);
	                int bll = by2int(bytes[++i] & 0x3F);
	                bh = by2int(bh << 4 | bl >> 2);
	                bl = by2int(bl << 6 | bll);
	                int c = bh << 8 | bl;
	                if(c == 58865){
	                    c = 32;
	                }
	                sb.append((char)c);
	            }
	        }
	        return sb.toString();
	    }

	    /**
	     * 待确认
	     * @param b
	     * @return
	     */
	    private static int by2int(int b){
	        return b & 0xff;
	    }

	    /**
	     * UTF8 -->ISO-8859-1
	     * @param str 需要转换的字符集
	     * @return 把UTF8的字符集转换为ISO-8859-1的字符集
	     */
	    public static String UTF2ISO(String str){
	        if(str == null){
	            return null;
	        }else{
	            try{
	                return new String(str.getBytes("UTF-8"),"ISO-8859-1");
	            }catch(Exception uee){
	                uee.printStackTrace();
	                return null;
	            }
	        }
	    }

	    /**
	     * ISO-8859-1 ==> UTF-8
	     * @param str 需要转换的字符串,字符串的字符集为ISO-8859-1
	     * @return 把ISO-8859-1的字符集转换为UTF-8字符集的字符串,如果字符串为空,则返回结果为空
	     */
	    public static String ISO2UTF(String str){
	        if(str == null){
	            return null;
	        }else{
	            try{
	                return new String(str.getBytes("ISO-8859-1"),"UTF-8");
	            }catch(Exception uee){
	                uee.printStackTrace();
	                return null;
	            }
	        }
	    }

	    /**
	     * ISO-8859-1 --> GB2312
	     * @param str 需要转换的字符串,字符串的字符集为ISO-8859-1
	     * @return 字符集为ISO-8859-1的字符串为GB2312的字符串
	     */
	    public static String ISO2GB(String str){
	        if(str == null){
	            return "";
	        }else{
	            try{
	                return new String(str.getBytes("ISO-8859-1"),"GB2312");
	            }catch(Exception uee){
	                uee.printStackTrace();
	                return "";
	            }
	        }
	    }

	    /**
	     * GB2312 --> ISO-8859-1
	     * @param str 需要转换的字符串,字符串的字符集为GB2312
	     * @return 字符集为GB2312的字符串为ISO-8859-1的字符串
	     */
	    public static String GB2ISO(String str){
	        if(str == null){
	            return "";
	        }else{
	            try{
	                return new String(str.getBytes("GB2312"),"ISO-8859-1");
	            }catch(Exception uee){
	                uee.printStackTrace();
	                return "";
	            }
	        }
	    }
	  
}
