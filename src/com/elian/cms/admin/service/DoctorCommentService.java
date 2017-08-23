package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.DoctorCommentDao;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DoctorCommentService extends Service<DoctorCommentDao, DoctorComment, Integer> {
	public List<DoctorComment> findByPageOrAll(Integer userId,Integer doctorId,Integer registerId,Pagination<DoctorComment> p);

	public List<Doctor> getCommentTopDoctor(int size);
}
