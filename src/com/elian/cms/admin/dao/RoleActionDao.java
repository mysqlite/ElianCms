package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface RoleActionDao extends Dao<RoleAction, Integer> {
	public List<RoleAction> findByAll(Integer roleId,
			Pagination<RoleAction> pagination);
	
	public List<RoleAction> findByRole(List<Integer> roleIdList);
	
	public List<RoleAction> findBySiteId(Integer siteId);
}
