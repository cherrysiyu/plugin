package com.cherry.db.jdbc;

import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 自定义转换结果集ResultSet为其他对象的实现接口
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 * @param <T>
 */
public interface RowCallbackHandler<T> extends ResultSetHandler<T> 
{

}
