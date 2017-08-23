package com.elian.cms.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component
@Scope("prototype")
public class TypeAction extends BaseAction {
	private static final long serialVersionUID = 458593230936371083L;
	
	private Pagination<Type> pagination=new Pagination<Type>(SearchParamUtils.getTypeConditionMap());
	private int id;
	private TypeService typeService;
	private boolean edit;
	private Type  type;
	private List<SelectItem> typeList;
	
	public String  list() {
		typeService.findByPage(pagination);
		typeList=ElianUtils.getTypeList();
		return LIST;
	}
	
	public String edit() {
		if(edit&&id>0) {
			type=typeService.get(id);
		}
		typeList=ElianUtils.getTypeList();
		return EDIT;
	}
	
	public String  save() {
		
		typeService.save(type);
		 return SAVE;
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			type = typeService.get(id);
			if (type == null)
				return NONE;
			type.setDisable(!disable);
			typeService.save(type);
		}
		return NONE;
	}
	
	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			type = typeService.get(id);
			if (type == null)
				return NONE;
			type.setTypeSort(sort);
			typeService.save(type);
		}
		return NONE;
	}

	public String check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				type = typeService.get(id);
				type.setDisable(!type.isDisable());
				typeService.save(type);
			}
		return NONE;
	}
	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				type = typeService.get(id);
				typeService.delete(type);
			}
		return NONE;
	}
	
	public void validateSave() {
		if(StringUtils.isBlank(type.getTypeClass()))
			this.addFieldError("type.parentId", "请选择类型");
		if(!StringUtils.isBlank(type.getTypeName())) {
			if(ValidateUtils.isLengOut(type.getTypeName(), 100))
				this.addFieldError("type.typeName", "类型名称长度在100字以内");
		}
		else 
			this.addFieldError("type.typeName", "请填写类型名称");
	    if(ValidateUtils.isLengOut(type.getTypeDesc(), 255))
	    	this.addFieldError("type.typeDesc", "类型描述在255字以内");
		if(null!=type.getTypeSort()) {
			if(!ValidateUtils.isInteger(type.getTypeSort().toString())) {
				this.addFieldError("type.typeSort","请输入1-9999的正整数");
			}
			else
				if(type.getTypeSort()<1||type.getTypeSort()>9999)
					this.addFieldError("type.typeSort","请输入1-9999的正整数");
		}
		else
			this.addFieldError("type.typeSort","请填写类型排序");
		if(this.hasFieldErrors())
			typeList=ElianUtils.getHospTypeList();
	}
	
	public Pagination<Type> getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination<Type> pagination) {
		this.pagination = pagination;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Resource
	public void setHospTypeService(TypeService typeService) {
		this.typeService = typeService;
	}
	
	public boolean isEdit() {
		return edit;
	}
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public List<SelectItem> getTypeList() {
		return typeList;
	}
	
	public void setHospTypeList(List<SelectItem> selectItem) {
		this.typeList = selectItem;
	}
}
