package com.cherry.db;



/**
 * DB 数据库全局异常类
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class DBException extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public DBException()
	{
	}

	public DBException(String msg)
	{
		super(msg);
	}

	public DBException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

	public DBException(Throwable cause)
	{
		super(cause);
	}
}
