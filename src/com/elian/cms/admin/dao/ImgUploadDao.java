package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.syst.dao.Dao;
public interface ImgUploadDao extends Dao<Images, Integer> {
	public void saveToFtp(Ftp ftp,Images img) ;
	public void saveToFtp(Ftp ftp,List<Images> img);
	public void delFTPImg(Ftp ftp,Images img);
	public void delFTPImg(Ftp ftp,List<Images> img);
	public  List<Images> getDelImgsByEitityAndId(String entityName,Integer entityId);
	public  List<Images> getDelImgsBySiteId(Integer siteId);
}
