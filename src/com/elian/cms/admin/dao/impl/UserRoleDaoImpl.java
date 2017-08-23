package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserRoleDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("userRoleDao")
public class UserRoleDaoImpl extends DaoImpl<UserRole, Integer> implements
		UserRoleDao {
	public List<UserRole> findByPagination(Pagination<SiteUser> p) {
		String hql = "from UserRole  where 1=1 ";
		if (null != p)
			return pageByHql(hql, false, p);
		else
			return findByHql(hql, false);
	}

	public UserRole findByUserId(Integer userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from UserRole ur where 1=1");
		if (userId != null && userId > 0) {
			hql.append(" and ur.user.id = ? ");
			params.add(userId);
		}
		return findByHqlFirst(hql.toString(), true, params.toArray());
	}
	public List<UserRole> findUserRoleByUserId(Integer userId) {
		String hql = "from UserRole ur where ur.user.id = ? ";
		return findByHql(hql.toString(), true,userId);
	}
	
	public List<UserRole> findBySiteId(Integer siteId){
		String hql="from UserRole ra where  exists(select 1 from Role r where r.id=ra.role.id and r.site.id=?)";
		if(siteId!=null) 
			return findByHql(hql, false, siteId);
		else
			return null;
	}
}
