package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ImgUploadDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.syst.service.Service;

public interface ImgUploadService extends Service<ImgUploadDao, Images, Integer>{
	/**将内容中的图片保存到FTP*/
	public void saveConContext(String eitityName,Integer entityId,String editorpvreImg, String context) ;
	public void saveImagesToFtp(String eitityName,Integer entityId,String[] prevImgs, String... realname);
	public  void delContentImg(List<String> delImgPath,String eitityName,Integer entityId) ;
	public void delFtpImg(String actionName,Integer entityId,String... path) ;
	public  List<Images> getDelImgsByEitityAndId(String entityName,Integer entityId);
	public void delFTPImg(Ftp ftp,Images img);
	public void delFTPImg(Ftp ftp,List<Images> img);
	public void delFtpImg(String  path);
	public  List<Images> getDelImgsBySiteId(Integer siteId);
	
}
