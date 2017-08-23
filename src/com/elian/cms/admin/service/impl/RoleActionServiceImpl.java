package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.RoleActionDao;
import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.admin.service.RoleActionService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("roleActionService")
public class RoleActionServiceImpl extends
		ServiceImpl<RoleActionDao, RoleAction, Integer> implements
		RoleActionService {
	public List<RoleAction> findByAll(Integer roleId, Pagination<RoleAction> p) {
		return dao.findByAll(roleId, p);
	}

	public List<RoleAction> findByRole(List<Integer> roleIdList) {
		return dao.findByRole(roleIdList);
	}

	public List<RoleAction> findBySiteId(Integer siteId){
		return dao.findBySiteId(siteId);
	}
	@Resource
	public void setDao(RoleActionDao dao) {
		this.dao = dao;
	}
}
