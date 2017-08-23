package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.RoleActionDao;
import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface RoleActionService extends
		Service<RoleActionDao, RoleAction, Integer> {
	public List<RoleAction> findByAll(Integer roleId,
			Pagination<RoleAction> pagination);

	public List<RoleAction> findByRole(List<Integer> roleIdList);

	public List<RoleAction> findBySiteId(Integer siteId);
}
