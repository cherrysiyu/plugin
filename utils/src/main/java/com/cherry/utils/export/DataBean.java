package com.cherry.utils.export;

import java.util.List;
import java.util.Map;

import com.cherry.utils.CommonMethod;


public class DataBean {
	
	/**
	 * 数据列表
	 */
	private List<Map<String,String>> datas;
	
	/**
	 * 绝对路径的文件名称
	 */
	private String fileName;
	
	/**
	 * 导出列的标题
	 */
	private List<FieldBean> exportFields ;
	public DataBean(){
		
	}

	public DataBean(List<Map<String, String>> datas, String fileName,List<FieldBean> exportFields) {
		this.datas = datas;
		this.fileName = fileName;
		this.exportFields = exportFields;
	}

	public String getFileName() {
		if(!CommonMethod.isNotEmpty(fileName)){
			fileName = "data.txt";
		}
		return fileName ;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public List<FieldBean> getExportFields() {
		return exportFields;
	}

	public void setExportFields(List<FieldBean> exportFields) {
		if(CommonMethod.isCollectionNotEmpty(exportFields)){
			this.exportFields = exportFields;
		}else{
			throw new IllegalArgumentException("导出的字段不能为空!");
		}
	}

	public List<Map<String, String>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, String>> datas) {
		this.datas = datas;
	}
	
	

}
