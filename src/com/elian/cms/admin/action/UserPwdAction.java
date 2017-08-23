package com.elian.cms.admin.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.StringUtils;
@Component
@Scope("prototype")
public class UserPwdAction extends BaseAction {
	private static final long serialVersionUID = -1138283437119085284L;
	private String prvePwd;
	private String nowPwd;
	private String nowPwd2;
	private Integer errorNum=0;
	private UserService userService;
	private User user;

	public String edit() {
		user=userService.get(ApplicationUtils.getUser().getId());
		return EDIT;
	}

	public String save() {
		user=userService.get(ApplicationUtils.getUser().getId());
		user.setPassword(LdMd5.MD5(nowPwd));
		userService.save(user);
		ApplicationUtils.setUser(user);
		return SHOW;
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


	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
