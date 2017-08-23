package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegUserManagerAction extends BaseAction {
	private static final long serialVersionUID = -1907331901344054806L;
	private User user;
	private String[] areaName= {null,null,null};
	private UserService userService;
	private SiteService siteService;
	private AreaService areaService ;
	public String main() {
		return "main";
	}
	
	public String myData() {
		if(ApplicationUtils.getUser()==null) 
			return "login";
		else {
			user=userService.get(ApplicationUtils.getUser().getId());
			if(user!=null)
    			if (null != user.getComefrom())
    				areaName = getAreaNames(user.getComefrom());
		}
		return LIST;
	}
	
	public String[] getAreaNames(Integer areaCode) {
	    List<String> list = new ArrayList<String>();
		List<Area> area = areaService.findAreaNameByAreaCode(areaCode);
		for (int i = 0, len = area.size(); i < len; i++) {
			list.add(area.get(i).getAreaName());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			areaName[i]= list.get(i);
		}
		return areaName;
	}
	
	public String save() {
		User user2=null;
		if(ApplicationUtils.getUser()!=null) {
			user2=userService.get(ApplicationUtils.getUser().getId());
			user2.setRealName(user.getRealName());
			user2.setGender(user.getGender());
			user2.setBirthday(user.getBirthday());
			user2.setEmail(user.getEmail());
			user2.setMobile(user.getMobile());
			user2.setIdentityCard(user.getIdentityCard());
			user2.setMedicalCard(user.getMedicalCard());
			user2.setComefrom(user.getComefrom());
			userService.save(user2);
			ApplicationUtils.setUser(user2);
			ApplicationUtils.setSite(siteService.get(1));
			ApplicationUtils.removeSite();
			this.addFieldError("successMsg", "用户资料更改成功,即时生效.");
		}else {
			this.addFieldError("successMsg", "用户资料更改失败!");
		}
		if (null != user.getComefrom())
			areaName = getAreaNames(user.getComefrom());
		return EDIT;
	}
	public void validateSave() {
		
		if(user==null) {
			this.addFieldError("user.realName", "当前用户已经退出，请重新登录");
			return;
		}
		//性别
		if(StringUtils.isBlank(user.getGender())) {
			this.addFieldError("user.gender", "请选择一下性别，谢谢");
		}
		// 真实姓名是否为全中文
		if (ValidateUtils.isChina(user.getRealName())) {
			// 真实姓名最大长度
			if (ValidateUtils.isLengOut(user.getRealName(), 16))
				this.addFieldError("user.realName", "真实名字在16字以内");
		}else {
			this.addFieldError("user.realName", "请输入真实姓名");
		}
		// 手机号码如不为空
		if (!StringUtils.isBlank(user.getMobile())) {
			// 手机号码是否符合规则
			if (!ValidateUtils.isMobile(user.getMobile()))
				this.addFieldError("user.mobile", "请输入正确的手机号");
		}else {
			this.addFieldError("user.mobile", "请输入手机号");
		}
		// 身份证号验证
		if (!StringUtils.isBlank(user.getIdentityCard())) {
			if (!IdCardUtils.validateCard(user.getIdentityCard()))
				this.addFieldError("user.identityCard", "身份证号不合法");
		}
		// 邮箱格式验证
		if (!StringUtils.isBlank(user.getEmail())) {
			if (!ValidateUtils.isEmail(user.getEmail(), 6, 40))
				this.addFieldError("user.email", "邮箱格式不正确");
			else if (ValidateUtils.isLengOut(user.getEmail(), 40))
				this.addFieldError("user.email", "邮箱在6-40字以内");
		}
		// 生日验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != user.getBirthday()) {
			String birthday = df.format(user.getBirthday());
			if (!ValidateUtils.isDateYYYY_MM_DD(birthday))
				this.addFieldError("user.birthday", "生日日期不正确");
		}
		if (null != user.getBirthday()& !StringUtils.isBlank(user.getIdentityCard())) {
			String birthday = df.format(user.getBirthday());
			if (birthday == IdCardUtils.getBirthDateByIdCard(user.getIdentityCard()))
				this.addFieldError("user.birthday", "生日日期与身份证号日期不相符");
		}
		if (!StringUtils.isBlank(user.getQq())) {
			if (!ValidateUtils.isQQ(user.getQq()))
				this.addFieldError("user.qq", "您输入的QQ号不合法");
		}
		if (!StringUtils.isBlank(user.getMedicalCard())) {
			if (!ValidateUtils.isNumber(user.getMedicalCard())& ValidateUtils.isLengOut(user.getMedicalCard(), 50)
				& !ValidateUtils.strLenth(user.getMedicalCard(), 10, 50))
				this.addFieldError("user.medicalCard", "请输入10-50位纯数字医疗卡号");
		}
		if (!StringUtils.isBlank(user.getMsn())) {
			if (!ValidateUtils.isEmail(user.getMsn(), 6, 45))
				this.addFieldError("user.msn", "请输入正确的MSN,且为6-45位");
		}
		if (ValidateUtils.isLengOut(user.getIntro(), 255))
			this.addFieldError("user.intro", "自我介绍最大长度为255字");
	}
	
	public List<SelectItem> getSexList() {
		return ElianUtils.getSex();
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getAreaName() {
		return areaName;
	}

	public void setAreaName(String[] areaName) {
		this.areaName = areaName;
	}
}
