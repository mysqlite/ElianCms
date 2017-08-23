package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.RoleDao;
import com.elian.cms.admin.model.Role;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;

@Component("roleDao")
public class RoleDaoImpl extends DaoImpl<Role, Integer> implements RoleDao {
	// public List<Role> findByAll(Integer roleId, Boolean existAction,
	// Pagination<Role> p) {
	// StringBuffer hql = new StringBuffer();
	// List<Object> params = new ArrayList<Object>();
	// hql.append(" from Role r where 1=1");
	// if (roleId != null && roleId > 0) {
	// hql.append(" and r.id = ? ");
	// params.add(roleId);
	// }
	// if (existAction != null) {
	// if (existAction.equals(true)) {
	// hql.append(" and exists ( select 1 from RoleAction re where re.role.id = r.id )");
	// }
	// else if (existAction.equals(false)) {
	// hql.append(" and not exists ( select 1 from RoleAction re where re.role.id = r.id )");
	// }
	// }
	// hql.append(" order by r.roleSort asc,r.id desc");
	// if (p != null) {
	// p.setAlias("r");
	// return pageByHql(hql.toString(), true, p, params.toArray());
	// }
	// else
	// return findByHql(hql.toString(), true, params.toArray());
	// }

	public List<Role> findByAll(Integer siteId, boolean disable,
			Pagination<Role> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Role r where 1=1");
		if (siteId != null && siteId > 0) {
			hql.append(" and r.site.id = ? ");
			params.add(siteId);
		}
		if (disable) {
			hql.append(" and r.disable = true ");
		}
		hql.append(" order by r.roleSort asc,r.id desc");
		if (p != null) {
			p.setAlias("r");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}
	
	public List<Role> findByAll(Integer siteId, boolean isDefault) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Role r where disable=true ");
		if (siteId != null && siteId > 0) {
			hql.append(" and r.site.id = ? ");
			params.add(siteId);
		}
		if(isDefault) {
		hql.append(" and r.default = true");
		}
		hql.append(" order by r.roleSort asc,r.id desc");
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<Role> findByCompType(String compType) {
		StringBuffer hql = new StringBuffer("from Role r where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (!StringUtils.isBlank(compType)) {
			hql.append("  and r.compType=?");
			params.add(compType);
		}
		return findByHql(hql.toString(), true, params.toArray());
	}

	public Role getRoleByComType(String compType, boolean isDefault) {
		StringBuffer hql = new StringBuffer("from Role r  ");
		List<Object> params = new ArrayList<Object>();
		hql.append("where r.default=?");
		params.add(isDefault);
		if (!StringUtils.isBlank(compType)) {
			hql.append("  and r.compType=?");
			params.add(compType);
		}
		return findByHql(hql.toString(), true, params.toArray()).get(0);
	}

	public Role getRoleByRoleName(String roleName) {
		String hql = "from Role r where r.roleName=?";
		return findByHqlFirst(hql, true, roleName);
	}
}
