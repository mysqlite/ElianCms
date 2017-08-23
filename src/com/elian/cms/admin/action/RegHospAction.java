package com.elian.cms.admin.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.TypeService;
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
public class RegHospAction extends BaseAction {
	private static final long serialVersionUID = -1310823806665726877L;
	
	private User user;
	private UserType userType;
	private Site site;
	private Hospital hospital;
	private Integer userId;
	private Integer siteId;
	private Integer hospitalId;
	private Integer userTypeId;
	private String captcha;
	private String areaName;
	
	private UserService userService;
	private SiteService siteService;
	private UserTypeService userTypeService;
	private HospitalService hospitalService;
	private GradeService gradeService;
	private SiteUserService siteUserService;
	private TypeService typeService;
	private SiteFileService siteFileService;
	
	private List<Type> natureList ;
	private List<Type> hospTypeList;
	private List<Type> rankList;
	private String pwd;
	
	public String regHospital() {
		getList();
		return "reg";
	}

	public String saveHospatil() {
		    //保存医院信息
			hospital.setStatus(ElianUtils.STATUS_5);
    		hospitalService.save(hospital);
			// 设置及保存站点信息
			saveSite();
			// 设置及保存用户信息及扩展信息
			saveUser();
			// 保存站点用户映射
			saveSiteUser();
			
			if(StringUtils.isNotBlank(hospital.getPermitImg())) {
    			hospital.setPermitImg(UploadUtils.genertFilename(FilePathUtils.HospImg("hospital"),FilenameUtils.getName(hospital.getPermitImg())));
    			hospitalService.save(hospital);
    			siteFileService.saveFileToFtp(hospital,getPrevFile(),hospital.getPermitImg());
			}
			ApplicationUtils.removeSite();
			ApplicationUtils.removeUser();
			ApplicationUtils.getSession().clear();	
			if (ApplicationUtils.getSession().get("path") == null) {
				ApplicationUtils.getSession().put("path", ApplicationUtils.getRequest().getContextPath());
			}
		return "success";
	}
	
	public List<SelectItem> getSexList(){
		return ElianUtils.getSex();
	}
	
	private void saveSiteUser() {
		SiteUser siteUser=new SiteUser();
		siteUser.setSite(site);
		siteUser.setUser(user);
		siteUserService.save(siteUser);
	}
	
	private void saveSite() {
		// 设置及保存站点信息
		site = new Site();
		site.setComType(ElianUtils.COMP_TYPE_HOSP);
		site.setSiteName(hospital.getHospName());
		Grade grade=null;
		List<Grade> grades=gradeService.findByComType(ElianUtils.COMP_TYPE_HOSP,true,true);
		if(grades!=null&&!CollectionUtils.isEmpty(grades)) {
			grade=grades.get(0);
		}else {
			
		}
		site.setGrade(grade);
		site.setComId(hospital.getId());
		site.setSiteSort(99);
		site.setCreateTime(new Date());
		site.setStatus(ElianUtils.STATUS_5);
		site.setSiteSize(Long.parseLong("104857600"));
		site.setSiteUsedSize(Long.parseLong("0"));
		site.setDomain("hosp");//checkDomain(site.getSiteName(), "", siteService.getDomainSet(), 0)
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
		userService.save(user);
		ApplicationUtils.setUser(user);
	}
	
	public void initJson(){
		rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
		JSONObject obj = new JSONObject();
		obj.put("hosp_rank_list", rankList);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		obj.put("time",df.format(new Date()));
		ApplicationUtils.sendJsonpObj(obj);	
	}
	
	public void validateSaveHospatil() {
		if(user==null||hospital==null) {
			this.addFieldError("captcha", "验证码错误");
			getList();
			return;
		}
		if (!captcha.equals(ApplicationUtils.getCaptcha())) {
			this.addFieldError("captcha", "验证码错误");
			getList();
			return;
		}
		
		if(StringUtils.isBlank(hospital.getHospName()))
			this.addFieldError("hospital.hospName", "请填写医院名称");
		
		if(ValidateUtils.isLengOut(hospital.getHospName(),50))
			this.addFieldError("hospital.hospName", "名称长度在50字以内");
		
		if(ValidateUtils.isLengOut(hospital.getShortName(),30))
			this.addFieldError("hospital.shortName", "简称长度在30字以内");
		
		if(ValidateUtils.isLengOut(hospital.getPermitImg(), 255))
			this.addFieldError("hospital.permitImg", "许可证长度在255字以内");
		
		if(null==hospital.getNature())
			this.addFieldError("hospital.nature", "请选择医院性质");
		
		if(null==hospital.getHospType())
			this.addFieldError("hospital.hospType", "请选择医院类型");
		
		if(null==hospital.getRank())
			this.addFieldError("hospital.rank", "请选择医院等级");
		
		if(ValidateUtils.isLengOut(hospital.getShortDesc(),255))
			this.addFieldError("hospital.shortDesc", "简要介绍长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getHospDesc(), 2000))
			this.addFieldError("hospital.hospDesc", "详细介绍长度在2000字以内");
		
		if(!StringUtils.isBlank(hospital.getPhone())) {
    		if(ValidateUtils.isNotPhoneAndMobile(hospital.getPhone()))
    			this.addFieldError("hospital.phone", "请填写正确的联系电话");
		}else {
			this.addFieldError("hospital.phone", "请填写医院联系电话");
		}
		if (StringUtils.isBlank(hospital.getAddress()))
			this.addFieldError("hospital.address", "请填写医院地址");
		if(ValidateUtils.isLengOut(hospital.getAddress(), 255))
			this.addFieldError("hospital.address", "医院地址长度在255字以内");
		
    	if(StringUtils.isNotBlank(hospital.getSiteUrl())){
    		if(!ValidateUtils.isLengOut(hospital.getSiteUrl(), 255)) {
        		if(!ValidateUtils.isHttpUrl(hospital.getSiteUrl()))
        			this.addFieldError("hospital.siteUrl", "请填写正确的网址");
    		}
    		else 
    			this.addFieldError("hospital.siteUrl", "医院网址在255");
    	}
		if(StringUtils.isNotBlank(hospital.getEmail())) {
    		if(!ValidateUtils.isLengOut(hospital.getEmail(), 50)) {	
    			if(!ValidateUtils.isEmail(hospital.getEmail(), 6, 50))
    				this.addFieldError("hospital.email", "请填写正确的邮箱");
    		}
    		else 
    			this.addFieldError("hospital.email", "邮箱长度在50字以内");
		}
		if(StringUtils.isNotBlank(hospital.getFax())) {
			if(!ValidateUtils.isPhone(hospital.getFax()))
				this.addFieldError("hospital.fax", "请填写正确的传真号");
		}
		if(StringUtils.isNotBlank(hospital.getPostcode())) {
			if(!ValidateUtils.isLengOut(hospital.getPostcode(), 6)) {
    			if(!ValidateUtils.isPostCode(hospital.getPostcode()))
    				this.addFieldError("hospital.postcode", "请输入正确的邮政编码");
			}
			else this.addFieldError("hospital.postcode", "请输入正确的邮政编码");
		}
		
		if(null==hospital.getAreaId())
			this.addFieldError("hospital.areaId", "请选择医院所在省市区");
		
		if(hospital.getAreaId().equals(0))
			this.addFieldError("hospital.areaId", "请选择医院所在省市区");
		
		if(ValidateUtils.isLengOut(hospital.getLogoImg(), 255))
			this.addFieldError("hospital.logoImg","LOGO图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getHospImg(),255))
			this.addFieldError("hospital.hospImg","形象图片长度在255字以内");
		
		if(ValidateUtils.isLengOut(hospital.getBusLine(), 2000))
			this.addFieldError("hospital.busLine","BUS线路长度在2000字以内");
		
		if(ValidateUtils.isLengOut(hospital.getMapImg(),255))
			this.addFieldError("hospital.mapImg", "医院地图长度在255字以内");
		
		//用户
		User u = userService.findByAccout(user.getAccount());
		if (!StringUtils.isBlank(user.getAccount())) {
			if (u != null)
				this.addFieldError("user.account", "用户名已存在,请重新输入");
			else if(!ValidateUtils.accountFilter(user.getAccount(), 4, 16)) {
				this.addFieldError("user.account", "用户名只能为4-16位的小写字母及数字组合");
			}
		}
		if (StringUtils.isBlank(user.getPassword()))
			this.addFieldError("user.password", "密码不能为空");
		else if(!ValidateUtils.pwdFilter(user.getPassword(), 6, 20)) {
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
		// 真实姓名是否为全中文
		if (ValidateUtils.isChina(user.getRealName())) {
			// 真实姓名最大长度
			if (ValidateUtils.isLengOut(user.getRealName(), 16))
				this.addFieldError("user.realName", "真实名字在16字以内");
		}else {
			this.addFieldError("user.realName", "请输入真实姓名");
		}
		if (!StringUtils.isBlank(user.getPassword())| !StringUtils.isBlank(pwd)) {
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
		
		if(this.hasFieldErrors()) 
		 getList();
	}
	
	private void getList() {
		natureList=typeService.findByType(true,ElianUtils.HOSP_NATURE);
		hospTypeList=typeService.findByType(true,ElianUtils.HOSP_TYPE);
		rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
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
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
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
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
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

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
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

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public List<Type> getNatureList() {
		return natureList;
	}

	public void setNatureList(List<Type> natureList) {
		this.natureList = natureList;
	}

	public List<Type> getRankList() {
		return rankList;
	}

	public void setRankList(List<Type> rankList) {
		this.rankList = rankList;
	}
	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	public List<Type> getHospTypeList() {
		return hospTypeList;
	}

	public void setHospTypeList(List<Type> hospTypeList) {
		this.hospTypeList = hospTypeList;
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
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}
