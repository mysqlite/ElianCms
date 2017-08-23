package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ActionDao;
import com.elian.cms.admin.model.Action;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;
@Component("actionDao")
public class ActionDaoImpl extends DaoImpl<Action, Integer> implements
		ActionDao {
	public List<Action> findByAll(Integer depth, Pagination<Action> p) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from Action a where 1=1");
		if (depth != null) {
			hql.append(" and a.depth = ? ");
			params.add(depth);
		}
		hql.append(" order by a.parentId asc,a.actionSort asc, a.id desc");
		if (p != null) {
			p.setAlias("a");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<Action> findByAll(boolean disable) {
		String hql=" from Action a where a.disable=true";
		return findByHql(hql.toString(), true); 
	}
	
	public List<Action> findByCompType(String compType, boolean isMainStation) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		if (isMainStation) {
			hql.append(" SELECT a from Action a");
			hql.append("         WHERE 1 = 1  ");
		}
		else {
			hql.append(" SELECT DISTINCT a from Action a, RoleAction ra");
			hql.append("         WHERE a.id = ra.action.id  ");
			hql.append("           AND ra.role.default = true  ");
			if (StringUtils.isNotBlank(compType)) {
				hql.append("           AND ra.role.compType = ? ");
				params.add(compType);
			}
		}
		hql.append("           AND a.disable = true  ");
		hql.append(" order by a.parentId asc, a.id desc");

		return findByHql(hql.toString(), true, params.toArray());
	}

	public List<Action> findByUser(Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT A.* ");
		sql.append("   FROM T_ACTION A ");
		if (userId != null) {
			sql.append("        INNER JOIN RE_ROLE_ACTION RER ON A.ACTION_ID = RER.ACTION_ID ");
			sql.append("        INNER JOIN Re_User_Role rur on rur.role_id = rer.role_id ");
			sql.append("  WHERE A.IS_DISABLE = 1 ");
			sql.append("    AND Rur.USER_ID = ? ");
			params.add(userId);
		}
		return findBySql(sql.toString(), false, params.toArray());
	}

	public Action getActionByActionName(String ActionName,String actionUrl) {
		if(StringUtils.isBlank(actionUrl)&&StringUtils.isBlank(ActionName))
			return null;
		String hql = "from Action a where 1=1  ";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotBlank(ActionName)) {
			hql+=" and actionName=? ";
			params.add(ActionName);
		}
		if(StringUtils.isNotBlank(actionUrl)) {
			hql+=" and actionUrl=? ";
			params.add(actionUrl);
		}
		List<Action> actions=findByHql(hql, false, params.toArray());
		if(actions!=null&&actions.size()>0)
			return actions.get(0);
		return null;
	}
}
