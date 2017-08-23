package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ModelDao;
import com.elian.cms.admin.model.Model;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
@Component("modelDao")
public class ModelDaoImpl extends DaoImpl<Model, Integer> implements ModelDao {
	public List<Model> findByAll(Pagination<Model> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Model r where 1=1");
		hql.append(" order by r.modelSort asc,r.id desc");
		if (p != null) {
			p.setAlias("r");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	
	/**
	 * ChannelAction用于查询模型
	 */
	public List<Model> findByComType(String comType, Pagination<Model> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Model r where 1=1 and r.disable = true");
		if (StringUtils.isNotBlank(comType)) {
			hql.append(" and (r.compType = '" + ElianUtils.COMP_TYPE_PUBL
					+ "' or r.compType = ?)");
			params.add(comType);
		}
		hql.append(" order by r.modelSort asc,r.id desc");
		if (p != null) {
			p.setAlias("r");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
}
