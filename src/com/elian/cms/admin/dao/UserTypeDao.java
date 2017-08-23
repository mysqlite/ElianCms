package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.UserType;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface UserTypeDao extends Dao<UserType, Integer> {
	public List<UserType> findUserTypeList(Pagination<UserType> p);

	public List<UserType> findUserTypeByAll();
	
	public List<UserType> findUserTypeByAll(boolean disable);
}
