package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteIncludeDao;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.service.SiteIncludeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("siteIncludeService")
public class SiteIncludeServiceImpl extends ServiceImpl<SiteIncludeDao, SiteInclude, Integer>
		implements SiteIncludeService{
	private JdbcService jdbcService;
	
	@Resource
	public void setDao(SiteIncludeDao dao) {
		this.dao = dao;
	}

	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public SiteInclude getBySiteId(Integer siteId) {
		return dao.getBySiteId(siteId);
	}

	public List<SiteInclude> getPageBySiteId(Pagination<SiteInclude> pagination,
			Integer siteId) {
		return dao.getPageBySiteId(pagination,siteId);		
	}

	public void deleteBySiteId(Integer siteId) {
		String sql="delete from T_SITE_INCLUDE  where site_id=? ";
		jdbcService.executeUpdate(sql, siteId);
	}
}
