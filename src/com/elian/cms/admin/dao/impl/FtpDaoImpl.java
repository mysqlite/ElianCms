package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.FtpDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;
@Component("ftpDao")
public class FtpDaoImpl extends DaoImpl<Ftp, Integer> implements FtpDao {
	public List<Ftp> findByAll(Pagination<Ftp> p) {
		String hql = "from Ftp g order by g.ftpSort asc,g.id desc";
		if (p != null) {
			p.setAlias("g");
			return pageByHql(hql, false, p);
		}
		else {
			return findByHql(hql, false);
		}
	}
	public List<Ftp> findByTypeIdOrTypeName(Integer typeId,String typeName){
		String hql="from Ftp f where 1=1 ";
		List<Object> param=new ArrayList<Object>();
		if(typeId!=null) {
			hql+=" and f.type.id=? and default=true ";
			param.add(typeId);
		}
		if(StringUtils.isNotBlank(typeName)) {
			hql+=" and f.type.typeName like '%"+ValidateUtils.isSql(typeName)+"%'  and f.default=true  and f.disable=true ";
		}
		hql+="  order by f.ftpSort,f.id desc ";
		return findByHql(hql, true, param.toArray());
	}
	
}
