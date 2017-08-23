package com.elian.cms.admin.service.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.MarkConfig;
import com.elian.cms.admin.service.FileUploadService;
import com.elian.cms.common.image.ImageScale;
import com.elian.cms.common.upload.FileType;
import com.elian.cms.common.upload.UploadUtils;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {
	private ImageScale imageScale;
	public Object[] saveFileToFtpTemp(File file, String realName,
			String prevFile,String ext,String contentAction,boolean controls, boolean mark,boolean video) {
			deleteFile(prevFile);
			String storeFileName="";
			String fileUrl="";
			boolean isFile=FileType.WORD.contains(ext)||FileType.PDF.contains(ext)||FileType.SWF.contains(ext);
			Ftp ftp=isFile?EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP):EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP);
			if(video)ftp=EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP);
			if(isHospImg(contentAction,controls))	
				storeFileName=ElianCodes.FTP_TEMP+FilenameUtils.getName(UploadUtils.generateFilename("", ext, FilePathUtils.HospImg(contentAction)));
			else
				storeFileName=ElianCodes.FTP_TEMP+ FilenameUtils.getName(UploadUtils.generateFilename("", ext)) ;//给出文件名/temp/084433n73l0a.JPEG
			try {
				if(mark) {
					File tempFile=mark(file, getMarkConfig());
					fileUrl=FtpToolUtils.storeByFilename(storeFileName, new FileInputStream(tempFile),ftp);
					tempFile.delete();
				}
				else
					fileUrl=FtpToolUtils.storeByFilename(storeFileName, new FileInputStream(file) ,ftp);
			}
			catch (FileNotFoundException e) {
				return new Object[] {null,null,null,"上传失败，请稍后再试！"};
			}
			String tempFileName=UploadUtils.genertFilename()+fileUrl;
			String noneFileUrl="";
			if(isHospImg(contentAction,controls)) 
		    	 noneFileUrl=UploadUtils.genertFilename(FilePathUtils.HospImg(contentAction), FilenameUtils.getName(fileUrl));
		    else 
		    	noneFileUrl=StringUtils.replaceStr(tempFileName, "/", ElianCodes.FTP_TEMP);//main/60/20121105/160404ayvpmf.JPEG存储到数据库
			realName=FilenameUtils.getName(fileUrl);//文件名称显示给用户看090204mo31do.JPEG//真实的fileUrl用作显示图片路径/temp/main/60/20121105/090204mo31do.JPEG
			if(controls) {
				file.delete();
			}
			return new Object[] {noneFileUrl,fileUrl,realName,null};
	}
	
	private void deleteFile(String prevFile) {
		String ext=FilenameUtils.getExtension(prevFile);
		if (StringUtils.isNotBlank(prevFile)) {//判断上一文件是否存在，如果存在，删除【为防止无限上传】
			if(FileType.IMGS.contains(ext))
			     FtpToolUtils.deleteFile(prevFile, EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));
			else if(FileType.WORD.contains(ext)||FileType.PDF.contains(ext)||FileType.SWF.contains(ext))
				FtpToolUtils.deleteFile(prevFile, EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP));
		}
	}
	
	private boolean isHospImg(String contentAction,boolean controls ) {
		return StringUtils.isNotBlank(FilePathUtils.HospImg(contentAction))&&controls;
	}
	/**
	 * 添加图片水印
	 * @param file
	 * @param conf
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private File mark(File file, MarkConfig conf){
		String path = ApplicationUtils.getTempFolderPath(ApplicationUtils.getRequest());// 获取文件路径
		String watermark =ApplicationUtils.getSiteRoot(ApplicationUtils.getRequest())+ conf.getImagePath();
		File tempFile = new File(path, String.valueOf(System.currentTimeMillis()));// 转换为新文件（路径，系统时间值）
		boolean imgMark = !StringUtils.isBlank(conf.getImagePath());// 判断设置的系统文件路径是否为空
		try {
    		FileUtils.copyFile(file, tempFile);
    		BufferedImage imgBuff = ImageIO.read(file);
    		if (imgMark) {
    			File markImg = new File(watermark);// 添加图片水印/获取水印文件的绝对路径
    			BufferedImage imgBuff1 = ImageIO.read(markImg);
    			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf.getMinHeight(), conf.getPos(), imgBuff.getWidth()- imgBuff1.getWidth(), imgBuff.getHeight()- imgBuff1.getHeight(), markImg);
    		}
    		else {
    			imageScale.imageMark(tempFile, tempFile, conf.getMinWidth(), conf.getMinHeight(), conf.getPos(), conf.getOffsetX(), conf.getOffsetY(), conf.getContent(), Color.decode(conf.getColor()), conf.getSize(), conf.getAlpha());
    		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempFile;
	}
	
	/**图片水印配置*/
	private MarkConfig getMarkConfig() {
		MarkConfig conf = new MarkConfig();// 获取图片配置信息,与水印有关
		conf.setAlpha(50);
		conf.setColor("#FF0000");// 水印颜色
		conf.setContent("www.elian.com");// 水印内容
		conf.setImagePath("images\\www.191580.png");// 水印图片地址
		conf.setMinHeight(250);// 水印高度
		conf.setMinWidth(250);// 水印宽度
		conf.setOffsetX(0);// 水印位于X坐标的点
		conf.setOffsetY(0);// 水印位于Y坐标的点
		conf.setOn(true);// 是否开启水印
		conf.setPos(1);//
		conf.setSize(20);
		return conf;
	}
	
	@Resource
	public void setImageScale(ImageScale imageScale) {
		this.imageScale = imageScale;
	}
}
