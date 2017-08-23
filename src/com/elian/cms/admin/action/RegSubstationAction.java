package com.elian.cms.admin.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.GetPinyin;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegSubstationAction extends BaseAction {
	private static final long serialVersionUID = -1310823806665726877L;

	private User user;
	private UserType userType;
	private Site site;
	private Integer userId;
	private Integer siteId;
	private Integer substationId;
	private Integer userTypeId;
	private String mainSubs;
	private String captcha;
	private String areaName;

	private UserService userService;
	private SiteService siteService;
	private UserTypeService userTypeService;
	private GradeService gradeService;
	private SiteUserService siteUserService;
	private UserRoleService userRoleService;
	private RoleService roleService;
	private AreaService areaService;
	private SubstationService substationService;
	private Substation substation;
	private String pwd;

	public String regSubstation() {

		return "reg";
	}

	public String saveSubstation() {
		// 保存分站信息
		if (site.getComType().equals(ElianUtils.COMP_TYPE_MAIN))
			substation.setStatus(ElianUtils.STATUS_1);
		else
			substation.setStatus(ElianUtils.STATUS_5);
		substation.setCreateTime(new Date());
		substationService.save(substation);
		// 设置及保存站点信息
		saveSite();
		// 设置及保存用户信息
		saveUser();
		// 保存站点用户映射
		saveSiteUser();
		// 如果是主站，保存主站信息对于角色
		if (site.getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
			Role role = roleService.getRoleByComType(StringUtils.isNotBlank(site.getComType()) ? site.getComType(): ElianUtils.COMP_TYPE_SUBS, true);
			role.setSite(site);
			roleService.save(role);
			saveUserRole(role);
		}
		ApplicationUtils.removeSite();
		ApplicationUtils.removeUser();
		ApplicationUtils.getSession().clear();	
		if (ApplicationUtils.getSession().get("path") == null) {
			ApplicationUtils.getSession().put("path", ApplicationUtils.getRequest().getContextPath());
		}
		return "success";
	}

	private void saveUserRole(Role role) {
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleService.save(userRole);
	}

	private void saveSiteUser() {
		SiteUser siteUser = new SiteUser();
		siteUser.setSite(site);
		siteUser.setUser(user);
		siteUserService.save(siteUser);
	}

	private void saveUser() {
		if (site.getComType().equals(ElianUtils.COMP_TYPE_MAIN))
			user.setStatus(ElianUtils.STATUS_1);
		else
			user.setStatus(ElianUtils.STATUS_5);
		user.setUserType(userTypeService.get(user.getUserType().getId()));
		user.setRegisterTime(new Date());
		user.setRegisterIp(ApplicationUtils.getIpAddr());
		user.setLoginCount(0);
		user.setMiblePass(false);
		user.setPassword(LdMd5.MD5(user.getPassword()));
		userService.save(user);
		ApplicationUtils.setUser(user);
	}

	private void saveSite() {
		// 设置及保存站点信息
		site.setComType(StringUtils.isNotBlank(site.getComType()) ? site.getComType() : ElianUtils.COMP_TYPE_SUBS);
		site.setSiteName(substation.getSubName());
		Grade grade = null;
		List<Grade> grades = gradeService.findByComType(site.getComType(),true, true);
		if (grades != null) {
			grade = grades.get(0);
		}
		site.setGrade(grade);
		site.setComId(substation.getId());
		site.setSiteSort(99);
		site.setCreateTime(new Date());
		String domain = "";
		Area area = areaService.get(substation.getAreaId());
		domain = area.getAreaName();
		String domains = checkDomain(domain, null, siteService.getDomainSet(),
				0);
		site.setDomain(domains);
		site.setDomainPass(false);
		site.setSiteSize(Long.parseLong("104857600"));
		site.setSiteUsedSize(Long.parseLong("0"));
		if (site.getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
			site.setStatus(ElianUtils.STATUS_1);
			site.setDomain("www");
			site.setDomainPass(true);
		}
		else
			site.setStatus(ElianUtils.STATUS_5);
		siteService.save(site);
		ApplicationUtils.setSite(site);
	}

	public List<SelectItem> getSexList() {
		return ElianUtils.getSex();
	}

	private static String checkDomain(String siteName, String domain,
			Set<String> set, int i) {
		if (siteName.length() == 4 || siteName.length() == 6)
			siteName = siteName.substring(0, 3);
		else
			siteName = siteName.substring(0, 2);
		if (StringUtils.isBlank(domain))
			domain = GetPinyin.getPinYinHeadChar(siteName);
		if (set.contains(domain)) {
			i++;
			return checkDomain(siteName, GetPinyin.getPinYinHeadChar(siteName)+ i, set, i);
		}
		else {
			return domain;
		}
	}

	public void validateSaveSubstation() {
		if(user==null||substation==null) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
		if (!captcha.equals(ApplicationUtils.getCaptcha())) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
			if (null == substation.getAreaId() || substation.getAreaId() <= 0)
				this.addFieldError("substation.areaId", "区域名称不能为空");
			else {
				String areaNames = "";
				Area area = areaService.get(substation.getAreaId());
				areaNames = area.getAreaName();
				Substation sub = substationService
						.findSubstationByAreaCode(substation.getAreaId());
				if (sub != null)
					this.addFieldError("substation.areaId", areaNames+ "分站已注册");
			      }
			if (StringUtils.isBlank(substation.getSubName()))
				this.addFieldError("substation.subName", "分站名称不能为空");
			if (ValidateUtils.isLengOut(substation.getSubName(), 100))
//				this.addFieldError("substation.subName", "分站名称长度必须在100字以内");
//			if (StringUtils.isBlank(substation.getShortName()))
				this.addFieldError("substation.shortName", "分站简称不能为空");
			if (ValidateUtils.isLengOut(substation.getShortName(), 50))
				this.addFieldError("substation.shortName", "分站简称长度必须在50字以内");
			if (StringUtils.isBlank(substation.getShortDesc()))
				this.addFieldError("substation.shortDesc", "简介不能为空");
			if (ValidateUtils.isLengOut(substation.getShortDesc(), 255))
				this.addFieldError("substation.shortDesc", "简介长度必须在255字以内");
			if (StringUtils.isBlank(substation.getPhone()))
				this.addFieldError("substation.phone", "联系电话不能为空");
			if (StringUtils.isBlank(substation.getAddress()))
				this.addFieldError("substation.address", "地址不能为空");
			if (StringUtils.isBlank(substation.getPostcode())) {
				this.addFieldError("substation.postcode", "邮政编码不能为空");
			}
			else if (!ValidateUtils.isPostCode(substation.getPostcode()))
				this.addFieldError("substation.postcode", "请输入正确的邮政编码");
			if (!StringUtils.isBlank(substation.getEmail())) {
				if (!ValidateUtils.isEmail(substation.getEmail(), 6, 35))
					this.addFieldError("substation.email", "邮箱格式不正确");
				else if (ValidateUtils.isLengOut(substation.getEmail(), 40))
					this.addFieldError("substation.email", "邮箱最大长度为40位");
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
				if (!ValidateUtils.strLenth(user.getPassword(), 6, 20))
					this.addFieldError("user.password", "密码在6-20字以内");
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
						& !ValidateUtils
								.strLenth(user.getMedicalCard(), 10, 50))
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
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	@Resource
	public void setSiteUserService(SiteUserService siteUserService) {
		this.siteUserService = siteUserService;
	}

	@Resource
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	public void setSubstationService(SubstationService substationService) {
		this.substationService = substationService;
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSubstationId() {
		return substationId;
	}

	public void setSubstationId(Integer substationId) {
		this.substationId = substationId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Substation getSubstation() {
		return substation;
	}

	public void setSubstation(Substation substation) {
		this.substation = substation;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMainSubs() {
		return mainSubs;
	}

	public void setMainSubs(String mainSubs) {
		this.mainSubs = mainSubs;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
