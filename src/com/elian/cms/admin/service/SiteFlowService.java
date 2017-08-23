package com.elian.cms.admin.service;

import java.util.List;

import net.sf.ehcache.Ehcache;

import com.elian.cms.admin.dao.SiteFlowDao;
import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SiteFlowService extends Service<SiteFlowDao, SiteFlow, Integer>{
	public SiteFlow save(Integer siteId,String ip, String page, String sessionId);
	
	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String page);

	public int freshCacheToDB(Ehcache cache);
	
	public int getSiteFlowCount(Integer siteId);
	
	public List<SiteFlow> findByPage(Pagination<SiteFlow> p, Integer siteId);

	public int getSitePV(Integer siteId,String time);
	
	public int getRefererSite(Integer siteId,String refererSite,String time);
	
	public int getSiteIP(Integer siteId,String time);
	
	public int getSiteUV(Integer siteId,String time) ;
}
