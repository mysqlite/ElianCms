package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface UserRegisterDao extends Dao<UserRegister, Integer> {

	public List<UserRegister> findByAll(Pagination<UserRegister> p,
			Integer doctorWorkId, Integer userId);
	
	public List<UserRegister> findByAll(Integer doctorId,
			Integer doctorWorkId, Integer userId);
}
