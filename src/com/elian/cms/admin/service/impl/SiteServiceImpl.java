package com.elian.cms.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteDao;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("siteService")
public class SiteServiceImpl extends ServiceImpl<SiteDao, Site, Integer>
		implements SiteService {
	public List<Site> findByAll(Pagination<Site> pagination) {
		return dao.findByAll(pagination);
	}

	public List<Site> findByAll(Boolean existAction, Pagination<Site> pagination) {
		return dao.findByAll(existAction, pagination);
	}

	public List<Site> findByUser(Integer userId) {
		return dao.findByUser(userId);
	}

	public Site getSiteByCompType(String compType) {
		return dao.getSiteByCompType(compType);
	}
	
	public List<Site> findAllSiteByCompType(Pagination<Site> p, String compType) {
		return dao.findAllSiteByCompType(p, compType);
	}

	public List<Site> findCompSiteByArea(Pagination<Site> p, Integer areaId) {
		return dao.findCompSiteByArea(p, areaId);
	}

	public List<Site> findHospSiteByArea(Pagination<Site> p, List<Integer> areaIds) {
		return dao.findHospSiteByAreaCode(p, areaIds);
	}
	public long findImgSize(Integer siteId) {
		return dao.findImgSize(siteId);
	}
	
	public Set<String> getDomainSet(){
		List<Site> site=dao.findByAll(null,null);
		Set<String> siteDomain=new HashSet<String>();
		for (Site s : site) {
			siteDomain.add(s.getDomain());
		}
		return siteDomain;
	}
	
	public List<Site> findSubSiteByArea(Integer areaId) {
		return dao.findSubSiteByArea(areaId);
	}
	
	@Resource
	public void setDao(SiteDao dao) {
		this.dao = dao;
	}

	public Site findByByComp(String compType, Integer compId) {
		return dao.findByByComp(compType,compId);
	}
}
