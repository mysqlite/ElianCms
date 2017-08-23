package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.common.upload.UploadUtils;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class RegCompanyAction extends BaseAction {
	private static final long serialVersionUID = -4211772514504735505L;
	private User user;
	// private UserType userType;
	private Site site = null;
	private Company company = null;
	// private Integer userId;
	// private Integer siteId;
	// private Integer hospitalId;
	// private Integer userTypeId;
	private String captcha;
	// private String areaName;
	//	
	private UserService userService;
	private SiteService siteService;
	private UserTypeService userTypeService;
	private CompanyService companyService;
	private GradeService gradeService;
	private SiteUserService siteUserService;
	// private TypeService typeService;
	//private FtpService ftpService;
	private SiteFileService siteFileService;
	private AreaService areaService;
	
	//	
	//private List<SelectItem> natureList ;
	// private List<Type> hospTypeList;
	// private List<Type> rankList;
	private String pwd;

	public String reg() {
		company=new Company();
		company.setType(ElianUtils.COMP_TYPE_MEDICINE_COMP);
		return "reg";
	}

	public String saveCompany() {
		// 保存企业信息
		prepareSaveCompany();
		companyService.save(company);
		// 保存站点
		saveSite();
		// 设置及保存用户信息及扩展信息
		saveUser();
		// 保存站点用户映射
		saveSiteUser();
		
		if(StringUtils.isNotBlank(company.getPermitImg())) {
			company.setPermitImg(UploadUtils.genertFilename(FilePathUtils.HospImg("company"),FilenameUtils.getName(company.getPermitImg())));
			companyService.save(company);
			siteFileService.saveFileToFtp(company,getPrevFile(),company.getPermitImg());
		}
		ApplicationUtils.removeSite();
		ApplicationUtils.removeUser();
		ApplicationUtils.getSession().clear();	
		if (ApplicationUtils.getSession().get("path") == null) {
			ApplicationUtils.getSession().put("path", ApplicationUtils.getRequest().getContextPath());
		}
		return "success";
	}

	private void prepareSaveCompany() {
		company.setStatus(ElianUtils.STATUS_5);
		company.setCreateTime(new Date());
		company.setHits(0);
		Integer areaId=(null==company.getArea().getAreaCode()?0:company.getArea().getAreaCode());
		Area area= areaService.get(areaId);
		company.setArea(area);
		if(StringUtils.isBlank(company.getAddress())){
			String address=(area==null?"中国":area.getAreaName());
			company.setAddress(address);
		}
	}

	public List<SelectItem> getSexList() {
		return ElianUtils.getSex();
	}

	private void saveSiteUser() {
		SiteUser siteUser = new SiteUser();
		siteUser.setSite(site);
		siteUser.setUser(user);
		siteUserService.save(siteUser);
	}

	private void saveSite() {
		// 设置及保存站点信息
		site = new Site();
		site.setComType(company.getType());
		site.setSiteName(company.getName());
		Grade grade = null;
		List<Grade> grades = gradeService.findByComType(company.getType(), true, true);
		if (grades != null) {
			grade = grades.get(0);
		}
		site.setGrade(grade);
		site.setComId(company.getId());
		site.setSiteSort(99);
		site.setCreateTime(new Date());
		site.setStatus(ElianUtils.STATUS_5);
		site.setSiteSize(Long.parseLong("104857600"));
		site.setSiteUsedSize(Long.parseLong("0"));
		site.setDomain("comp");// checkDomain(site.getSiteName(), "",
							   // siteService.getDomainSet(), 0)
		site.setDomainPass(false);
		siteService.save(site);
		ApplicationUtils.setSite(site);
	}

	private void saveUser() {
		// 设置及保存用户信息
		user.setStatus(ElianUtils.STATUS_5);
		user.setUserType(userTypeService.get(user.getUserType().getId()));
		user.setRegisterTime(new Date());
		user.setRegisterIp(ApplicationUtils.getIpAddr());
		user.setLoginCount(0);
		user.setMiblePass(false);
		user.setPassword(LdMd5.MD5(user.getPassword()));
		if(StringUtils.isBlank(user.getRealName())){
			user.setRealName(user.getAccount());
		}
		userService.save(user);
		ApplicationUtils.setUser(user);
	}

	public void initJson() {
		/*
		 * rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
		 * JSONObject obj = new JSONObject(); obj.put("hosp_rank_list",
		 * rankList); SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 * obj.put("time",df.format(new Date()));
		 * ApplicationUtils.sendJsonpObj(obj);
		 */
	}

	public void validateSaveCompany() {
		if(user==null||company==null) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
		if (!captcha.equals(ApplicationUtils.getCaptcha())) {
			this.addFieldError("captcha", "验证码错误");
			return;
		}
		
		if (StringUtils.isBlank(company.getName())) {
			this.addFieldError("company.name", "企业名称不能为空");
		}
		else if (ValidateUtils.isLengOut(company.getName(), 45)) {
			this.addFieldError("company.name", "企业名称在45字以内");
		}
		
		if (StringUtils.isBlank(company.getType())) {
			this.addFieldError("company.type", "请选择企业类型");
		}
		else if (ValidateUtils.isLengOut(company.getType(), 20)) {
			this.addFieldError("company.type", "企业类型在20字以内");
		}
		
		if(null==company.getArea())
			this.addFieldError("company.area.id", "请选择医院所在省市区");
		
		if(company.getArea().getId().equals(0))
			this.addFieldError("company.area.id", "请选择医院所在省市区");
		
		if (!StringUtils.isBlank(company.getPhone())) {
			if (ValidateUtils.isNotPhoneAndMobile(company.getPhone()))
				this.addFieldError("company.phone", "请填写正确的联系电话");
		}

		if (ValidateUtils.isLengOut(company.getAddress(), 250))
			this.addFieldError("company.address", "企业地址长度在250字以内");

		if (ValidateUtils.isLengOut(company.getPermitImg(), 250))
			this.addFieldError("company.permitImg", "形象图片长度在250字以内");

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
		if (user.getUserType().getId() == null)
			this.addFieldError("user.userType.id", "请选择用户类型");
		// 用户名是否为空
		if (StringUtils.isBlank(user.getAccount()))
			this.addFieldError("user.account", "用户名不能为空");
		// 用户名最大长度
		if (ValidateUtils.isLengOut(user.getAccount(), 20))
			this.addFieldError("user.account", "用户名在20字以内");
//		// 真实姓名是否为全中文
//		if (ValidateUtils.isChina(user.getRealName())) {
//			// 真实姓名最大长度
//			if (ValidateUtils.isLengOut(user.getRealName(), 16))
//				this.addFieldError("user.realName", "真实名字在16字以内");
//		}
//		else {
//			this.addFieldError("user.realName", "请输入真实姓名");
//		}
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
		if (StringUtils.isBlank(user.getEmail())) {
			this.addFieldError("user.email", "请填写邮箱号码");
		}else{
			if (!ValidateUtils.isEmail(user.getEmail(), 6, 40))
				this.addFieldError("user.email", "邮箱格式不正确");
			else if (ValidateUtils.isLengOut(user.getEmail(), 40))
				this.addFieldError("user.email", "邮箱在6-40字以内");
		}
		
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
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

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

//	@Resource
//	public void setFtpService(FtpService ftpService) {
//		this.ftpService = ftpService;
//	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@Resource
	public void setSiteUserService(SiteUserService siteUserService) {
		this.siteUserService = siteUserService;
	}

	@Resource
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public List<SelectItem> getNatureList() {
		return ElianUtils.getCompanyTypeList();
	}
}
