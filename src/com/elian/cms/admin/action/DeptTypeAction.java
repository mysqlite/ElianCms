package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.DeptType;
import com.elian.cms.admin.service.DeptTypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 科室类型功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class DeptTypeAction extends BaseAction {
	private static final long serialVersionUID = -2349527453086311146L;

	private String parentName;
	private DeptType deptType;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<DeptType> pagination = new Pagination<DeptType>(
			SearchParamUtils.getDeptTypeConditionMap());

	private DeptTypeService deptTypeService;

	public String list() {
		deptTypeService.findByAll(ElianUtils.DEPT_TYPE, false, pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			deptType = deptTypeService.get(id);
		}
		else {
			deptType = createDeptType();
		}
		return EDIT;
	}

	public DeptType createDeptType() {
		DeptType deptType = new DeptType();
		deptType.setDisable(true);
		deptType.setTypeSort(99);
		deptType.setParentId(0);
		deptType.setType(ElianUtils.DEPT_TYPE);
		return deptType;
	}

	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			deptType = deptTypeService.get(id);
			if (deptType == null)
				return;
			deptType.setTypeSort(sort);
			deptTypeService.save(deptType);
		}
	}

	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			deptType = deptTypeService.get(id);
			if (deptType == null)
				return;
			deptType.setDisable(!disable);
			deptTypeService.save(deptType);
		}
	}

	public String save() {
		deptTypeService.save(deptType);
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				deptType = deptTypeService.get(id);
				if (deptType == null)
					continue;
				deptTypeService.delete(deptType);
			}
		}
	}

	/*
	 * 获取科室类型树结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<DeptType> dtList = deptTypeService.findByAll(ElianUtils.DEPT_TYPE,
				false, null);
		if (dtList != null && dtList.size() > 0) {
			Map<String, Object> map = null;
			for (DeptType t : dtList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", t.getId());
				map.put("name", t.getTypeName());
				map.put("pId", t.getParentId());
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}

	public String getParentName() {
		if (StringUtils.isBlank(parentName) && deptType != null
				&& deptType.getParentId() > 0) {
			DeptType parentDt = deptTypeService.get(deptType.getParentId());
			if (parentDt != null)
				parentName = parentDt.getTypeName();
		}
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public DeptType getDeptType() {
		return deptType;
	}

	public void setDeptType(DeptType deptType) {
		this.deptType = deptType;
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

	public Pagination<DeptType> getPagination() {
		return pagination;
	}

	@Resource
	public void setDeptTypeService(DeptTypeService deptTypeService) {
		this.deptTypeService = deptTypeService;
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public boolean isMainStation() {
		return ApplicationUtils.isMainStation();
	}

	public void validateSave() {
		if (ValidateUtils.isBlank(deptType.getTypeName()))
			this.addFieldError("deptType.typeName", "科室类型名称不能为空");
		else if (ValidateUtils.isLengOut(deptType.getTypeName(), 100))
			this.addFieldError("deptType.typeName", "科室类型名称必须在100字以内");
		if (ValidateUtils.isLengOut(deptType.getTypeDesc(), 255))
			this.addFieldError("deptType.typeDesc", "描述必须在255字以内");
	}
}
