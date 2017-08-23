package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.SiteIncludeDao;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface SiteIncludeService extends 
	Service<SiteIncludeDao, SiteInclude, Integer> {
	
	public SiteInclude getBySiteId(Integer siteId);

	public List<SiteInclude> getPageBySiteId(Pagination<SiteInclude> pagination, Integer siteId);

	public void deleteBySiteId(Integer siteId);
}
