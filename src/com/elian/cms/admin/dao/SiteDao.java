package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SiteDao extends Dao<Site, Integer> {
	public List<Site> findByAll(Pagination<Site> pagination);
	
	public List<Site> findByAll(Boolean existAction, Pagination<Site> pagination);
	
	public List<Site> findByUser(Integer userId);
	
	public Site getSiteByCompType(String compType);
	
	public List<Site> findAllSiteByCompType(Pagination<Site> p,String compType);
	
	public List<Site> findCompSiteByArea(Pagination<Site> p,Integer areaId);
	
	public List<Site> findHospSiteByAreaCode(Pagination<Site> p,List<Integer> areaCodes);
	
	public List<Site> findSubSiteByArea(Integer areaId);

	public Site findByByComp(String compType, Integer compId);
	
	public long findImgSize(Integer siteId);
}
