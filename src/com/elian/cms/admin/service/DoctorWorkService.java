package com.elian.cms.admin.service;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.dao.DoctorWorkDao;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DoctorWorkService extends
		Service<DoctorWorkDao, DoctorWork, Integer> {
	
	public List<DoctorWork> findByAll(Integer doctorId,
			Pagination<DoctorWork> p);

	/** 获取停诊列表*/
	public List<DoctorWork> getTingZhenList(Integer size, Integer areaId, Date startTime,
			Date endTime);
			
	public List<DoctorWork> findAllDoctorWork(Integer doctorId,
			String startTime, String endTime, Integer rank);
	
	public List<DoctorWork> findConfirmDoctorWork(Integer doctorId, int rank,
			String startTime, String endTime);
	
	public Integer findDoctorOverScore(Integer doctorId,String startTime, String endTime);
}
