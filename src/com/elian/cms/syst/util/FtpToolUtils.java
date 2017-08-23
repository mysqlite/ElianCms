package com.elian.cms.syst.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.common.upload.UploadUtils;

/**
 * 使用sun.net.ftp工具包进行ftp上传下载
 * 
 * @author Gechuanyi
 * @date 2012-11-27
 * @description:
 */
public class FtpToolUtils {
	private final static Log logger = LogFactory.getLog(FtpToolUtils.class);
	private static SimpleDateFormat sf = new SimpleDateFormat("HHmm");

	/*
	 * 根据文件后缀名存储文件到FTP，文件将会重命名，命名格式为（\\yyyyMMdd\\ddHHmmss.ext）
	 * 
	 * @param path
	 * 
	 * @param ext
	 * 
	 * @param in
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public String storeByExt(String path, String ext, InputStream in, Ftp ftps)
			throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);// 获取上传文件名称
		store(filename, in, ftps);
		return filename;
	}

	/**
	 * 根据文件名称存储文件到FTP，将保留原文件，如发现同名文件，给予覆盖
	 * 
	 * @param filename
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String storeByFilename(String filename, InputStream in,Ftp ftps) {
		store(filename, in, ftps);
		return filename;
	}
	
	/**
	 * 根据文件名称存储文件到FTP，将保留原文件，如发现同名文件，给予覆盖
	 * 
	 * @param filename
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String storeByFilename(String filename, InputStream in,FTPClient ftp) {
		store(filename, in, ftp);
		return filename;
	}
	
	public static File retrieve(String name,FTPClient ftp,String path) {// 批量取回不断开连接
		File file = new File(path, name);
		file = UploadUtils.getUniqueFile(file);
		boolean flag=false;
		try {
			createNestFile( path,StringUtils.replaceStr(FilenameUtils.getFullPath(name), "\\", "/"));
			OutputStream output = new FileOutputStream(file);
			flag= ftp.retrieveFile("/" + name, output);
			output.close();
		}
		catch (FileNotFoundException e) {
			logger.warn("取回文件:"+name+"--失败,本地文件路径未找到");
			file.delete();
		}
		catch (IOException e) {
			logger.warn("取回文件:"+name+"--失败,读取异常");
			file.delete();
		}
		if (flag) {
			logger.warn("取回文件:"+name+"--成功");
			return file;
		}
		else {
			logger.warn("取回文件:"+name+"--失败,该文件不存在");
			file.delete();
			return null;
		}
	}
	

	public static File retrieve(String name, Ftp ftps,String path) {// 取回
		File file = new File(path, name);
		file = UploadUtils.getUniqueFile(file);
		FTPClient ftp = getClient(ftps);
		boolean flag=false;
		try {
			OutputStream output = new FileOutputStream(file);
			flag= ftp.retrieveFile(ftps.getFtpPath() + name, output);
			output.close();
		}
		catch (FileNotFoundException e) {
			logger.warn("取回文件:"+name+"--失败,本地文件路径未找到");
		}
		catch (IOException e) {
			logger.warn("取回文件:"+name+"--失败,读取异常");
		}
		if (flag) {
			logger.warn("取回文件:"+name+"--成功");
			return file;
		}
		else {
			logger.warn("取回文件:"+name+"--失败,该文件不存在");
			file.delete();
			return null;
		}
	}
	
	public static File retrieve(String name, Ftp ftps) {// 取回
		String path =System.getProperty("java.io.tmpdir");
		File file = new File(path, name);
		file = UploadUtils.getUniqueFile(file);
		FTPClient ftp = getClient(ftps);
		boolean flag=false;
		try {
			OutputStream output = new FileOutputStream(file);
			flag= ftp.retrieveFile(ftps.getFtpPath() + name, output);
			output.close();
		}
		catch (FileNotFoundException e) {
			logger.warn("取回文件:"+name+"--失败,本地文件路径未找到");
		}
		catch (IOException e) {
			logger.warn("取回文件:"+name+"--失败,读取异常");
		}
		if (flag) {
			logger.warn("取回文件:"+name+"--成功");
			return file;
		}
		else {
			logger.warn("取回文件:"+name+"--失败,该文件不存在");
			file.delete();
			return null;
		}
	}
	//做测试用
	private static Ftp  getFtp() {
	      Ftp ftp1 = new Ftp();
		  ftp1.setServerIp("IP地址");
		  ftp1.setAccount("账号");
		  ftp1.setPassword("密码");
		  ftp1.setFtpUrl("访问地址");
		  ftp1.setEncoding("UTF-8");
		  ftp1.setTimeout(8000);
		  ftp1.setFtpPort(21);
		  ftp1.setFtpPath(""); 
		  return ftp1;
	}
	
	
	public boolean restore(String name, File file, Ftp ftps) throws IOException {// 还原
		store(name, FileUtils.openInputStream(file), ftps);
		file.deleteOnExit();
		return true;
	}

	/**
	 * FTP移动文件{通过指令方式}
	 * 
	 * @param realPath
	 * @param newPath
	 * @param ftps
	 */
	public static void moveFile(String realPath, String newPath, Ftp ftps) {
		FTPClient ftp = getClient(ftps);
		if (ftp != null) {
			try {
				createDirectory(newPath, ftp);
				ftp.sendCommand(" RNFR   " + realPath);
				ftp.sendCommand(" RNTO  " + newPath);
				logger.warn("移动文件:"+realPath+"到"+newPath+"--成功");
			}
			catch (IOException e) {
				logger.warn("移动文件:"+realPath+"到"+newPath+"--失败");
			}finally {
				close(ftp, ftps);
			}
		}
	}

	public static int deleteStaticFile(String fileName, Ftp ftps) {
		FTPClient ftp = getClient(ftps);
		if (ftp != null) {
			try {
				ftp.deleteFile(fileName);
				logger.warn("删除文件:"+fileName+"--成功");
				int i = ftp.nlst(StringUtils.getFileDirectory(fileName));
				if (i == 550) {
					logger.warn("当前为空文件夹，进行删除 :"+fileName);
					ftp.sendCommand(" RMD "+ StringUtils.getFileDirectory(fileName));
					logger.warn(fileName+"空文件夹删除--成功!");
				}
			}
			catch (IOException e) {
				logger.warn("删除文件:"+fileName+"--失败");
				e.printStackTrace();
			}finally {
				close(ftp, ftps);
			}
		}
		return 0;
	}

	public static int deleteFile(String fileName, Ftp ftps) {
		FTPClient ftp = getClient(ftps);
		if (ftp != null) {
			try {
				ftp.deleteFile(fileName);
				logger.warn("删除文件:"+fileName+"--成功");
				int i = ftp.nlst(StringUtils.getFileDirectory(fileName));
				if (i == 550) {
					logger.warn("当前为空文件夹，进行删除 :"+fileName);
					ftp.sendCommand(" RMD "+ StringUtils.getFileDirectory(fileName));
					logger.warn(fileName+"空文件夹删除--成功!");
				}
			}
			catch (IOException e) {
				logger.warn("删除文件:"+fileName+"--失败");
				e.printStackTrace();
			}finally {
				close(ftp, ftps);
			}
		}
		return 0;
	}

	public static int deleteFile(Ftp ftps, List<String> fileName) {
		FTPClient ftp = getClient(ftps);
		if (ftp != null) {
			try {
				for (int i = 0; i < fileName.size(); i++) {
					ftp.deleteFile(fileName.get(i));
					logger.warn("删除文件:"+fileName.get(i)+"--成功");
					int s = ftp.nlst(StringUtils.getFileDirectory(fileName.get(i)));
					if (s == 550) {
						logger.warn("当前为空文件夹，进行删除 :"+fileName.get(i));
						ftp.sendCommand(" RMD "+ StringUtils.getFileDirectory(fileName.get(i)));
						logger.warn(fileName.get(i)+"空文件夹删除--成功!");
					}
				}
			}
			catch (IOException e) {
				logger.warn("批量删除文件--失败");
				e.printStackTrace();
			}finally {
				close(ftp, ftps);
			}
		}
		return 0;
	}

	public static int deleteFile(Ftp ftps, String... fileName) {
		FTPClient ftp = getClient(ftps);
		if (ftp != null) {
			try {
				for (int i = 0; i < fileName.length; i++) {
					ftp.deleteFile(fileName[i]);
					logger.warn("删除文件:"+fileName[i]+"--成功");
					int s = ftp.nlst(StringUtils.getFileDirectory(fileName[i]));
					if (s == 550) {
						logger.warn("当前为空文件夹，进行删除 :"+fileName[i]);
						ftp.sendCommand(" RMD "+ StringUtils.getFileDirectory(fileName[i]));
						logger.warn(fileName[i]+"空文件夹删除--成功!");
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}finally {
				close(ftp, ftps);
			}
		}
		return 0;
	}

	public static void createDirectory(String filename, FTPClient ftp) {//创建目录,不关闭连接
		if (ftp != null) {
			String path = FilenameUtils.getFullPath(filename);
			try {
				if (!ftp.changeWorkingDirectory(path)) {
					String[] ps = org.apache.commons.lang3.StringUtils.split(path, '/');
					String p = "/";
					ftp.changeWorkingDirectory(p);
					for (String s : ps) {
						p += s + "/";
						if (!ftp.changeWorkingDirectory(p)) {
							ftp.makeDirectory(s);
							logger.warn("创建目录:"+p+"--成功");
							ftp.changeWorkingDirectory(p);
						}
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int store(String filename, InputStream in, Ftp ftps) {// 存储文件
		FTPClient ftp = getClient(ftps);
		String name = FilenameUtils.getName(filename);
		try {
			if (ftp != null) {
				createDirectory(filename, ftp);
				ftp.storeFile(name, in);
				logger.warn("上传文件:"+filename+"--成功!");
			}
			if (in != null)
				in.close();
			return 0;
		}
		catch (SocketException e) {
			logger.warn("上传文件:"+filename+"--失败!");
			return 3;
		}
		catch (IOException e) {
			logger.warn("上传文件:"+filename+"--失败!");
			return 4;
		}finally {
			close(ftp, ftps);
		}
	}
	
	
	public static int store(String filename, InputStream in, FTPClient ftp) {// 存储文件，不关闭连接
		try {
			if (ftp != null) {
				String name = FilenameUtils.getName(filename);
				createDirectory(filename, ftp);
				ftp.storeFile(name, in);
				logger.warn("上传文件:"+filename+"--上传成功!");
			}
			if (in != null)
				in.close();
			return 0;
		}
		catch (SocketException e) {
			logger.warn("上传文件:"+filename+"--上传失败!");
			return 3;
		}
		catch (IOException e) {
			logger.warn("上传文件:"+filename+"--上传失败!");
			return 4;
		}
	}
	
	public static void main(String[] args) {
		FTPClient ftp=getClient(getFtp());
		String newPath="/test/27/abcd.jpg";
		String realPath="/aaaa.jpg";
		if (ftp != null) {
			try {
				createDirectory(newPath, ftp);
				ftp.sendCommand(" mget   " + realPath);
				ftp.sendCommand(" mget  " + newPath);
				logger.warn("移动文件:"+realPath+"到"+newPath+"--成功");
			}
			catch (IOException e) {
				logger.warn("移动文件:"+realPath+"到"+newPath+"--失败");
			}finally {
				close(ftp, getFtp());
			}
		}
		
		
	}
	public static List<String> getDelTempFile(Ftp ftps, String flieName) {
		Integer fileTime = 0;
		Integer nowHours = 0;
		FTPClient ftp = getClient(ftps);
		flieName = getFtpPath(flieName);
		List<String> delList = new ArrayList<String>();
		try {
			logger.warn("获取临时文件清理目录*******");
			FTPFile[] ftpFiles = ftp.listFiles(flieName);
			if(ftpFiles!=null) {
    			for (int i = 0; i < ftpFiles.length; i++) {
    				if (!ftpFiles[i].getName().equals(".")&&!ftpFiles[i].getName().equals("..")) {
    					if(ftpFiles[i].getName().length()>25) {
    						fileTime = Integer.valueOf(ftpFiles[i].getName().substring(9, 13));
    					}
    					else {
    						fileTime = Integer.valueOf(ftpFiles[i].getName().substring(0, 4));
    					}
    					nowHours = Integer.valueOf(sf.format(new Date()));
    					if (nowHours - fileTime >= (ElianUtils.DEL_TEMP_FILE_TIME * 100))
    						delList.add(flieName + ftpFiles[i].getName());
    				}
    			}
			}
			if(delList.isEmpty()) {
				logger.warn("临时文件清理目录为空");
			}
		}
		catch (IOException e) {
			logger.warn("获取临时文件清理目录发生异常");
		}finally {
			close(ftp, ftps);
		}
		return delList;
	}
	
	private static String getFtpPath(String path) {
		if (path.indexOf("/") != 0)
			path = "/" + path;
		if (path.lastIndexOf("/") < path.length()-1)
			path = path + "/";
		return path;
	}
	
	public static void delAllFlie(Ftp ftps, String path) {
		FTPClient ftp = getClient(ftps);
		recursionDele(ftp, path);
	    try {
			ftp.sendCommand("RMD "+path);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		close(ftp, ftps);
	}

	private static String getOperator() {
		String userIp="";
		String userAccount="";
		try {
			if(ApplicationUtils.getIpAddr()!=null&&ApplicationUtils.getUser()!=null) {
				userIp=ApplicationUtils.getIpAddr();
				userAccount=ApplicationUtils.getUser().getAccount();
			}
		}
		catch (Exception e) {
			userIp="服务端";
			userAccount="服务端";
		}
		return "操作者["+"IP:"+userIp+",账号:"+userAccount+"]";
	}
	
	public static void close(FTPClient ftp, Ftp ftps) {
		try {
			ftp.logout();
			ftp.disconnect();
			logger.warn(getOperator());
			logger.warn("FTP退出登录:  IP:" +ftps.getServerIp() + " user:" +ftps.getAccount()+ " password:" + ftps.getPassword() + " port:" +ftps.getFtpPort()+"\n");
		}
		catch (IOException e) {
			logger.warn(getOperator());
			logger.warn("FTP退出登录发生异常，该链接已为空\n");
		}
	}
	
	public static void close(FTPClient ftp) {
		try {
			ftp.logout();
			ftp.disconnect();
			logger.warn(getOperator());
			logger.warn("FTP退出登录 \n");
		}
		catch (IOException e) {
			logger.warn(getOperator());
			logger.warn("FTP退出登录发生异常，该链接已为空\n");
		}
	}
	
	public static long getFileSize(String filePath,FTPClient ftp,boolean isColse) {
		FTPFile[] ftpfile=null;
		long size=0;
		try {
			ftpfile = ftp.listFiles(filePath);
			if(ftpfile!=null&&ftpfile[0]!=null) {
				size= ftpfile[0].getSize();
				logger.warn("文件大小:"+size+"byte;"+DoubleUtlis.divLong(size, (1024*1024), 2)+"MB");
				return size;
			}
		}
		catch (Exception e) {
			logger.warn("获取文件大小异常，"+filePath+"文件不存在");
		}
		finally {
			if(isColse)
				close(ftp);
		}
		return size;
	}
	
	public static boolean exists(String path,Ftp ftps) {
		FTPClient ftp=getClient(ftps);
		FTPFile[] ftpfile=null;
		try {
			ftpfile = ftp.listFiles(path);
			logger.warn("获取文件目录:"+path);
		}
		catch (IOException e) {
			logger.warn("获取文件列表大小异常");
		}
		if(ftpfile!=null&&ftpfile.length>0) {
			logger.warn("目录:"+path+"存在");
			close(ftp, ftps);
			return true;
		}
		close(ftp, ftps);
		return false;
	}
	

	public static FTPClient getClient(Ftp ftps) {// 获取连接
		FTPClient ftp = new FTPClient();
		//ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));//将过程中使用的命令添加到控制台
		ftp.setDefaultPort(ftps.getFtpPort());
		try {
			ftp.connect(ftps.getServerIp());
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.warn("FTP server refused connection:");
				ftp.disconnect();
				return null;
			}
			if (!ftp.login(ftps.getAccount(), ftps.getPassword())) {
				close(ftp, ftps);
				return null;
			}
			ftp.setControlEncoding(ftps.getEncoding());
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			 logger.warn("FTP连接成功-IP:" + ftps.getServerIp() + "  user:"+ ftps.getAccount()+ " pwd:" + ftps.getPassword() + " port:"+ ftps.getFtpPort());
			 logger.warn(getOperator());
		}
		catch (SocketException e1) {
			 logger.error("FTP连接失败-IP:" + ftps.getServerIp() + "  user:" +ftps.getAccount()+ " password:" + ftps.getPassword() + " port:" + ftps.getFtpPort());
			 logger.warn(getOperator());
		}
		catch (IOException e2) {
			 logger.error("FTP连接失败-IP:" +ftps.getServerIp() + " user:" +ftps.getAccount()+ " password:" + ftps.getPassword() + " port:" + ftps.getFtpPort());
			 logger.warn(getOperator());
		}
		return ftp;
	}

	/***
	 * 递归删除某个文件夹下的文件并删除当前文件夹
	 * 
	 * @param path
	 */
	public static void recursionDele(FTPClient ftpClient, String path) {
		try {
			FTPFile files[] = ftpClient.listFiles(path);
			for (FTPFile file : files) {
				if (!file.getName().equals("..") && !file.getName().equals(".")) {
					if (!file.isDirectory()) {
						logger.warn("删除文件:"+path + "/" + file.getName());
						ftpClient.deleteFile(path + "/" + file.getName());
						logger.warn("删除文件:"+path + "/" + file.getName()+"---成功");
						continue;
					}
					if (file.isDirectory())
						recursionDelele(ftpClient, path);
				}
			}
			ftpClient.deleteFile(path);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归删除某个文件夹下的文件,但不会删除当前文件夹，需要自己关闭FTP连接
	 * 
	 * @param path
	 * @return
	 */
	public static void recursionDelele(FTPClient ftpClient, String path) {
		try {
			FTPFile files[] = ftpClient.listFiles(path);
			if (files == null || files.length == 0) {
				logger.warn("删除文件夹:"+path);
				ftpClient.deleteFile(path);
				logger.warn("删除文件夹:"+path+"--成功");
				return;
			}
			for (FTPFile file : files) {
				if (!file.getName().equals(".") && !file.getName().equals("..")) {
					String p = path+ (file.isDirectory() ? path.endsWith("/") ? file.getName() : "/" + file.getName() + "/": path.endsWith("/") ? file.getName() : "/"+ file.getName());
					if (!file.isDirectory()) {
						logger.warn("删除文件:"+p);
						ftpClient.deleteFile(p);
						logger.warn("删除文件:"+p+"--成功");
						continue;
					}
					if (file.isDirectory()) {
						logger.warn("删除文件夹:"+p);
						recursionDelele(ftpClient, p);
						logger.warn("删除文件夹:"+p+"--成功");
					}
					ftpClient.sendCommand("RMD " + p);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 递归下载某个目录下的所有文件和文件夹，文件的命名和FTP目录的命名结构一样，不关闭FTP连接
	 * 
	 * @param localDir
	 *            本地接收的目录
	 * @param remotePath
	 *            @
	 * @throws IOException
	 */
	private static void recursionDownLoad(FTPClient ftpClient, final String localDir,
			String remotePath, final String rootPath) {
		try {
			FTPFile[] files = ftpClient.listFiles(remotePath);
			File ff = new File(localDir+ "/"+ (rootPath == null ? remotePath : remotePath.substring(rootPath.length() + 1, remotePath.length())));
			if (!ff.exists())
				ff.mkdir();
			for (FTPFile file : files) {
				if(!file.getName().equals(".")&&!file.getName().equals("..")) {
    				String p = remotePath+ (file.isDirectory() ? remotePath.endsWith("/") ? file.getName() : "/" + file.getName() + "/": remotePath.endsWith("/") ? file.getName(): "/" + file.getName());
    				String local = rootPath == null ? localDir + "/" + p : localDir
    						+ "/" + p.substring(rootPath.length() + 1, p.length());
    				if (!file.isDirectory()) {
    					OutputStream outputStream = new FileOutputStream(new File(
    							local));
    					boolean b = ftpClient.retrieveFile(p, outputStream);
    					logger.info(file.getName() + " 下载 " + (b ? "成功！" : "失败！"));
    					outputStream.close();
    				}
    				if (file.isDirectory()) {
    					File f = new File(local);
    					if (!f.exists())
    						f.mkdir();
    					recursionDownLoad(ftpClient, localDir, p, rootPath);
    				}
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 下载远程目录下的所有的文件和文件夹，连远程目录会一起下载
	 * 
	 * @param localDir
	 * @param remotePath
	 * @throws IOException
	 *             @
	 */
	public void recursionDownLoad(Ftp ftps, final String localDir,String remotePath) {
		FTPClient ftpClient = getClient(ftps);
		recursionDownLoad(ftpClient, localDir, remotePath, null);
	}
	/***
	 * 递归下载某个目录下的所有文件和文件夹，
	 * 文件的命名和FTP目录的命名结构一样不包括当前的目录，且会关闭FTP连接
	 * 
	 * @param localDir
	 *            本地接收的目录
	 * @param remotePath
	 *            @
	 * @throws IOException
	 */
	public static void recursionDownLoadAndClose(Ftp ftp, final String localDir,String remotePath) {
		FTPClient ftpClient = getClient(ftp);
		FTPFile[] files;
		try {
			files = ftpClient.listFiles(remotePath);
			for (FTPFile file : files) {
				if(!file.getName().equals(".")&&!file.getName().equals("..")) {
    				String p = remotePath+ (file.isDirectory() ? remotePath.endsWith("/") ? file.getName() : "/" + file.getName() + "/": remotePath.endsWith("/") ? file.getName(): "/" + file.getName());
    				String local = remotePath == null ? localDir + "/" + p: localDir+ "/"+ p.substring(remotePath.length() + 1, p.length());
    				if (!file.isDirectory()) {
    					OutputStream outputStream = new FileOutputStream(new File(local));
    					boolean b = ftpClient.retrieveFile(p, outputStream);
    					logger.info(file.getName() + " 下载 " + (b ? "成功！" : "失败！"));
    					outputStream.close();
    				}
    				if (file.isDirectory()) {
    					recursionDownLoad(ftpClient, localDir, p, remotePath);
    				}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			close(ftpClient, ftp);
		}
	}

	/***
	 * 上传文件夹，下的所有文件和文件夹
	 * 
	 * @param local
	 * @param remotePath
	 *            @
	 * @throws IOException
	 */
	private static void recursionUpload(FTPClient ftpClient, String local,
			final String remotePath) {
		File file = new File(local);
		if (!file.exists())
			return;
		try {
			InputStream stream = null;
			if (file.isFile()) {
				stream = new FileInputStream(file);
				boolean b = ftpClient.storeFile(remotePath + "/"+ file.getName(), stream);
				logger.info(file.getName() + " 上传 " + (b ? "成功！" : "失败！"));
				stream.close();
				return;
			}
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isFile()) {
						stream = new FileInputStream(files[i]);
						ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
						boolean b = ftpClient.storeFile(remotePath + "/"+ files[i].getName(), stream);
						logger.info(files[i].getName() + " 上传 "+ (b ? "成功！" : "失败！"));
						stream.close();
						continue;
					}
					if (files[i].isDirectory()) {
						ftpClient.makeDirectory(remotePath + "/"+ files[i].getName());
						recursionUpload(ftpClient, files[i].getAbsolutePath(),remotePath + "/" + files[i].getName());
					}
				}
			}
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
		}
	}

	/***
	 * 上传文件夹，下的所有文件和文件夹 主调方法
	 * 
	 * @param local
	 * @param remotePath
	 *            @
	 * @throws IOException
	 */
	public static void recursionUploadFiles(Ftp ftp, String local,final String remotePath) {
		FTPClient ftpClient = getClient(ftp);
		recursionUpload(ftpClient, local, remotePath);
		close(ftpClient, ftp);
	}
	
	/**
	 * 创建递归文件夹及文件
	 * 
	 * @param realPath
	 * @param nestFile
	 * @return
	 */
	public static File createNestFile(String realPath, String nestFile) {
		String[] urls = nestFile.split(ElianCodes.SPRIT);
		String path = realPath;
		File lastFile = null;
		for (String url : urls) {
			if (StringUtils.isBlank(url))
				continue;
			path += ElianCodes.SPRIT + url;
			File file = new File(path);
			if (path.endsWith(ElianCodes.SUFFIX_HTML)) {
				if (file.exists()) {
					file.delete();
				}
				lastFile = file;
				break;
			}
			else if (!file.exists()) {
				file.mkdirs();
			}
		}
		return lastFile;
	}
}
