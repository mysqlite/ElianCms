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
public class HospTypeAction extends BaseAction {
	private static final long serialVersionUID = 458593230936371083L;
	
	private Pagination<Type> pagination=new Pagination<Type>(SearchParamUtils.getTypeConditionMap());
	private int id;
	private TypeService typeService;
	private boolean edit;
	private Type hospType;
	private List<SelectItem> hospTypeList;
	
	public String  list() {
		typeService.findByTypeListPage(pagination, ElianUtils.getHospTypeList());
		hospTypeList=ElianUtils.getHospTypeList();
		return LIST;
	}
	
	public String edit() {
		if(edit&&id>0) {
			hospType=typeService.get(id);
		}
		hospTypeList=ElianUtils.getHospTypeList();
		return EDIT;
	}
	
	public String  save() {
		typeService.save(hospType);
		 return SAVE;
	}
	
	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			hospType = typeService.get(id);
			if (hospType == null)
				return NONE;
			hospType.setDisable(!disable);
			typeService.save(hospType);
		}
		return NONE;
	}
	
	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			hospType = typeService.get(id);
			if (hospType == null)
				return NONE;
			hospType.setTypeSort(sort);
			typeService.save(hospType);
		}
		return NONE;
	}

	public String check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				hospType = typeService.get(id);
				hospType.setDisable(!hospType.isDisable());
				typeService.save(hospType);
			}
		return NONE;
	}
	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				hospType = typeService.get(id);
				typeService.delete(hospType);
			}
		return NONE;
	}
	
	public void validateSave() {
		if(StringUtils.isBlank(hospType.getTypeClass()))
			this.addFieldError("hospType.parentId", "请选择类型");
		if(!StringUtils.isBlank(hospType.getTypeName())) {
			if(ValidateUtils.isLengOut(hospType.getTypeName(), 100))
				this.addFieldError("hospType.typeName", "类型名称长度在100字以内");
		}
		else 
			this.addFieldError("hospType.typeName", "请填写类型名称");
	    if(ValidateUtils.isLengOut(hospType.getTypeDesc(), 255))
	    	this.addFieldError("hospType.typeDesc", "类型描述在255字以内");
		if(null!=hospType.getTypeSort()) {
			if(!ValidateUtils.isInteger(hospType.getTypeSort().toString())) {
				this.addFieldError("hospType.typeSort","请输入1-9999的正整数");
			}
			else
				if(hospType.getTypeSort()<1||hospType.getTypeSort()>9999)
					this.addFieldError("hospType.typeSort","请输入1-9999的正整数");
		}
		else
			this.addFieldError("hospType.typeSort","请填写类型排序");
		if(this.hasFieldErrors())
			hospTypeList=ElianUtils.getHospTypeList();
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
	
	public Type getHospType() {
		return hospType;
	}
	
	public void setHospType(Type hospType) {
		this.hospType = hospType;
	}
	
	public List<SelectItem> getHospTypeList() {
		return hospTypeList;
	}
	
	public void setHospTypeList(List<SelectItem> selectItem) {
		this.hospTypeList = selectItem;
	}
}
