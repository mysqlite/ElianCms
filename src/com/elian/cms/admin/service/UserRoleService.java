package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.UserRoleDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface UserRoleService extends Service<UserRoleDao, UserRole, Integer> {
	public List<UserRole> findByPagination(Pagination<SiteUser> p);
	
	public UserRole findByUserId(Integer userId);
	
	public List<UserRole> findUserRoleByUserId(Integer userId);
	
	public List<UserRole> findBySiteId(Integer siteId);
	
}
