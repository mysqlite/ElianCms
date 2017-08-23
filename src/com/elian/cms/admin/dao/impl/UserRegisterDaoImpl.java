package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserRegisterDao;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("userRegisterDao")
public class UserRegisterDaoImpl extends DaoImpl<UserRegister, Integer>
		implements UserRegisterDao {
	public List<UserRegister> findByAll(Pagination<UserRegister> p,
			Integer doctorWorkId, Integer userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from  UserRegister ur  where 1=1 ");
		if (userId != null && userId > 0) {
			hql.append(" and ur.user.id=? ");
			params.add(userId);
		}
		if (doctorWorkId != null && doctorWorkId > 0) {
			hql.append(" and ur.schedulingId.id=? ");
			params.add(doctorWorkId);
		}
		hql.append(" order by ur.createTime desc,ur.id desc");
		if (p != null) {
			p.setAlias(" ur ");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	public List<UserRegister> findByAll(Integer doctorId,
			Integer doctorWorkId, Integer userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from  UserRegister ur  where 1=1 ");
		if (userId != null && userId > 0) {
			hql.append(" and ur.user.id=? ");
			params.add(userId);
		}
		if (doctorWorkId != null && doctorWorkId > 0) {
			hql.append(" and ur.schedulingId.id=? ");
			params.add(doctorWorkId);
		}
		if(doctorId!=null) {
			hql.append(" and ur.schedulingId.doctor.id=? ");
			params.add(doctorId);
		}
		hql.append(" order by ur.createTime desc,ur.id desc");
	  return findByHql(hql.toString(), false, params.toArray());
	}
}
