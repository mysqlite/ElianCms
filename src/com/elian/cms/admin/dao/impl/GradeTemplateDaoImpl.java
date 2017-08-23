package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.GradeTemplateDao;
import com.elian.cms.admin.model.GradeTemplate;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("gradeTemplateDao")
public class GradeTemplateDaoImpl extends DaoImpl<GradeTemplate, Integer>
		implements GradeTemplateDao {
	public List<GradeTemplate> findByAll(Integer gradeId,
			Pagination<GradeTemplate> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from GradeTemplate gt where 1=1");
		if (gradeId != null && gradeId > 0) {
			hql.append(" and gt.grade.id = ? ");
			params.add(gradeId);
		}
		if (p != null) {
			p.setAlias("gt");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}

	public List<GradeTemplate> findByGradeIds(List<Integer> gradeIdList) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from GradeTemplate r where 1=1");
		if (!CollectionUtils.isEmpty(gradeIdList)) {
			hql.append(" and r.grade.id in ( ");
			int i = 0;
			for (Integer id : gradeIdList) {
				if (i != 0) {
					hql.append(" , ");
				}
				hql.append(" ? ");
				params.add(id);
				i++;
			}
			hql.append(" )");
		}
		return findByHql(hql.toString(), true, params.toArray());
	}
}
