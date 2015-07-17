package com.cherry.db.dialect;

import com.cherry.db.DBException;

/**
 * 未知数据库方言异常
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class UnknownDialectException extends DBException
{
	private static final long	serialVersionUID	= 1L;

	public UnknownDialectException()
	{
	}

	public UnknownDialectException(String message)
	{
		super(message);
	}

	public UnknownDialectException(Throwable cause)
	{
		super(cause);
	}

	public UnknownDialectException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
