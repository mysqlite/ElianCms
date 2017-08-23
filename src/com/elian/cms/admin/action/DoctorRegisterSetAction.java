package com.elian.cms.admin.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.admin.service.DoctorRegisterSetService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.job.DoctorWorkJob;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 医生排班设置功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DoctorRegisterSetAction extends BaseAction {
	private static final long serialVersionUID = 2215052343367654483L;

	private Pagination<DoctorRegisterSet> pagination = new Pagination<DoctorRegisterSet>();

	private DoctorRegisterSet doctorRegisterSet;
	private Integer doctorId = Integer.valueOf(0);
	private Integer id = Integer.valueOf(0);

	private boolean isEdit = false;
	private boolean isGenerate = false;

	private DoctorRegisterSetService doctorRegisterSetService;
	private DoctorService doctorService;
	private UserService userService;

	public String list() {
		doctorRegisterSetService.findByAll(doctorId, null, pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			doctorRegisterSet = doctorRegisterSetService.get(id);
		}
		else {
			doctorRegisterSet = createDrs();
		}
		return EDIT;
	}

	public DoctorRegisterSet createDrs() {
		DoctorRegisterSet drs = new DoctorRegisterSet();
		drs.setCreateTime(new Date());
		drs.setCreater(ApplicationUtils.getUser());
		return drs;
	}
	
	/**
	 * 验证设置的排班数据，是否已经有患者挂号
	 */
	public void checkDoctorRegister() {
		Integer doctorId = Integer.valueOf(ApplicationUtils.getRequest()
				.getParameter("doctorId"));
		int rank = Integer.valueOf(ApplicationUtils.getRequest().getParameter(
				"rank"));
		int weeks = Integer.valueOf(ApplicationUtils.getRequest().getParameter(
				"weeks"));
		String dateTime = new SimpleDateFormat("yyyy-MM-dd")
				.format(getWeeksCalendar(weeks).getTime());
		if (getDoctorRegisterLength(dateTime, doctorId, rank) > 0)
			ApplicationUtils.sendMsg("true");
		else
			ApplicationUtils.sendMsg("false");
	}
	
	public int getDoctorRegisterLength(String dateTime, Integer doctorId,
			int rank) {
		JdbcService jdbcService = SpringUtils.getBean("jdbcService");
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT (1) ");
		sql.append("   FROM T_DOCTOR_WORK DW ");
		sql.append("  WHERE     CONVERT (VARCHAR (10), DW.START_TIME, 120) = ? ");
		sql.append("        AND DW.DOCT_ID =? ");
		sql.append("        AND DW.RANK = ? ");
		sql.append("        AND EXISTS ");
		sql.append("               (SELECT 1 ");
		sql.append("                  FROM T_USER_REGISTER UR ");
		sql.append("                 WHERE UR.SCHEDULING_ID = DW.SCHEDULING_ID) ");
		return (Integer) jdbcService.findSqlQuery(sql.toString(), dateTime,
				doctorId, rank).toArray()[0];
	}
	
	public void validateSave() {
		boolean error = false;
		if (ValidateUtils.isBlank(doctorRegisterSet.getStartTime())) {
			this.addFieldError("doctorRegisterSet.startTime", "开始时间不能为空");
			error = true;
		}
		if (ValidateUtils.isBlank(doctorRegisterSet.getEndTime())) {
			this.addFieldError("doctorRegisterSet.endTime", "结束时间不能为空");
			error = true;
		}
		if (doctorRegisterSet.getCycle() == 2) {
			if (doctorRegisterSet.getCloseTime() == null) {
				this.addFieldError("doctorRegisterSet.endTime", "截止时间不能为空");
				error = true;
			}
			else if (doctorRegisterSet.getCloseTime().getTime() <= new Date()
					.getTime()) {
				this.addFieldError("doctorRegisterSet.endTime", "截止时间必须大于当前时间");
				error = true;
			}
		}
		if (error) {
			doctorRegisterSet.setCreater(userService.get(doctorRegisterSet
					.getCreater().getId()));
			isGenerate = false;
		}
	}

	public String save() {
		if (doctorRegisterSet.getCycle() == 0
				|| doctorRegisterSet.getCycle() == 1) {
			doctorRegisterSet.setCloseTime(null);
		}
		if (doctorId != null && doctorId > 0) {
			doctorRegisterSet.setDoctor(doctorService.get(doctorId));
		}
		if (doctorRegisterSet.getCreater().getId() != null) {
			doctorRegisterSet.setCreater(userService.get(doctorRegisterSet
					.getCreater().getId()));
		}
		doctorRegisterSetService.save(doctorRegisterSet);
		
		if (isGenerate) {
			// 生成医生排班一个星期的数据
			Calendar c = getWeeksCalendar(doctorRegisterSet.getWeeks());
			deleteDefaultDoctorWork(doctorRegisterSet, c);
			insertDefaultDoctorWork(doctorRegisterSet, c, new DoctorWorkJob());
		}
		
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				doctorRegisterSet = doctorRegisterSetService.get(id);
				if (doctorRegisterSet == null)
					continue;
				doctorRegisterSetService.delete(doctorRegisterSet);
				deleteDefaultDoctorWork(doctorRegisterSet,
						getWeeksCalendar(doctorRegisterSet.getWeeks()));
			}
		}
	}

	/**
	 * 获取星期所在的时间对象
	 */
	public Calendar getWeeksCalendar(int weeks) {
		Calendar c = Calendar.getInstance();
		int todayWeeks = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (todayWeeks < weeks) {
			int day = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, day + weeks - todayWeeks);
		}
		else if (todayWeeks > weeks) {
			int day = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, day + 7 + weeks - todayWeeks);
		}
		return c;
	}

	/**
	 * 删除医生默认排班数据
	 */
	public void deleteDefaultDoctorWork(DoctorRegisterSet drs, Calendar c) {
		JdbcService jdbcService = SpringUtils.getBean("jdbcService");
		jdbcService
				.executeUpdate(
						" DELETE FROM T_DOCTOR_WORK WHERE CONVERT (VARCHAR (10), START_TIME, 120) = ? AND DOCT_ID = ? AND RANK = ?",
						new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()),
						drs.getDoctor().getId(), drs.getRank());
	}

	/**
	 * 插入医生默认排班数据
	 */
	public void insertDefaultDoctorWork(DoctorRegisterSet drs, Calendar c,
			DoctorWorkJob bean) {
		List<DoctorRegisterSet> drsList = new ArrayList<DoctorRegisterSet>(1);
		drsList.add(drs);
		bean.insertDwData(c, drsList);
	}
	
	public void insertDoctorRegisterSet() {
		// try {
		// List<DoctorRegisterSet> dwList = doctorRegisterSetService
		// .findByAll(541, null, null);
		// if (CollectionUtils.isEmpty(dwList))
		// return;
		// HospitalService hospitalService = SpringUtils
		// .getBean("hospitalService");
		// List<Hospital> hospitalList = hospitalService
		// .findBypage(null, null);
		// if (hospitalList.isEmpty())
		// return;
		// DoctorRegisterSet temp = null;
		// List<DoctorRegisterSet> tempList = new
		// ArrayList<DoctorRegisterSet>();
		// for (Hospital h : hospitalList) {
		// if (h.getId().equals(130))
		// continue;
		// List<Doctor> doctorList = doctorService.findPageByHosp(null, h
		// .getId());
		// if (doctorList.isEmpty())
		// continue;
		// tempList.clear();
		// for (Doctor d : doctorList) {
		// for (DoctorRegisterSet dw : dwList) {
		// temp = new DoctorRegisterSet();
		// BeanUtils.copyProperties(dw, temp, new String[] { "id",
		// "doctor" });
		// temp.setDoctor(d);
		// tempList.add(temp);
		// }
		// }
		// doctorRegisterSetService.save(tempList);
		// }
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
	}
	
	public void insertDoctorWork() {
//		String day = ApplicationUtils.getRequest().getParameter("day");
//		DoctorWorkJob dwj = new DoctorWorkJob();
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DAY_OF_YEAR, Integer.valueOf(i));
//
//		List<DoctorRegisterSet> drsList = doctorRegisterSetService.findForDoctorWork(c
//				.get(Calendar.DAY_OF_WEEK) - 1);
//		if (CollectionUtils.isEmpty(drsList))
//			return;
//		dwj.insertDwData(c, drsList);
	}

	public List<SelectItem> getWeekList() {
		return ElianUtils.getWeekList();
	}

	public List<SelectItem> getRankList() {
		return ElianUtils.getRankList();
	}

	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}
	
	public List<SelectItem> getStepList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(15, "15"));
		itemList.add(new SelectItem(30, "30"));
		itemList.add(new SelectItem(60, "60"));
		return itemList;
	}
	
	public List<SelectItem> getCycleList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(3);
		itemList.add(new SelectItem(0, "是"));
		itemList.add(new SelectItem(1, "否"));
		itemList.add(new SelectItem(2, "其它"));
		return itemList;
	}

	public Pagination<DoctorRegisterSet> getPagination() {
		return pagination;
	}

	public DoctorRegisterSet getDoctorRegisterSet() {
		return doctorRegisterSet;
	}

	public void setDoctorRegisterSet(DoctorRegisterSet doctorRegisterSet) {
		this.doctorRegisterSet = doctorRegisterSet;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		if (doctorId == null || doctorId < 0)
			return "";
		else
			return doctorService.get(doctorId).getDoctName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	public boolean isGenerate() {
		return isGenerate;
	}

	public void setGenerate(boolean isGenerate) {
		this.isGenerate = isGenerate;
	}

	@Resource
	public void setDoctorRegisterSetService(
			DoctorRegisterSetService doctorRegisterSetService) {
		this.doctorRegisterSetService = doctorRegisterSetService;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
