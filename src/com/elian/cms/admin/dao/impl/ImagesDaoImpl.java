package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ImagesDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Images;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FtpToolUtils;
import com.elian.cms.syst.util.StringUtils;
@Component("imagesDao")
public class ImagesDaoImpl extends DaoImpl<Images, Integer> implements
		ImagesDao {
	public List<Images> findByPage(Pagination<Images> p,Integer siteId,
			String entityName,Integer entityId,Date createDate){
		StringBuffer hql=new StringBuffer(" from Images  i  where 1=1 ");
		List<Object> param=new ArrayList<Object>();
		if(siteId!=null) {
			hql.append(" and i.siteId =? ");
			param.add(siteId);
		}
		if(StringUtils.isNotBlank(entityName)) {
			hql.append(" and i.entityName=? ");
			param.add(entityName);
		}
		if(entityId!=null) {
			hql.append(" and i.entityId =? ");
			param.add(entityId);
		}
		if(createDate!=null) {
			hql.append(" and i.createTime>? ");
			param.add(createDate);
		}
		hql.append(" order by i.id  desc ");
		if(p!=null) {
			return pageByHql(hql.toString(), true, p,param.toArray());
		}else {
			return findByHql(hql.toString(), true,param.toArray());
		}
	}
	
	public Images findByImagePath(String path){
		StringBuffer hql=new StringBuffer(" from Images i where 1=1 ");
		List<Object> param=new ArrayList<Object>();
		if(StringUtils.isNotBlank(path)) {
			hql.append(" and i.imagesPath =? ");
			param.add(path);
			return findByHqlFirst(hql.toString(), true,param.toArray());
		}
		return null;
	}
	
	
	public void saveToFtp(Ftp ftp,Images img) {
		if(img!=null&&ftp!=null) {
    		FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+img.getImagesName(), img.getImagesPath(),ftp);
    		save(img);
    		Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()+img.getFileSize());
			siteService.save(site);
		}
	}

	public void saveToFtp(Ftp ftp,List<Images> img) {
		if(img!=null&&ftp!=null) {
			long size=0;
			for (Images i : img) {
				FtpToolUtils.moveFile(ElianCodes.FTP_TEMP+i.getImagesName(),i.getImagesPath(),ftp);
				save(i);
				size=size+i.getFileSize();
			}
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()+size);
			siteService.save(site);
		}
	}
	
	public void saveQYFYToFtp(Ftp ftp,List<Images> img) {
		if(img!=null&&ftp!=null) {
			long size=0;
			for (Images i : img) {
				FtpToolUtils.moveFile(i.getImportImgpath(),i.getImagesPath(),ftp);
				save(i);
				size=size+i.getFileSize();
			}
			Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()+size);
			siteService.save(site);
		}
	}
	
	public void delFTPImg(Ftp ftp,Images img) {
		if(img!=null&&ftp!=null) {
    		FtpToolUtils.deleteFile(ftp,img.getImagesPath());
    		delete(img);
    		Site site=siteService.get(ApplicationUtils.getSite().getId());
			site.setSiteUsedSize(site.getSiteUsedSize()-img.getFileSize());
			siteService.save(site);
		}
	}
	
	public void delFTPImg(Ftp ftp,List<Images> img) {
		if(CollectionUtils.isEmpty(img))return;
		if(img.size()>0&&ftp!=null) {
			long size=0;
			 String[] delPath=new String[img.size()];
    		for (int i = 0; i < img.size(); i++) {
    			delPath[i]=img.get(i).getImagesPath();
    		}
    		 FtpToolUtils.deleteFile(ftp,delPath);
			for (Images i : img) {
				size=size+i.getFileSize();
				delete(i);
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
