/**
 * 
 */
package com.cherry.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @description: 执行命令的
 * @author:Cherry
 * @version:1.0
 * @date:2012-5-28
 * 
 */
public class ExecComUtils {

	public static boolean executeShell(String path, String shname) {
		boolean flag = true;
		Process pro = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		if (flag) {
			try {
				pro = Runtime.getRuntime().exec(
						new StringBuilder().append(path).append(
								System.getProperty("file.separator")).append(shname).toString());
				flag = true;
				in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
				String c = null;
				while ((c = in.readLine()) != null)
					sb.append(new StringBuilder().append(c).append("\n").toString());
				try {
					pro.waitFor();
				} catch (Exception e) {
					flag = false;
				}
			} catch (IOException e) {
				flag = false;
			} finally {
				pro.destroy();
				FileUtils.close(in);
			}
		}
		return flag;
	}

	public static boolean executeCommand(String command) {
		boolean flag = true;
		Process pro = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		if (flag) {
			try {
				pro = Runtime.getRuntime().exec(command);
				flag = true;
				in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
				String c = null;
				while ((c = in.readLine()) != null)
					sb.append(new StringBuilder().append(c).append("\n").toString());
				try {
					pro.waitFor();
				} catch (InterruptedException e) {
					flag = false;
				}
			} catch (IOException e) {
				flag = false;
			} finally {
				pro.destroy();

				FileUtils.close(in);
			}
		}
		return flag;
	}

	public static String getEchoCommand(String command) throws Exception {
		Process pro = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			pro = Runtime.getRuntime().exec(command);
			in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String c = null;
			while ((c = in.readLine()) != null) {
				sb.append(new StringBuilder().append(c).append("\n").toString());
			}
			pro.waitFor();
		} catch (Exception e) {
			pro.destroy();
			throw e;
		} finally {
			pro.destroy();
			FileUtils.close(in);
		}
		return sb.toString();
	}

}
