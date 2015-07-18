package com.cherry.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cherry.utils.CommonMethod;
import com.cherry.utils.JacksonUtils;
import com.cherry.utils.LogUtils;

/**
 * @Description :Response公用方法类
 * @author: Cherry
 * @version 1.0
 * @data:Oct 19, 2011
 */
public class ResponseCommon {

	/**
	 * 输出JSON数据，Object不需要转换，方法内部自己转换
	 * @param response
	 * @param object
	 * @throws Exception
	 */
	public static void printJSON(HttpServletResponse response, Object object)throws Exception {
		response.setContentType("application/json");
		printObject(response,JacksonUtils.getInstance().writerJavaObject2JSON(object));
	}

	/**
	 * 不负责转换object的类容，如果需要转换则自行转换
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	public static void printObject(HttpServletResponse response, Object object)throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Encoding", "gzip");
		PrintWriter pw = getGzipPrintWriter(response);
		pw.print(object);
		pw.close();
	}

	/**
	 * 调用这个之前，请DataExportUtils.exportDatas2Excel(dataBean),这样页面就可以了
	 * 媒体返回类型为Excel
	 * @param response
	 * @param fileName 要导出的文件名称
	 * @throws IOException
	 */
	public static void responseExcel(HttpServletResponse response,String fileName) throws IOException {
		responseContentDisposition(response, fileName, "application/msexcel");
	}

	/**
	 * 返回文件
	 * @param response
	 * @param fileName 文件路径
	 * @throws IOException
	 */
	public static void responseFile(HttpServletResponse response,String fileName) throws IOException {
		responseContentDisposition(response, fileName,"application/octet-stream");
	}

	/**
	 * 调用这个之前，请DataExportUtils.exportDatas2CSV(dataBean),这样页面就可以了
	 * 媒体返回类型为csv
	 * @param response
	 * @param fileName 要导出的文件名称
	 * @throws IOException
	 */
	public static void responseCsv(HttpServletResponse response, String fileName)throws IOException {
		responseContentDisposition(response, fileName, "application/csv");
	}

	/**
	 * response ContentDisposition 以附件形式返回文件
	 * @param response
	 * @param fileName 文件名
	 * @param ContentType response ContentType HTTP内容类型
	 * @throws IOException
	 */
	public static void responseContentDisposition(HttpServletResponse response,
			String fileName, String ContentType) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition", "attachment;filename="+ fileName);
		response.setContentType(ContentType);

		outputFile(response, fileName);
	}

	/**
	 * 发送文本。使用UTF-8编码。
	 * @param response   HttpServletResponse
	 * @param text 发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * @param response  HttpServletResponse
	 * @param text  发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * @param response  HttpServletResponse
	 * @param text   发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
			response.getWriter().close();
		} catch (IOException e) {
			LogUtils.error(e.getMessage());
		}
	}
	
	/**
	 * 转换得到excle导出的文件名，防止乱码
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getExportExcelName(String fileName) throws IOException{
		if(CommonMethod.isEmpty(fileName))
			throw new IllegalArgumentException("文件名称不能为空！");
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if(CommonMethod.isEmpty(fileType))
			throw new IllegalArgumentException("文件没有后缀名");
		String excelName = fileName.substring(0,fileName.lastIndexOf("."));
		if(CommonMethod.isEmpty(excelName))
			throw new IllegalArgumentException("文件没有合法的名称");
		return getExportExcelName(excelName,fileType);
	}
	/**
	 * 输出文件
	 * @param response
	 * @param fileName 文件名
	 * @throws IOException
	 */
	private static void outputFile(HttpServletResponse response, String fileName)throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName)); 
		int i = 0;
		while ((i = bis.read()) != -1)
			response.getOutputStream().write(i);

		bis.close();
		response.getOutputStream().close();
	}

	/**
	 * 得到Gzip方式的输出
	 * @param response
	 * @return Gzip PrintWriter
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private static PrintWriter getGzipPrintWriter(HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		return new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new BufferedOutputStream(response.getOutputStream())), "UTF-8"));
	}
	
	/**
	 * 导出文件的名称:此方法需要优化扩展
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static   String getExportExcelName(String excelName, String exportType)throws UnsupportedEncodingException {
		
		String fileName = excelName;
		// 导出的文件名。
		// 使用"iso-8859-1"编码，防止导出时excel文件名称为乱码
		if(CommonMethod.isContainsChinese(fileName)){
			fileName =  new String(excelName.getBytes("gbk"), "iso-8859-1");
		}
		if ("csv".equalsIgnoreCase(exportType)) 
			exportType = "csv";
		return fileName+"."+exportType;
	}
	/**
	 * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		final String CONTENT_DISPOSITION = "Content-Disposition";
		
		try {
			String agent = request.getHeader("User-Agent");
			String encodedfileName = null;
	        if (null != agent) {  
	        	agent = agent.toLowerCase();  
	            if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {  
	    			encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
	            } else if (agent.contains("msie")) {  
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            } else if (agent.contains("opera")) {  
	            	encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
	            } else {
	            	encodedfileName = "filename=\"" + URLEncoder.encode(fileName,"UTF-8") + "\"";
	            }
	        }
			
	        response.setHeader(CONTENT_DISPOSITION, "attachment; " + encodedfileName);
		} catch (UnsupportedEncodingException e) {
		}
	}
	
	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

}
