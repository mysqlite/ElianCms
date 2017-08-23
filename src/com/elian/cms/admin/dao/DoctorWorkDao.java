package com.elian.cms.admin.dao;

import java.util.Date;
import java.util.List;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DoctorWorkDao extends Dao<DoctorWork, Integer> {

	public List<DoctorWork> findByAll(Integer doctorId,
			Pagination<DoctorWork> p);

	public List<DoctorWork> getTingZhenList(Integer size, Integer areaId,  Date startTime,
			Date endTime);
	
	public List<DoctorWork> findAllDoctorWork(Integer doctorId,
			String startTime, String endTime, Integer rank);
	
	public List<DoctorWork> findConfirmDoctorWork(Integer doctorId, int rank,
			String startTime, String endTime);
	
	public Integer findDoctorOverScore(Integer doctorId,String startTime, String endTime);
}
