package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserTypeDao;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("userTypeDao")
public class UserTypeDaoImpl extends DaoImpl<UserType, Integer> implements
		UserTypeDao {

	public List<UserType> findUserTypeByAll() {
		String hql = " from UserType ";
		return findByHql(hql, false, (Object[]) null);
	}
	
	public List<UserType> findUserTypeByAll(boolean disable){
		String hql = " from UserType u  where u.disable=?";
		return findByHql(hql, false,disable);
	}

	public List<UserType> findUserTypeList(Pagination<UserType> userTypeP) {
		String hql = "from UserType u where 1=1  order by u.typeSort asc,u.id desc";
		if (userTypeP != null) {
			return pageByHql(hql, false, userTypeP, (Object[]) null);
		}
		else {
			return findByHql(hql.toString(), false, (Object[]) null);
		}
	}
}
