package com.cherry.socket.common.bean;

public final class Constans {

	/**
	 * 放在session属性中的key值
	 */
	public static final String SESSIONATTR_KEY = "sessionAttrKey"; 
	
	public static final Long ExceptionMessageId = -1L;
	// 帧特征常量定义
	/**
	 * 帧头标识
	 */
	public static final byte FRAME_HEAD = 0x8; 
	/**
	 * 帧最短长度大于等于19
	 */
	public static final int FRAME_MIN_LENGTH = 19; 
	/**
	 * 长度属性的长度，即存放数据包长度的变量的的字节所占的长度
	 */
	public static final int lengthFieldLength = 4;
	/**
	 * 长度属性的起始指针(偏移量)
	 */
	public static final int lengthFieldOffset = FRAME_MIN_LENGTH-lengthFieldLength;
}
