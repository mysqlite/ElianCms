package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;

@Component
public class RegSideBarData {

	DoctorWorkService doctorWorkService = null;

	private HospitalService hospitalService;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public List<DoctorWork> getTingZhenList(Integer areaId) {
		/*
		 * Calendar Start=StringUtils.getCNWeekStatr(); Date
		 * weekStart=Start.getTime(); Date
		 * weekEnd=StringUtils.getCNWeekEnd(Start).getTime();
		 */
		return doctorWorkService.getTingZhenList(10, areaId, null, null);
	}

	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	/** 查询当前七天后的医院放号排行 */
	public List<HospitalNoScore> getHospNoScores() {
		return hospitalService.findHospNoScore(8, null, null, 7);
	}

	/** 查询本周医生、科室挂号排名 */
	public List<WorkTopDeparment> getWorkTopDeparments() {
		// return hospitalService.findTopWorkDoctor(8,6,
		// sf.format(StringUtils.getCNWeekStatr().getTime()),
		// sf.format(StringUtils.getCNWeekEnd(StringUtils.getCNWeekStatr()).getTime()));
		return hospitalService.findTopWorkDoctor(8, 6, "2010-01-01 00:00:00",
				sf.format(new Date()));
	}
}
