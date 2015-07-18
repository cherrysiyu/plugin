/**
 * 
 */
package com.cherry.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件的拷贝
 * 
 * @Description :
 * @author： 李波
 * @version 1.0
 * @date:2011-12-20
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * 文件的拷贝
	 * 
	 * @param srcfile
	 *            源文件
	 * @param descfile
	 *            目标文件
	 */
	public static void copyFile(File srcfile, File descfile) {
		InputStream is = null;
		OutputStream os = null;

		try {
			is = new BufferedInputStream(new FileInputStream(srcfile),
					BUFFER_SIZE);
			os = new BufferedOutputStream(new FileOutputStream(descfile),
					BUFFER_SIZE);
			int length = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			try {
				while ((length = is.read(buffer)) > 0) {
					// os.write(buffer[length]);
					os.write(buffer, 0, length);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {

			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 像文件里面写如内容
	 * 
	 * @param fileName
	 *            文件的名称
	 * @param content
	 *            需要写到文件中的内容
	 */
	public static void WriterFile(String fileName, String[][] titles,
			List<Map<String, Object>> listContent) {

		OutputStream os = null;
		StringBuffer sb = new StringBuffer();
		// 导出标题列
		if (titles != null && titles.length > 0) {
			for (String[] t : titles) {
				sb.append(t[0] + "  ");
			}
			sb.append("\r\n\r\n");
		}
		try {
			os = new BufferedOutputStream(new FileOutputStream(fileName, true));
			if (listContent != null && listContent.size() > 0) {
				for (Map<String, Object> map : listContent) {
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						// String key = entry.getKey();
						String value = StringUtils.restoreHtmlChars(entry
								.getValue().toString() != null ? entry
								.getValue().toString().replaceAll("</?[^>]+>",
										"") : "");
						if (StringUtils.isEmptyString(value)) {
							value = " - ";
						}
						sb.append(value + "  ");
					}
					sb.append("\r\n");
				}
			}
			byte[] content = sb.toString().getBytes();
			os.write(content);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static List<Map<String, Object>> readFile(String fileName) {

		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(fileName),
					BUFFER_SIZE);
			int length = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((length = is.read(buffer)) != -1) {
				System.out.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 读写java基本数据类型
	 * 
	 * @param filePath
	 * @param content
	 * @throws Exception
	 */
	public static void Write2File(String filePath, String content)
			throws Exception {
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(
				filePath, true));
		byte[] buffer = content.getBytes();
		dos.write(buffer);
		dos.flush();
		dos.close();

	}

	/**
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void Read2File(String filePath) throws Exception {

		// 下面是固定模式，极其重要，一定要熟悉掌握
		DataInputStream is = new DataInputStream(new FileInputStream(filePath));
		byte[] buffer = new byte[2048];
		int length = 0;
		while ((length = is.read(buffer)) != -1) {
			String str = new String(buffer, 0, length);
			System.out.println(str);
		}
		is.close();
	}

	public static boolean createPathFile(String pathFile) throws IOException {
		if (pathFile.endsWith(File.separator)) {
			return createDirectory(pathFile);
		}
		int pos = pathFile.lastIndexOf(File.separator);
		String path = pathFile.substring(0, pos);

		String filename = pathFile.substring(pos + 1, pathFile.length());

		File dic = new File(path);
		if (dic.mkdirs()) {
			File file = new File(dic, filename);
			return file.createNewFile();
		}
		return false;
	}

	public static synchronized boolean createDirectory(String directory) {
		File file = new File(directory);

		if ((!file.exists()) && (!file.isDirectory())) {
			return file.mkdirs();
		}
		return false;
	}

	public static String readFileByStream(String filepath) throws IOException {
		File file = new File(filepath);

		int bt = 0;
		StringBuilder sb = new StringBuilder();
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);

			while ((bt = fis.read()) != -1)
				sb.append((char) bt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return sb.toString();
	}

	public static String readFileByBufferedStream(String filepath)
			throws IOException {
		File file = new File(filepath);

		int buffer = 10;

		byte[] bts = new byte[buffer];

		StringBuilder sb = new StringBuilder();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			while (bis.read(bts) != -1)
				sb.append(String.valueOf(bts));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
		return sb.toString();
	}

	public static String readFileByBufferedStream(String filepath, int buffer)
			throws IOException {
		File file = new File(filepath);

		byte[] bts = new byte[buffer];

		StringBuilder sb = new StringBuilder();
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			while (bis.read(bts) != -1) {
				sb.append(String.valueOf(bts));
			}
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
		return sb.toString();
	}

	public static String readFileByBufferedReader(String filepath) {
		File file = new File(filepath);
		int bufsize = 25;

		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));

			char[] buf = new char[bufsize];
			while (bufferedReader.read(buf) != -1) {
				sb.append(buf);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String readFileByBufferedReader(String filepath, int bufsize)
			throws IOException {
		File file = new File(filepath);

		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));

			char[] buf = new char[bufsize];
			while (bufferedReader.read(buf) != -1) {
				sb.append(buf);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return sb.toString();
	}

	public static List<String> readFileByLineNumberReader(String filepath)
			throws IOException {
		File file = new File(filepath);

		List<String> list = new ArrayList<String>();
		LineNumberReader lineNumberReader = null;
		try {
			lineNumberReader = new LineNumberReader(new FileReader(file));

			String line;
			while ((line = lineNumberReader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					list.add(line);
				}
			}
			lineNumberReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (lineNumberReader != null) {
				lineNumberReader.close();
			}
		}
		return list;
	}

	public static void writeWithFileWriter(String data, String filePath)
			throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath);
			fw.write(data, 0, data.length());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fw != null)
				fw.close();
		}
	}

	public static void writeWithOutputStreamWriter(String data, String filePath)
			throws IOException {
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(filePath));
			osw.write(data, 0, data.length());
			osw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (osw != null)
				osw.close();
		}
	}

	public static void writeWithPrintWriter(String data, String filePath)
			throws FileNotFoundException {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					filePath)));
			pw.println(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			close(pw);
		}
	}

	public static void delFile(String filepath) {
		File file = new File(filepath);
		file.deleteOnExit();
	}

	public static void delDirectory(String filepath) {
	}

	public static boolean canWrite(String filePath) {
		File file = new File(filePath);
		return file.canWrite();
	}

	public static void close(InputStream in) {
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(FileReader fr) {
		try {
			if (fr != null)
				fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(BufferedReader in) {
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close(PrintWriter pw) {
		if (pw != null)
			pw.close();
	}

	public static boolean unzipDir(String zipFilePath, String savePath) {
		try {
			if (!savePath.endsWith(File.separator)) {
				savePath = savePath + File.separator;
			}

			FileInputStream fin = new FileInputStream(zipFilePath);
			ZipInputStream zin = new ZipInputStream(fin);

			ZipEntry z = null;
			while (null != (z = zin.getNextEntry())) {
				String strFileName = z.getName();

				if (z.isDirectory()) {
					File f = new File(savePath + strFileName);
					boolean result = f.mkdirs();
					if (!result)
						;
				} else {
					File f = new File(savePath + strFileName);

					File parent = f.getParentFile();
					if ((null != parent) && (!parent.exists())) {
						boolean result = parent.mkdirs();
						if (!result)
							;
					}

					FileOutputStream fout = new FileOutputStream(f);
					BufferedOutputStream bout = new BufferedOutputStream(fout);

					byte[] buffer = new byte[4096];

					int nCount = 0;
					while ((nCount = zin.read(buffer)) > 0) {
						bout.write(buffer, 0, nCount);
					}

					zin.closeEntry();
					bout.close();
					fout.close();
				}
			}

			zin.close();
			fin.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean zipDir(String dirPath, String savePath, String zipName) {
		return zipDir(dirPath, savePath + zipName);
	}

	public static boolean zipDir(String dirPath, String savePath) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(savePath);
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(fout));

			zout.setLevel(0);

			File file = new File(dirPath);

			addDirToZipOutStream(file, "", zout);

			zout.close();
			fout.close();
			fout = null;
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fout) {
				try {
					fout.close();
				} catch (IOException e) {
				}
			}
		}
		return false;
	}

	private static void addDirToZipOutStream(File file, String basePath,
			ZipOutputStream zout) throws IOException {
		if (file.isDirectory()) {
			zout.putNextEntry(new ZipEntry(basePath + file.getName() + "/"));

			for (File subFile : file.listFiles()) {
				addDirToZipOutStream(subFile, basePath + file.getName() + "/",
						zout);
			}

		} else {
			zout.putNextEntry(new ZipEntry(basePath + file.getName()));

			FileInputStream fin = null;
			try {
				fin = new FileInputStream(file);

				byte[] buffer = new byte[4096];

				int nCount = 0;
				while ((nCount = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, nCount);
				}

				zout.closeEntry();
				if (null != fin) {
					fin.close();
				}
			} finally {
				if (null != fin) {
					fin.close();
				}
			}
		}
	}
}
