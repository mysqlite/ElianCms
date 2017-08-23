package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserRoleDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("userRoleService")
public class UserRoleServiceImpl extends
		ServiceImpl<UserRoleDao, UserRole, Integer> implements UserRoleService {
	public List<UserRole> findByPagination(Pagination<SiteUser> p) {
		return dao.findByPagination(p);
	}

	public UserRole findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}
	
	public List<UserRole> findUserRoleByUserId(Integer userId){
		return dao.findUserRoleByUserId(userId);
	}
	public List<UserRole> findBySiteId(Integer siteId){
		return dao.findBySiteId(siteId);
	}

	@Resource
	public void setDao(UserRoleDao dao) {
		this.dao = dao;
	}
}
