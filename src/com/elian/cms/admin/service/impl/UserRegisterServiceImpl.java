package com.elian.cms.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserRegisterDao;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("userRegisterService")
public class UserRegisterServiceImpl extends
		ServiceImpl<UserRegisterDao, UserRegister, Integer> implements
		UserRegisterService {

	public List<UserRegister> findByAll(Pagination<UserRegister> p,
			Integer doctorWorkId, Integer userId) {
		return dao.findByAll(p, doctorWorkId, userId);
	}
	
	public List<UserRegister> findByDoctorId(Integer doctorId,
			Integer doctorWorkId, Integer userId){
		return dao.findByAll(doctorId, doctorWorkId, userId);
	}

	@Autowired
	public void setDao(UserRegisterDao dao) {
		this.dao = dao;
	}
}
