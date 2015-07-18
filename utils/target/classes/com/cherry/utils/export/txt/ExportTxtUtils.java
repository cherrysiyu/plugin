package com.cherry.utils.export.txt;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.LogUtils;
import com.cherry.utils.export.DataBean;
import com.cherry.utils.export.FieldBean;


/**
 * 导出数据到txt文件中
 * @description:
 * @author:Cherry
 * @version:1.0
 * @date:Jan 5, 2013
 */
public class ExportTxtUtils{

	private ExportTxtUtils(){}
	
	public static boolean exportDatas2Txt(DataBean data) {
		String fileName = data.getFileName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if(!"txt".equalsIgnoreCase(fileType))
			throw new UnsupportedOperationException("您导出的不是txt合法类型，暂时不支持此文件类型");
		
		List<Map<String, String>> listContent = data.getDatas();
		List<FieldBean> titles = data.getExportFields();
		StringBuffer sb = new StringBuffer();
		// 导出标题列
		if (titles != null && titles.size() > 0) {
			for (FieldBean t : titles) {
				sb.append(t.getCnFiledName()).append("\t");
			}
			sb.append("\r\n\r\n");
		}
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(data.getFileName(), true));
			if (listContent != null && listContent.size() > 0) {
				for (Map<String, String> map : listContent) {
					for (FieldBean field : titles) {
						 String value = map.get(field.getEnFiledName());
						 if (CommonMethod.isNotEmpty(value)) {
							 value =  CommonMethod.restoreHtmlChars(value.replaceAll("</?[^>]+>", ""));
						 }else{
							 value = " - ";
						 }
						 sb.append(value).append("  	");
					}
					sb.append("\r\n\r\n");
				}
			}
			byte[] content = sb.toString().getBytes();
			os.write(content);
			os.flush();
		} catch (IOException e) {
			LogUtils.error(e);
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		}
		return true;
	}
	

}
