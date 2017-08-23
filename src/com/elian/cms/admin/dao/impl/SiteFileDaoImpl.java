package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.SiteFileDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteFile;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("siteFileDao")
public class SiteFileDaoImpl extends DaoImpl<SiteFile, Integer> implements
		SiteFileDao {

	public List<SiteFile> findByAll(Pagination<SiteFile> p, Integer siteId,
			String entityName, Integer entityId,Date createDate) {
		String hql = "from SiteFile sf  where 1=1 ";
		List<Object> params=new ArrayList<Object>();
		if(siteId!=null) {
			hql+=" and sf.site.id=? ";
			params.add(siteId);
		}
		if(StringUtils.isNotBlank(entityName)) {
			hql+=" and sf.entityName=? ";
			params.add(entityName);
		}
		if(entityId!=null) {
			hql+=" and sf.entityId=? ";
			params.add(entityId);
		}
		if(createDate!=null) {
			hql+=" and sf.uploadTime>? ";
		}
		
		hql+="order by sf.id desc ";
		if (p != null) {
			p.setAlias("sf");
			return pageByHql(hql, false, p,params.toArray());
		}
		return findByHql(hql, false,params.toArray());
	}
	
	public SiteFile findByPath(String path) {
		StringBuffer hql=new StringBuffer(" from SiteFile sf where 1=1 ");
		List<Object> params=new ArrayList<Object>();
		if(!StringUtils.isBlank(path)) {
			hql.append(" and sf.filePath=? ");
			params.add(path);
			return findByHqlFirst(hql.toString(), false, params.toArray());
		}
		return null;
	}
	
	/**
	 * 移动单个文件到FTP真实路径
	 * @param ftp
	 * @param file
	 */
	public void saveToFtp(Ftp ftp,SiteFile file) {
		if(ftp!=null&&file!=null) {
			FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+file.getFileName(),file.getFilePath(), ftp);
			save(file);
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()+file.getFileSize());
			siteService.save(site);
		}
	}
	
	/**
	 * 移动多个文件到FTP真实路径
	 * @param ftp
	 * @param file
	 */
	public void saveToFtp(Ftp ftp,List<SiteFile> file) {
		if(ftp!=null&&file!=null) {
			long size=0;
			for (SiteFile sf : file) {
				FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+sf.getFileName(), sf.getFilePath(), ftp);
				save(sf);
				size=size+sf.getFileSize();
			}
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()+size);
			siteService.save(site);
		}
	}
	
	public void delFtpFile(Ftp ftp,SiteFile sf) {
		if(ftp!=null&&sf!=null) {
			FtpToolUtils.deleteFile(ftp,sf.getFilePath());
			delete(sf);
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()-sf.getFileSize());
			siteService.save(site);
		}
	}
	
	public void delFtpFile(Ftp ftp,List<SiteFile> sf) {
		if(!CollectionUtils.isEmpty(sf)&&ftp!=null) {
			long size=0;
			String[] delPath=new String[sf.size()];
			for (int i = 0; i < sf.size(); i++) {
				delPath[i]=sf.get(i).getFilePath();
			}
			FtpToolUtils.deleteFile(ftp,delPath);
			for (SiteFile siteFile: sf) {
				size=size+siteFile.getFileSize();
				delete(siteFile);
			}
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()-size);
			siteService.save(site);
		}
	}
	
	private SiteService siteService;
	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
