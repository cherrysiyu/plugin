package com.cherry.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.cherry.utils.export.DataBean;
import com.cherry.utils.export.FieldBean;
import com.cherry.utils.export.excle.ExportCsv;
import com.cherry.utils.export.excle.ExportExcel;
import com.cherry.utils.export.excle.ExportExcel.Header;
import com.cherry.utils.export.txt.ExportTxtUtils;


/**
 * 导出数据工具类
  @description:
  @version:0.1
  @author:Cherry
  @date:2013-7-6
 */
public class DataExportUtils {
	
	/**
	 * 导出数据到Excle文件中
	 * @param dataBean
	 * @return
	 * @throws Exception 
	 */
	public static boolean exportDatas2Excel(DataBean dataBean) throws Exception{
		if(dataBean == null)
			throw new IllegalArgumentException("参数dataBean不能为null");
		
		ExportExcel excle = new ExportExcel(dataBean .getFileName());
		Header header = excle.createHeader();
		//导出头信息
		for (FieldBean field : dataBean.getExportFields()) {
			header.appendColumn(field.getCnFiledName(), field.getEnFiledName());
		}	
		//导出数据
		try {
			excle.exportListToExcel(dataBean.getDatas(), header);
		} catch (IOException e) {
			LogUtils.error("导出数据到Excel文件中失败，"+e.getMessage());
			LogUtils.error(e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 导出数据到Excle文件中
	 * @param dataBean
	 * @return
	 */
	public static boolean exportDatas2CSV(DataBean dataBean){
		if(dataBean == null)
			throw new IllegalArgumentException("参数dataBean不能为null");
		String fileName = dataBean.getFileName();
		OutputStream fileOut = null;
		ExportCsv exportCsv = null;
		try {
			fileOut = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
			exportCsv = new ExportCsv(fileName);
			//导出头信息
			StringBuilder line = new StringBuilder();
			for (FieldBean field : dataBean.getExportFields()) {
				line.append(field.getCnFiledName()).append(",");
			}
			line.deleteCharAt(line.length() - 1);

			// 添加换行符
			exportCsv.addWrapToLine(line);
			exportCsv.exportContent(line);
			
			//导出数据
			List<Map<String, String>> dataList = dataBean.getDatas();
			if(CommonMethod.isCollectionNotEmpty(dataList)){
				StringBuilder listSb = new StringBuilder();
				StringBuilder data = null;
				for (Map<String, String> map : dataList) {
					data = new StringBuilder();
					for (FieldBean field : dataBean.getExportFields()) {
						data.append(exportCsv.handleValue(map.get(field.getEnFiledName()))).append(",");
					}
					data.deleteCharAt(data.length() - 1);
					exportCsv.addWrapToLine(data);
					listSb.append(data);
				}
				exportCsv.exportContent(listSb);
			}
			exportCsv.closeStream();
			fileOut.flush();
			
		} catch (IOException e) {
			LogUtils.error("导出数据到CSV文件中失败，"+e.getMessage());
			LogUtils.error(e);
			return false;
		}finally{
			if(fileOut != null){
				try {
					fileOut.close();
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		}
		return true;
	}
	
	public static boolean exportDatas2Txt(DataBean dataBean) {
		if(dataBean == null)
			throw new IllegalArgumentException("参数dataBean不能为null");
		return ExportTxtUtils.exportDatas2Txt(dataBean);
	}

}
