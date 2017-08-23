package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.UserTypeDao;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface UserTypeService extends
		Service<UserTypeDao, UserType, Integer> {
	public List<UserType> findUserTypeByAll();

	public List<UserType> findUserTypes(Pagination<UserType> p);

	public List<Map<String, Object>> findAjaxUserTypeByAll() ;
}
