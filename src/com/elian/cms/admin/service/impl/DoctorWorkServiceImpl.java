package com.elian.cms.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctorWorkDao;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("doctorWorkService")
public class DoctorWorkServiceImpl extends
		ServiceImpl<DoctorWorkDao, DoctorWork, Integer> implements
		DoctorWorkService {

	public List<DoctorWork> findByAll(Integer doctorId, Pagination<DoctorWork> p) {
		return dao.findByAll(doctorId, p);
	}

	public List<DoctorWork> findAllDoctorWork(Integer doctorId,
			String startTime, String endTime, Integer rank) {
		return dao.findAllDoctorWork(doctorId, startTime, endTime, rank);
	}

	public List<DoctorWork> findConfirmDoctorWork(Integer doctorId, int rank,
			String startTime, String endTime) {
		return dao.findConfirmDoctorWork(doctorId, rank, startTime, endTime);
	}

	@Autowired
	public void setDao(DoctorWorkDao dao) {
		this.dao = dao;
	}

	public List<DoctorWork> getTingZhenList(Integer size,Integer areaId, Date startTime,
			Date endTime) {
		return dao.getTingZhenList(size,areaId,startTime,endTime);
	}
	
	public Integer findDoctorOverScore(Integer doctorId,String startTime, String endTime) {
		return dao.findDoctorOverScore(doctorId, startTime, endTime);
	}
}
