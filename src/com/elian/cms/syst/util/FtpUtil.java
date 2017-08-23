 package com.elian.cms.syst.util;
  import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
  
  /**
   * 
   * Purpose: Ftp操作客户端 apache 版
   * 
   * @see
   * @since 6.1.0
   */
  public class FtpUtil
  {
  
      private static final FtpUtil ftp = new FtpUtil();
      private Log log =LogFactory.getLog(getClass());
  
      FTPClient ftpClient;
  
      private FtpUtil()
      {
  
      }
  
      public static FtpUtil getInstance(String server, int port, String loginName, String loginPwd) 
      {
          ftp.init(server, port, loginName, loginPwd);
          return ftp;
      }
  
      public static FtpUtil getInstance()
      {
          return ftp;
      }
  
      public void init(String server, int port, String loginName, String loginPwd, boolean isPrintCommand) 
      {
          init(null, server, port, loginName, loginPwd, isPrintCommand);
      }
  
      public void init(String server, int port, String loginName, String loginPwd) 
      {
          init(null, server, port, loginName, loginPwd, false);
      }
  
      public void init(FTPClientConfig config, String server, int port, String loginName, String loginPwd, boolean isPrintCommand) 
      {
          ftpClient = new FTPClient();
          // 设置编码
          ftpClient.setControlEncoding("utf-8");
  
          if (config == null)
          {
              config = new FTPClientConfig();
              config.setServerLanguageCode("zh");
          }
  
          ftpClient.configure(config);
  
          try
          {
              ftpClient.connect(server, port);
          } catch (SocketException e)
          {
          } catch (IOException e)
         {
         }
     }
 
     /***************************************************************************
      * 连接是否有效的
      * 
      * @return
      */
     public Boolean isPositive()
     {
         return FTPReply.isPositiveCompletion(ftpClient.getReplyCode());
     }
 
     private void isPositiveable() 
     {
         if (!isPositive())
         {
             closeFtpConnection();
         }
     }
 
     /***************************************************************************
      * 下载文件
      * 
      * @param outputStream
      * @param remoteFileName
      * @param fileType
      * @
      * @throws IOException
      */
     public boolean download(OutputStream outputStream, String remoteFileName, int fileType)
     {
         isPositiveable();
         try
         {
             ftpClient.setFileType(fileType);
             return ftpClient.retrieveFile(remoteFileName, outputStream);
         } catch (FileNotFoundException e)
         {
             e.printStackTrace();
             return false;
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         } finally
         {
             if (outputStream != null)
				try {
					outputStream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
             closeFtpConnection();
         }
     }
 
     /***************************************************************************
      * 下载文件
      * 
      * @param outputStream
      * @param remoteFileName
      * @param fileType
      * @
      * @throws IOException
      */
     public boolean download(OutputStream outputStream, String remoteFileName)
     {
         return download(outputStream, remoteFileName, FTP.BINARY_FILE_TYPE);
     }
 
     /***************************************************************************
      * 
      * @param file
      *            下载下来的文件
      * @param remoteFileName
      * @
      * @throws IOException
      */
     public boolean download(File file, String remoteFileName) 
     {
         OutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
         return download(outputStream, remoteFileName, FTP.BINARY_FILE_TYPE);
     }
 
     /***************************************************************************
      * 
      * @param file
      *            下载下来的文件
      * @param remoteFileName
      * @
      * @throws IOException
      */
     public boolean download(String fileName, String remoteFileName)
     {
         File file = new File(fileName);
         OutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream(file);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return download(outputStream, remoteFileName, FTP.BINARY_FILE_TYPE);
     }
 
     /***************************************************************************
      * 上传文件
      * 
      * @param InputStream
      * @param remoteFileName
      * @
      * @throws IOException
      */
     public boolean upload(InputStream stream, String remoteFileName, Integer fileType) 
     {
         isPositiveable();
         try
         {
             ftpClient.setFileType(fileType);
             return ftpClient.storeFile(remoteFileName, stream);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         } finally
         {
             if (stream != null)
				try {
					stream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
             closeFtpConnection();
         }
     }
     /***
      * 切换工作目录
      * @param pathname
      * @return
      * @
      */
     public boolean changeWorkingDirectory(String pathname) 
     {
         try
         {
             return ftpClient.changeWorkingDirectory(pathname);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         }
     }
 
     /***************************************************************************
      * 上传文件,默认以二进制传输
      * 
      * @param InputStream
      * @param remoteFileName
      * @
      * @throws IOException
      */
     public boolean upload(InputStream stream, String remoteFileName) 
     {
         return upload(stream, remoteFileName, FTP.BINARY_FILE_TYPE);
     }
 
     /***************************************************************************
      * 上传文件,默认以二进制传输
      * 
      * @param InputStream
      * @param remoteFileName
      * @
      * @throws IOException
      */
     public boolean upload(File file) 
     {
         InputStream inputStream=null;
		try {
			inputStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
         return upload(inputStream, file.getName(), FTP.BINARY_FILE_TYPE);
     }
 
     /***************************************************************************
      * 上传文件
      * 
      * @param file
      * @param fileType
      * @
      * @throws IOException
      */
     public boolean upload(File file, int fileType)
     {
         InputStream inputStream=null;
		try {
			inputStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
         return upload(inputStream, file.getName(), fileType);
     }
 
     /***************************************************************************
      * 列出某目录下的所有文件和文件夹
      * 
      * @param pathName
      * @return
      * @
      */
     public FTPFile[] arrayFiles(String pathName) 
     {
         isPositiveable();
         try
         {
             return ftpClient.listFiles(pathName);
         } catch (IOException e)
         {
             e.printStackTrace();
             return null;
         } finally
         {
             closeFtpConnection();
         }
     }
 
     /***************************************************************************
      * 删除文件夹或文件,删除文件夹时，必须没有子目录或子文件
      * 
      * @param pathName
      * @
      */
     public boolean dele(String pathName) 
     {
         isPositiveable();
         try
         {
             return FTPReply.isPositiveCompletion(ftpClient.dele(pathName));
         } catch (IOException e)
         {
        	 return false;
         } finally
         {
             closeFtpConnection();
         }
     }
 
     /***************************************************************************
      * 删除单个文件
      * 
      * @param pathName
      * @
      */
     public boolean delele(String pathName) 
     {
         isPositiveable();
         try
         {
             return ftpClient.deleteFile(pathName);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         } finally
         {
             closeFtpConnection();
         }
     }
     /****
      * 删除文件夹里的文件，不是会像 rm -rf 一样的
      * @param path
      * @return
      * @
      */
     public boolean removeDirectory(String path) {
         isPositiveable();
         try
         {
            return  ftpClient.removeDirectory(path);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         }
         finally
         {
             closeFtpConnection();
         }
     }
     /***************************************************************************
      * 递归删除某个文件夹下的文件,但不会删除当前文件夹，需要自己关闭FTP连接
      * @param path
      * @return
      * @
      * @throws IOException
      */
     public void recursionDelele(String path)
     {
         isPositiveable();
         try {
			FTPFile files[] = ftpClient.listFiles(path);
			 if (files == null || files.length == 0){
			     ftpClient.deleteFile(path);
			     return;
			 }
			 for (FTPFile file : files)
			 {
			     String p = path + ( file.isDirectory() ? path.endsWith("/") ? file.getName() : "/" + file.getName() + "/"
			             : path.endsWith("/") ?  file.getName() : "/" + file.getName());
			     if(!file.isDirectory()){
			         ftpClient.deleteFile(p);
			         continue;
			     }
			     if (file.isDirectory())
			         recursionDelele(p);
			 }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
     }
     /***
      * 递归下载某个目录下的所有文件和文件夹，文件的命名和FTP目录的命名结构一样，不关闭FTP连接
      * @param localDir 本地接收的目录
      * @param remotePath
      * @
      * @throws IOException 
      */
     private  void  recursionDownLoad(final String localDir,String remotePath,final String rootPath) {
         isPositiveable();
         try {
			FTPFile[] files = ftpClient.listFiles(remotePath);
			 File ff = new File(localDir + "/" +( rootPath == null ? remotePath : remotePath.substring( rootPath.length()+1,remotePath.length())));
			 if (!ff.exists())
			     ff.mkdir();
			 for (FTPFile file : files)
			 {
			     String p = remotePath+ (file.isDirectory() ? remotePath.endsWith("/") ? 
			                 file.getName() : "/" + file.getName() + "/" : remotePath.endsWith("/") ? file
			                     .getName() : "/" + file.getName());
			     String local = rootPath == null ? localDir + "/" + p :localDir + "/" + p.substring(rootPath.length() +1,p.length());
			     if (!file.isDirectory())
			     {
			         OutputStream outputStream = new FileOutputStream(new File(local));
			         boolean b = ftpClient.retrieveFile(p, outputStream);
			         if (log.isDebugEnabled())
			             log.debug(file.getName()+" 下载 " +( b ? "成功！" : "失败！"));   
			         outputStream.close();
			     }
			     if (file.isDirectory())
			     {
			         File f = new File(local);
			         if (!f.exists())
			             f.mkdir();
			         recursionDownLoad(localDir, p,rootPath);
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
      * @param localDir
      * @param remotePath
      * @throws IOException 
      * @ 
      */
     public void recursionDownLoad(final String localDir,String remotePath) {
             recursionDownLoad(localDir,remotePath,null);
            closeFtpConnection();
     }
     
     /***
      * 递归下载某个目录下的所有文件和文件夹，文件的命名和FTP目录的命名结构一样不包括当前的目录，且会关闭FTP连接
      * @param localDir 本地接收的目录
      * @param remotePath
      * @
      * @throws IOException 
      */
     public void  recursionDownLoadAndClose(final String localDir,String remotePath) {
         isPositiveable();
         FTPFile[] files;
         try
         {
             files = ftpClient.listFiles(remotePath);
             for (FTPFile file : files)
             {
                 String p =remotePath + (file.isDirectory() ? remotePath.endsWith("/") ? 
                             file.getName() : "/" + file.getName() + "/" : remotePath.endsWith("/") ? file
                                 .getName() : "/" + file.getName());
                 String local = remotePath == null ? localDir + "/" + p :localDir + "/" + p.substring(remotePath.length() +1,p.length());
                 if (!file.isDirectory())
                 {
                     OutputStream outputStream = new FileOutputStream(new File(local));
                     boolean b=ftpClient.retrieveFile(p, outputStream);
                     if (log.isDebugEnabled())
                         log.debug(file.getName()+" 下载 " +( b ? "成功！" : "失败！"));   
                     outputStream.close();
                 }
                 if (file.isDirectory())
                 {
                     recursionDownLoad(localDir, p,remotePath);
                 }
             }
         } catch (IOException e)
         {
             e.printStackTrace();
         }finally{
             closeFtpConnection();
         }
     }
     /***
      * 上传文件夹，下的所有文件和文件夹
      * @param local
      * @param remotePath
      * @
      * @throws IOException
      */
     private void recursionUpload(String local,final String remotePath) {
         isPositiveable();
         File file = new File(local);
         if(!file.exists())
             return;
         try
         {
             InputStream stream =null;
             if (file.isFile())
             {
                 stream =new FileInputStream(file);
                 boolean b= ftpClient.storeFile(remotePath+"/" + file.getName(), stream);
                 if (log.isDebugEnabled())
                     log.debug(file.getName()+" 上传 " +( b ? "成功！" : "失败！"));   
                 stream.close();
                 return ;
             }
             if(file.isDirectory()){
                 File []  files =  file.listFiles();
                 for (int i = 0; i < files.length; i++)
                 {
                     if(files[i].isFile()){
                         stream =new FileInputStream(files[i]);
                         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                         boolean b  = ftpClient.storeFile(remotePath+"/"+files[i].getName(), stream);
                         if (log.isDebugEnabled())
                             log.debug(files[i].getName()+" 上传 " +( b ? "成功！" : "失败！"));   
                         stream.close();
                         continue;
                     }
                     if(files[i].isDirectory()){
                         ftpClient.makeDirectory(remotePath+"/"+ files[i].getName());
                         recursionUpload(files[i].getAbsolutePath(),remotePath+"/"+files[i].getName());
                     }
                     
                 }
             }
         } catch (FileNotFoundException e)
         {
         } catch (IOException e)
         {
         }
     }
     
     /***
      * 上传文件夹，下的所有文件和文件夹
      * @param local
      * @param remotePath
      * @
      * @throws IOException
      */
     public void recursionUploadFiles(String local,final String remotePath){
             recursionUpload(local,remotePath);
             closeFtpConnection();
     }
     
     /***************************************************************************
      * 列出pathName目录下的所有文件,排除文件夹,如果不传入路径，就会显示当前的工作空间的路径
      * 
      * @param pathName
      * @return
      * @
      */
     public List<String> listFile(String pathName) 
     {
         return listFiles(pathName, FTPFile.FILE_TYPE);
     }
     
     /***************************************************************************
      * @param pathName
      * @return
      * @
      */
     public List<String> listFiles(String pathName, int fileType) 
     {
         FTPFile[] files = arrayFiles(pathName);
         List<String> fileList = new ArrayList<String>();
         for (FTPFile file : files)
         {
             if (file.getType() == fileType)
                 fileList.add(file.getName());
         }
         return fileList;
     }
 
     /***************************************************************************
      * 列出pathName目录下的所有文件,排除文件夹
      * 
      * @param pathName
      * @return
      * @
      */
     public List<String> listDirectory(String pathName) 
     {
         return listFiles(pathName, FTPFile.DIRECTORY_TYPE);
     }
 
     /***************************************************************************
      * 移动文件
      * 
      * @param remoteFileFrom
      *            目标文件
      * @param remoteFileTo
      *            移动至
      * @
      */
     public boolean move(String remoteFileFrom, String remoteFileTo) 
     {
         isPositiveable();
         try
         {
             return ftpClient.rename(remoteFileFrom, remoteFileTo);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         } finally
         {
             closeFtpConnection();
         }
     }
     
     /***************************************************************************
      * 创建文件夹
      * 
      * @param remoteDirectory
      * @
      */
     public boolean makeDirectory(String remoteDirectory) 
     {
         isPositiveable();
         try
         {
             return ftpClient.makeDirectory(remoteDirectory);
         } catch (IOException e)
         {
             e.printStackTrace();
             return false;
         } finally
         {
             closeFtpConnection();
         }
     }
 
     /***************************************************************************
      * 关闭FTP连接
      * 
      * @
      */
     public void closeFtpConnection() 
     {
         try
         {
             if (!isPositive())
             {
                 ftpClient.logout();
                 ftpClient.disconnect();
             }
         } catch (IOException e)
         {
             e.printStackTrace();
         }
     }
 
 }