package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
import com.elian.cms.syst.util.StringUtils;

/**
 * 挂号确认界面
 * 
 * Joe
 */
@Component
@Scope("prototype")
public class RegRegisterConfirmAction extends BaseAction{
	private static final long serialVersionUID = -6187663543364905503L;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	private DoctorWork doctorWork;
	private User user;
	private String phone;
	private UserRegister userRegister;

	private DoctorWorkService doctorWorkService;
	private UserRegisterService userRegisterService;

	public String confirm() {
		Integer doctorId = Integer.valueOf(ApplicationUtils.getRequest()
				.getParameter("i"));
		int rank = Integer.valueOf(ApplicationUtils.getRequest().getParameter(
				"r"));
		String dateYhd = ApplicationUtils.getRequest().getParameter("dy");
		String dateHm = ApplicationUtils.getRequest().getParameter("dh");
		String[] dates = dateHm.split(ElianCodes.UNDERLINE);
		List<DoctorWork> dwList = doctorWorkService.findConfirmDoctorWork(
				doctorId, rank, dateYhd + " " + dates[0], dateYhd + " "
						+ dates[1]);
		if (dwList.size() == 1) {
			doctorWork = dwList.get(0);
			return "confirm";
		}
		return "";
	}
	
	public String save() {
		doctorWork = doctorWorkService.get(doctorWork.getId());
		if (doctorWork.getNoSource() > 0) {
			if (existsUserRegister())
				return "exists";
			// 医生排班表号源减一
			doctorWork.setNoSource(doctorWork.getNoSource() - 1);
			doctorWorkService.save(doctorWork);

			userRegister = createUserRegister();
			userRegisterService.save(userRegister);
			return "ok";
		}
		else {
			return "fail";
		}
	}
	
	public boolean existsUserRegister() {
		List<UserRegister> list = userRegisterService.findByAll(null,
				doctorWork.getId(), ApplicationUtils.getUser().getId());
		return !CollectionUtils.isEmpty(list);
	}

	public String main() {
		ApplicationUtils.removeForwardPage();
		return "regOk";
	}

	public UserRegister createUserRegister() {
		UserRegister ur = new UserRegister();
		ur.setSchedulingId(doctorWork);
		ur.setUser(ApplicationUtils.getUser());
		ur.setRegisterCode(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date())
				+ ur.getUser().getId());
		ur.setAmount(doctorWork.getAmount());
		ur.setPayType(1);
		ur.setCreateTime(new Date());
		return ur;
	}

	public List<SelectItem> getRegTypeList() {
		return ElianUtils.getRegTypeList();
	}
	
	public static List<SelectItem> getPayTypeList() {
		return ElianUtils.getDeliveryTypeList();
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
		if(StringUtils.isBlank(phone)){
			phone = ApplicationUtils.getUser().getMobile();
		}
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
	
	public void confirmJson() {
		confirm();
		getUser();
		JSONObject obj = new JSONObject();
		obj.put("doctorWorkId", doctorWork.getId());
		obj.put("hospName", doctorWork.getDoctor().getDept().getHospital().getHospName()+" "+doctorWork.getDoctor().getDept().getDeptName());
		obj.put("doctName", doctorWork.getDoctor().getDoctName());
		obj.put("date", format.format(doctorWork.getStartTime()) + " "
				+ dateFormat.format(doctorWork.getStartTime())
				+ ElianCodes.UNDERLINE
				+ dateFormat.format(doctorWork.getEndTime()));
		obj.put("amount", doctorWork.getAmount());
		obj.put("userName", user.getRealName());
		obj.put("mobile", user.getMobile());
		obj.put("identityCard", user.getIdentityCard());
		obj.put("medicalCard", user.getMedicalCard());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void saveJson() {
		JSONObject obj = new JSONObject();
		if("exists"==save()){
			obj.put("msg", "exists");
		}
		if (userRegister != null && doctorWork != null) {
			obj.put("userRegisterId", userRegister.getId());
			obj.put("doctorWorkId", doctorWork.getId());
		}
		ApplicationUtils.sendJsonpObj(obj);
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
