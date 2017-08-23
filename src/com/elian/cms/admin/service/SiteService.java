package com.elian.cms.admin.service;

import java.util.List;
import java.util.Set;

import com.elian.cms.admin.dao.SiteDao;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SiteService extends Service<SiteDao, Site, Integer> {
	public List<Site> findByAll(Pagination<Site> pagination);
	
	public List<Site> findByAll(Boolean existAction, Pagination<Site> pagination);
	
	public List<Site> findByUser(Integer userId);
	
	public Site getSiteByCompType(String compType);
	
	public List<Site> findAllSiteByCompType(Pagination<Site> p,String compType);
	
	public List<Site> findHospSiteByArea(Pagination<Site> p,List<Integer> areaId);
	
	public List<Site> findCompSiteByArea(Pagination<Site> p,Integer areaId);
	
	public Set<String> getDomainSet();
	
	public List<Site> findSubSiteByArea(Integer areaId);
	
	public Site findByByComp(String compType , Integer compId);
	
	public long findImgSize(Integer siteId);
}
