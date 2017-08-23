package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 用户类型
 * 
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class UserTypeAction extends BaseAction {
	private static final long serialVersionUID = 237122691501584018L;
	
	private UserType userType;
	private UserTypeService userTypeService;
	private Pagination<UserType> pagination = new Pagination<UserType>(SearchParamUtils.getUserTypeConditionMap());
	private boolean edit;
	private Integer id;
	private boolean disable;
	private String typeName;

	public List<UserType> findUserTypeByAll() {
		return userTypeService.findUserTypeByAll();
	}

	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				userType = userTypeService.get(id);
				userType.setDisable(!userType.isDisable());
				userTypeService.save(userType);
			}
	}
	public String edit() {
		if (edit && id > 0) {
			userType = userTypeService.get(id);
			UserType typeNames=userTypeService.get(userType.getParentId());
			if(typeNames!=null) {
				typeName=typeNames.getTypeName();
			}
		}else {
			userType=new UserType();
			userType.setParentId(0);
			userType.setTypeSort(99);
		}
		return EDIT;
	}

	public String save() {
		userTypeService.save(userType);
		return SAVE;
	}

	public String list() {
		userTypeService.findUserTypes(pagination);
		return LIST;
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null & disable != null) {
			userType = userTypeService.get(id);
			if (userType == null)
				return NONE;
			userType.setDisable(!disable);
			userTypeService.save(userType);
		}
		return NONE;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			userType = userTypeService.get(id);
			if (userType == null)
				return NONE;
			userType.setTypeSort(sort);
			userTypeService.save(userType);
		}
		return NONE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				userType = userTypeService.get(id);
				userTypeService.delete(userType);
			}
		return NONE;
	}

	public String show() {
		userType = userTypeService.get(id);
		return SHOW;
	}

	public void validateSave() {
		if (!ValidateUtils.strLenth(userType.getTypeName(), 1, 20))
			this.addFieldError("userType.typeName", "用户类型名称长度为1-20之间");
		if (ValidateUtils.isLengOut(userType.getTypeDesc(), 255))
			this.addFieldError("userType.typeDesc", "用户类型描述字符长度小于256个字");
		if (userType.getParentId() == null)
			this.addFieldError("userType.parentId", "请选择类型组");
		if (userType.getTypeSort() == null||ValidateUtils.isNumber(userType.getTypeSort().toString())||userType.getTypeSort() > 9999) {
			this.addFieldError("userType.typeSort", "请填写用户类型排序,且为1-9999的正整数");
		}
	}

	public void dwrDelete(Integer id) {
		userType = userTypeService.get(id);
		userTypeService.delete(userType);
	}

	public void updateName(Integer id, String name) {
		userType = userTypeService.get(id);
		userType.setTypeName(name);
		userTypeService.save(userType);
	}

	public UserType getUserType(Integer parentId) {
		return userTypeService.get(parentId);
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public Pagination<UserType> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<UserType> pagination) {
		this.pagination = pagination;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Resource
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
