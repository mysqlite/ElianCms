package com.elian.cms.reg.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.LogParamUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

@Component
@Scope("prototype")
public class RegUserPwdAction extends BaseAction {
	private static final long serialVersionUID = -2893234488092085674L;
	private String prvePwd;
	private String nowPwd;
	private String nowPwd2;
	private Integer errorNum = 0;
	private UserService userService;
	private LogService logService;
	private SiteService siteService;
	private User user;

	public String main() {
		return "main";
	}

	
	public String edit() {
		if(ApplicationUtils.getUser()==null) {
			return "login";
		}
		user=userService.get(ApplicationUtils.getUser().getId());
		return EDIT;
	}

	public String save() {
		user=userService.get(ApplicationUtils.getUser().getId());
		user.setPassword(LdMd5.MD5(nowPwd));
		userService.save(user);
		ApplicationUtils.setUser(user);
		if(siteService==null)
			siteService=SpringUtils.getBean("siteService");
		ApplicationUtils.setSite(siteService.get(1));
		logService.save(LogParamUtils.OPERATION_UPDATE,LogParamUtils.USER_UPDATE_PWD_CN,LogParamUtils.USER_ACCOUNT, user.getAccount());
		ApplicationUtils.removeSite();
		this.addFieldError("successMsg", "密码修改成功，即时生效");
		return EDIT;
	}

	public void validateSave() {
		if (StringUtils.isBlank(prvePwd))
			this.addFieldError("prvePwd", "请输入原密码");
		else {
			user=userService.get(ApplicationUtils.getUser().getId());
			if(!LdMd5.MD5(prvePwd).equals(user.getPassword())) {
				this.addFieldError("prvePwd", "请输入正确的原密码");
				errorNum=errorNum+1;
				if(errorNum>=3) {
					this.addFieldError("errorNum", "本次修改3次原密码错误，请稍后再试");
					return;
				}
			}
		}
		if (StringUtils.isBlank(nowPwd))
			this.addFieldError("nowPwd", "请输入新密码");
		if (StringUtils.isBlank(nowPwd2))
			this.addFieldError("nowPwd2", "请输入重复新密码");
		if (!nowPwd.equals(nowPwd2)) {
			this.addFieldError("nowPwd", "两次密码不相同");
			this.addFieldError("nowPwd2", "两次密码不相同");
		}
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	// @Resource
	// public void setSiteService(SiteService siteService) {
	// this.siteService = siteService;
	// }

	public String getPrvePwd() {
		return prvePwd;
	}

	public void setPrvePwd(String prvePwd) {
		this.prvePwd = prvePwd;
	}

	public String getNowPwd() {
		return nowPwd;
	}

	public void setNowPwd(String nowPwd) {
		this.nowPwd = nowPwd;
	}

	public String getNowPwd2() {
		return nowPwd2;
	}

	public void setNowPwd2(String nowPwd2) {
		this.nowPwd2 = nowPwd2;
	}

	public Integer getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
