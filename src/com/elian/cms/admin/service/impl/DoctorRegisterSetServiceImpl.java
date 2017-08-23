package com.elian.cms.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctorRegisterSetDao;
import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.admin.service.DoctorRegisterSetService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("doctorRegisterSetService")
public class DoctorRegisterSetServiceImpl extends
		ServiceImpl<DoctorRegisterSetDao, DoctorRegisterSet, Integer> implements
		DoctorRegisterSetService {

	public List<DoctorRegisterSet> findByAll(Integer doctorId, Integer weeks,
			Pagination<DoctorRegisterSet> p) {
		return dao.findByAll(doctorId, weeks, p);
	}

	public List<DoctorRegisterSet> findForDoctorWork(Integer weeks) {
		return dao.findForDoctorWork(weeks);
	}

	@Autowired
	public void setDao(DoctorRegisterSetDao dao) {
		this.dao = dao;
	}
}
