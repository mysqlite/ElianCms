package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Grade;
import com.elian.cms.admin.model.GradeTemplate;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.GradeService;
import com.elian.cms.admin.service.GradeTemplateService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;

/**
 * 等级模板管理
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class GradeTemplateAction extends BaseAction {
	private static final long serialVersionUID = 5164628209123425544L;

	public static final String TEMPLATE_URLS = "templateUrls";

	private Grade grade;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Grade> pagination = new Pagination<Grade>(
			SearchParamUtils.getGradeTemplateConditionMap());

	private GradeService gradeService;
	private TemplateService templateService;
	private GradeTemplateService gradeTemplateService;

	public String list() {
		gradeService.findById(null, true, pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0)
			grade = gradeService.get(id);
		else
			grade = new Grade();
		return EDIT;
	}

	public String save() {
		if (isEdit) {
			List<GradeTemplate> gtList = gradeTemplateService.findByAll(grade
					.getId(), null);
			doDelete(gtList);
		}
		return doSave();
	}

	public void doDelete(List<GradeTemplate> gtList) {
		if (CollectionUtils.isEmpty(gtList))
			return;
		for (GradeTemplate gt : gtList) {
			gradeTemplateService.delete(gt);
		}
	}

	public String doSave() {
		List<GradeTemplate> gtList = createGradeTemplate(grade.getId());
		if (gtList == null || gtList.size() < 1) {
			return SAVE;
		}
		for (GradeTemplate gt : gtList) {
			gradeTemplateService.save(gt);
		}
		return SAVE;
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		List<GradeTemplate> gtList = gradeTemplateService
				.findByGradeIds(idList);
		doDelete(gtList);
	}

	public List<GradeTemplate> createGradeTemplate(Integer gradeId) {
		Grade grade = gradeService.get(gradeId);
		List<GradeTemplate> gtList = new ArrayList<GradeTemplate>();
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				gtList.add(createGradeTemplate(grade, templateService.get(id)));
			}
		}
		return gtList;
	}

	private GradeTemplate createGradeTemplate(Grade grade, Template t) {
		GradeTemplate gt = new GradeTemplate();
		gt.setGrade(grade);
		gt.setTemplate(t);
		return gt;
	}

	public void validateSave() {
		Integer gradeId = grade.getId();
		if (gradeId == null)
			this.addFieldError("grade.id", "请选择等级名称！");
	}

	public List<Grade> getGradeList() {
		if (isEdit)
			return gradeService.findById(grade.getId(), true, null);
		else
			return gradeService.findById(null, false, null);
	}
	
	private List<Template> templateList = null;

	public List<Template> getTemplateList() {
		if (templateList == null) {
			templateList = templateService.findByAll(0, true, null);
			selectedTemplateList();
		}
		return templateList;
	}

	public void selectedTemplateList() {
		if (CollectionUtils.isEmpty(templateList))
			return;
		List<GradeTemplate> gtList = gradeTemplateService.findByAll(grade
				.getId(), null);
		if (CollectionUtils.isEmpty(gtList))
			return;
		for (Template t : templateList) {
			for (GradeTemplate gt : gtList) {
				if (t.equals(gt.getTemplate())) {
					t.setSelected(true);
					break;
				}
			}
		}
	}
	
	@Resource
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Resource
	public void setGradeTemplateService(
			GradeTemplateService gradeTemplateService) {
		this.gradeTemplateService = gradeTemplateService;
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
}
