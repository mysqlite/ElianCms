package com.elian.cms.mall.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 商城注册功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class MallRegisterAction extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 710072953487346686L;

	private UserService userService;
	private UserTypeService userTypeService;

	public String list() {
		return LIST;
	}

	public void validateRegister(String userName, String account,
			String password) {
		// 用户名是否为空
		if (StringUtils.isBlank(account)) {
			this.addFieldError("account", "用户名不能为空");
		}
		else if (ValidateUtils.isLengOut(account, 20)) {
			// 用户名最大长度
			this.addFieldError("account", "用户名在20字以内");
		}
		else if (ValidateUtils.isLengOut(userName, 16)) {
			this.addFieldError("user.realName", "真实名字在16字以内");
		}
		else if (!ValidateUtils.strLenth(password, 6, 20)) {
			this.addFieldError("user.password", "密码在6-20字以内");
		}
		else {
			UserService userService = SpringUtils.getBean("userService");
			User u = userService.findByAccout(account);
			if (u != null) {
				this.addFieldError("account", "当前用户已被注册");
			}
		}
	}

	public void register() {
		String userName = ApplicationUtils.getRequest()
				.getParameter("userName");
		String account = ApplicationUtils.getRequest().getParameter("account");
		String password = ApplicationUtils.getRequest()
				.getParameter("password");
		validateRegister(userName, account, password);

		Map<String, List<String>> map = getFieldErrors();
		JSONObject json = new JSONObject();
		User user = null;
		if (CollectionUtils.isEmpty(map)
				&& (user = saveUser(userName, account, password)) != null) {
			
			ApplicationUtils.setUser(user);
			MallLoginAction bean = SpringUtils.getBean("mallLoginAction");
			bean.setUser(user);
			bean.login();
			
			json.put("STATUS", 1);
			json.put("MSG", userName);
		}
		else {
			json.put("STATUS", 0);
			json.put("MSG", map.values().toArray()[0]);
		}
		ApplicationUtils.sendJsonStr(json.toString());
	}

	private User saveUser(String userName, String account, String password) {
		User user = createUser(userName, account, password);
		userService.save(user);
		return user;
	}

	public User createUser(String userName, String account, String password) {
		User user = new User();
		user.setRealName(userName);
		user.setAccount(account);
		user.setPassword(password);

		user.setUserType(userTypeService.get(1));
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
		return user;
	}

	@Resource
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
