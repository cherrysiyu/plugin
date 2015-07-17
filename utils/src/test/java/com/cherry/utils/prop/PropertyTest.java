package com.cherry.utils.prop;

import com.cherry.utils.FilePathEnum;
import com.cherry.utils.LogUtils;
import com.cherry.utils.PropertyUtils;

public class PropertyTest {

	public static void main(String[] args) throws InterruptedException {
		//testDefaultPath();
		//testRelativePath();
		//testAbsolutePath();
		
		Thread.currentThread().sleep(10000);
		LogUtils.debug("重新取值......");
		//testAbsolutePath();
		
	}
	
	public static  void testDefaultPath(){
		String fileName="log4j";
		PropertyUtils propertyUtils = PropertyUtils.newInstance(fileName);
		LogUtils.debug(propertyUtils.getProperty("log4j.logger.GIS2"));
		propertyUtils.setProperty("log4j.logger.GIS2", "rere23sdf");
		LogUtils.debug(propertyUtils.getProperty("log4j.logger.GIS2"));
		
	}
	
	public static  void testRelativePath(){
		String path = "com/xml/gis";
		String fileName= "gisConfig";
		PropertyUtils propertyUtils = PropertyUtils.newInstance(fileName,path,FilePathEnum.RELATIVE_PATH);
		LogUtils.debug(propertyUtils.getProperty("Page.pageSize"));
		propertyUtils.setProperty("Page.pageSize", "20");
		LogUtils.debug(propertyUtils.getProperty("Page.pageSize"));
	}
	
	public static  void testAbsolutePath(){
		String path = "E:/workspace/GIS/src/com/xml/gis";
		String fileName="postgresql";
		PropertyUtils propertyUtils = PropertyUtils.newInstance(fileName,path,FilePathEnum.ABSOLUTE_PATH);
		LogUtils.debug(propertyUtils.getProperty("hive.password"));
		propertyUtils.setProperty("hive.password", "123456");
		LogUtils.debug(propertyUtils.getProperty("hive.password"));
		
	}
	
	

}
