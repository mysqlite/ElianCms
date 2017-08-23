package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SiteFlowDao extends Dao<SiteFlow, Integer> {

	SiteFlow findUniqueByProperties(Integer siteId, String accessDate,
			String sessionId, String page);
	
	public List<SiteFlow> findByPage(Pagination<SiteFlow> p, Integer siteId);
}
