package com.elian.cms.reg.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.LogParamUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegLoginAction extends BaseAction {
	private static final long serialVersionUID = 1022467057624518821L;
	private User user;
	private UserType userType;
	private String captcha;
	private UserService userService;
	private LogService logService;
	private SiteService siteService;
	private String pwd;
	private boolean serviceTerms;
	
	public String reg() {
		return "reg";
	}
	public String main() {
		return "main";
	}
	
	public String success() {
		return "main";
	}
	
	@Transactional
	public String login() {
		User user = ApplicationUtils.getUser();
		if (user == null)
			return "success";
		else {
			user = userService.get(user.getId());
		}
		user.setLoginIp(user.getLastLoginIp());
		user.setLoginTime(user.getLastLoginTime());
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginIp(ApplicationUtils.getIpAddr());
		user.setLastLoginTime(new Date());
		userService.save(user);
		ApplicationUtils.setSite(siteService.get(1));
		logService.save(LogParamUtils.LOGIN_SUCCESS,LogParamUtils.LOGIN_REG_SUCCESS_CN, LogParamUtils.USER_ACCOUNT,user.getAccount());
		ApplicationUtils.removeSite();
		return "success";
	}
	
	public void loginJson() {
		int check =checkLogin();
		JSONObject obj = new JSONObject();
		ApplicationUtils.getUser();
		if(check==0) 
			obj.put("status", 1);//用户名或密码错误
		else if(check==1) 
			obj.put("status",3);//用户没有登录权限
		else if(check==2) 
			obj.put("status", 2);//登录成功
		obj.put("forwards", ApplicationUtils.getForwardPage());
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void setForwardJson() {
		ApplicationUtils.setForwardPage(getForwardPage());
		JSONObject obj=new JSONObject();
		obj.put("status", 1);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	
	@Transactional
	public void validateLogin() {
		if (user != null) {
			int check = -1;
			if (ValidateUtils.isBlank(user.getAccount())) {
				this.addFieldError("loginmsg", "用户名不能为空");
			}
			else if (ValidateUtils.isBlank(user.getPassword())) {
				this.addFieldError("loginmsg", "密码不能为空");
			}
			else if (!captcha.equals(ApplicationUtils.getCaptcha())) {
				this.addFieldError("loginmsg", "验证码错误");
			}
			else {
				check = checkLogin();
				if (check == 0) {
					this.addFieldError("loginmsg", "用户名或密码错误");
				}
				else if (check == 1) {
					this.addFieldError("loginmsg", "用户没有登录权限");
				}
			}
		}
	}
	
	public int checkLogin() {
		if(userService==null)
		    userService=SpringUtils.getBean("userService");
		if(siteService==null)
			siteService=SpringUtils.getBean("siteService");
		if(logService==null)
			logService=SpringUtils.getBean("logService");
		List<User> userList = userService.findByAll(user.getAccount(), user.getPassword(), null);
		if (userList != null && userList.size() == 1) {
			User user = userList.get(0);
			if (user.getStatus() != ElianUtils.STATUS_1)
				return 1;
			ApplicationUtils.setUser(user);
			return 2;
		}
		else
			return 0;
	}
	
	public String loginOut() {
		ApplicationUtils.removeUser();
		return LOGIN_OUT;
	}
	
	public void loginOutJson() {
		ApplicationUtils.removeUser();
		JSONObject obj=new JSONObject();
		obj.put("status", 1);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	public void updateUserJson() {
    	User u=ApplicationUtils.getUser();
    	JSONObject obj=new JSONObject();
	   if(user!=null) {
		   try {
	    		user.setRealName(URLDecoder.decode(user.getRealName(),"UTF-8"));
	    	}
	    	catch (UnsupportedEncodingException e) {}
		   String msg=valUpdateUserJson();
		   if(StringUtils.isBlank(msg)) {
			   u.setMobile(user.getMobile());
			   u.setEmail(user.getEmail());
			   u.setRealName(user.getRealName());
			   u.setIdentityCard(user.getIdentityCard());
			   u.setMedicalCard(user.getMedicalCard());
			   u.setComefrom(user.getComefrom());
			   userService.save(u);
			   obj.put("msg", "修改成功!");
			   obj.put("status",true);
			   ApplicationUtils.setUser(u);
		   }
		   else {
			   obj.put("msg", msg);
			   obj.put("status",false);
		   }
	   }
	   else {
		   obj.put("msg", "当前用户未登录");
		   obj.put("status",false);
	   }
	   obj.put("forward", ApplicationUtils.getForwardPage());
	   ApplicationUtils.sendJsonpObj(obj);
	}
	
	public String  valUpdateUserJson() {
		String msg="";
		if(user==null) {
			msg+="当前用户为空";
			return msg;
		}
		// 手机号码如不为空
		if (!StringUtils.isBlank(user.getMobile())) {
			if (!ValidateUtils.isMobile(user.getMobile()))
				msg+= "请输入正确的手机号</br>";
		}
		else 
			msg+= "请输入手机号</br>";
		// 身份证号验证
		if (!IdCardUtils.validateCard(user.getIdentityCard()))
			msg+="身份证号不合法</br>";
		// 邮箱格式验证
		if (!StringUtils.isBlank(user.getEmail())) {
			if (!ValidateUtils.isEmail(user.getEmail(), 6, 40))
				msg+= "邮箱格式不正确</br>";
			else if (ValidateUtils.isLengOut(user.getEmail(), 40))
				msg+="邮箱在6-40字以内</br>";
		}
		// 真实姓名是否为全中文
		if (ValidateUtils.isChina(user.getRealName())) {
			// 真实姓名最大长度
			if (ValidateUtils.isLengOut(user.getRealName(), 16))
				msg+="真实名字在16字以内</br>";
		}
		else {
			msg+="请输入真实姓名</br>";
		}
		if (!StringUtils.isBlank(user.getMedicalCard())) {
			if (!ValidateUtils.isNumber(user.getMedicalCard())& ValidateUtils.isLengOut(user.getMedicalCard(), 50)& !ValidateUtils.strLenth(user.getMedicalCard(), 10, 50))
				msg+="请输入10-50位纯数字医疗卡号</br>";
		}
		return msg;
	}
	
	public List<SelectItem> getSexList() {
		return ElianUtils.getSex();
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
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
