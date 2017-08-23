package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.RoleDao;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role, Integer>
		implements RoleService {
	// public List<Role> findByAll(Integer roleId, Boolean existAction,
	// Pagination<Role> p) {
	// // if (p != null
	// // && SearchParamUtils.ROLE_TYPE.equals(p.getConditionName())
	// // && StringUtils.isNotBlank(p.getConditionContent())) {
	// // for (SelectItem item : ElianUtils.getCompTypeList()) {
	// // if (item.getKey().contains(p.getConditionContent())) {
	// // p.setConverterContent(item.getValue().toString());
	// // break;
	// // }
	// // }
	// // }
	// return dao.findByAll(roleId, existAction, p);
	// }

	public List<Role> findByAll(Integer siteId, boolean disable,
			Pagination<Role> p) {
		return dao.findByAll(siteId, disable, p);
	}

	public List<Role> findByAll(Integer siteId, boolean isDefault){
		return dao.findByAll(siteId, isDefault);
	}
	
	public List<Role> findByCompType(String compType) {
		return dao.findByCompType(compType);
	}

	public Role getRoleByComType(String comType, boolean isDefault) {
		return dao.getRoleByComType(comType, isDefault);
	}

	public Role getRoleByRoleName(String roleName) {
		return dao.getRoleByRoleName(roleName);
	}

	@Resource
	public void setDao(RoleDao dao) {
		this.dao = dao;
	}
}
