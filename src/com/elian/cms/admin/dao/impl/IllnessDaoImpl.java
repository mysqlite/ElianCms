package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.IllnessDao;
import com.elian.cms.admin.model.Illness;
import com.elian.cms.syst.dao.impl.DaoImpl;

@Component("illnessDao")
public class IllnessDaoImpl extends DaoImpl<Illness, Integer>
	implements IllnessDao {

	public List<Illness> getByIds(String illLink) {
		String hql="select new com.elian.cms.admin.model.Illness(i.id,i.illnessName,i.department,i.often,i.notOften) from Illness i where i.id in("+illLink+")";
		return findByHql(hql, false);
	}

	public List<Illness> getByDepartmentName(String departmentName, boolean isEqueal) {
		String hql="select new com.elian.cms.admin.model.Illness(i.id,i.illnessName,i.department,i.often,i.notOften) from Illness i where i.department like '%"+departmentName+"'";
		if(isEqueal)
			hql="select new com.elian.cms.admin.model.Illness(i.id,i.illnessName,i.department,i.often,i.notOften) from Illness i where i.department = '?'";
		return findByHql(hql, false);
	}
	
}