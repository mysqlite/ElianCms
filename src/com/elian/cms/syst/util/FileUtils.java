package com.elian.cms.syst.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件公用类
 * 
 * @author Joe
 * 
 */
public final class FileUtils {
	/**
	 * 
	 * 系统安装的winRAR位置
	 */
	private static final String WINRAR_PATH = "C:\\Program Files\\WinRAR\\WinRAR.exe";

	/**
	 * 替换文件
	 * 
	 * @param oldFile
	 * @param newFile
	 */
	public static void replaceFile(File oldFile, File newFile)
			throws IOException {
		org.apache.commons.io.FileUtils.moveFile(oldFile, newFile);
	}

	/**
	 * 替换文件夹
	 * 
	 * @param oldDir
	 * @param newDir
	 */
	public static void replaceDirectory(File oldDir, File newDir)
			throws IOException {
		org.apache.commons.io.FileUtils.moveDirectory(oldDir, newDir);
	}

	/**
	 * 读取文件并转换成字符串
	 */
	public static String readFileToString(File file, String unicode)
			throws IOException {
		return org.apache.commons.io.FileUtils.readFileToString(file, unicode);
	}

	/**
	 * 把字符串写入到文件，并覆盖文件以前的内容
	 */
	public static void writeStringToFile(File file, String fileContent,
			String unicode) throws IOException {
		org.apache.commons.io.FileUtils.writeStringToFile(file, fileContent,
				unicode);
	}

	/**
	 * 删除某个路径下的所有文件及文件夹
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取当前文件下所有文件及其子文件
	 * 
	 * @param fileList
	 * @param path
	 */
	public static void listPath(List<File> fileList, File path) {
		File files[] = path.listFiles(); // 获得目录下所有文件
		for (int i = 0; i < files.length; i++) {
			fileList.add(files[i]);
			if (files[i].isDirectory()) // 用递归列出子目录
				listPath(fileList, files[i]);
		}
	}

	public static void rarToFile(String rarFilePath, String unFilePath)
			throws IOException {
		File f = new File(unFilePath);
		if (!f.exists()) // 如果发现指定解压的路径不存在，创建目录
		{
			f.mkdirs();
		}
		String cmd = WINRAR_PATH + " x -r -p -o " + rarFilePath + " "
				+ unFilePath; // 需要执行的命令
		Runtime runtime = Runtime.getRuntime(); // 得到命令对象
		Process process = runtime.exec(cmd); // 获取执行命令过程中返回的流

		/**
		 * 
		 * 下面是打印出流的内容，查看是否有异常
		 */
		InputStreamReader isr = new InputStreamReader(process.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while ((str = br.readLine()) != null) {
			if (!"".equals(str.trim()) && str != null) // 如果当前行不为空
			{
				System.out.println(str);
			}
		}
		br.close();
	}

	/**
	 * 解压zip文件
	 * 
	 * @param sourceFile
	 *            ,待解压的zip文件; toFolder,解压后的存放路径
	 * @throws Exception
	 **/
	public static void zipToFile(String sourceFile, String toFolder)
			throws Exception {
		String toDisk = toFolder;// 接收解压后的存放路径
		ZipFile zfile = new ZipFile(sourceFile);// 连接待解压文件

		Enumeration<?> zList = zfile.entries();// 得到zip包里的所有元素
		ZipEntry ze = null;
		byte[] buf = new byte[1024];

		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				continue;
			}
			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(getRealFileName(toDisk, ze.getName())));
			InputStream inputStream = new BufferedInputStream(zfile
					.getInputStream(ze));
			int readLen = 0;
			while ((readLen = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, readLen);
			}
			inputStream.close();
			outputStream.close();
		}
		zfile.close();
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param zippath
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	private static File getRealFileName(String zippath, String absFileName) {
		String[] dirs = absFileName.split("/", absFileName.length());
		File ret = new File(zippath);// 创建文件对象
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {// 检测文件是否存在
			
			ret.mkdirs();// 创建此抽象路径名指定的目录
		}
		ret = new File(ret, dirs[dirs.length - 1]);// 根据 ret 抽象路径名和 child
		// 路径名字符串创建一个新 File 实例
		return ret;
	}
}
