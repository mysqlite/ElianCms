package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.SiteUserDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SiteUserService extends
		Service<SiteUserDao, SiteUser, Integer> {
	public List<SiteUser> findByPagination(Pagination<SiteUser> p);

	public SiteUser findByUserId(Integer userId);

	public List<SiteUser> findAuditSiteBypagination(Pagination<SiteUser> p,Integer status,String comType);

	public List<SiteUser> findSiteUserBySite(Integer siteId);
	
	public List<SiteUser> findAuditSiteBySubArea(Pagination<SiteUser> p,List<Integer> areaCodes,Integer status,String compType);
}
