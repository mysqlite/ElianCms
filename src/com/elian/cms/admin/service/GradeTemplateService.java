package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.GradeTemplateDao;
import com.elian.cms.admin.model.GradeTemplate;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface GradeTemplateService extends
		Service<GradeTemplateDao, GradeTemplate, Integer> {
	public List<GradeTemplate> findByAll(Integer gradeId,
			Pagination<GradeTemplate> p);

	public List<GradeTemplate> findByGradeIds(List<Integer> gradeIdList);
}
