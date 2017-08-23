package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface SiteIncludeDao extends Dao<SiteInclude, Integer> {

	SiteInclude getBySiteId(Integer siteId);

	List<SiteInclude> getPageBySiteId(Pagination<SiteInclude> pagination, Integer siteId);

}
