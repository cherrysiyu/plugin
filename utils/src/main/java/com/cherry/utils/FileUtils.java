package com.cherry.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * 此工具类主要用来做文件的一些操作，并且把NOAH提供的OpenOffice转换doc等文件成Html文件提供通用方法
 * @description:
 * @author:Cherry
 * @version:1.0
 * @date:Apr 10, 2012
 */
public class FileUtils {

	
	/**
	 * 默认缓冲区的大小
	 */
	public static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * 
	 * @param srcFile源文件（需要拷贝的文件)
	 * @param descFile目标文件(拷贝后的文件);
	 * @throws Exception
	 */
	public static void copyFile(File srcFile,File descFile){
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(descFile),BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			LogUtils.error(e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		}
		
	}
		
	/**
	 * 通过NIO方式拷贝文件
	 * @param srcFile源文件（需要拷贝的文件)
	 * @param descFile目标文件(拷贝后的文件);
	 */
	public static void copyFileByNIO(File srcFile,File destFile){
		

		FileChannel inputchannel = null;
		FileChannel outputchannel = null;
		FileInputStream in = null;
		FileOutputStream out = null;
		try{
			in = new FileInputStream(srcFile);
			inputchannel = in.getChannel();
			out = new FileOutputStream(destFile);
			outputchannel = out.getChannel();
			
			ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
			while(inputchannel.read(buf) != -1){
				//锁定Buffer的空白区
				buf.flip();
				//写出
				outputchannel.write(buf);
				//将Buffer初始化，为下一次读取数据做准备
				buf.clear();
			}
		}catch (Exception e) {
			LogUtils.error(e);
		}finally{
			if(inputchannel != null){
				try {
					inputchannel.close();
					inputchannel = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
			if(in != null){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
			
			if(outputchannel != null){
				try {
					outputchannel.close();
					outputchannel = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
			if(out != null){
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		}
	}
	
	
	public static void copyFileByNIO2(File srcFile,File destFile){
		FileChannel inputchannel = null;
		FileChannel outputchannel = null;
		try{
			inputchannel = new FileInputStream(srcFile).getChannel();
			outputchannel = new FileOutputStream(destFile).getChannel();
			outputchannel.transferFrom(inputchannel, 0, inputchannel.size());
		}catch (Exception e) {
			LogUtils.error(e);
		}finally{
			if(inputchannel != null){
				try {
					inputchannel.close();
					inputchannel = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
			if(outputchannel != null){
				try {
					outputchannel.close();
					outputchannel = null;
				} catch (IOException e) {
					LogUtils.error(e);
				}
			}
		}
	}
	
	public static <S extends Readable & Closeable,T extends Appendable & Closeable> void copy(S src,T trg){
			try{
				CharBuffer buf = CharBuffer.allocate(BUFFER_SIZE);
				int i = src.read(buf);
				while(i >= 0){
					buf.flip();
					trg.append(buf);
					buf.clear();
					i = src.read(buf);
				}
				
			}catch (IOException e) {
				LogUtils.error(e);
			}finally{
				if(src != null){
					try {
						src.close();
						src = null;
					} catch (IOException e) {
						LogUtils.error(e);
					}
				}
				if(trg != null){
					try {
						trg.close();
						trg = null;
					} catch (IOException e) {
						LogUtils.error(e);
					}
				}
			}
		}
	
	
		/**
		 * 说明：此方法是删除文件的方法，会递归的把文件删除，如果是目录则递归删除，如果是文件就直接删除
		 * @param file 需要删除的文件或者目录
		 */
		public static  void deleteAllFiles(File file){
			
			if(file.isFile() || file.list().length == 0){
				//程序出口点
				file.delete();
			}else{
				 File[] files = file.listFiles();
				 for (File f : files) {
					 deleteAllFiles(f);//把此文件的里面的文件删掉
					 f.delete();//把自己删掉
				}
				
			}
			
		}
		

		public static void readFile(String fileName,OutputStream out) {

			InputStream is = null;
			try {
				is = new BufferedInputStream(new FileInputStream(fileName),BUFFER_SIZE);
				int length = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((length = is.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}
			} catch (IOException e) {
				LogUtils.error(e);
			} finally {
				if (is != null) {
					try {
						is.close();
						is = null;
					} catch (IOException e) {
						LogUtils.error(e);
					}
				}
				if (out != null) {
					try {
						out.close();
						out = null;
					} catch (IOException e) {
						LogUtils.error(e);
					}
				}
			}
		}
		
		
}
