package com.cherry.spring.plugin.page;

/**
 * 排序条件
  @description:
  @version:0.1
  @author:Cherry
  @date:Dec 17, 2013
 */
public class OrderCondition {
	
	private String order="ASC";
	private String cloumName;
	
	/**
	 * 以默认升序排序构建排序条件
	 * @param cloumName 排序字段名称
	 */
	public OrderCondition(String cloumName) {
		this("ASC",cloumName);
	}

	/**
	 * 
	 * @param isASC 是否升序,false表示降序
	 * @param cloumName 排序字段名称
	 */
	public OrderCondition(String order,String cloumName) {
		super();
		if((cloumName == null ||cloumName.equals("")) ||(order == null ||order.equals("")))
			throw new IllegalArgumentException("cloumName is null or order is null");
		this.cloumName = cloumName;
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public String getCloumName() {
		return cloumName;
	}
	
}
