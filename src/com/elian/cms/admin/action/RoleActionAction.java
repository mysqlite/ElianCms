package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Action;
import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.model.RoleAction;
import com.elian.cms.admin.service.ActionService;
import com.elian.cms.admin.service.RoleActionService;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 角色权限管理功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class RoleActionAction extends BaseAction {
	private static final long serialVersionUID = 3708716107838369613L;
	
	private Role role;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Role> pagination = new Pagination<Role>(SearchParamUtils
			.getRoleActionConditionMap());

	private RoleActionService roleActionService;
	private RoleService roleService;
	private ActionService actionService;

	public String list() {
		roleService.findByAll(ApplicationUtils.getSite().getId(), true,
				pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			role = roleService.get(id);
		}
		else {
			role = new Role();
		}
		return EDIT;
	}

	public void validateSave() {
		Integer roleId = role.getId();
		if (roleId == null)
			this.addFieldError("role.id", "请选择角色！");
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			this.addFieldError("actionIds", "请选择权限！");
	}

	public String save() {
		if (isEdit) {
			List<RoleAction> raList = roleActionService.findByAll(role.getId(),
					null);
			doDelete(raList);
		}
		return doSave();
	}

	public String doSave() {
		List<RoleAction> roleActionList = createRoleAction(role.getId());
		if (roleActionList == null || roleActionList.size() < 1) {
			return SAVE;
		}
		for (RoleAction roleAction : roleActionList) {
			roleActionService.save(roleAction);
		}
		return SAVE;
	}

	public List<RoleAction> createRoleAction(Integer roleId) {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return null;
		List<Action> actionList = actionService.get(idList);
		if (CollectionUtils.isEmpty(actionList))
			return null;
		addActionParent(idList, actionList);
		Role role = roleService.get(roleId);
		List<RoleAction> roleActionList = new ArrayList<RoleAction>();
		Set<Integer> idSet = getAuthorizeIds();
		for (Action action : actionList) {
			roleActionList.add(createRoleAction(role, action, idSet));
		}
		return roleActionList;
	}
	
	public Set<Integer> getAuthorizeIds() {
		String ids = ApplicationUtils.getRequest().getParameter("authorizeIds");
		Set<Integer> idSet = new HashSet<Integer>();
		if (StringUtils.isNotBlank(ids)) {
			String[] items = ids.split(",");
			for (String id : items) {
				idSet.add(Integer.valueOf(id));
			}
		}
		return idSet;
	}

	public void addActionParent(List<Integer> idList, List<Action> actionList) {
		List<Action> parentList = new ArrayList<Action>(actionList);
		for (Action action : parentList) {
			addParent(action, idList, actionList);
		}
	}

	public void addParent(Action action, List<Integer> idList,
			List<Action> actionList) {
		Integer parentId = action.getParentId();
		if (parentId > 0 && !idList.contains(parentId)) {
			Action act = actionService.get(parentId);
			if (act != null)
				actionList.add(act);
			addParent(act, idList, actionList);
		}
	}

	public RoleAction createRoleAction(Role role, Action action, Set<Integer> idSet) {
		RoleAction roleAction = new RoleAction();
		roleAction.setRole(role);
		roleAction.setAction(action);
		if(idSet.contains(action.getId())){
			roleAction.setAuthorize(true);
		}
		return roleAction;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return NONE;
		List<RoleAction> raList = roleActionService.findByRole(idList);
		return doDelete(raList);
	}

	public String doDelete(List<RoleAction> raList) {
		if (CollectionUtils.isEmpty(raList))
			return NONE;
		for (RoleAction roleAction : raList) {
			roleActionService.delete(roleAction);
		}
		return NONE;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Pagination<Role> getPagination() {
		return pagination;
	}

	@Resource
	public void setRoleActionService(RoleActionService roleActionService) {
		this.roleActionService = roleActionService;
	}

	@Resource
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	@Resource
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRoleList() {
		return roleService.findByAll(ApplicationUtils.getSite().getId(), true,
				null);
	}
	
	public List<SelectItem> getCompTypeList() {
		return ElianUtils.getCompTypeList();
	}

	public void treeModel() {
		List<Action> actionList = null;
		Integer roleId = ApplicationUtils.getAjaxId();
		if (roleId != null) {
			actionList = actionService.findByCompType(ApplicationUtils
					.getSite().getComType(), ApplicationUtils.isMainStation());
			selectActionList(roleId, actionList);
		}
		ApplicationUtils.sendJsonArray(actionList);
	}

	public void selectActionList(Integer roleId, List<Action> actionList) {
		if (CollectionUtils.isEmpty(actionList))
			return;
		List<RoleAction> raList = roleActionService.findByAll(roleId, null);
		if (CollectionUtils.isEmpty(raList))
			return;
		for (Action action : actionList) {
			for (RoleAction ra : raList)
				if (action.equals(ra.getAction())) {
					action.setSelected(true);
					//该字段用于是否可授权
					action.setActionDesc(String.valueOf(ra.isAuthorize()));
				}
		}
	}
}
