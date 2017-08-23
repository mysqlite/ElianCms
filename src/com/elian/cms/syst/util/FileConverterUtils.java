package com.elian.cms.syst.util;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.elian.cms.admin.model.Ftp;

/**
 * 文件转换类
 * @author Gechuanyi
 *
 */
public class FileConverterUtils {
	private final static Log logger = LogFactory.getLog(FileConverterUtils.class);
	/**
	 * 将  word文档(.doc,docx),execl(.xls,.xlsx),幻灯片(.ppt,.pptx)等转换为PDF格式
	 * ，意欲将其转换为swf供前端浏览
	 * @return boolean
	 */
	public static String fileToPDF(File soureFile,File pdfFile) {
		if(soureFile.exists()) {
			if(!pdfFile.exists()) {
				OpenOfficeConnection connection=new SocketOpenOfficeConnection(8100);
				try {
					connection.connect();
				}
				catch (ConnectException e) {
					logger.warn("OpenOffice服务尚未启动，连接失败");
					return "";
				}
				DocumentConverter converter=new OpenOfficeDocumentConverter(connection);
				converter.convert(soureFile, pdfFile);
				try {
					pdfFile.createNewFile();
				}
				catch (IOException e) {
					logger.warn("将文件转换为PDF发生异常");
					connection.disconnect();
					return "";
				}
				connection.disconnect();
				return pdfFile.getPath();
			}else {
				logger.warn("该文件已经转换为PDF，无需再次转换");
				return pdfFile.getPath();
			}
		}else {
			logger.warn("源文件不存在，无法转换");
		}
		return "";
	}
	
	public static String convertPDF2SWF(String sourcePath, String destPath, String fileName){ 
		File swfFile=new File(destPath+"\\"+fileName);
		if(swfFile.exists()&&swfFile.length()>0) {
			logger.warn("文件"+swfFile.getPath()+"已存在，无需转换");
			return swfFile.getPath();
		}
        //目标路径不存在则建立目标路径     
         File dest = new File(destPath);     
        if (!dest.exists()) dest.mkdirs();     
        //源文件不存在则返回     
         File source = new File(sourcePath);     
        if (!source.exists()) return null;     
        //调用pdf2swf命令进行转换    
        String command= "D:\\DevelopmentTools\\SWFTools\\pdf2swf"+" -t "+sourcePath+" -o "+destPath+"\\"+fileName+" -s flashversion=9 ";     
       
        Process pro=null;
		try {
			pro = Runtime.getRuntime().exec(command);     
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));     
			while (bufferedReader.readLine() != null);
		}
		catch (IOException e1) {
			logger.warn("SWF文件转换异常");
		}     
        try {     
             pro.waitFor();     
         } catch (InterruptedException e) {     
        	 logger.warn("SWF文件转换异常");
         }     
        return destPath+"\\"+fileName;     
     }     
	
	/*public static void main(String[] args) throws IOException {
		File file=new File("D:\\bbbc.ppt");
		String pdfPath="";
		String swfPath="";
		if(!FileType.getFileByFile(FileUtils.openInputStream(file)).equals("pdf")) {
		    pdfPath=fileToPDF(file, new File("D:\\bbbc.pdf"));
		    System.out.println("not Pdf");
		}else {
			pdfPath=file.getPath();
			 System.out.println("is Pdf");
		}
		
		boolean isExists=FtpToolUtils.exists("aaa/bbbjjkkyy.swf", getFtpSwf());
		if(isExists) {
			System.out.println("改文件已经转换过，无需再次转换");
		}else {
	    swfPath=convertPDF2SWF(pdfPath,"D:\\DevelopmentTools\\apache-tomcat-6.0.29\\webapps\\OpenFileOnline\\swfFile" , "ffffff.swf");
		System.out.println(swfPath);
		File ftpfile=new File(swfPath);
		FtpToolUtils.storeByFilename("aaa/bbbjjkkyy.swf", new FileInputStream(ftpfile), getFtpSwf());
		ftpfile.delete();
		}
	}*/
	
	public static void main(String[] args) throws IOException {
		File outputFile =null;
		   
	     File inputFile = new File("D:\\office.dwg");
	     BufferedImage input = ImageIO.read(inputFile);
	    //转换为jpg格式的图片
	    outputFile = new File("D:\\office.dwg");
	     ImageIO.write(input, "BMP", outputFile);
	}
	
	/* private static Ftp getFtpFile() { 
	  Ftp ftp1 = new Ftp();
	  ftp1.setServerIp("www.elian.cc"); ftp1.setAccount("ftpFile");
	  ftp1.setPassword("eliancms91580site(!%*)file");
	  ftp1.setFtpUrl("http://file.elian.cc");
	  ftp1.setEncoding("UTF-8");
	  ftp1.setTimeout(8000); 
	  ftp1.setFtpPort(21);
	  ftp1.setFtpPath(""); 
	  return ftp1;
	 }*/
	 
	 private static Ftp getFtpSwf() { 
		  Ftp ftp1 = new Ftp();
		  ftp1.setServerIp("www.elian.cc"); ftp1.setAccount("ftpSwf");
		  ftp1.setPassword("eliancms91580site(!%*)swf");
		  ftp1.setFtpUrl("http://swf.elian.cc");
		  ftp1.setEncoding("UTF-8");
		  ftp1.setTimeout(8000); 
		  ftp1.setFtpPort(21);
		  ftp1.setFtpPath(""); 
		  return ftp1;
		 }
}
