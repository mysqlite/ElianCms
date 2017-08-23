package com.elian.cms.syst.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.service.DoctorRegisterSetService;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 医生自动排班定时服务
 * 
 * @author Joe
 */
public class DoctorWorkJob implements Job {
	private final static Log logger = LogFactory.getLog(DoctorWorkJob.class);
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		long startTime = System.currentTimeMillis();
		logger.error("DoctorWorkJob is running...");

		Calendar c = getNextWeekToday();

		DoctorRegisterSetService drsService = SpringUtils
				.getBean("doctorRegisterSetService");
		List<DoctorRegisterSet> drsList = drsService
				.findForDoctorWork(getWeeks(c));
		if (CollectionUtils.isEmpty(drsList))
			return;
		insertDwData(c, drsList);

		logger.error("DoctorWorkJob is completed,use time:"
				+ ((double) (System.currentTimeMillis() - startTime)) / 1000
				/ 60 + " minute");
	}

	public void insertDwData(Calendar c, List<DoctorRegisterSet> drsList) {
		List<DoctorWork> dwList = new ArrayList<DoctorWork>();
		for (DoctorRegisterSet drs : drsList) {
			Date startTime = getSpiltDate(c, drs.getStartTime());
			Date endTime = getSpiltDate(c, drs.getEndTime());
			int[] count = { 0 };
			carculateCount(drs, startTime, endTime, count);
			addDoctorWorkInList(dwList, drs, startTime, endTime, drs
					.getNoSource()
					/ count[0]);
		}
		DoctorWorkService dwService = SpringUtils.getBean("doctorWorkService");
		dwService.save(dwList);
	}

	public void carculateCount(DoctorRegisterSet drs, Date startTime,
			Date endTime, int[] count) {
		if (startTime.getTime() >= endTime.getTime())
			return;
		count[0]++;
		Date stepTime = getStepDate(startTime, drs.getStep());
		if (!(stepTime.getTime() > endTime.getTime())) {
			carculateCount(drs, stepTime, endTime, count);
		}
	}

	public void addDoctorWorkInList(List<DoctorWork> dwList,
			DoctorRegisterSet drs, Date startTime, Date endTime, int nosource) {
		if (startTime.getTime() >= endTime.getTime())
			return;
		Date stepTime = getStepDate(startTime, drs.getStep());
		if (stepTime.getTime() > endTime.getTime()) {
			dwList.add(createDw(drs, startTime, endTime, nosource));
		}
		else {
			dwList.add(createDw(drs, startTime, stepTime, nosource));
			addDoctorWorkInList(dwList, drs, stepTime, endTime, nosource);
		}
	}

	/**
	 * 获取分解步长时间
	 */
	public Date getStepDate(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MINUTE);
		c.set(Calendar.MINUTE, m + minute);
		return c.getTime();
	}

	/**
	 * 获取分解后的时间,例：17:35
	 */
	public Date getSpiltDate(Calendar c, String startTime) {
		String[] dates = startTime.split(":");
		int hour = Integer.valueOf(dates[0]);
		int minute = Integer.valueOf(dates[1]);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		return c.getTime();
	}

	/** 获取下一个星期的今天 */
	private Calendar getNextWeekToday() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 7);
		return c;
	}

	/** 获取星期几 */
	private int getWeeks(Calendar c) {
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 创建医生排班实体
	 */
	public DoctorWork createDw(DoctorRegisterSet drs, Date startTime,
			Date endTime, int nosource) {
		DoctorWork dw = new DoctorWork();
		dw.setStartTime(startTime);
		dw.setEndTime(endTime);
		dw.setNoSource(nosource);
		dw.setCreateTime(new Date());
		BeanUtils.copyProperties(drs, dw, new String[] { "id", "version",
				"startTime", "endTime", "creater", "createTime", "noSource" });
		return dw;
	}
}