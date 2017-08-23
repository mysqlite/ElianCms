package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DrugCommonDao;
import com.elian.cms.admin.model.DrugCommon;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;

@Component("drugCommonDao")
public class DrugCommonDaoImpl extends DaoImpl<DrugCommon, Integer> implements
		DrugCommonDao {
	public List<DrugCommon> findByAll(String name, String marjorFunction,
			String drugType, Pagination<DrugCommon> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DrugCommon d where 1=1");
		if (StringUtils.isNotBlank(name)) {
			hql.append("  and d.pdtName like '%" + name + "%'");
		}
		if (StringUtils.isNotBlank(marjorFunction)) {
			hql.append("  and d.marjorFunc like '%" + marjorFunction + "%'");
		}
		if (StringUtils.isNotBlank(drugType)) {
			hql.append("  and d.drugType=?");
			params.add(drugType);
		}
		hql.append(" order by d.id desc");
		if (p != null) {
			p.setAlias("d");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
}
