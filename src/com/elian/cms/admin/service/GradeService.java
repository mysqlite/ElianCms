package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.GradeDao;
import com.elian.cms.admin.model.Grade;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface GradeService extends Service<GradeDao, Grade, Integer> {
	public List<Grade> findByAll(Pagination<Grade> pagination);

	public List<Grade> findByComType(String comType,Pagination<Grade> pagination);

	public List<Grade> findById(Integer gradeId, boolean existTemplate,
			Pagination<Grade> pagination);
	
	public List<Grade> findByComType(String comType,Boolean disable,Boolean isdefault);
}
