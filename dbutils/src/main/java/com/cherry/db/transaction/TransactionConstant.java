package com.cherry.db.transaction;

/**
 * 事务常量类
 * @description: 
 * @author:Cherry
 * @version:1.0
 * @date:2012-3-17
 *
 */
public class TransactionConstant {
	
	/** 单数据源模式 */
	public final static int TRANS_MODE_SINGLEDATASOURCE = 0;
	
	/** 多数据源模式 */
	public final static int TRANS_MODE_MULTIDATASOURCE = 1;
	
	/** 无事务模式 */
	public final static int TRANS_MODE_NOTRANSACTION = -1;
	
}
