package com.elian.cms.admin.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.IdCardUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.SysXmlUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 8798964498069489518L;
	
	private Pagination<User> pagination = new Pagination<User>(SearchParamUtils.getUserActionConditionMap());
	private Integer id;
	private String pwd;
	private User user;
	private String rawPwd;
	private String areaName = "";
	private String initialPassword;
	private boolean edit;
	private int status;
	private String roleIds;
	private String roleName;
	
	private UserService userService;
	private Area area;
	private boolean myInfo;
	private AreaService areaService ;
	private int roleId;
	private int siteId;
	private boolean siteUserAdd;
	private boolean thisSiteUser;
	
	private SiteUserService siteUserService;
	private UserRoleService userRoleService;
	private UserTypeService userTypeService;
	private RoleService roleService;
	private SiteService siteService;
	
	public String list() {
		if(ApplicationUtils.isHosp()) //待扩展
			userService.findBySiteAndRole(ApplicationUtils.getSite().getId(), null, pagination);
		else if(ApplicationUtils.isMainStation()&&!thisSiteUser)
			userService.findByAll(pagination);
		else if(ApplicationUtils.isSubStation()&&!thisSiteUser)
		   userService.findBySubstation(pagination, ApplicationUtils.getSubstation().getAreaId());
		else if(thisSiteUser)
			userService.findBySiteAndRole(ApplicationUtils.getSite().getId(), null, pagination);
		return LIST;
	}

	public String edit() {
		if (myInfo)
			id = ApplicationUtils.getUser().getId();
		roleName="";
		roleIds="";
		if (edit && id > 0) {
			user=new User();
			BeanUtils.copyProperties(userService.get(id), user);
			user.setIntro(FilePathUtils.setEditorOutPath(user.getIntro()));
			List<UserRole> userRoles=userRoleService.findUserRoleByUserId(user.getId());
			for (int i = 0,len=userRoles.size(); i < len; i++) {
				if(i<len-1) {
				roleName+=userRoles.get(i).getRole().getRoleName()+",";
				roleIds+=userRoles.get(i).getRole().getId()+",";
				}
				else {
				roleName+=userRoles.get(i).getRole().getRoleName();
				roleIds+=userRoles.get(i).getRole().getId();
				}
			}
			
			initialPassword=user.getPassword();
			if (user != null) {
				if (null != user.getComefrom())
					areaName = getAreaNames(user.getComefrom());
			}
		}
		return EDIT;
	}
	
	public String show() {
		if (id > 0) {
			user=new User();
			BeanUtils.copyProperties(userService.get(id), user);
			if (user != null) {
				if(StringUtils.isNotBlank(user.getIntro())) {
					user.setIntro(FilePathUtils.setEditorOutPath(user.getIntro()));
				}
				if (null != user.getComefrom())
					areaName = getAreaNames(user.getComefrom());
			}
			return SHOW;
		}
		else {
			return SAVE;
		}
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

	public String save() {
		saveUser();
		if (siteUserAdd) {
			//saveUserRole(roleService.get(roleId));
			saveSiteUser(siteService.get(id));
		}
		else if(!edit) {
			saveSiteUser(siteService.get(ApplicationUtils.getSite().getId()));
		}
		if(user.getId().equals(ApplicationUtils.getUser().getId()))
			ApplicationUtils.setUser(user);
		
		if(edit) {
			List<UserRole> userRoles= userRoleService.findUserRoleByUserId(user.getId());
			userRoleService.delete(userRoles);
		}
			saveUserRole();
			
		if (myInfo) {
			this.id = user.getId();
			this.areaName = "";
			return show();
		}
		else if (siteUserAdd) {
			return "siteUserList";
		}
		else
			return SAVE;
	}
	
	public boolean checkAccount(String account,HttpServletRequest request) {
		if(StringUtils.isBlank(account)) 
			return false;
		if(!ValidateUtils.accountFilter(account, 4, 20))
		   return false;
		if(userService==null) 
			userService=(UserService) SpringUtils.getEntityService(User.class);
		User u = userService.findByAccout(account);
			if (u != null)
			   return false;
			else
			  return true;
	}
	
	
	private void saveUserRole() {
		UserRole userRole=null;
		int[] roleIds=StringUtils.getSplitByString(this.roleIds, ",");
		if(roleIds!=null)
		for (int i : roleIds) {
			userRole=new UserRole();
			userRole.setRole(roleService.get(i));
			userRole.setUser(user);
			userRoleService.save(userRole);
		}
	}
	
	public List<SelectItem> getDisableStatus(){
		return ElianUtils.getDisableStatus();
	}
	public List<SelectItem> getSexList(){
		return ElianUtils.getSexList();
	}
	
	private void saveSiteUser(Site siteUserSite) {
		//保存站点用户映射
		SiteUser siteUser=new SiteUser();
		siteUser.setSite(siteUserSite);
		siteUser.setUser(user);
		siteUserService.save(siteUser);
	}
	
	/*private void saveUserRole(Role SiteUserrole) {
		//保存用户角色映射
		UserRole userRole=new UserRole();
		userRole.setRole(SiteUserrole);
		userRole.setUser(user);
		userRoleService.save(userRole);
		logService.save(LogParamUtils.OPERATION_UPDATE,LogParamUtils.USER_ROLE_ADD_CN, LogParamUtils.USER_ROLE_ID,userRole.getId().toString());
	}*/
	
	private void saveUser() {
		if(!edit) {//如果是添加状态，设置注册ip，注册时间，登录次数
			user.setRegisterIp(ApplicationUtils.getIpAddr());
			user.setRegisterTime(StringUtils.getSystDate());
			user.setLoginCount(0);
		}
		user.setUserType(userTypeService.get(user.getUserType().getId()));//获取用户类型
		if (!StringUtils.isBlank(user.getPassword()))
			user.setPassword(LdMd5.MD5(user.getPassword()));
		else
			user.setPassword(initialPassword);
		user.setIntro(FilePathUtils.getConContext(user.getIntro()));
		userService.save(user);
		siteFileService.saveConContext(user,getEditorPrevImg(),user.getIntro());
		siteFileService.saveFileToFtp(user,getPrevFile(),user.getIdCardImg(),user.getUserImg());
	}
	
	/**是否有密码初始化的权限*/
	public boolean isInitialization(){
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(getEntityName() + "!initialization");
	}
	
	public String initialPwd() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if(idList!=null)
			id=idList.get(0);
		if(id!=null&&id>0) {
		user=userService.get(id);
		pwd=SysXmlUtils.getString("initialPwd");
		user.setPassword(LdMd5.MD5(pwd));
		userService.save(user);
		if(ApplicationUtils.getUser().getId().equals(id)) {
			ApplicationUtils.setUser(user);
		}
		}
		return NONE;
	}
	
	public String status() {
		Integer id = ApplicationUtils.getAjaxId();
		int status = ApplicationUtils.getAjaxStatus();
		if (id != null && status > -1) {
			user = userService.get(id);
			if (user == null)
				return NONE;
			if(ApplicationUtils.getSite().getComType()==ElianUtils.COMP_TYPE_MAIN||!ApplicationUtils.getUser().getId().equals(id)) {
			user.setStatus(status);
			userService.save(user);
			}
		}
		return NONE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				user = userService.get(id);
				user.setStatus(ElianUtils.STATUS_3);
				userService.save(user);
			}
		return NONE;
	}
	
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				user = userService.get(id);
				if (user.getStatus() == ElianUtils.STATUS_0) {
					user.setStatus(ElianUtils.STATUS_1);
					userService.save(user);
				}
			}
	}
	
	public  void findRoleBySiteId() {
		List<Role> roleList=new ArrayList<Role>();
		List<Role> r=roleService.findByAll(ApplicationUtils.getSite().getId(), false);
		Role nRole=null;
		for (Role role : r) {
			nRole=new Role();
			BeanUtils.copyProperties(role, nRole);
			nRole.setSite(null);
			nRole.setRoleActionSet(null);
			roleList.add(nRole);
		}
		JSONObject obj=new JSONObject();
		obj.put("role", roleList);
		ApplicationUtils.sendJsonpObj(obj);		
	}
	
	public void validateSave() {
		// 添加
		if (!edit) {
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
		}
    	 if(!siteUserAdd&!myInfo)	{
    		if(StringUtils.isBlank(roleIds))
    			this.addFieldError("roleIds", "请选择所属组");
    	 }
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
	}
	
	public Pagination<User> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<User> pagination) {
		this.pagination = pagination;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<SelectItem> getUserStatusList() {
		return ElianUtils.getStatusList();
	}
	
	public List<SelectItem> getMainUserStatusList() {
		return ElianUtils.getAvailableList();
	}
	
	 private SiteFileService siteFileService;
	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRawPwd() {
		return rawPwd;
	}

	public void setRawPwd(String rawPwd) {
		this.rawPwd = rawPwd;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isMyInfo() {
		return myInfo;
	}

	public void setMyInfo(boolean myInfo) {
		this.myInfo = myInfo;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public boolean isSiteUserAdd() {
		return siteUserAdd;
	}

	public void setSiteUserAdd(boolean siteUserAdd) {
		this.siteUserAdd = siteUserAdd;
	}
	
	public String getInitialPassword() {
		return initialPassword;
	}

	public void setInitialPassword(String initialPassword) {
		this.initialPassword = initialPassword;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isThisSiteUser() {
		return thisSiteUser;
	}

	public void setThisSiteUser(boolean thisSiteUser) {
		this.thisSiteUser = thisSiteUser;
	}
}
