package com.cherry.db.datasource;

import com.cherry.db.DBException;

/**
 * GB 数据源异常类
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class DataSourceException extends DBException
{
	private static final long	serialVersionUID	= 1L;

	public DataSourceException()
	{
	}

	public DataSourceException(String message)
	{
		super(message);
	}

	public DataSourceException(Throwable cause)
	{
		super(cause);
	}

	public DataSourceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
