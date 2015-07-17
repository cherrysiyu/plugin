package com.cherry.spring.plugin;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public class CommonMethod {
	private CommonMethod(){}
	

	/**
	 * 判断一个集合是否是空值
	 * @param collection 带判断的集合
	 * @return true/false (true:不是空的，false：是空的)
	 */
	public static boolean isCollectionNotEmpty(Collection<?> collection){
		return collection != null && !collection.isEmpty();
	}
	
	/**
	 * 判断一个Map集合是否是空值
	 * @param map 带判断的Map集合
	 * @return true/false (true:不是空的，false：是空的)
	 */
	public static boolean isMapNotEmpty(Map<?,?> map){
		return map != null && !map.isEmpty();
	}
	
	public static boolean isAnyEmpty(Object... objects ){
		return !isAllNotEmpty(objects);
	}
	
	/**
	 * <font color='red'>判断一个数组是否为空的,不推荐使用此方法判断数组是否为空，可以使用org.apache.commons.lang.ArrayUtils.isNotEmpty()方法来判断</font>
	 * @param array 待判断的数组
	 * @return true/false (true:不是空的，false：是空的)
	 */
	public static <T> boolean isArrayNotEmpty(T... array) {
		if(array == null)
			return false;
		else if(array.getClass().isArray()){
			boolean flag = true;
			for (T t : array) {
				if(t== null)
					flag = false;
				else if(t.getClass().isArray()){
					if("{}".equals(ArrayUtils.toString(t)))
						flag = false;
				}
			}
			return flag;	
		}
		return false;
    }
	
	/**
	 * 判断是否不是空，可以支持判断Object 类型,String 类型，Number 类型，Map类型及其子类，collection及其子类,以及数组对象(<font color='red'>不推荐使用此方法判断数组是否为空，可以使用org.apache.commons.lang.ArrayUtils.isNotEmpty()</font>),
	 * @param obj Object ,String,Map(及其子类)，Collection(及其子类) Array 类型
	 * @return 是否不是空值，如果不是空返回true，否则返回false
	 */
	public static boolean isNotEmpty(Object obj){
		
		if(obj == null)
			return false;
		else if(obj.getClass() == String.class)
			return !"".equals(obj);
		else if(obj instanceof Number)
			return obj != null;
		else if(obj instanceof Map<?,?>)
			return !((Map<?,?>)obj).isEmpty();
		else if(obj instanceof Collection<?>)
			return !((Collection<?>)obj).isEmpty();
		else if(obj.getClass().isArray())
			return Array.getLength(obj) != 0;
		else 
		return false;
	}
	
	/**
	 * 判断是否是空，可以支持判断Object 类型,String 类型，Map类型及其子类，collection及其子类,以及数组对象
	 * @param obj Object ,String,Map(及其子类)，Collection(及其子类) Array 类型
	 * @return 是否是空值，如果是空返回true，否则返回false
	 */
	public static boolean isEmpty(Object obj){
		return !isNotEmpty(obj);
	}
	
	
	/**
	 * 判断可变个参数范围类是否都是不为空的情况
	 * <pre>
	 * <b>说明:此种方法适合于界面上一次判断多个空值的情况,多个参数间是并且的关系</b></br>
	 *  例子:</br>
	 *  if(string != null && list != null && map != null){
	 *  	doSomething();
	 *  	...
	 *  }
	 *  可以替换成:
	 *	if(isAllNotEmpty(string,list,map)){
	 *  	doSomething();
	 *  	...
	 *  }
	 * </pre>
	 * @param objects 
	 * @return 如果都不为空，则返回true，只要有一个为空则返回false
	 */
	public static boolean isAllNotEmpty(Object... objects ){
		boolean flag = false;
		if(objects != null && objects.length > 0){
			for (Object object : objects) {
				flag = isNotEmpty(object);
				if(!flag){//只要有空的情况就立即结束
					break;
				}
			}
		}
		return flag;
	}
	
	
	
}
