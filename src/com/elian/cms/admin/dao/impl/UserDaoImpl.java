package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserDao;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;
@Component("userDao")
public class UserDaoImpl extends DaoImpl<User, Integer> implements UserDao {
	
	public List<User> findByAll(String account, String password,
			Pagination<User> p) {
		StringBuilder hql = new StringBuilder("from User u where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(account)) {
			hql.append(" and u.account = ? ");
			paramList.add(account);
		}
		if (StringUtils.isNotBlank(password)) {
			hql.append(" and u.password = ? ");
			paramList.add(password);
		}
		hql.append(" order by u.account asc,u.password desc ");
		if (p != null)
			return pageByHql(hql.toString(), true, p, paramList.toArray());
		else
			return findByHql(hql.toString(), true, paramList.toArray());
	}

	public List<User> findByAll(Pagination<User> p) {
		return pageByHql("from User u order by u.userType.id,u.status asc,u.id,u.lastLoginTime desc", false,
				p);
	}

	public User findByAccout(String account) {
		StringBuffer hql = new StringBuffer("from User u where account=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(account);
		List<User> users = findByHql(hql.toString(), false, paramList.toArray());
		if (users.size() == 0) {
			return null;
		}
		else {
			return users.get(0);
		}
	}
	private String getRecursionFunction(Integer areaCode) {
		StringBuilder f=new StringBuilder();
		if(areaCode!=null&&areaCode>0) {
		 f.append("with FINDUSERBYAREACODE(AREA_CODE, PARENT_CODE) ");
		 f.append("as ( ");
		 f.append("select AREA_CODE, PARENT_CODE from t_area where AREA_CODE =").append(areaCode).append(" and is_disable=1 union all ");    
		 f.append("select t.AREA_CODE,t.PARENT_CODE from t_area as t inner join FINDUSERBYAREACODE as c on c.AREA_CODE=t.PARENT_CODE "); 
		 f.append(")");
		}
		return f.toString();
	}
	
	public List<User> findBySubstation(Pagination<User> p, Integer areaCode){
		StringBuffer sql=new StringBuffer();
		String function=getRecursionFunction(areaCode);
		sql.append("select u.* from t_user u where exists(");
		sql.append("select 1 from re_site_user su where exists(");
		sql.append(" select 1 from t_site s where exists");
		sql.append(" (");
		sql.append("select 1 from t_hospital h where exists");
		sql.append(" (");
		sql.append("  select 1 from FINDUSERBYAREACODE a where h.area_id=a.area_code");
		sql.append("  )");
		sql.append(" and s.com_id=h.hosp_id");
		sql.append(" )");
		sql.append(" and su.site_id=s.site_id");
		sql.append(")");
		sql.append("and u.user_id=su.user_id");
		sql.append(")");
		if(p!=null) {
			p.setAlias("u");
			return pageByRecursionSql(sql.toString(), false, p, function);
		}
		return findByRecursionSql(sql.toString(), false, function);
	}

	public List<User> findBySiteAndRole(Integer siteId, Integer roleId,
			Pagination<User> p) {
		StringBuilder hql = new StringBuilder("from User u where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if (siteId != null && siteId > 0) {
			hql.append(" and exists ( select 1 from SiteUser su where su.user.id = u.id and su.site.id = ?)");
			paramList.add(siteId);
		}
		if (roleId != null && roleId > 0) {
			hql.append(" and exists ( select 1 from UserRole ur where ur.user.id = u.id and ur.role.id = ?)");
			paramList.add(roleId);
		}
		hql.append(" order by u.account asc,u.password desc ");
		if (p != null)
			return pageByHql(hql.toString(), true, p, paramList.toArray());
		else
			return findByHql(hql.toString(), true, paramList.toArray());
	}
	
	public List<User> findBySiteId(Integer siteId) {
		StringBuilder hql = new StringBuilder("from User u where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if (siteId != null && siteId > 0) {
			hql.append(" and exists ( select 1 from SiteUser su where su.user.id = u.id and su.site.id = ?)");
			paramList.add(siteId);
			return findByHql(hql.toString(), true, paramList.toArray());
		}
		else {
			return null;
		}
	}
}
