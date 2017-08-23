package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ImagesDao;
import com.elian.cms.admin.dao.SiteFileDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.admin.model.SiteFile;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.common.upload.FileType;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.PersistentInterface;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("siteFileService")
public class SiteFileServiceImpl extends
		ServiceImpl<SiteFileDao, SiteFile, Integer> implements SiteFileService {
	private ImagesDao imagesDao;
	private SiteFile siteFile;
	private Images img;
	private List<SiteFile> siteFiles;
	private List<SiteFile> videoSiteFiles;
	private List<Images> imgs;
	private String ext="";
	private Integer id;
	private String entityName;
	
	public List<SiteFile> findByAll(Pagination<SiteFile> p, Integer siteId,
			Object t, Date createDate) {
		getEntity(t);
		return dao.findByAll(p, siteId, entityName, id, createDate);
	}
	
	public List<Images> findByPage(Pagination<Images> p,Integer siteId,Object t,Date createDate){
		getEntity(t);
		return imagesDao.findByPage(p,siteId, entityName, id,createDate);
	}
	
	public void saveImg(Images img) {
		imagesDao.save(img);
	}
	
	public Images getImg(Integer id) {
		return imagesDao.get(id);
	}
	
	public void deleteImg(Images img) {
		imagesDao.delete(img);
	}

	/**
	 * 主调方法
	 */
	public void saveFileToFtp(Object t,
			String[] prevFiles, String... realname) {
		getEntity(t);
		if(StringUtils.isNotBlank(entityName)&&id!=null) {
    		for (int i = 0; i < prevFiles.length; i++) {
    			if (!prevFiles[i].equals(realname[i])) {
    				if (FilePathUtils.isHttp(prevFiles[i])) {
    					delFtpFile(prevFiles[i]);
    				}
    				if (FilePathUtils.isHttp(realname[i])) {
    					saveFileToFtp(realname[i],false);
    				}
    			}
    		}
		}
	}
	
	/**主调方法*/
	public void saveConContext(Object t,String[] editorPvreFile,String... context) {
		getEntity(t);
		if(StringUtils.isNotBlank(entityName)&&id!=null) {
			List<String> delFiles=FilePathUtils.getDeleteFilePath(StringUtils.arrayToStr(editorPvreFile), StringUtils.arrayToStr(context));
			List<String> addFiles=FilePathUtils.getDeleteFilePath(StringUtils.arrayToStr(context),StringUtils.arrayToStr(editorPvreFile));
			delFtpFile(delFiles.toArray(new String[delFiles.size()]));
			for (String path : addFiles) {
				saveFileToFtp(path, true);
			}
		}
	}
	
	public void deleteImgs(Object entity) {
		List<Images> imgs =findByPage(null,null,entity,null);
		List<SiteFile> siteFiles=findByAll(null, null, entity, null);
		List<SiteFile> files=new ArrayList<SiteFile>(!CollectionUtils.isEmpty(siteFiles)?siteFiles.size():0);
		List<SiteFile> videoFiles=new ArrayList<SiteFile>(!CollectionUtils.isEmpty(siteFiles)?siteFiles.size():0);
		for (SiteFile sf : siteFiles) {
			if(FileType.FILES.contains(FilenameUtils.getExtension(sf.getFilePath()))) 
				files.add(sf);
			else if(FileType.VIDEO.contains(FilenameUtils.getExtension(sf.getFilePath())))
			videoFiles.add(sf);
		}
		delFtpImgs(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP), imgs);
		delFtpFile(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP), files);
		delFtpFile(EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP), videoFiles);
	}
	
	private void getEntity(Object t) {
		if(t==null)return;
		if(t instanceof PersistentInterface) {
			PersistentInterface entityId=(PersistentInterface)t;
			id=entityId.getId();
		}
		entityName=StringUtils.getEN(t);
	}

	private void saveFileToFtp(String fileName,Boolean editor) {
		if (StringUtils.isNotBlank(fileName)) {
			ext=FilenameUtils.getExtension(fileName);
			if(FileType.FILES.contains(ext)||FileType.VIDEO.contains(ext)) {
    			siteFile = new SiteFile();
    			siteFile.setFileName(FilenameUtils.getName(fileName));
    			siteFile.setShowName(FilenameUtils.getName(fileName));
    			siteFile.setUploadTime(new Date());
    			siteFile.setFileType(FilenameUtils.getExtension(fileName));
    			siteFile.setEntityId(id);
    			siteFile.setEntityName(entityName);
    			siteFile.setFilePath(fileName);
    			siteFile.setSite(ApplicationUtils.getSite());
    			siteFile.setUser(ApplicationUtils.getUser());
    			siteFile.setEditor(editor);
    			if(FileType.FILES.contains(ext)) {
    				siteFile.setFileSize(FtpToolUtils.getFileSize(ElianCodes.FTP_TEMP
        					+ siteFile.getFileName(), FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP)), true));
        			dao.saveToFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP), siteFile);
    			}
    			else if(FileType.VIDEO.contains(ext)) {
    				siteFile.setFileSize(FtpToolUtils.getFileSize(ElianCodes.FTP_TEMP
        					+ siteFile.getFileName(), FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP)), true));
        			dao.saveToFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP), siteFile);
    			}
    			
			}
			else if(FileType.IMGS.contains(ext)) {
				img=new Images();
				img.setEntityName(entityName);
				img.setCreateTime(new Date());
				img.setEditor(editor);
				img.setEntityId(id);
				img.setImagesPath(fileName);
				img.setSiteId(ApplicationUtils.getSite().getId());
				img.setImagesName(FilenameUtils.getName(fileName));
				img.setFileSize(FtpToolUtils.getFileSize(ElianCodes.FTP_TEMP+img.getImagesName(), FtpToolUtils.getClient(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP)),true));
				imagesDao.saveToFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP), img);
			}
		}
	}

	public void delFtpFile(Ftp ftp, SiteFile sf) {
		dao.delFtpFile(ftp, sf);
	}

	public void delFtpFile(Ftp ftp, List<SiteFile> sf) {
		dao.delFtpFile(ftp, sf);
	}
	
	public void delFtpImgs(Ftp ftp, List<Images> imgs) {
		imagesDao.delFTPImg(ftp, imgs);
	}
	
	private void delFtpFile(String... path) {
		imgs=new ArrayList<Images>();
		siteFiles = new ArrayList<SiteFile>();
		videoSiteFiles=new ArrayList<SiteFile>();
		for (String s : path) {
			ext=FilenameUtils.getExtension(s);
			if(FileType.IMGS.contains(ext)) {
				img=imagesDao.findByImagePath(s);
				if(null!=img&&img.getEntityName().equals(entityName)&& img.getEntityId().equals(id)) 
					imgs.add(img);
			}
			else if(FileType.VIDEO.contains(ext)) {
				siteFile = dao.findByPath(s);
				if (null!=siteFile&&siteFile.getEntityName().equals(entityName)&& siteFile.getEntityId().equals(id)) 
					videoSiteFiles.add(siteFile);
			}
			else if(FileType.FILES.contains(ext)) {
				siteFile = dao.findByPath(s);
				if (null!=siteFile&&siteFile.getEntityName().equals(entityName)&& siteFile.getEntityId().equals(id)) 
					siteFiles.add(siteFile);
			}
		}
		if(!CollectionUtils.isEmpty(imgs))
			imagesDao.delFTPImg(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP),imgs);
		if(!CollectionUtils.isEmpty(siteFiles))
			dao.delFtpFile(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP),siteFiles);
		if(!CollectionUtils.isEmpty(videoSiteFiles))
			dao.delFtpFile(EhcacheUtils.getCacheFtp(EhcacheUtils.VIDEO_FTP),videoSiteFiles);
	}
	
	@Autowired
	public void setDao(SiteFileDao dao) {
		this.dao = dao;
	}
	
	@Resource
	public void setImagesDao(ImagesDao imagesDao) {
		this.imagesDao = imagesDao;
	}
}
