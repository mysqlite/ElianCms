package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.UserRegisterDao;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface UserRegisterService extends
		Service<UserRegisterDao, UserRegister, Integer> {
	
	public List<UserRegister> findByAll(Pagination<UserRegister> p,
			Integer doctorWorkId, Integer userId);
	
	public List<UserRegister> findByDoctorId(Integer doctorId,
			Integer doctorWorkId, Integer userId);
}
