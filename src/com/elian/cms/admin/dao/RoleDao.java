package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Role;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface RoleDao extends Dao<Role, Integer> {
	// public List<Role> findByAll(Integer roleId, Boolean existAction,
	// Pagination<Role> p);

	public List<Role> findByAll(Integer siteId, boolean disable,
			Pagination<Role> p);

	public List<Role> findByCompType(String compType);

	public Role getRoleByComType(String comType, boolean isDefault);

	public Role getRoleByRoleName(String roleName);
	
	public List<Role> findByAll(Integer siteId, boolean isDefault);
}
