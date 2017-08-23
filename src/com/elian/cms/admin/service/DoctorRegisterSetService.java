package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.DoctorRegisterSetDao;
import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DoctorRegisterSetService extends
		Service<DoctorRegisterSetDao, DoctorRegisterSet, Integer> {

	public List<DoctorRegisterSet> findByAll(Integer doctorId, Integer weeks,
			Pagination<DoctorRegisterSet> p);
	
	public List<DoctorRegisterSet> findForDoctorWork(Integer weeks);
}
