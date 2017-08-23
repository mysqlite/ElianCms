package com.elian.cms.admin.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DeptTypeDao;
import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("deptTypeService")
public class DeptTypeServiceImpl extends
		ServiceImpl<DeptTypeDao, DeptType, Integer> implements DeptTypeService {
	@Resource
	public void setDao(DeptTypeDao dao) {
		this.dao = dao;
	}

	public List<DeptType> findByAll(String type, boolean disable,
			Pagination<DeptType> p) {
		return dao.findByAll(type, disable, p);
	}

	public List<DeptType> findParentBySubId(Integer deptTypeId) {
		return dao.findParentBySubId(deptTypeId);
	}

	public DeptType getByName(String name) {

		return dao.getByName(name);
	}

	public List<DeptType> findParentBySubsId(Set<Integer> departmentSet) {
		if(CollectionUtils.isEmpty(departmentSet)) return null;
		return dao.findParentBySubsId(departmentSet);
	}
	
	public List<DeptType> findInstrumentType(String classType,Integer compId){
		return dao.findInstrumentType(classType, compId);
	}
}
