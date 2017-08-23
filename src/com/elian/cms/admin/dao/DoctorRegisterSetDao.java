package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DoctorRegisterSetDao extends Dao<DoctorRegisterSet, Integer> {

	public List<DoctorRegisterSet> findByAll(Integer doctorId, Integer weeks,
			Pagination<DoctorRegisterSet> p);

	public List<DoctorRegisterSet> findForDoctorWork(Integer weeks);
}
