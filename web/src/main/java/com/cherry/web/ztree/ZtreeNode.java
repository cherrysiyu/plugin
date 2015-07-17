package com.cherry.web.ztree;
/**
 * ZTree必须要的基本树上节点信息，具体使用如下
 *
 * @author Cherry
   @date Oct 25, 2013
 *
 */
public class ZtreeNode {
	/**
	 * 树节点id
	 */
	private String id;
	/**
	 * 树节点名称
	 */
	private String name;
	/**
	 * 此树节点的父类节点id
	 */
	private String pId;
	/**
	 * 是否有子节点
	 */
	private boolean isParent;
	/**
	 * 节点小图标
	 */
	private String icon;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String id) {
		pId = id;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
