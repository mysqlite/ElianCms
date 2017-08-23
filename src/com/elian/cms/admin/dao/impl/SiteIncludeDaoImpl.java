package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteIncludeDao;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("siteIncludeDao")
public class SiteIncludeDaoImpl extends 
	DaoImpl<SiteInclude, Integer> implements SiteIncludeDao {

	public SiteInclude getBySiteId(Integer siteId) {
		String sql="select top 1 * from T_SITE_INCLUDE where site_id=?";
		return findBySqlFirst(sql, false, siteId);
	}

	public List<SiteInclude> getPageBySiteId(Pagination<SiteInclude> pagination,
			Integer siteId) {
		String hql="from SiteInclude s where s.siteId=? ";
		if(null!=pagination)
			return pageByHql(hql, false, pagination, siteId);
		return findByHql(hql, false, siteId);
	}
}
