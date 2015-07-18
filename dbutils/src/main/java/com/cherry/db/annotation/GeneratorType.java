package com.cherry.db.annotation;

/**
 * 主键生成类型
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public enum GeneratorType
{
	/**
	 * 手工分配
	 */
	ASSIGN,
	
	/**
	 * 自动增长方式，适用于MySQL等支持主键自增特性的数据库
	 */
	AUTO_INCREMENT,
	
	/**
	 * Sequence方式，适用于Oracle
	 */
	SEQUENCE;
}
