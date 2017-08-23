package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SiteUserDao extends Dao<SiteUser, Integer> {
	public List<SiteUser> findByPagination(Pagination<SiteUser> p);

	public SiteUser findByUserId(Integer userId);

	public List<SiteUser> findAuditSiteBypagination(Pagination<SiteUser> p,Integer status,String comType);

	public List<SiteUser> findSiteUserBySite(Integer siteId);
	
	public List<SiteUser> findAuditSiteBySubArea(Pagination<SiteUser> p,List<Integer> areaCodes,Integer status,String comType);
}
