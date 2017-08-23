package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.GradeDao;
import com.elian.cms.admin.model.Grade;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.StringUtils;
@Component("gradeDao")
public class GradeDaoImpl extends DaoImpl<Grade, Integer> implements GradeDao {
	public List<Grade> findByComType(String comType,Pagination<Grade> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Grade g where 1=1");
		if (comType != null && !comType.equals("") || !comType.equals("mainstation")) {
			hql.append(" and g.comType = ? ");
			params.add(comType);
		}
		hql.append(" and g.disable = true ");
		hql.append(" order by g.gradeSort asc,g.id desc");
		if (p != null) {
			p.setAlias("g");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Grade> findByAll(Pagination<Grade> p) {
		String hql = "from Grade g order by g.gradeSort asc,g.id desc";
		if (p != null) {
			p.setAlias("g");
			return pageByHql(hql, false, p);
		}
		else {
			return findByHql(hql, false);
		}
	}

	public List<Grade> findByComType(String comType,Boolean disable,Boolean isdefault){
		String hql=" from Grade g where 1=1 ";
		List<Object> param=new ArrayList<Object>();
		if(StringUtils.isNotBlank(comType)) {
			hql+=" and g.comType=?  ";
			param.add(comType);
		}
		if(isdefault!=null) {
			hql+="and default=? ";
			param.add(isdefault);
		}
		if(disable!=null) {
			hql+="  and disable=?  ";
			param.add(disable);
		}
	return findByHql(hql, true,param.toArray());
	}
	
	public List<Grade> findById(Integer gradeId, Boolean existTemplate,
			Pagination<Grade> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from Grade g where 1=1");
		if (gradeId != null && gradeId > 0) {
			hql.append(" and g.id = ? ");
			params.add(gradeId);
		}
		if (existTemplate != null) {
			if (existTemplate) {
				hql.append(" and exists ( select 1 from GradeTemplate gt where gt.grade.id = g.id )");
			}
			else if (!existTemplate) {
				hql.append(" and not exists ( select 1 from GradeTemplate gt where gt.grade.id = g.id )");
			}
		}
		hql.append(" and g.disable = true ");
		hql.append(" order by g.gradeSort asc,g.id desc");
		if (p != null) {
			p.setAlias("g");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
}
