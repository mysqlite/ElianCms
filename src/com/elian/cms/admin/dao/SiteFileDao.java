package com.elian.cms.admin.dao;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.SiteFile;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SiteFileDao extends Dao<SiteFile, Integer> {
	public List<SiteFile> findByAll(Pagination<SiteFile> p, Integer siteId,
			String entityName, Integer entityId,Date createDate) ;

	public SiteFile findByPath(String path) ;
	
	public void saveToFtp(Ftp ftp,SiteFile file) ;
	
	public void saveToFtp(Ftp ftp,List<SiteFile> file);
	
	public void delFtpFile(Ftp ftp,SiteFile sf);
	
	public void delFtpFile(Ftp ftp,List<SiteFile> sf);
	
}
