package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.RoleActionDao;
import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("roleActionDao")
public class RoleActionDaoImpl extends DaoImpl<RoleAction, Integer> implements
		RoleActionDao {
	public List<RoleAction> findByAll(Integer roleId, Pagination<RoleAction> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from RoleAction r where 1=1");
		if (roleId != null && roleId > 0) {
			hql.append(" and r.role.id = ? ");
			params.add(roleId);
		}
		hql.append(" order by r.id desc");
		if (p != null) {
			p.setAlias("r");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<RoleAction> findByRole(List<Integer> roleIdList) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from RoleAction r where 1=1");
		if (!CollectionUtils.isEmpty(roleIdList)) {
			hql.append(" and r.role.id in ( ");
			int i = 0;
			for (Integer id : roleIdList) {
				if (i != 0) {
					hql.append(" , ");
				}
				hql.append(" ? ");
				params.add(id);
				i++;
			}
			hql.append(" )");
		}
		return findByHql(hql.toString(), true, params.toArray());
	}
	public List<RoleAction> findBySiteId(Integer siteId){
		String hql="  from RoleAction re where exists(select 1 from Role as r where re.role.id = r.id and r.site.id=?) ";
		if(siteId!=null) 
			return findByHql(hql, false, siteId);
		else 
			return null;
	}
	
}
