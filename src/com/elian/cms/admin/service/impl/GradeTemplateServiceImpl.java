package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.GradeTemplateDao;
import com.elian.cms.admin.model.GradeTemplate;
import com.elian.cms.admin.service.GradeTemplateService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("gradeTemplateService")
public class GradeTemplateServiceImpl extends
		ServiceImpl<GradeTemplateDao, GradeTemplate, Integer> implements
		GradeTemplateService {
	public List<GradeTemplate> findByAll(Integer gradeId,
			Pagination<GradeTemplate> p) {
		return dao.findByAll(gradeId, p);
	}

	public List<GradeTemplate> findByGradeIds(List<Integer> gradeIdList) {
		return dao.findByGradeIds(gradeIdList);
	}

	@Resource
	public void setDao(GradeTemplateDao dao) {
		this.dao = dao;
	}
}
