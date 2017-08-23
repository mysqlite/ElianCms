package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 医生排班
 */
@Component
@Scope("prototype")
public class RegDoctorWorkAction extends BaseAction {
	private static final long serialVersionUID = -4575304160546266559L;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

	private Integer doctorId = Integer.valueOf(0);
	// 排班表横向日期
	private List<SelectItem> dateList = null;
	// 出诊时间
	private String dateMark = null;
	// 排班表数据
	private Map<String, List<Object[]>> workMap = null;

	private DoctorWorkService doctorWorkService;

	public String list() {
		return LIST;
	}
	
	public void checkLogin() {
		ApplicationUtils.removeForwardPage();
		User user = ApplicationUtils.getUser();
		if (user == null)
			ApplicationUtils.sendMsg("false");
		else
			ApplicationUtils.sendMsg("true");
	}
	
	public void checkIdentityCard() {
		User user = ApplicationUtils.getUser();
		if (StringUtils.isBlank(user.getIdentityCard()))
			ApplicationUtils.sendMsg("false");
		else
			ApplicationUtils.sendMsg("true");
	}

	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}

	public List<SelectItem> getRankList() {
		return ElianUtils.getRankList();
	}

	private void loadDatas() {
		loadDateList();
		String startTime = String.valueOf(dateList.get(0).getKey());
		String endTime = String.valueOf(dateList.get(dateList.size() - 1)
				.getKey());
		dateMark = startTime + " " + endTime;
		loadWorkMap(startTime, endTime);
	}

	private void loadDateList() {
		dateList = new ArrayList<SelectItem>(7);
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < 7; i++) {
			dateList.add(new SelectItem(format.format(c.getTime()), getWeeks(c
					.get(Calendar.DAY_OF_WEEK) - 1)));
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	private void loadWorkMap(String startTime, String endTime) {
		workMap = new HashMap<String, List<Object[]>>();
		List<DoctorWork> dwList = doctorWorkService.findAllDoctorWork(doctorId,
				startTime, endTime, null);
		if (CollectionUtils.isEmpty(dwList)) {
			return;
		}
		String key, value;
		String[] values;
		for (DoctorWork dw : dwList) {
			key = dw.getRank() + ElianCodes.UNDERLINE
					+ format.format(dw.getStartTime());
			value = dateFormat.format(dw.getStartTime()) + ElianCodes.UNDERLINE
					+ dateFormat.format(dw.getEndTime());
			values = new String[] { String.valueOf(dw.getRegType()), value };
			if (workMap.get(key) == null) {
				List<Object[]> list = new ArrayList<Object[]>();
				list.add(values);
				workMap.put(key, list);
			}
			else
				workMap.get(key).add(values);
		}
	}

	public List<SelectItem> getDateList() {
		if (dateList == null) {
			loadDatas();
		}
		return dateList;
	}

	public String getDateMark() {
		if (dateMark == null) {
			loadDatas();
		}
		return dateMark;
	}

	public String getWeeks(int week) {
		for (SelectItem item : ElianUtils.getWeekList()) {
			if (item.getKey().equals(week)) {
				return item.getValue();
			}
		}
		return "";
	}

	public Map<String, List<Object[]>> getWorkMap() {
		if (workMap == null) {
			loadDatas();
		}
		return workMap;
	}
	
	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	
	private void loadJsonDatas() {
		loadDateList();
		String startTime = String.valueOf(dateList.get(0).getKey());
		String endTime = String.valueOf(dateList.get(dateList.size() - 1)
				.getKey());
		loadJsonWorkMap(startTime, endTime);
	}
	
	private void loadJsonWorkMap(String startTime, String endTime) {
		workMap = new HashMap<String, List<Object[]>>();
		List<DoctorWork> dwList = doctorWorkService.findAllDoctorWork(doctorId,
				startTime, endTime, null);
		if (CollectionUtils.isEmpty(dwList)) {
			return;
		}
		String key;
		for (DoctorWork dw : dwList) {
			key = dw.getRank() + ElianCodes.UNDERLINE
					+ format.format(dw.getStartTime());
			if (workMap.get(key) == null) {
				List<Object[]> list = new ArrayList<Object[]>();
				list.add(new Integer[] { dw.getNoSource() });
				workMap.put(key, list);
			}
			else {
				Integer[] noSources = (Integer[]) workMap.get(key).get(0);
				noSources[0] = noSources[0] + dw.getNoSource();
			}
		}
	}
	
	public void listJson(){
		loadJsonDatas();
		JSONObject obj = new JSONObject();
		obj.put("dateList", dateList);
		obj.put("rankList", getRankList());
		obj.put("workMap", workMap);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void registerJson() {
		String date = ApplicationUtils.getRequest().getParameter("date");
		Integer rank = Integer.valueOf(ApplicationUtils.getRequest()
				.getParameter("r"));
		JSONObject obj = new JSONObject();
		obj.put("workTimeList", loadWorkTime(date, date, rank));
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	private List<Object[]> loadWorkTime(String startTime, String endTime,
			Integer rank) {
		List<DoctorWork> dwList = doctorWorkService.findAllDoctorWork(doctorId,
				startTime, endTime, rank);
		if (CollectionUtils.isEmpty(dwList)) {
			return null;
		}
		List<Object[]> workTimeList = new ArrayList<Object[]>();
		for (DoctorWork dw : dwList) {
			Object[] datas = new Object[3];
			datas[0] = dateFormat.format(dw.getStartTime())
					+ ElianCodes.UNDERLINE + dateFormat.format(dw.getEndTime());
			datas[1] = dw.getAmount();
			datas[2] = dw.getNoSource();
			workTimeList.add(datas);
		}
		return workTimeList;
	}

	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}
}
