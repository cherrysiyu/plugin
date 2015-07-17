package com.cherry.utils;

/**
 * 此枚举主要用来列出文件的位置，
 *  默认路径;
 *  相对路径,以%classpath%/为头. 如: "com/frame/properties" ;
 *  path为绝对路径,如:"e:/file"
	@version:0.1
	@author :Cherry
    @date:2013-6-21
 */
public enum FilePathEnum {

	DEFAULT_PATH(0),
	
	RELATIVE_PATH(1),
	
	ABSOLUTE_PATH(2);
	
	private final int intValue;
	
	FilePathEnum(int intValue){
		this.intValue = intValue;
	}

	public int getIntValue() {
		return intValue;
	}
	
}
