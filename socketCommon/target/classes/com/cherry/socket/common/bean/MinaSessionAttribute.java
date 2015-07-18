package com.cherry.socket.common.bean;

import java.io.Serializable;

/**
 * 每个Session附属信息类
 * @author:Cherry
 * @date:2014-6-28
 * @version:1.0
 */
public final class MinaSessionAttribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 277079151657585412L;
	/**
	 * 全局唯一sessionId
	 */
	private long sessionId;
	/**
	 * 此session对应的系统的负荷率
	 */
	private int loadingRate = 0;
	/**
	 * 名称
	 */
	private int appNumber;
	
	public MinaSessionAttribute(long sessionId, int appNumber) {
		super();
		this.sessionId = sessionId;
		this.appNumber = appNumber;
	}
	public int getLoadingRate() {
		return loadingRate;
	}
	public void setLoadingRate(int loadingRate) {
		this.loadingRate = loadingRate;
	}
	public int getAppNumber() {
		return appNumber;
	}
	
	
	public long getSessionId() {
		return sessionId;
	}
	public boolean isIdle() {
		if (loadingRate <= 90)
			return true;

		return false;
	}
}
