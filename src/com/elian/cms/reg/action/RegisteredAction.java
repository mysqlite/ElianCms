package com.elian.cms.reg.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegisteredAction extends BaseAction {
	private static final long serialVersionUID = 1022467057624518821L;
	private User user;
	private UserType userType;
	private String captcha;
	private UserService userService;
	private UserTypeService userTypeService;
	private SiteService siteService;
	private AreaService areaService ;
	private String pwd;
	private boolean serviceTerms;
	private String areaName = "";
	
	public String reg() {
		return "reg";
	}
	public String main() {
		return "main";
	}
	public String saveReg() {
		user.setUserType(userTypeService.get(5));
		user.setAdmin(false);
		user.setGender(ElianUtils.SECRET);
		user.setBirthday(null);
		user.setMiblePass(false);
		user.setRegisterIp(ApplicationUtils.getIpAddr());
		user.setRegisterTime(new Date());
		user.setLoginCount(0);
		user.setLastLoginIp(ApplicationUtils.getIpAddr());
		user.setLastLoginTime(new Date());
		user.setStatus(ElianUtils.STATUS_1);
		user.setProposer(false);
		user.setPassword(LdMd5.MD5(user.getPassword()));
		userService.save(user);
		ApplicationUtils.setUser(user);
		ApplicationUtils.setSite(siteService.get(1));
		ApplicationUtils.removeSite();
		return "success";
	}
	
	public void saveRegJson() {
		JSONObject obj=new JSONObject();
		if (!StringUtils.isBlank(user.getAccount())) {
			User u = userService.findByAccout(user.getAccount());
			if (u != null) {
				obj.put("status", -1);
				obj.put("msg","当前用户已被注册");
			}
    		else if (!ValidateUtils.isMobile(user.getMobile())) {
    			obj.put("status", 2);
    			obj.put("msg","请输入正确的手机号码");
    			return;
    		}
    		else {
        		user.setUserType(userTypeService.get(6));
        		user.setAdmin(false);
        		user.setGender(ElianUtils.SECRET);
        		user.setBirthday(null);
        		user.setMiblePass(false);
        		user.setRegisterIp(ApplicationUtils.getIpAddr());
        		user.setRegisterTime(new Date());
        		user.setLoginCount(0);
        		user.setLastLoginIp(ApplicationUtils.getIpAddr());
        		user.setLastLoginTime(new Date());
        		user.setStatus(ElianUtils.STATUS_1);
        		user.setProposer(false);
        		user.setPassword(LdMd5.MD5(user.getPassword()));
        		user.setRealName(user.getAccount());
        		userService.save(user);
        		ApplicationUtils.setUser(user);
        		ApplicationUtils.setSite(siteService.get(1));
        		ApplicationUtils.removeSite();
        		obj.put("status", 1);
        		obj.put("msg","用户"+user.getAccount()+"注册成功");
    		}
		}
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void checkLogin() {
	  user=ApplicationUtils.getUser();
	  JSONObject obj = new JSONObject();
	  if(user!=null) {
		  obj.put("user", user);
		  obj.put("login", "true");
		  if(null!=user.getComefrom())
			  obj.put("address", getAreaNames(user.getComefrom()));
		  else
			  obj.put("address", "尚未填写");
	  }
	  else {
		  obj.put("login", "false");
	  }
	  ApplicationUtils.sendJsonpObj(obj);
	}
	
	public String getAreaNames(Integer areaCode) {
	    List<String> list = new ArrayList<String>();
		List<Area> area = areaService.findAreaNameByAreaCode(areaCode);
		for (int i = 0, len = area.size(); i < len; i++) {
			list.add(area.get(i).getAreaName());
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			areaName += list.get(i) + " ";
		}
		return areaName;
	}

	public String loginOut() {
		ApplicationUtils.removeUser();
		return LOGIN_OUT;
	}
	
	public List<SelectItem> getSexList() {
		return ElianUtils.getSex();
	}

	public void validateSaveReg() {
		if(captcha==null||user==null) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
		if (!captcha.equals(ApplicationUtils.getCaptcha())) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
		// 用户
		User u = userService.findByAccout(user.getAccount());
		if (!StringUtils.isBlank(user.getAccount())) {
			if (u != null)
				this.addFieldError("user.account", "用户名已存在,请重新输入");
			else if (!ValidateUtils.accountFilter(user.getAccount(), 4, 16)) {
				this.addFieldError("user.account", "用户名只能为4-16位的小写字母及数字组合");
			}
		}
		if (StringUtils.isBlank(user.getPassword()))
			this.addFieldError("user.password", "密码不能为空");
		else if (!ValidateUtils.pwdFilter(user.getPassword(), 6, 20)) {
			this.addFieldError("user.password", "密码只能为6-20位的英文、数字、半角字符");
		}
		if (StringUtils.isBlank(pwd))
			this.addFieldError("pwd", "确认密码不能为空");
		// 用户类型
		if(user.getUserType()!=null) {	
			if (user.getUserType().getId() == null)
				this.addFieldError("user.userType.id", "请选择用户类型");
		}
		// 用户名是否为空
		if (StringUtils.isBlank(user.getAccount()))
			this.addFieldError("user.account", "用户名不能为空");
		// 用户名最大长度
		if (ValidateUtils.isLengOut(user.getAccount(), 20))
			this.addFieldError("user.account", "用户名在20字以内");
		// 真实姓名是否为全中文
		if (ValidateUtils.isChina(user.getRealName())) {
			// 真实姓名最大长度
			if (ValidateUtils.isLengOut(user.getRealName(), 16))
				this.addFieldError("user.realName", "真实名字在16字以内");
		}
		else {
			this.addFieldError("user.realName", "请输入真实姓名");
		}
		if (!StringUtils.isBlank(user.getPassword())
				| !StringUtils.isBlank(pwd)) {
			// 密码中是否为除汉字之外的字符
			if (ValidateUtils.isChina(user.getPassword()))
				this.addFieldError("user.password", "密码不能包含中文");
			// 确认密码和输入密码是否相等
			if (!user.getPassword().equals(pwd))
				this.addFieldError("pwd", "两次密码不相等");
		}
		// 手机号码如不为空
		if (!StringUtils.isBlank(user.getMobile())) {
			// 手机号码是否符合规则
			if (!ValidateUtils.isMobile(user.getMobile()))
				this.addFieldError("user.mobile", "请输入正确的手机号");
		}
		else {
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
		if (null != user.getBirthday()
				& !StringUtils.isBlank(user.getIdentityCard())) {
			String birthday = df.format(user.getBirthday());
			if (birthday == IdCardUtils.getBirthDateByIdCard(user
					.getIdentityCard()))
				this.addFieldError("user.birthday", "生日日期与身份证号日期不相符");
		}
		if (!StringUtils.isBlank(user.getQq())) {
			if (!ValidateUtils.isQQ(user.getQq()))
				this.addFieldError("user.qq", "您输入的QQ号不合法");
		}
		if (!StringUtils.isBlank(user.getMedicalCard())) {
			if (!ValidateUtils.isNumber(user.getMedicalCard())
					& ValidateUtils.isLengOut(user.getMedicalCard(), 50)
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

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isServiceTerms() {
		return serviceTerms;
	}

	public void setServiceTerms(boolean serviceTerms) {
		this.serviceTerms = serviceTerms;
	}

}
