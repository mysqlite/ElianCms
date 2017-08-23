package com.elian.cms.reg.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.elian.cms.syst.util.LogParamUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegLoginsAction extends BaseAction {
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
		if(logService==null)
			logService=SpringUtils.getBean("logService");
		logService.save(LogParamUtils.LOGIN_SUCCESS,LogParamUtils.LOGIN_REG_SUCCESS_CN, LogParamUtils.USER_ACCOUNT,user.getAccount());
		ApplicationUtils.removeSite();
		return "success";
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
