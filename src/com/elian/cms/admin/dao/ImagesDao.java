package com.elian.cms.admin.dao;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ImagesDao extends Dao<Images, Integer> {
	
	public List<Images> findByPage(Pagination<Images> p,Integer siteId,String entityName,Integer entityId,Date createDate);

	public Images findByImagePath(String path);
	
	public void saveToFtp(Ftp ftp,Images img) ;
	
	public void saveToFtp(Ftp ftp,List<Images> img);
	
	public void saveQYFYToFtp(Ftp ftp,List<Images> img);
	
	public void delFTPImg(Ftp ftp,Images img);
	
	public void delFTPImg(Ftp ftp,List<Images> img);
	
}
