package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.GradeTemplate;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface GradeTemplateDao extends Dao<GradeTemplate, Integer> {
	public List<GradeTemplate> findByAll(Integer gradeId,
			Pagination<GradeTemplate> p);

	public List<GradeTemplate> findByGradeIds(List<Integer> gradeIdList);
}
