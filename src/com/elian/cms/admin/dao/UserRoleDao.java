package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface UserRoleDao extends Dao<UserRole, Integer> {
	public List<UserRole> findByPagination(Pagination<SiteUser> p);
	
	public UserRole findByUserId(Integer userId);
	
	public List<UserRole> findUserRoleByUserId(Integer userId);
	
	public List<UserRole> findBySiteId(Integer siteId);
}
