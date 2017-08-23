package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ContacterDao;
import com.elian.cms.admin.model.Contacter;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("contacterDao")
public class ContacterDaoImpl extends DaoImpl<Contacter, Integer> implements
		ContacterDao {
	public List<Contacter> findByPage(Pagination<Contacter> p,Site site){
		StringBuffer hql=new StringBuffer( "from Contacter c where 1=1 ");
		List<Object> param=new ArrayList<Object>();
		if(site!=null) {
			hql.append(" and c.site.id=? ");
			param.add(site.getId());
		}
		hql.append("  order by c.contactName asc ");
		if(p!=null)
			return pageByHql(hql.toString(), true, p,param.toArray());
		else
			return findByHql(hql.toString(), true,param.toArray());
	}
}
