package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteUserDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;
@Component("siteUserDao")
public class SiteUserDaoImpl extends DaoImpl<SiteUser, Integer> implements
		SiteUserDao {
	public List<SiteUser> findByPagination(Pagination<SiteUser> p) {
		String hql = "from SiteUser s";
		if (null != p)
			return pageByHql(hql, false, p);
		else
			return findByHql(hql, false);
	}
	
	public List<SiteUser> findAuditSiteBypagination(Pagination<SiteUser> p,Integer status,String comType){
		String hql="from SiteUser s where 1=1  ";
		List<Object> param=new ArrayList<Object>();
		if(StringUtils.isNotBlank(comType)) {
			hql+=" and s.site.comType=?";
			param.add(comType);
		}
		if(status!=null) {
			hql+=" and  s.site.status=? and  s.user.status=? and s.user.proposer=true ";
			param.add(status);
			param.add(status);
		}
		hql+="  order by s.site.createTime desc";
		if (null != p)
			return pageByHql(hql, false, p,param.toArray());
		else
			return findByHql(hql, false,param.toArray());
	}
	
public List<SiteUser> findAuditSiteBySubArea(Pagination<SiteUser> p,List<Integer> areaCodes,Integer status,String comType){
		StringBuffer hql=new  StringBuffer(" from SiteUser su where  su.site.id in  (");
						hql.append("select s.id from Site s where  s.comId in ( select h.id  from Hospital h where  h.areaId in( "); 
						for (int i = 0,len=areaCodes.size(); i <len ; i++) {
							if(i>0&&i<len) {
								hql.append(",");
							}
							hql.append(areaCodes.get(i));
						}
					    hql.append(" ))) " );
				hql.append(" and  su.site.status=? and su.user.status=? and su.user.proposer=true ");
		List<Object> param=new ArrayList<Object>();
		param.add(status);
		param.add(status);
	    if(p!=null) {
	    	return pageByHql(hql.toString(), true, p, param.toArray());
	    }
		return findByHql(hql.toString(), true, param.toArray());
	}
	
	public List<SiteUser> findSiteUserBySite(Integer siteId){
		String hql="from SiteUser s where  s.site.id=? ";
		if(siteId!=null) {
			return findByHql(hql, false,siteId);
		}
		else {
			return null;
		}
	}
	
	public SiteUser findByUserId(Integer userId) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from SiteUser su where 1=1");
		if (userId != null && userId > 0) {
			hql.append(" and su.user.id = ? ");
			params.add(userId);
		}
		return findByHqlFirst(hql.toString(), true, params.toArray());
	}
}
