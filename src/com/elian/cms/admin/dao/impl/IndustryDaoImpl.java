package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.IndustryDao;
import com.elian.cms.admin.model.Industry;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("industryDao")
public class IndustryDaoImpl extends DaoImpl<Industry, Integer> implements IndustryDao {

	public List<Industry> findByAll(Integer siteId, boolean disable,
			Pagination<Industry> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Industry r where 1=1");
		
		hql.append(" order by r.id desc");
		if (p != null) {
			p.setAlias("r");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<Industry> findByName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Industry> findByName(String name) {
		return null;
	}

	public List<Industry> findByIndustry() {

		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"  from Industry t order by t.id  ");	
		return findByHql(hql.toString(), false, params.toArray());
	}

}
