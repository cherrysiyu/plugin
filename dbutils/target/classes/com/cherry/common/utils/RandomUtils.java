/**
 * 
 */
package com.cherry.common.utils;

import java.util.Random;

/**
 * @description:生成指定的随机数 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class RandomUtils {

	
	/**
	 * 随机字符数字串库
	 */
	private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 随机数字
	 */
	private static final String RANDOM_NUMBER = "0123456789";

	/**
	 * 创建随机数，由a-zA-Z0-9字串随机组成
	 * 
	 * @param len 随机数长度
	 * @return 随机数
	 */
	public static String randomString(int len) {
		if (len <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Random radom = new Random();
		for (int r = 0; r < len; r++) {
			// 返回[0 62)之间的随机int数
			int at = radom.nextInt(62);
			// 从 RANDOM_STRING 中取出随机的字符
			sb.append(RANDOM_STRING.charAt(at));
		}

		return sb.toString();
	}

	/**
	 * 创建随机数，由0-9数字随机组成
	 * 
	 * @param len 随机数长度
	 * @return 随机数
	 */
	public static String randomNumber(int len) {
		if (len <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Random radom = new Random();
		for (int r = 0; r < len; r++) {
			// 返回[0 62)之间的随机int数
			int at = radom.nextInt(10);
			// 从 RANDOM_STRING 中取出随机的字符
			sb.append(RANDOM_NUMBER.charAt(at));
		}

		return sb.toString();
	}
	
	/**
	   * 获取指定范围的随机数
	   * @param start
	   * @param end
	   * @return
	   */
	  public static int getRandom(int start,int end){
	      int temp1 = 0;
	      int temp2 = 0;
	      int result = 0;
	      if(start > end){
	          temp1 = start - end + 1;
	          temp2 = end;
	      }else if(start < end){
	          temp1 = end - start + 1;
	          temp2 = start;
	      }
	      if(start == end){
	          temp2 = start;
	          result = start;
	      }else{
	          result = (Math.abs(new Random().nextInt())) % temp1 + temp2;
	      }
	      return result;
	  }
	  
	  /**
	   * 生成指定长度的字符串格式随机数
	   * @param randomDigit 随机数种子
	   * @param length 随机数长度
	   * @return 生成指定长度的字符串格式随机数,字符类型
	   */
	  public static String getRandom(Long randomDigit,int length){
	      String random = "";
	      if(length > 0){
	          Random r = new Random(randomDigit);
	          random = (r.nextLong() + "").replace("-","");
	          random = random.substring(0,length);
	      }
	      return random;
	  }
	
	
}
