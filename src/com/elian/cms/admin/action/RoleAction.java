package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Role;
import com.elian.cms.admin.service.RoleService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 角色功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class RoleAction extends BaseAction {
	private static final long serialVersionUID = -2349527453086311146L;

	private Role role;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Role> pagination = new Pagination<Role>(SearchParamUtils
			.getRoleConditionMap());

	private RoleService roleService;

	// private MailService mailService;

	// @Resource
	// public void setMailService(MailService mailService) {
	// this.mailService = mailService;
	// }

	public String list() {
		roleService.findByAll(ApplicationUtils.getSite().getId(), false,
				pagination);
		// try {
		// mailService.sendHtml(null, new String[] { "a466350665@qq.com" },
		// null,
		// null, "Joe Test", "UTF-8", "<b>Test1123412341234</b>", new Date(),
		// new String[]{"E:/0040.jpg"});
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			role = roleService.get(id);
		}
		else {
			role = createRole();
		}
		return EDIT;
	}

	public Role createRole() {
		Role role = new Role();
		role.setDisable(true);
		role.setRoleSort(99);
		role.setCreateTime(new Date());
		role.setCompType(ApplicationUtils.getSite().getComType());
		return role;
	}

	public void defaultData() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean def = ApplicationUtils.getAjaxDisable();
		if (id != null && def != null) {
			role = roleService.get(id);
			if (role == null)
				return;
			role.setDefault(!def);
			roleService.save(role);
		}
	}

	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			role = roleService.get(id);
			if (role == null)
				return;
			role.setRoleSort(sort);
			roleService.save(role);
		}
	}

	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			role = roleService.get(id);
			if (role == null)
				return;
			role.setDisable(!disable);
			roleService.save(role);
		}
	}

	public String save() {
		role.setSite(ApplicationUtils.getSite());
		roleService.save(role);
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				role = roleService.get(id);
				if (role == null)
					continue;
				roleService.delete(role);
			}
		}
	}

	public void changeSelect() {
		String cc = ApplicationUtils.getAjaxSelectedValue();
		List<SelectItem> list = null;
		if ("1".equals(cc)) {
			list = ElianUtils.getCompTypeList();
		}
		else if ("2".equals(cc)) {
			list = new UserAction().getUserStatusList();
		}
		ApplicationUtils.sendJsonArray(list);
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
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<SelectItem> getCompTypeList() {
		return ElianUtils.getCompTypeList();
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public TrueFalseItem getDefaultItem() {
		return ElianUtils.getTrueFalseItem();
	}

	public boolean isMainStation() {
		return ApplicationUtils.isMainStation();
	}

	public void validateSave() {
		if (ValidateUtils.isBlank(role.getRoleName()))
			this.addFieldError("role.roleName", "角色名称不能为空");
		else if (ValidateUtils.isLengOut(role.getRoleName(), 20))
			this.addFieldError("role.roleName", "角色名称必须在20字以内");
		if (ValidateUtils.isLengOut(role.getRoleDesc(), 255))
			this.addFieldError("role.roleDesc", "角色描述必须在255字以内");
	}
}
