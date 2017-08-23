package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Action;
import com.elian.cms.admin.service.ActionService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 权限功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class ActionAction extends BaseAction {
	private static final long serialVersionUID = 101933931018632091L;
	private Action action;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private String parentName;
	private Pagination<Action> pagination = new Pagination<Action>(SearchParamUtils.getActionConditionMap());

	private boolean showActionSelected;
	private boolean addActionSelected;
	private boolean updateActionSelected;
	private boolean deleteActionSelected;
	private boolean checkActionSelected;
	private ActionService actionService;

	public String list() {
		actionService.findByAll(null, pagination);
		return LIST;
	}
	
	public String edit() {
		if (isEdit && id > 0) {
			action = actionService.get(id);
		}
		else {
			action = createAction();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			action = actionService.get(id);
		}
		return SHOW;
	}

	public Action createAction() {
		Action action = new Action();
		action.setDisable(true);
		action.setParentId(0);
		action.setActionSort(99);
		return action;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			action = actionService.get(id);
			if (action == null)
				return NONE;
			action.setActionSort(sort);
			actionService.save(action);
		}
		return NONE;
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			action = actionService.get(id);
			if (action == null)
				return NONE;
			action.setDisable(!disable);
			actionService.save(action);
		}
		return NONE;
	}
	
	public void validateSave() {
		if (ValidateUtils.isBlank(action.getActionName()))
			this.addFieldError("action.actionName", "权限名称不能为空");
		else if (ValidateUtils.isLengOut(action.getActionName(), 20))
			this.addFieldError("action.actionName", "权限名称必须在20字以内");
		if (ValidateUtils.isBlank(action.getActionUrl()))
			this.addFieldError("action.actionUrl", "权限URL不能为空");
		else if (ValidateUtils.isLengOut(action.getActionUrl(), 255))
			this.addFieldError("action.actionUrl", "权限URL必须在255字以内");
		if (ValidateUtils.isLengOut(action.getActionDesc(), 255))
			this.addFieldError("action.actionDesc", "权限描述必须在255字以内");
	}

	public String save() {
		if (isEdit) {
			actionService.save(action);
		}
		else {
			List<Action> subActionList = new ArrayList<Action>();
			subActionList.add(action);
			addSubAction(subActionList);
			int i = 0;
			for (Action act : subActionList) {
				if (i > 0) {
					act.setParentId(action.getId());
				}
				actionService.save(act);
				i++;
			}
		}
		return SAVE;
	}

	public void addSubAction(List<Action> subActionList) {
		Action act = null;
		if (showActionSelected && (act = createSubAction("查看", "show")) != null)
			subActionList.add(act);
		if (addActionSelected && (act = createSubAction("添加", "add")) != null)
			subActionList.add(act);
		if (updateActionSelected
				&& (act = createSubAction("修改", "update")) != null)
			subActionList.add(act);
		if (deleteActionSelected
				&& (act = createSubAction("删除", "delete")) != null)
			subActionList.add(act);
		if (checkActionSelected
				&& (act = createSubAction("审核", "check")) != null)
			subActionList.add(act);
	}

	public Action createSubAction(String actionStr, String url) {
		try {
			Action act = (Action) BeanUtils.cloneBean(action);
			act.setId(null);
			act.setActionName(action.getActionName().substring(0,
					action.getActionName().length() - 2)
					+ actionStr);
			act.setActionUrl(action.getActionUrl().substring(1,
					action.getActionUrl().length() - 11)
					+ url);
			act.setActionDesc(act.getActionName());
			return act;
		}
		catch (Exception e) {
		}
		return null;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				Action action = actionService.get(id);
				if (action == null)
					continue;
				actionService.delete(action);
			}
		}
		return NONE;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
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

	public String getParentName() {
		if (StringUtils.isBlank(parentName) && action != null
				&& action.getParentId() > 0) {
			Action parentAct = actionService.get(action.getParentId());
			if (parentAct != null)
				parentName = parentAct.getActionName();
		}
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Pagination<Action> getPagination() {
		return pagination;
	}

	@Resource
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}


	public boolean isShowActionSelected() {
		return showActionSelected;
	}

	public void setShowActionSelected(boolean showActionSelected) {
		this.showActionSelected = showActionSelected;
	}

	public boolean isAddActionSelected() {
		return addActionSelected;
	}

	public void setAddActionSelected(boolean addActionSelected) {
		this.addActionSelected = addActionSelected;
	}

	public boolean isUpdateActionSelected() {
		return updateActionSelected;
	}

	public void setUpdateActionSelected(boolean updateActionSelected) {
		this.updateActionSelected = updateActionSelected;
	}

	public boolean isDeleteActionSelected() {
		return deleteActionSelected;
	}

	public void setDeleteActionSelected(boolean deleteActionSelected) {
		this.deleteActionSelected = deleteActionSelected;
	}

	public boolean isCheckActionSelected() {
		return checkActionSelected;
	}

	public void setCheckActionSelected(boolean checkActionSelected) {
		this.checkActionSelected = checkActionSelected;
	}
	
	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}
}
