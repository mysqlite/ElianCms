package com.elian.cms.admin.service;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.dao.SiteFileDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.admin.model.SiteFile;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SiteFileService extends Service<SiteFileDao, SiteFile,Integer> {
	
	public List<SiteFile> findByAll(Pagination<SiteFile> p, Integer siteId,
			Object entity,Date createDate);
	
	public List<Images> findByPage(Pagination<Images> p,Integer siteId,Object entity,Date createDate);
	
	public void saveFileToFtp(Object entity,String[] prevFiles, String... file);

	public void saveConContext(Object t,String editorPvreFile[],String... context);
	
	public void delFtpFile(Ftp ftp,SiteFile sf);
	
	public void delFtpImgs(Ftp ftp,List<Images> imgs);
	
	public void delFtpFile(Ftp ftp,List<SiteFile> sf);
	
	public void saveImg(Images img);
	
	/**实体删除的后继动作*/
	public void deleteImgs(Object entity);

	public Images getImg(Integer id);

	public void deleteImg(Images images);

}
