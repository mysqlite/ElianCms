package com.elian.cms.admin.dao;

import java.util.List;
import java.util.Set;

import com.elian.cms.admin.model.DeptType;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DeptTypeDao extends Dao<DeptType, Integer> {
	public List<DeptType> findByAll(String type, boolean disable,
			Pagination<DeptType> p);

	public List<DeptType> findParentBySubId(Integer deptTypeId);
	
	public DeptType getByName(String name);
	
	public List<DeptType> findParentBySubsId(Set<Integer> departmentSet);
	
	public List<DeptType> findInstrumentType(String classType,Integer compId);
	
}
