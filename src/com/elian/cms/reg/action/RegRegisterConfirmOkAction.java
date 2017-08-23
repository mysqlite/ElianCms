package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRegister;
import com.elian.cms.admin.service.DoctorWorkService;
import com.elian.cms.admin.service.UserRegisterService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 挂号确认界面
 * 
 * Joe
 */
@Component
@Scope("prototype")
public class RegRegisterConfirmOkAction extends BaseAction {
	private static final long serialVersionUID = 8115636221640667542L;
	private DoctorWork doctorWork;
	private User user;
	private String phone;
	private UserRegister userRegister;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

	private DoctorWorkService doctorWorkService;
	private UserRegisterService userRegisterService;

	public String list() {
		doctorWork = doctorWorkService.get(doctorWork.getId());
		userRegister = userRegisterService.get(userRegister.getId());
		return LIST;
	}
	
	public void listJson() {
		list();
		JSONObject obj = new JSONObject();
		if (userRegister != null && doctorWork != null) {
			obj.put("registerCode", userRegister.getRegisterCode());
			obj.put("hospName", doctorWork.getDoctor().getDept().getHospital()
					.getHospName());
			obj.put("deptName", doctorWork.getDoctor().getDept().getDeptName());
			obj.put("doctName", doctorWork.getDoctor().getDoctName());
			obj.put("date", format.format(doctorWork.getStartTime()) + " "
					+ dateFormat.format(doctorWork.getStartTime())
					+ ElianCodes.UNDERLINE
					+ dateFormat.format(doctorWork.getEndTime()));
			obj.put("userName", ApplicationUtils.getUser().getRealName());
			obj.put("amount", doctorWork.getAmount());
		}
		ApplicationUtils.sendJsonpObj(obj);
	}

	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}

	public static List<SelectItem> getPayTypeList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(2);
		itemList.add(new SelectItem(0, "网上支付"));
		itemList.add(new SelectItem(1, "线下支付"));
		return itemList;
	}

	public static List<SelectItem> getStatusList() {
		List<SelectItem> itemList = new ArrayList<SelectItem>(7);
		itemList.add(new SelectItem(0, "待支付"));
		itemList.add(new SelectItem(1, "待就诊"));
		itemList.add(new SelectItem(2, "待确认"));
		return itemList;
	}

	public DoctorWork getDoctorWork() {
		return doctorWork;
	}

	public void setDoctorWork(DoctorWork doctorWork) {
		this.doctorWork = doctorWork;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserRegister getUserRegister() {
		return userRegister;
	}

	public void setUserRegister(UserRegister userRegister) {
		this.userRegister = userRegister;
	}

	public User getUser() {
		if (user == null) {
			user = ApplicationUtils.getUser();
		}
		return user;
	}

	@Resource
	public void setDoctorWorkService(DoctorWorkService doctorWorkService) {
		this.doctorWorkService = doctorWorkService;
	}

	@Resource
	public void setUserRegisterService(UserRegisterService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}
}
