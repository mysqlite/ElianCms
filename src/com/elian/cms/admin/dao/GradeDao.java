package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Grade;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface GradeDao extends Dao<Grade, Integer> {
	public List<Grade> findByAll(Pagination<Grade> pagination);
	
	public List<Grade> findByComType(String comType,Pagination<Grade> pagination);
	
	public List<Grade> findById(Integer gradeId, Boolean existTemplate,
			Pagination<Grade> pagination);
	
	public List<Grade> findByComType(String comType,Boolean disable,Boolean isdefault);
}
