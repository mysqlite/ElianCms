package com.elian.cms.admin.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 等级action
 * 
 * @author CZH
 * 
 */
@Component
@Scope("prototype")
public class GradeAction extends BaseAction {
	private static final long serialVersionUID = -4376394585267424068L;
	
	private Grade grade;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Grade> pagination = new Pagination<Grade>(
			SearchParamUtils.getGradeConditionMap());

	private GradeService gradeService;
	
	public String list() {
		gradeService.findByAll(pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			grade = gradeService.get(id);
		}
		else {
			grade = creategrade();
		}
		return EDIT;
	}

	public String show() {
		if (id > 0) {
			grade = gradeService.get(id);
		}
		return SHOW;
	}

	public Grade creategrade() {
		Grade grade = new Grade();
		grade.setCreateTime(new Date());
		grade.setDisable(true);
		return grade;
	}

	public String save() {
		gradeService.save(grade);
		if(grade.isDefault())
			updateDefaule(grade);
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null)
			for (Integer id : idList) {
				grade = gradeService.get(id);
				gradeService.delete(grade);
			}
	}
	
	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			grade = gradeService.get(id);
			if (grade == null)
				return NONE;
			grade.setGradeSort(sort);
			gradeService.save(grade);
		}
		return NONE;
	}

	public List<SelectItem> getComTypeList(){
		return ElianUtils.getCompTypeList();
	}
	
	
	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			grade = gradeService.get(id);
			if (grade == null)
				return;
			grade.setDisable(!disable);
			gradeService.save(grade);
		}
	}

	public boolean isMainStation() {
		return ApplicationUtils.isMainStation();
	}
	public TrueFalseItem getDefaultItem() {
		return ElianUtils.getTrueFalseItem();
	}
	
	public void defaultData() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean def = ApplicationUtils.getAjaxDisable();
		if (id != null && def != null) {
			grade = gradeService.get(id);
			if (grade != null)
    			if(def) {
    				updateDefaule(grade);
    			}
		}
	}
	
	private void updateDefaule(Grade grade) {
		if(grade!=null) {
		List<Grade> grades=gradeService.findByComType(grade.getComType(),null,true);
		for (Grade grade2 : grades) {
			if(!grade.getId().equals(grade2.getId())) {
			grade2.setDefault(false);
			gradeService.save(grade2);
			}
		}
		grade.setDefault(true);
		gradeService.save(grade);
		}
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

	public Pagination<Grade> getPagination() {
		return pagination;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}
	
	@Resource
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setPagination(Pagination<Grade> pagination) {
		this.pagination = pagination;
	}

	public void validateSave() {
		if (!ValidateUtils.strLenth(grade.getGradeName(), 2, 20))
			this.addFieldError("grade.gradeName", "等级名称长度，必须大于等于2且小于等于20");
		
		/*if (ValidateUtils.isINTEGER_NEGATIVE(grade.getGradeSort().toString().trim()))
			this.addFieldError("grade.gradeSort", "排序类型应该为正整数");
		if (ValidateUtils.isLengOut(grade.getGradeSort().toString().trim(), 5))
			this.addFieldError("grade.gradeSort", "排序长度，必须小于等于5");
		if (ValidateUtils.isLengOut(grade.getGradeDesc(), 250))
			this.addFieldError("grade.gradeDesc", "等级介绍长度，必须小于等于250");*/
	}
}
