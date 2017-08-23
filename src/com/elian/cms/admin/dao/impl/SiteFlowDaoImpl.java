package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteFlowDao;
import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("siteFlowDao")
public class SiteFlowDaoImpl extends DaoImpl<SiteFlow, Integer> implements SiteFlowDao {

	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate,
			String sessionId, String page) {
		String sql="select * from T_SITE_FLOW sf where sf.site_id=? and sf.access_date=? and sf.session_id=? and sf.access_page=?";
		List<Object> params=new ArrayList<Object>(4);
		params.add(siteId);
		params.add(accessDate);
		params.add(sessionId);
		params.add(page);
		return findBySqlFirst(sql, false, params.toArray());
	}
	
	public List<SiteFlow> findByPage(Pagination<SiteFlow> p, Integer siteId){
		String hql=" from SiteFlow sf where 1=1 ";
		List<Object> params=new ArrayList<Object>();
		if(siteId!=null) {
			hql+=" and sf.siteId=? ";
			params.add(siteId);
		}
		hql+=" order by id desc";
		if(p!=null) {
			p.setAlias("sf");
			return pageByHql(hql, true,p , params.toArray());
		}
		else 
			return findByHql(hql, true);
	}

}