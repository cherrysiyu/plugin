package com.cherry.db.persistence;


import com.cherry.db.annotation.GeneratorType;

/**
 * 列字段
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class ColumnField
{
	/**
	 * 数据库表对应的列名
	 */
	private String name;
	
	/**
	 * 列值
	 */
	private Object value;
	
	/**
	 * 列字段类型
	 */
	private Class<?> type;
	
	/**
	 * 是否是主键
	 */
	private boolean primaryKey = false;
	
	/**
	 * 列值生成方式
	 */
	private GeneratorType generatorType;
	
	
	public ColumnField() {
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public Class<?> getType()
	{
		return type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public boolean isPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	public GeneratorType getGeneratorType()
	{
		return generatorType;
	}

	public void setGeneratorType(GeneratorType generatorType)
	{
		this.generatorType = generatorType;
	}
	
}
