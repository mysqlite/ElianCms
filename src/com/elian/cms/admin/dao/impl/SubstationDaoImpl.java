package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SubstationDao;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("substationDao")
public class SubstationDaoImpl extends DaoImpl<Substation, Integer> implements
		SubstationDao {
	public List<Substation> findByPaging(Pagination<Substation> p) {
		String hql = "from Substation s ";
		if (p != null) {
			return pageByHql(hql, false, p);
		}
		else {
			return findByHql(hql, false);
		}

	}
	
	public Substation findSubstationByAreaCode(Integer areaCode) {
		String hql = "from Substation s  ";
		if(areaCode!=null) {
			hql+="  where s.areaId=? ";
			return findByHqlFirst(hql, true, areaCode);
		}else {
			return null;
		}
	}

}
