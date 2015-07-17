package com.cherry.utils.export;


public class FieldBean {
	
	/**
	 * 英文描述，这里的需要和导出数据的Map中的key一致
	 */
	private String enFiledName;
	/**
	 * 中文描述
	 */
	private String cnFiledName;
	
	public FieldBean(String enFiledName, String cnFiledName) {
		this.enFiledName = enFiledName;
		this.cnFiledName = cnFiledName;
	}

	public String getEnFiledName() {
		return enFiledName;
	}

	public void setEnFiledName(String enFiledName) {
		this.enFiledName = enFiledName;
	}

	public String getCnFiledName() {
		return cnFiledName;
	}

	public void setCnFiledName(String cnFiledName) {
		this.cnFiledName = cnFiledName;
	}
	
	

}
