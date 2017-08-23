package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dto.SiteAuditDto;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Images;
import com.elian.cms.admin.model.Log;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.Substation;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.LogService;
import com.elian.cms.admin.service.RoleActionService;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.SubstationService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class AuditAction extends BaseAction {
	private static final long serialVersionUID = -4171841891096589419L;
	private static final String NOTAUDIT="notAudit",AUDIT="audit",EXITAUDIT="exitAudit",DISABLE="disable",DELETE="delete";
	private Integer siteStatus=0;
	private User user;
	private UserType userType;
	private Substation substation;
	private SiteUser siteUser;
	private Site site;
	private Hospital hospital;
	private Integer userId;
	private Integer siteId;
	private Integer hospitalId;
	private Integer userTypeId;
	private String status;
	private Integer id;
	private String areaName="";
	private Company company=null;
	
	private List<Type> natureList ;
	private List<Type> hospTypeList;
	private List<Type> rankList;
	private String compType;
	private Role role;
	
	private UserRoleService userRoleService;
	private RoleService roleService;
	private AreaService areaService;
	private UserService userService;
	private LogService logService;
	private SiteService siteService;
	private HospitalService hospitalService;
	private SiteUserService siteUserService;
	private SubstationService substationService;
	private TypeService typeService;
	private SiteFileService siteFileService;
	private RoleActionService roleActionService;
	private CompanyService companyService=null;
	
	private Pagination<SiteUser> pagination = new Pagination<SiteUser>(SearchParamUtils.getAuditSiteUserConditionMap());
	private List<SiteAuditDto> siteAudit;
	public String list(){
		if(null==status)
			status=NOTAUDIT;
		List<SiteUser> siteUsers=null;
			if(status.equals(NOTAUDIT))
				siteStatus=5;
			else if(status.equals(AUDIT))
				siteStatus=1;
			else if(status.equals(EXITAUDIT))
				siteStatus=4;
			else if(status.equals(DISABLE))
				siteStatus=2;
			else if(status.equals(DELETE))
				siteStatus=3;
			else
				siteStatus=5;

			if(ApplicationUtils.isMainStation())//总站
				siteUsers=siteUserService.findAuditSiteBypagination(pagination,siteStatus,compType);
			if(ApplicationUtils.isSubStation())//分站
			    siteUsers=siteUserService.findAuditSiteBySubArea(pagination, areaService.findChirdByParent(ApplicationUtils.getSubstation().getAreaId()), siteStatus,compType);
			getsiteUserList(siteUsers);
		return LIST;
	}
	/**
	 * 此操作将删除如下数据:
	 * 1.站点用户映射表
	 * 2.角色表
	 * 3.站点表
	 * 4.用户表
	 * 5.组织信息表
	 * 6.用户角色表
	 * 7.日志表
	 * 8.图片表
	 * 9.角色权限表
	 * @return
	 */
	public String delete() {
		Integer id = ApplicationUtils.getAjaxId();
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer ids : idList) {
				delRegInfo(ids);
			}
		}
		else if(id!=null) {
			delRegInfo(id);
		}
	   list();
	   return LIST;
	}
	public  void delRegInfo(Integer id) {
		siteUser=siteUserService.get(id);
		List<SiteUser> siteUsers=siteUserService.findSiteUserBySite(siteUser.getSite().getId());
		List<UserRole> userRoles=userRoleService.findBySiteId(siteUser.getSite().getId());
		List<RoleAction>  roleActions=roleActionService.findBySiteId(siteUser.getSite().getId());
		List<Images> imgs=siteFileService.findByPage(null, siteUser.getSite().getId(), null, null);
		List<Role> roles=roleService.findByAll(siteUser.getSite().getId(), false, null);
		List<User> users=userService.findBySiteId(siteUser.getSite().getId());
		List<Log> logs=logService.findLogByAll(null, siteUser.getSite().getId(), null);
		                         site=siteUser.getSite();
		                         
		siteUserService.delete(siteUsers);
		userRoleService.delete(userRoles);
		roleActionService.delete(roleActions);
		siteFileService.delFtpImgs(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP), imgs);
		logService.delete(logs);
		roleService.delete(roles);
		siteService.delete(site);
		userService.delete(users);
		
		if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_HOSP)) {
			 hospital=hospitalService.get(siteUser.getSite().getComId());
			 hospitalService.delete(hospital);
		}
		else if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_SUBS)) {
			substation=substationService.get(siteUser.getSite().getComId());
			substationService.delete(substation);
		}
		else if(siteUser.getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {			
		}
		
	}
	
	public void disable() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		Integer id = ApplicationUtils.getAjaxId();
		if(idList!=null) {
			for (Integer ids : idList) {
				disableAudit(ids);
			}
		}else if(id!=null)
			disableAudit(id);
		list();
	}
	
	public String check() {
		Integer id = ApplicationUtils.getAjaxId();
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer ids : idList) {
				setSiteUserAudit(ids);
			}
		}
		else if(id!=null) {
			setSiteUserAudit(id);
		}
	   list();
	   return LIST;
	}
	
	public String exitCheck() {
		Integer id = ApplicationUtils.getAjaxId();
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer ids : idList) {
				siteUser=siteUserService.get(ids);
				if(!siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
    				setUserStatus(ElianUtils.STATUS_5,siteUser.getUser());
    				setSiteStatus(ElianUtils.STATUS_5, siteUser.getSite(),true);
    				List<UserRole> userRoles= userRoleService.findUserRoleByUserId(siteUser.getUser().getId());
    				if(userRoles!=null)
    				  userRoleService.delete(userRoles);
    				if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_HOSP)) {
    					hospital=hospitalService.get(siteUser.getSite().getComId());
    					setHospitalAudit(ElianUtils.STATUS_5,hospital);
    				}
    				else if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_SUBS)||siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
    					substation=substationService.get(siteUser.getSite().getComId());
    					setSubstationAudit(ElianUtils.STATUS_5,substation);
    				}
			    }
			}
		}
		else if(id!=null) {
			siteUser=siteUserService.get(id);
			setUserStatus(ElianUtils.STATUS_5,siteUser.getUser());
			setSiteStatus(ElianUtils.STATUS_5, site,true);
			List<UserRole> userRoles= userRoleService.findUserRoleByUserId(siteUser.getUser().getId());
			if(userRoles!=null)
			  userRoleService.delete(userRoles);
		}
	   list();
	   return LIST;
	}
	
	public String show() {
		showComp();
		return SHOW;
	}
	
	public String edit() {
		showComp();
		
		return EDIT;
	}
	
	public String save() {
		siteUser=siteUserService.get(siteUser.getId());
		if(!siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_MAIN)){
    		setUserStatus(ElianUtils.STATUS_4,siteUser.getUser());
    		setSiteStatus(ElianUtils.STATUS_4, siteUser.getSite(),false);
    		compType=siteUser.getSite().getComType();
    		List<UserRole> userRoles= userRoleService.findUserRoleByUserId(siteUser.getUser().getId());
    		if(userRoles!=null)
    		  userRoleService.delete(userRoles);
    		
    		if(compType.equals(ElianUtils.COMP_TYPE_HOSP)) {
    			Hospital hos= hospitalService.get(siteUser.getSite().getComId());
    			hos.setRemarks(hospital.getRemarks());
    			setHospitalAudit(ElianUtils.STATUS_4,hos);
    		}else if(compType.startsWith(ElianUtils.COMP_TYPE_COMP)){
    			Company comp= companyService.get(siteUser.getSite().getComId());
    			comp.setRemarks(company.getRemarks());
    			setCompanyAudit(ElianUtils.STATUS_4,comp);
    		}
    		else if(compType.equals(ElianUtils.COMP_TYPE_SUBS)|compType.equals(ElianUtils.COMP_TYPE_MAIN)) {
    			Substation sub=substationService.get(siteUser.getSite().getComId());
    			sub.setRemarks(substation.getRemarks());
    			setSubstationAudit(ElianUtils.STATUS_4,sub);
    		}
    		else{}
		}
		return list();
	}
	
	public List<SelectItem> getStatusList(){
		return ElianUtils.getStatusList();
	}

	public void validateSave() {
    	if(null!=hospital) {
    		if(StringUtils.isNotBlank(hospital.getRemarks())) {
    			if(ValidateUtils.isLengOut(hospital.getRemarks(), 255)) {
    				this.addFieldError("hospital.remarks", "退回说明在255字以内");
    			}
    		}
    		else {
    			this.addFieldError("hospital.remarks", "请填写退回说明");
			}
    	}
    	else if(null!=substation)	{
    		if(StringUtils.isNotBlank(substation.getRemarks())) {
    			if(ValidateUtils.isLengOut(substation.getRemarks(), 255)) {
    				this.addFieldError("substation.remarks", "退回说明在255字以内");
    			}
    		}
    		else {
    			this.addFieldError("substation.remarks", "请填写退回说明");
			}
    	}
    	if(this.hasErrors()) {
    		id=siteUser.getId();
    		edit();
    	}
	}
	
	private void disableAudit(Integer id) {
		if(null==id||id<=0)
			 list();
			siteUser=siteUserService.get(id);
			setUserStatus(ElianUtils.STATUS_2, siteUser.getUser());
			setSiteStatus(ElianUtils.STATUS_2, siteUser.getSite(),false);
			compType=siteUser.getSite().getComType();
			if(compType.equals(ElianUtils.COMP_TYPE_HOSP)) {
				setHospitalAudit(ElianUtils.STATUS_2, hospitalService.get(siteUser.getSite().getComId()));
			}
			else if(compType.equals(ElianUtils.COMP_TYPE_SUBS)|compType.equals(ElianUtils.COMP_TYPE_MAIN)) {
				setSubstationAudit(ElianUtils.STATUS_2,substationService.get(siteUser.getSite().getComId()));
			}
			else{}
	}
	
	private void setUserStatus(int status,User user) {
		user.setStatus(status);
		userService.save(user);
	}
	
	private void setSiteStatus(int status,Site site,boolean domainPass) {
		site.setDomainPass(domainPass);
		site.setStatus(status);
		siteService.save(site);
	}
	
	private void setHospitalAudit(int status,Hospital  hospital) {
		hospital.setStatus(status);
		hospital.setAuditor(ApplicationUtils.getAuditor());
		hospital.setAuditTime(new Date());
		hospitalService.save(hospital);
	}
	
	private void setCompanyAudit(int status,Company company) {
		company.setStatus(status);
		company.setAuditor(ApplicationUtils.getAuditor());
		company.setAuditTime(new Date());
		companyService.save(company);
	}
	
	private void setSubstationAudit(int status,Substation substation) {
		substation.setStatus(status);
		substation.setAuditor(ApplicationUtils.getAuditor());
		substation.setAuditTime(new Date());
		substationService.save(substation);
	}
	
	private void setSiteUserAudit(int id) {
		siteUser=siteUserService.get(id);
		site=siteUser.getSite();
		  setUserStatus(ElianUtils.STATUS_1,siteUser.getUser());
		  setSiteStatus(ElianUtils.STATUS_1,siteUser.getSite(),false);
		 compType=siteUser.getSite().getComType();
		 saveUserRole(compType);
		if(compType.equals(ElianUtils.COMP_TYPE_HOSP)) {
			setHospitalAudit(ElianUtils.STATUS_1, hospitalService.get(siteUser.getSite().getComId()));
		}else if(compType.startsWith(ElianUtils.COMP_TYPE_COMP)) {
			setCompanyAudit(ElianUtils.STATUS_1, companyService.get(siteUser.getSite().getComId()));
		}
		else if(compType.equals(ElianUtils.COMP_TYPE_SUBS)|compType.equals(ElianUtils.COMP_TYPE_MAIN)) {
			setSubstationAudit(ElianUtils.STATUS_1,substationService.get(siteUser.getSite().getComId()));
		}
		else{}	
	}
	
	private void saveUserRole(String compType) {
		//保存用户角色映射
	    role=roleService.getRoleByComType(compType, true);
		UserRole userRole=new UserRole();
		userRole.setRole(role);
		userRole.setUser(siteUser.getUser());
		userRoleService.save(userRole);
	}
	
	private void showComp() {
		if(id!=null&&id>0) {
			 siteUser=siteUserService.get(id);
			 user=siteUser.getUser();
			 site=siteUser.getSite();
			 if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_HOSP)) {
				 hospital=hospitalService.get(siteUser.getSite().getComId());
				 areaName=getAreaNames(hospital.getAreaId());
				 natureList=typeService.findByTypePage(null, ElianUtils.HOSP_NATURE);
			     hospTypeList=typeService.findByTypePage(null, ElianUtils.HOSP_TYPE);
			     rankList=typeService.findByTypePage(null, ElianUtils.HOSP_RANK);
			 }
			 else if(siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_SUBS)|siteUser.getSite().getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
				 substation=substationService.get(siteUser.getSite().getComId());
				 areaName=getAreaNames(substation.getAreaId());
			 }
			 else if(siteUser.getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {
				 company=companyService.get(siteUser.getSite().getComId());
			 }
			}
	}
	
	private void getsiteUserList(List<SiteUser> siteUsers) {
		SiteAuditDto auditDto=null;
		Hospital hospitals=null;
		Substation substations=null;
		Company company=null;
		siteAudit=new ArrayList<SiteAuditDto>();
		if(siteUsers!=null)
		for (int i = 0,len=siteUsers.size(); i < len; i++) {
			auditDto=new SiteAuditDto();
			auditDto.setSiteUser(siteUsers.get(i));
			if(siteUsers.get(i).getSite().getComType().equals(ElianUtils.COMP_TYPE_HOSP)) {
				hospitals=hospitalService.get(siteUsers.get(i).getSite().getComId());
				if(hospitals!=null) {
    				auditDto.setCompName(hospitals.getHospName());
    			    auditDto.setStatus(hospitals.getStatus());
    				auditDto.setCompId(hospitals.getId());
    				auditDto.setAuditor(hospitals.getAuditor());
    				auditDto.setAuditTime(hospitals.getAuditTime());
				}
			}
			else if(siteUsers.get(i).getSite().getComType().equals(ElianUtils.COMP_TYPE_SUBS)|siteUsers.get(i).getSite().getComType().equals(ElianUtils.COMP_TYPE_MAIN)) {
				substations=substationService.get(siteUsers.get(i).getSite().getComId());
				if(substations!=null) {
    				auditDto.setCompName(substations.getSubName());
    				auditDto.setStatus(substations.getStatus());
    				auditDto.setCompId(substations.getId());
    				auditDto.setAuditor(substations.getAuditor());
    				auditDto.setAuditTime(substations.getAuditTime());
				}
			}
			else  if(siteUsers.get(i).getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP)){
				//企业
				company=companyService.get(siteUsers.get(i).getSite().getComId());
				if(company!=null) {
    				auditDto.setCompName(company.getName());
    				auditDto.setStatus(company.getStatus());
    				auditDto.setCompId(company.getId());
    				auditDto.setAuditor(company.getAuditor());
    				auditDto.setAuditTime(company.getAuditTime());
				}
			}
		       siteAudit.add(auditDto);
		}
	}
	
	public List<SelectItem> getCompTypeList() {
		return ElianUtils.getCompTypeList();
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
	
	private List<SelectItem> comList = null;

	public List<SelectItem> getComList() {
		if (ApplicationUtils.getSite().getComType().equals(
				ElianUtils.COMP_TYPE_MAIN)) {
			if (comList == null) {
				comList = new ArrayList<SelectItem>(4);
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_SUBS,ElianUtils.COMP_TYPE_SUBS_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_HOSP,ElianUtils.COMP_TYPE_HOSP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_MEDICINE_COMP,ElianUtils.COMP_TYPE_MEDICINE_COMP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN));
			}
		}
		else if (ApplicationUtils.getSite().getComType().equals(
				ElianUtils.COMP_TYPE_SUBS)) {
			if (comList == null) {
				comList = new ArrayList<SelectItem>(4);
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_HOSP,ElianUtils.COMP_TYPE_HOSP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_MEDICINE_COMP,ElianUtils.COMP_TYPE_MEDICINE_COMP_CN));
				comList.add(new SelectItem(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN));
			}
		}
		return comList;
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
	
	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	@Resource
	public void setSiteUserService(SiteUserService siteUserService) {
		this.siteUserService = siteUserService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}
	
	@Resource
	public void setRoleActionService(RoleActionService roleActionService) {
		this.roleActionService = roleActionService;
	}
	
	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public List<Type> getNatureList() {
		return natureList;
	}

	public void setNatureList(List<Type> natureList) {
		this.natureList = natureList;
	}

	public List<Type> getHospTypeList() {
		return hospTypeList;
	}

	public void setHospTypeList(List<Type> hospTypeList) {
		this.hospTypeList = hospTypeList;
	}

	public List<Type> getRankList() {
		return rankList;
	}

	public void setRankList(List<Type> rankList) {
		this.rankList = rankList;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public Pagination<SiteUser> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<SiteUser> pagination) {
		this.pagination = pagination;
	}

	public List<SiteAuditDto> getSiteAudit() {
		return siteAudit;
	}

	public void setSiteAudit(List<SiteAuditDto> siteAudit) {
		this.siteAudit = siteAudit;
	}
	
	public Substation getSubstation() {
		return substation;
	}
	
	public void setSubstation(Substation substation) {
		this.substation = substation;
	}
	
	public SiteUser getSiteUser() {
		return siteUser;
	}
	
	public void setSiteUser(SiteUser siteUser) {
		this.siteUser = siteUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
}
