package com.cherry.web;


import java.util.HashMap;
import java.util.Map;

/**
 * 系统支持的语言枚举类
  @description:
  @version:0.1
  @author:Cherry
  @date:Sep 9, 2013
 */
public enum LanguageEnum {
	
	/**
	 * 中文
	 */
	CHINESE(1),
	/**
	 * 英文
	 */
	OTHER(2);
	
	private static final Map<Integer,LanguageEnum>	 cache = new HashMap<Integer,LanguageEnum>();
	static{
		for (LanguageEnum languageEnum : LanguageEnum.values()) {
			cache.put(languageEnum.getLanguageType(), languageEnum);
		}
	}
	/**
	 * 语言类型
	 */
	private int languageType;

	LanguageEnum(int languageType){
		this.languageType = languageType;
	}
	
	public int getLanguageType() {
		return languageType;
	}
	
	/**
	 * int类型的语言转换成LanguageEnum
	 * @param measureType
	 * @return
	 */
	public static LanguageEnum formTypeByInt(int languageType){
		if(!cache.containsKey(languageType))
			throw new IllegalArgumentException("languageType is illegal,languageType must in "+cache.keySet());
		return cache.get(languageType);
	}

}