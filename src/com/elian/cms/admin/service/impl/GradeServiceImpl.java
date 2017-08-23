package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.GradeDao;
import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("gradeService")
public class GradeServiceImpl extends ServiceImpl<GradeDao, Grade, Integer>
		implements GradeService {
	public List<Grade> findByAll(Pagination<Grade> pagination) {
		return dao.findByAll(pagination);
	}
	
	public List<Grade> findByComType(String comType,Pagination<Grade> pagination) {
		return dao.findByComType(comType, pagination);
	}

	public List<Grade> findById(Integer gradeId, boolean existTemplate,
			Pagination<Grade> pagination) {
		return dao.findById(gradeId, existTemplate, pagination);
	}
	public List<Grade> findByComType(String comType,Boolean disable,Boolean isdefault){
		return dao.findByComType(comType,disable,isdefault);
	}

	@Resource
	public void setDao(GradeDao dao) {
		this.dao = dao;
	}
}
