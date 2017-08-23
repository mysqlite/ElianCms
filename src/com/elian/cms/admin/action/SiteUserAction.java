package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.model.UserRole;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.admin.service.UserRoleService;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;

/**
 * 站点用户功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SiteUserAction extends BaseAction {
	private static final long serialVersionUID = 214141679090984188L;

	private Pagination<User> pagination = new Pagination<User>(SearchParamUtils
			.getUserActionConditionMap());
	private Integer roleId;
	private List<SelectItem> userStatusList = null;

	private UserService userService;
	private RoleService roleService;
	private SiteUserService siteUserService;
	private UserRoleService userRoleService;

	public String list() {
		if (roleId != null && roleId > 0) {
			userService.findBySiteAndRole(ApplicationUtils.getSite().getId(),
					roleId, pagination);
			return "userList";
		}
		return LIST;
	}

	public void addRole() {
		Role role = createRole();
		role.setCompType(ApplicationUtils.getRequest().getParameter(
						"compType"));
		role.setRoleName(ApplicationUtils.getRequest().getParameter(
						"roleName"));
		roleService.save(role);

		JSONObject json = new JSONObject();
		json.put("ID", role.getId());
		json.put("NAME", role.getRoleName());
		json.put("PID", 0);
		ApplicationUtils.sendJsonStr(json.toString());
	}

	public void updateRole() {
		Integer id = ApplicationUtils.getAjaxId();
		if (id != null && id > 0) {
			Role role = roleService.get(id);
			role.setRoleName(ApplicationUtils.getRequest().getParameter(
					"roleName"));
			roleService.save(role);
		}
	}

	public void deleteRole() {
		Integer id = ApplicationUtils.getAjaxId();
		if (id != null && id > 0) {
			roleService.deleteById(id);
		}
	}

	private Role createRole() {
		Role role = new Role();
		role.setDisable(true);
		role.setRoleSort(99);
		role.setCreateTime(new Date());
		return role;
	}

	public void roleTreeModel() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Site site = ApplicationUtils.getSite();
		addRootNode(site, list);
		List<Role> roleList = roleService.findByCompType(site.getComType());
		addRoleNode(roleList, list);
		ApplicationUtils.sendJsonArray(list);
	}

	private void addRootNode(Site site, List<Map<String, Object>> list) {
		list.add(createDataMap(0, site.getSiteName(), null, site.getComType()));
	}

	private void addRoleNode(List<Role> roleList, List<Map<String, Object>> list) {
		for (Role role : roleList) {
			list.add(createDataMap(role.getId(), role.getRoleName(), 0, null));
		}
	}

	private Map<String, Object> createDataMap(Object id, Object name,
			Object pId, Object compType) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ID", id);
		map.put("NAME", name);
		map.put("PID", pId);
		map.put("COMP_TYPE", compType);
		return map;
	}
	
	public void deleteUser(){
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			User user = null;
			SiteUser siteUser = null;
			UserRole userRole = null;
			for (Integer id : idList) {
				user = userService.get(id);
				if (user == null)
					continue;
				
				siteUser = siteUserService.findByUserId(id);
				if (siteUser != null) {
					siteUserService.delete(siteUser);
				}
				
				userRole = userRoleService.findByUserId(id);
				if (userRole != null) {
					userRoleService.delete(userRole);
				}
				
				userService.delete(user);
			}
		}
	}
	
	public List<SelectItem> getUserStatusList() {
		if (userStatusList == null) {
			userStatusList = new ArrayList<SelectItem>(4);
			userStatusList.add(new SelectItem(ElianUtils.STATUS_0,ElianUtils.STATUS_0_CN, ElianCodes.COLOR_RED));
			userStatusList.add(new SelectItem(ElianUtils.STATUS_1,ElianUtils.STATUS_1_CN, ElianCodes.COLOR_GREEN));
			userStatusList.add(new SelectItem(ElianUtils.STATUS_2,ElianUtils.STATUS_2_CN, ElianCodes.COLOR_RED));
			userStatusList.add(new SelectItem(ElianUtils.STATUS_3,ElianUtils.STATUS_3_CN));
		}
		return userStatusList;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource
	public void setSiteUserService(SiteUserService siteUserService) {
		this.siteUserService = siteUserService;
	}

	@Resource
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	public Pagination<User> getPagination() {
		return pagination;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return roleId;
	}
}
