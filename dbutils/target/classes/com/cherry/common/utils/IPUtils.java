/**
 * 
 */
package com.cherry.common.utils;


import java.net.InetAddress;
import java.util.StringTokenizer;
/**
 * 操作IP转换的工具类
 * @description:此类负责IP Long类型和String类型的转换
 * @author: Cherry
 * @version:1.0
 * @data:Mar 16, 2012
 */
public class IPUtils {

	/**
	 * long类型IP转换成String类型的IP 
	 * @param ip
	 * @return 192.168.1.12
	 */
	public static String convertIPToString(long ip)
	  {
	    String rtn = "";
	    try {
	      byte[] by = new byte[4];

	      InetAddress address = InetAddress.getByAddress(long2byte(by, ip, 0));
	      rtn = address.getHostAddress();

	      StringBuffer sb = new StringBuffer();
	      String[] ids = parseToken2String(rtn, ".");

	      sb.append(ids[3]);
	      sb.append(".");
	      sb.append(ids[2]);
	      sb.append(".");
	      sb.append(ids[1]);
	      sb.append(".");
	      sb.append(ids[0]);
	      rtn = sb.toString();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      return "";
	    }
	    return rtn;
	  }

	/**
	 * String类型的IP地址转换成Long类型的IP地址
	 * @param ipStr
	 * @return
	 */
	  public static long convertIPToLong(String ipStr)
	  {
	    try
	    {
	      if ((ipStr == null) || (ipStr.length() == 0))
	        return 0L;
	      long addr = 0L;
	      String b = "";
	      int tmpFlag1 = 0; int tmpFlag2 = 0;
	      tmpFlag1 = 0;
	      tmpFlag2 = ipStr.indexOf(".");
	      b = ipStr.substring(tmpFlag1, tmpFlag2);
	      addr = new Long(b).longValue();
	      tmpFlag1 = tmpFlag2 + 1;
	      tmpFlag2 = ipStr.indexOf(".", tmpFlag1);
	      addr <<= 8;
	      b = ipStr.substring(tmpFlag1, tmpFlag2);
	      addr += new Long(b).longValue();
	      tmpFlag1 = tmpFlag2 + 1;
	      tmpFlag2 = ipStr.indexOf(".", tmpFlag1);
	      addr <<= 8;
	      b = ipStr.substring(tmpFlag1, tmpFlag2);
	      addr += new Long(b).longValue();
	      addr <<= 8;
	      addr += new Long(ipStr.substring(tmpFlag2 + 1, ipStr.length())).longValue();
	      if (addr > 2147483647L) {
	        addr -= Long.parseLong("4294967296");
	      }
	      return addr; } catch (Exception e) {
	    }
	    return 0L;
	  }
	
	  private static String[] parseToken2String(String str, String delim)
	  {
	    String[] strRtn = (String[])null;
	    StringTokenizer st = new StringTokenizer(str, delim);
	    int length = st.countTokens();
	    strRtn = new String[length];
	    int i = 0;
	    while (st.hasMoreTokens()) {
	      String str1 = st.nextToken().trim();
	      strRtn[i] = str1;
	      i++;
	    }
	    return strRtn;
	  }

	  private static byte[] long2byte(byte[] out, long in, int offset) {
	    if (out.length > 0) {
	      out[offset] = (byte)(int)in;
	    }
	    if (out.length > 1) {
	      out[(offset + 1)] = (byte)(int)(in >>> 8);
	    }
	    if (out.length > 2) {
	      out[(offset + 2)] = (byte)(int)(in >>> 16);
	    }
	    if (out.length > 3) {
	      out[(offset + 3)] = (byte)(int)(in >>> 24);
	    }
	    if (out.length > 4) {
	      out[(offset + 4)] = (byte)(int)(in >>> 32);
	    }
	    if (out.length > 5) {
	      out[(offset + 5)] = (byte)(int)(in >>> 40);
	    }
	    if (out.length > 6) {
	      out[(offset + 6)] = (byte)(int)(in >>> 48);
	    }
	    if (out.length > 7) {
	      out[(offset + 7)] = (byte)(int)(in >>> 56);
	    }
	    return out;
	  }
	  
	  /**
	     * String类型的IP数值转换为字符型 如:1761716416 ==> 192.168.1.105
	     * @param ipValue String类型的IP的数值
	     * @return 根据IP的数值转换为字符串型的IP 如:1761716416 ==> 192.168.1.105
	     */
	    public static String convertIPNumToDot(String ipValue){
	        String sIp[] = new String[4];
	        Long ipLong = Long.parseLong(ipValue);
	        for(int i = 0 ; i < 4 ; i++){
	            long temp = ipLong >> (8 * i);
	            sIp[i] = Long.toString(temp & 0xff);
	        }
	        return sIp[3] + "." + sIp[2] + "." + sIp[1] + "." + sIp[0];
	    }

	    /**
	     * 长整型的IP数值转换为字符型
	     * @param ipValue long类型的IP的数值
	     * @return 根据IP的数值转换为字符串型的IP 如:1761716416 ==> 192.168.1.105
	     */
	    public static String convertIPNumToDot(Long ipValue){
	        String sIp[] = new String[4];
	        Long ipLong = ipValue;
	        for(int i = 0 ; i < 4 ; i++){
	            long temp = ipLong >> (8 * i);
	            sIp[i] = Long.toString(temp & 0xff);
	        }
	        return sIp[3] + "." + sIp[2] + "." + sIp[1] + "." + sIp[0];
	    }

	   
}
