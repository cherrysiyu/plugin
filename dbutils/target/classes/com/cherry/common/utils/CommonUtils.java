/**
 * 
 */
package com.cherry.common.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @description:公用转换方法
 * @author: Cherry
 * @version:1.0
 * @data:Sep 23, 2011
 */
public class CommonUtils {

	 /**
     * 将一个字符串数组转换成一个以,分割的字符串,一般用来根据界面的批量选择,来转换对应的字符串
     * @param stringArr 字符串数组
     * @return 根据字符串数据,返回以","分隔的字符串
     */
    public static String convertArrayToStr(String[] stringArr){
        String str = "";
        if(null != stringArr){
            for(int i = 0 ; i < stringArr.length ; i++){
                str += stringArr[i] + ",";
            }
        }
        if(str.length() > 1)
            str = str.substring(0,str.length() - 1);
        return str;
    }
    /**
     * 把对象转换为字符串,并且设定对象为空的情况下返回的默认值
     * @param obj 需要转换的对象
     * @param aDefault obj为空时的默认字符串
     * @return 如果对象不为空,则返回对象转换为字符串的值;如果为空,则返回设定的默认值
     */
    public static String convertObjToStr(Object obj,String aDefault){
        String strRtn = null;
        if(obj != null){
            strRtn = obj.toString();
        }else if(aDefault != null){
            strRtn = aDefault;
        }else{
            strRtn = "";
        }
        return strRtn;
    }
    /**
     * 判断数组中是否包含指定字符串
     * @param array 数组
     * @param str 特定的字符串值
     * @return 如果特殊字符存在数组中,则返回true;否则为false
     */
    public static boolean isContainInArrayByStr(String[] array,String str){
        boolean isClude = false;
        for(int i = 0 ; i < array.length ; i++){
            if(str != null){
                if(str.toUpperCase().equals(array[i].toUpperCase())){
                    isClude = true;
                    break;
                }
            }
        }
        return isClude;
    }
    
    public static String getStackTraceMessage(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
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
    /**
     * 转换数据库或者是全文返回的字符串中<,>,&,",\,' '特殊字符,使之能在HTML中正常显示
     * @param content 需要进行替换的字符串
     * @return 针对字符串中特殊的字符进行替换,如&==>&amp; "==>&quot; '==>&#039; < ==> &lt;
     */
    public static String replaceSpecialChar(String content){
        if(isEmpty(content)){
            return "";
        }else{
            return content.replaceAll("&","&amp;").replaceAll("\"","&quot;")
                    .replaceAll("\'","&#039;").replaceAll("<","&lt;")
                    .replaceAll(">","&gt;");
        }
    }

    /**
     * 根据命令行值以及特殊的命令行参数,获得命令行参数对应的值,常用于6系统的分析功能.
     * @param cmdLine 命令行,格式固定.格式如右:key1=1;key2=2;key3=3; 其中最后字符可为";"也可为空
     * @param paramName 命令行的参数名称,如key1
     * @return 根据命令行的参数名称,到命令行中获取对应的参数,如有多个对应的值,则返回以","分割的字符串.
     */
    public static String getCommandParamStr(String cmdLine,String paramName){
        String value = "";
        if(isEmpty(cmdLine)){
            value = "";
        }
        if(!paramName.endsWith("=")){
            paramName += "=";
        }
        if(!isEmpty(cmdLine)){
            // 判断命令行不为空
            String[] commands = cmdLine.split(";");
            if(commands != null){
                // 判断命令行的格式后的数组是否为空
                for(String command : commands){
                    if(command.startsWith(paramName)){
                        if(!value.equals("")){
                            value += ",";
                        }
                        value += command.substring(paramName.length());
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * 判断输入的字符串是否符合Email样式
     * @param email
     * @return 如果为mail,则返回true.如果不是mail,则返回false
     */
    public static boolean isEmail(String email){
        if(email == null || email.length() < 1 || email.length() > 256){
            return false;
        }
        Pattern pattern = Pattern
                .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * 判断输入的字符串是否为纯汉字
     * @param str 需要判断的字符串
     * @return 如果字符串中无汉字,则返回true;如果有汉字,则返回false
     */
    public static boolean isChinese(String str){
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断对象是否为空,字符串对象包含""
     * @param obj 需要判断的对象
     * @return 如果对象为空,那么返回true;如果对象不为空,则返回false
     */
    public static boolean isEmpty(Object obj){
        boolean sign = false;
        if(obj == null){
            sign = true;
        }else if(obj.getClass() == String.class){
            if("".equals(obj.toString())){
                sign = true;
            }
        }
        return sign;
    }

    /**
     * 根据需要转换的字符串,转换为int类型
     * @param str 需要转换的字符串
     * @return 如果字符串含有特殊字符等情况,则返回0;如字符串正常,则返回转换后的Interger类型的字符串
     */
    public static int convertStrToInt(String str){
        Integer i = 0;
        try{
            i = Integer.parseInt(str);
            return i;
        }catch(NumberFormatException ex){
            Log.error("类型转换异常:" + ex.getMessage());
        }
        return 0;
    }

    /**
     * 把Integer类型,转换为字符串的类型
     * @param intValue 需要转换的Integer类型的值
     * @return 把Integer类型的值转换为String类型
     */
    public static String convertIntToStr(Integer intValue){
        return intValue + "";
    }

    /**
     * 将字符串转换为Long包装类
     * @param str 需要转换的String类型的值
     * @return 把String类型的值转换为Integer类型
     */
    public static Long convertStrToLong(String str){
        Long l = null;
        try{
            l = Long.parseLong(str);
            return l;
        }catch(NumberFormatException ex){
            Log.error("类型转换异常:" + ex.getMessage());
        }
        return null;
    }

    /**
     * 将Long类型的值转换为String类型的值
     * @param longValue Long类型的参数值
     * @return 将Long类型的值转换为String类型的值
     */
    public static String convertLongToStr(Long longValue){
        return longValue + "";
    }

    /**
     * 去除null和字符串两边的空格
     * @param str 字符串的值
     * @return 去除null和字符串两边的空格
     */
    public static String delEmpty(String str){
        String returnStr = "";
        if(null == str)
            returnStr = "";
        else{
            if(!str.trim().equals(""))
                returnStr = str.trim();
            else
                returnStr = "";
        }
        return returnStr;
    }
    
    /**
     * 判断是否为整数
     * @param str 需要判断的对象
     * @return 如果对象是整数,则返回true;否则返回false;
     */
    public static boolean isInteger(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     * @param str 需要判断的对象
     * @return 如果对象是浮点数,则返回true;否则返回false;
     */
    public static boolean isDouble(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
        return pattern.matcher(str).matches();
    }
    
    
    /**
     * 得到指定分隔的字符串
     * @param separater 分隔符号
     * @param objects 对象集合
     * @param separaterRule 生成集合中单个对象String的规则
     * @return
     */
	public static String getSeparaterString(String separater, Collection<? extends Object> objects, SeparaterRule<Object> separaterRule ){
		
		if( objects == null ){
			return "";
		}
		
		if( separater == null ){
			throw new IllegalArgumentException( "分隔符不可为null" );
		}
		
		List<String> list = new ArrayList<String>();
		for ( Object object:objects ){
			
			if( separaterRule.getString( object ) == null || "".equals( separaterRule.getString( object ) ) ){
				continue;
			}
			
			list.add( separaterRule.getString( object ) );
		}
		
		return getSeparaterString( separater,  list );
	}
    
    /**
	 * 得到指定分隔的字符串
	 * @param separater 分隔符号
	 * @param maps 要分隔集合
	 * @param key 分隔集合中map的键值
	 * @throws IllegalAccessException 不和函数调用逻辑异常
	 */
	public static String getSeparaterString(String separater, Collection<? extends Map<String,Object>> maps, String key){
		
		if( maps ==null ){
			return "";
		}
		
		if( separater == null ){
			throw new IllegalArgumentException( "分隔符不可为null" );
		}
		
		List<String> list = new ArrayList<String>();
		for ( Map<String,Object> map:maps ){
			
			if( map.get( key ) == null || "".equals( String.valueOf( map.get( key ) ) ) ){
				continue;
			}
			
			list.add( String.valueOf( map.get( key ) ) );
		}
		
		return getSeparaterString( separater,  list );
	}
    
    /**
	 * 得到指定分隔的字符串
	 * @param separater 分隔符号
	 * @param strings 要分隔集合
	 * @throws IllegalAccessException 不和函数调用逻辑异常
	 */
	public static String getSeparaterString(String separater, Collection<String> strings){
		
		if ( strings == null || strings.size() == 0 ){
			return "";
		}
		
		String[] temp_string = new String[strings.size()];
		int i = 0;
		for ( String str:strings ){
			temp_string[i++] = str;
		}
		return getSeparaterString( separater, temp_string );
		
	}
    
	/**
	 * 得到指定分隔的字符串
	 * @param separater 分隔符号
	 * @param strings 要分隔集合
	 * @throws IllegalAccessException 不符合函数调用逻辑异常
	 */
	public static String getSeparaterString(String separater, String... strings  ) {
		return getSeparaterString(  separater,  false,  strings  );
	}
	
	
	/**
	 * 得到sql语句中使用逗号分隔的字符串
	 * @param objects 对象集合
	 * @param separaterRule 生成集合中单个对象String的规则
	 * @return 逗号分隔的字符串
	 */
	public static String getSQLSeparaterString( Collection<? extends Object> objects, SeparaterRule<Object> separaterRule ){
		
		if( objects == null ){
			return "";
		}

		List<String> list = new ArrayList<String>();
		for ( Object object:objects ){
			
			if( separaterRule.getString( object ) == null || "".equals( separaterRule.getString( object ) ) ){
				continue;
			}
			
			list.add( separaterRule.getString( object ) );
		}
		
		return getSQLSeparaterString(  list );
	}
	
	/**
	 * 得到sql语句中使用逗号分隔的字符串
	 * @param c 含有Map对象的集合
	 * @param key map中的键值
	 * @return 逗号分隔的字符串
	 */
	public static String getSQLSeparaterString( Collection<? extends Map<String,Object>> c, String key){
		if( c ==null ){
			return "";
		}
		
		List<String> list = new ArrayList<String>();
		for ( Map<String,Object> map:c ){
			
			if( map.get( key ) == null || "".equals( String.valueOf( map.get( key ) ) ) ){
				continue;
			}
			
			list.add( String.valueOf( map.get( key ) ) );
		}
		
		return getSQLSeparaterString(  list );
	}
	
	/**
	 * 得到sql语句中使用逗号分隔的字符串
	 * @param strings string类型的集合对象
	 * @return 逗号分隔后的字符串
	 */
	public static String getSQLSeparaterString( Collection<String> strings){
		if ( strings == null || strings.size() == 0 ){
			return "";
		}
		
		String[] temp_string = new String[strings.size()];
		int i = 0;
		for ( String str:strings ){
			temp_string[i++] = str;
		}
		return getSQLSeparaterString( temp_string  );
	}
	
	/**
	 * 得到sql语句中使用逗号分隔的字符串
	 * @param strings string类型数组
	 * @return 逗号分隔后的字符串
	 */
	public static String getSQLSeparaterString( String... strings  ){
		return getSeparaterString(  ",",  true,  strings  );
	}
	
	/**
	 * 得到指定分隔的字符串
	 * @param separater 分隔符号
	 * @param isAppSql 是否应用于SQL语句
	 * @param strings 要分隔集合
	 * @throws IllegalAccessException 不和函数调用逻辑异常
	 */
	private static String getSeparaterString(String separater, boolean isAppSql, String... strings  ) {
		
		if( isAppSql ){
			separater = ",";
		}
			
		if( strings == null ){
			return "";
		}
		
		if( separater == null ){
			throw new IllegalArgumentException( "分隔符不可为null" );
		}
		
		StringBuilder strb = new StringBuilder();
		for (String string : strings) {
			
			if( CommonUtils.isEmpty( CommonUtils.isEmpty( string ) ) ){
				continue;
			}
			
			if( string.contains( separater ) ){
				throw new IllegalArgumentException( "集合中含有分隔符号" );
			}
			
			if( strb.length()>0  ){
				strb.append( separater );
			}
			
			if( isAppSql ){
				strb.append( "'" ).append( string ).append( "'" );
			}else{
				strb.append( string );
			}
		}
		
		return strb.toString();
	}
  
	
	/**
     * 去除特殊字符串
     * @param str  要过滤的字符串
     * @return 去除特殊字符后的字符串
     */
    public static String StringFilter(String str){
   	 
   	 // 只允许字母和数字        
   	  // String   regEx  =  "[^a-zA-Z0-9]";                      
   	  // 清除掉所有特殊字符   
   	  String regEx="[`~!@#$%^&*()+=|{}':;',///\\[//\\].<>/?~！_@#￥%……&* （）——+|{}【】‘；：”“’。，、？]";   
   	  Pattern   p   =   Pattern.compile(regEx);      
   	  Matcher   m   =   p.matcher(str);      
   	  return   m.replaceAll("").trim();    
    }
    
    /**
     * 将金额转换成大写
     * @param rmb 金额的数值
     * @return 根据金额的数值,转换为大写.
     */
    public static String convertPriceToUpercase(double rmb){
        char[] hunit = {'拾','佰','仟'}; // 段内位置表示
        char[] vunit = {'万','亿'}; // 段名表示
        char[] digit = {'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'}; // 数字表示
        long midVal = (long)(rmb * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串
        String head = valStr.substring(0,valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分
        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if(rail.equals("00")){ // 如果小数部分为0
            suffix = "整";
        }else{
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for(int i = 0 ; i < chDig.length ; i++){ // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if(chDig[i] == '0'){ // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if(zero == '0'){ // 标志
                    zero = digit[0];
                }else if(idx == 0 && vidx > 0 && zeroSerNum < 4){
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if(zero != '0'){ // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if(idx > 0)
                prefix += hunit[idx - 1];
            if(idx == 0 && vidx > 0){
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }
        if(prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

}
