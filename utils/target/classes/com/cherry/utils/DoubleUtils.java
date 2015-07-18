package com.cherry.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 如果使用 double 构造，API中已经说明，这个构造器的结果可能会有不可预知的结果。如： LogUtils.debug(new BigDecimal(1.2)); 
结果是：1.1999999999999999555910790149937383830547332763671875
而并非我们认为的 1.2；

所以再构造BigDecimal的时候，原则上我们使用 BigDecimal(String val)构造，这样就不会出问题
 * @author:Cherry
 * @date:2013-03-13
 * @version:1.0
 */
public class DoubleUtils {
	
	private DoubleUtils(){}
	
	/**
     * 判断一字符串是否可转化为Double
     * @param str
     * @return   可转为true
     */
     public static Boolean isDoubleType(String str) {
         try {
                 Double.valueOf(str);
                 return true;
         } catch (Exception e) {
                 return false;
         }
     }
     
     /**
      * 判断一个数组中的元素是否都是Double类型
      * @param array
      * @return
      */
     public static Boolean isAllDoubleType(String... array){
    	boolean flag = false;
 		if(CommonMethod.isArrayNotEmpty(array)){
 			for (String str : array) {
 				flag = isDoubleType(str);
 				if(!flag){//只要有空的情况就立即结束
 					break;
 				}
 			}
 		}
 		return flag;
     }

	/** 
     * 对double数据进行取精度. 
     * @param value double数据. 
     * @param scale 精度位数(保留的小数位数). 
     * @param roundingMode 精度取值方式. 
     * @return 精度计算后的数据. 
     */ 
    public static double round(double value, int scale, int roundingMode) {   
        BigDecimal bd = new BigDecimal(value);   
        bd = bd.setScale(scale, roundingMode);   
        double d = bd.doubleValue();   
        bd = null;   
        return d;   
    }   


     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    } 


    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2,int scale){ 
        // 当然在此之前，你要判断分母是否为0，   
        // 为0你可以根据实际需求做相应的处理 

        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    } 
    
    
    /**
     * 会4舍5入的
     * @param value
     * @param scale
     * @return
     */
    public static double getDoubleValue(double value, int scale){
    	String pattern = ".";
    	for (int i = 0; i < scale; i++) {
    		pattern += "#";
		}
    	DecimalFormat df = new DecimalFormat(pattern);
		return Double.valueOf(df.format(value));
    }
    
	public static void main(String[] args) {
			//下面都以保留2位小数为例 
		//ROUND_UP 
		//只要第2位后面存在大于0的小数，则第2位就+1 
		LogUtils.debug(round(12.3401,2,BigDecimal.ROUND_UP));//12.35 
		LogUtils.debug(round(-12.3401,2,BigDecimal.ROUND_UP));//-12.35 
		//ROUND_DOWN 
		//与ROUND_UP相反 
		//直接舍弃第2位后面的所有小数 
		LogUtils.debug(round(12.349,2,BigDecimal.ROUND_DOWN));//12.34 
		LogUtils.debug(round(-12.349,2,BigDecimal.ROUND_DOWN));//-12.34 
		//ROUND_CEILING 
		//如果数字>0 则和ROUND_UP作用一样 
		//如果数字<0 则和ROUND_DOWN作用一样 
		LogUtils.debug(round(12.3401,2,BigDecimal.ROUND_CEILING));//12.35 
		LogUtils.debug(round(-12.349,2,BigDecimal.ROUND_CEILING));//-12.34 
		//ROUND_FLOOR 
		//如果数字>0 则和ROUND_DOWN作用一样 
		//如果数字<0 则和ROUND_UP作用一样 
		LogUtils.debug(round(12.349,2,BigDecimal.ROUND_FLOOR));//12.34 
		LogUtils.debug(round(-12.3401,2,BigDecimal.ROUND_FLOOR));//-12.35 
		//ROUND_HALF_UP [这种方法最常用] 
		//如果第3位数字>=5,则第2位数字+1 
		//备注:只看第3位数字的值,不会考虑第3位之后的小数的 
		LogUtils.debug(round(12.345,2,BigDecimal.ROUND_HALF_UP));//12.35 
		LogUtils.debug(round(12.3449,2,BigDecimal.ROUND_HALF_UP));//12.34 
		LogUtils.debug(round(-12.345,2,BigDecimal.ROUND_HALF_UP));//-12.35 
		LogUtils.debug(round(-12.3449,2,BigDecimal.ROUND_HALF_UP));//-12.34 
		//ROUND_HALF_DOWN 
		//如果第3位数字>=5,则做ROUND_UP 
		//如果第3位数字<5,则做ROUND_DOWN 
		LogUtils.debug(round(12.345,2,BigDecimal.ROUND_HALF_DOWN));//12.35 
		LogUtils.debug(round(12.3449,2,BigDecimal.ROUND_HALF_DOWN));//12.34 
		LogUtils.debug(round(-12.345,2,BigDecimal.ROUND_HALF_DOWN));//-12.35 
		LogUtils.debug(round(-12.3449,2,BigDecimal.ROUND_HALF_DOWN));//-12.34
		//ROUND_HALF_EVEN 
		//如果第3位是偶数,则做ROUND_HALF_DOWN 
		//如果第3位是奇数,则做ROUND_HALF_UP 
		LogUtils.debug(round(12.346,2,BigDecimal.ROUND_HALF_EVEN));//12.35 
		LogUtils.debug(round(12.345,2,BigDecimal.ROUND_HALF_EVEN));//12.35 
	
	
		double d = 12352.344678222;
		LogUtils.debug("=================");
		LogUtils.debug(getDoubleValue(d,2));
		LogUtils.debug(getDoubleValue(d,3));
		
	
	}
	
	
}
