/**
 * 
 */
package com.cherry.utils.export.excle;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;

/**
 * 导出CSV等字符文件 每行末尾使用{@link #addWrapToLine(StringBuilder)}插入换行符号后再导出
 * 导出整个文件后，需要关闭输出流{@link #closeStream()}
 * 
 */
public class ExportCsv {
	/**
	 * 输出流
	 */
	private Writer writer;

	public ExportCsv(String fileName) throws IOException {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if(!"csv".equals(fileType))
			throw new UnsupportedOperationException("您导出的不是csv合法类型，不支持此文件类型");
		createWriter(fileName);
	}

	/**
	 * 对数据值进行处理
	 * <pre>
	 * 若value为null 返回为 {@code &quot;&quot;+&quot;\t&quot;}
	 * 若value为逗号割开的多值返回为 {@code &quot;\&quot;&quot;+value+&quot;\&quot;&quot;+&quot;\t&quot;}
	 * </pre>
	 * 
	 * @param value
	 * @return
	 */
	public String handleValue(String value) {
		if (value == null)
			value = "";
		// 若value为逗号割开的多值添加两个双引号
		else if (StringUtils.indexOf(value, ",") != -1)
			value = "\"" + value + "\"";

		// 添加tab解决日期显示为######的问题
		value = value + "\t";

		return value;
	}

	/**
	 * 添加换行附号到行尾
	 * 
	 * @param sb
	 */
	public void addWrapToLine(StringBuilder sb) {
		sb.append("\r\n");
	}

	/**
	 * 导出内容
	 * 
	 * @param content
	 * @throws IOException
	 */
	public void exportContent(CharSequence content) throws IOException {
		writer.append(content);
	}

	/**
	 * 关闭流
	 * 
	 * @throws IOException
	 */
	public void closeStream() throws IOException {
		writer.flush();
		writer.close();
	}

	/**
	 * createWriter
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	private void createWriter(String fileName) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
	}
}
