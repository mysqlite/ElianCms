package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Action;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.service.ActionService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.LogParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 登陆功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class LoginAction extends BaseAction {
	private static final long serialVersionUID = 6107760231129665820L;

	/** 登陆次数 */
	private int loginNum;
	/** 验证码 */
	private String captcha;
	protected User user;
	// private Pagination<User> pagination = new Pagination<User>();
	protected UserService userService;
	private ActionService actionService;
	protected SiteService siteService;
	private LogService logService;
	private UserRoleService userRoleService;
	private HospitalService hospitalService;
	private SubstationService substationService;
	private CompanyService companyService=null;
	
	//private Site mainSite;

	public void validateLogin() {
		loginNum++;
		if (user != null) {
			int check = -1;
			if (ValidateUtils.isBlank(user.getAccount())) {
				this.addFieldError("user.account", "用户名不能为空");
			}
			else if (ValidateUtils.isBlank(user.getPassword())) {
				this.addFieldError("user.password", "密码不能为空");
			}
			else if (loginNum > 3) {
				if (!captcha.equals(ApplicationUtils.getCaptcha())) {
					this.addFieldError("captcha", "验证码错误");
				}
				else {
					check = checkLogin();
					if (check == 0) {
						this.addFieldError("user", "用户名或密码错误");
					}
					else if (check == 1) {
						this.addFieldError("user", "用户没有登录权限");
					}
				}
			}
			else {
				check = checkLogin();
				if (check == 0) {
					this.addFieldError("user", "用户名或密码错误");
				}
				else if (check == 1) {
					this.addFieldError("user", "用户没有登录权限");
				}
			}

			// if (error)
			// logService.save(LogParamUtils.LOGIN_FAIL,
			// LogParamUtils.LOGIN_FAIL_CN,
			// LogParamUtils.USER_ACCOUNT, user.getAccount());
		}
	}

	public String login() {
		if (user != null) {
			saveUserInfo();
			logService.save(LogParamUtils.LOGIN_SUCCESS,LogParamUtils.LOGIN_SUCCESS_CN, LogParamUtils.USER_ACCOUNT,user.getAccount());
			return SUCCESS;
		}
		return FAIL;
	}
	
	public void loginJson() {
		JSONObject obj = new JSONObject();
		if(user==null&&ApplicationUtils.getUser()==null) {
			obj.put("status", 9);
			ApplicationUtils.sendJsonpObj(obj);
			return;
		}
		   
		if(ApplicationUtils.getUser()==null) {
    		int result=checkLoginJson();
    		if (user != null&&user.getId()!=null) {
    			saveUserInfo();
    			logService.save(LogParamUtils.LOGIN_SUCCESS,LogParamUtils.LOGIN_SUCCESS_CN, LogParamUtils.USER_ACCOUNT,user.getAccount());
    			obj.put("user",user);
    		}
    		obj.put("status", result);
		}
		else {
			obj.put("user",ApplicationUtils.getUser());
			if(ApplicationUtils.getActionSet()!=null) 
				obj.put("status", 0);
			else
				obj.put("status", 2);
		}
		ApplicationUtils.sendJsonpObj(obj);
	}

	public String main() {
		return "main";
	}

	public void saveUserInfo() {
		User u = userService.findByAccout(user.getAccount());
		u.setLoginIp(u.getLastLoginIp());
		u.setLoginTime(u.getLastLoginTime());
		u.setLoginCount(u.getLoginCount() + 1);
		u.setLastLoginIp(ApplicationUtils.getIpAddr());
		u.setLastLoginTime(new Date());
		userService.save(u);
		ApplicationUtils.setUser(u);
	}

	/***
	 * 0[管理员登陆],1[当前用户被禁用],2[非管理员],-1[用户名或密码错误]
	 * @return
	 */
    private int checkLoginJson() {
    	Integer result=0;
    	List<User> userList = userService.findByAll(user.getAccount(), user.getPassword(), null);
		if (userList != null && userList.size() == 1) {
			 user = userList.get(0);
			if (user.getStatus() != ElianUtils.STATUS_1)
				return 1;//当前用户被禁用
			List<Site> siteList = siteService.findByUser(user.getId());
			if (!CollectionUtils.isEmpty(siteList)) {
				Site site = siteList.get(0);
				ApplicationUtils.setSite(site);
				if (ElianUtils.COMP_TYPE_HOSP.equals(site.getComType()))
					setHospitalInSession(site.getComId());
				else if (ElianUtils.COMP_TYPE_SUBS.equals(site.getComType())||ElianUtils.COMP_TYPE_MAIN.equals(site.getComType()))
					setSubstationInSession(site.getComId());
			}else {
				result=2;//当前用户没有后台权限
			}
			ApplicationUtils.setUser(user);
			setActionInSession(user.getId());
			setRoleInSession(user.getId());
			return result;
		}
		else
			return -1;
    }
    
	public int checkLogin() {
		List<User> userList = userService.findByAll(user.getAccount(), user
				.getPassword(), null);
		if (userList != null && userList.size() == 1) {
			User user = userList.get(0);
			if (user.getStatus() != ElianUtils.STATUS_1)
				return 1;
			ApplicationUtils.setUser(user);
			List<Site> siteList = siteService.findByUser(user.getId());
			if (CollectionUtils.isEmpty(siteList))
				return 1;
			setDataInfoInSession(user, siteList);
			return 2;
		}
		else
			return 0;
	}
	
	protected void setDataInfoInSession(User user, List<Site> siteList) {
		Site site = siteList.get(0);
		ApplicationUtils.setSite(site);
		setActionInSession(user.getId());
		setRoleInSession(user.getId());
		if (ElianUtils.COMP_TYPE_HOSP.equals(site.getComType()))
			setHospitalInSession(site.getComId());
		else if (site.getComType().startsWith(ElianUtils.COMP_TYPE_COMP))
			setCompanyInSession(site.getComId());
		else if (ElianUtils.COMP_TYPE_SUBS.equals(site.getComType())
				|| ElianUtils.COMP_TYPE_MAIN.equals(site.getComType()))
			setSubstationInSession(site.getComId());
		ApplicationUtils.setImgFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));
		ApplicationUtils.setSwfFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.SWF_FTP));
		ApplicationUtils.setFileFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.FILE_FTP));
		ApplicationUtils.setStaticFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.STATIC_FTP));
	}

	private void setActionInSession(Integer userId) {
		List<Action> actionList = actionService.findByUser(userId);
		if (CollectionUtils.isEmpty(actionList))
			return;
		Set<String> actionSet = new HashSet<String>();
		for (Action action : actionList) {
			actionSet.add(action.getActionUrl());
		}
		ApplicationUtils.setActionSet(actionSet);
	}

	private void setRoleInSession(Integer userId) {
		List<UserRole> userRoleList = userRoleService
				.findUserRoleByUserId(userId);
		List<Role> roleList = new ArrayList<Role>();
		for (UserRole urole : userRoleList) {
			roleList.add(urole.getRole());
		}
		ApplicationUtils.setRoleList(roleList);
	}

	private void setHospitalInSession(int hospid) {
		ApplicationUtils.setHospital(hospitalService.get(hospid));

	}


	private void setCompanyInSession(int compId) {
		ApplicationUtils.setCompany(companyService.get(compId));

	}
	
	private void setSubstationInSession(int subId) {
		ApplicationUtils.setSubstation(substationService.get(subId));
	}

	public String loginOut() {
		ApplicationUtils.getSession().clear();
		return LOGIN_OUT;
	}

	// public String list() {
	// userService.findByAll(null, null, pagination);
	// return LIST;
	// }

	// public Pagination<User> getPagination() {
	// return pagination;
	// }

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public int getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public User getUser() {
		return user;
	}

	@Resource
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
	}

	@Resource	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setUser(User user) {
		this.user = user;
	}

/*	public Site getMainSite() {
		return mainSite;
	}

	public void setMainSite(Site mainSite) {
		this.mainSite = mainSite;
	}*/
}
