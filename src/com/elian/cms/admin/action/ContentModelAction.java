package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.ContentModel;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.service.ContentModelService;
import com.elian.cms.admin.service.ModelService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 内容模型功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class ContentModelAction extends BaseAction {
	private static final long serialVersionUID = -3287305291531297485L;

	private Integer id = Integer.valueOf(0);

	private String modelName;
	private Integer contentModelId;
    private ContentModel contentModel;
	private ModelService modelService;
	private ContentModelService contentModelService;
//	private Pagination<Industry> pagination = new Pagination<Industry>(
//			SearchParamUtils.getIndustryConditionMap());
	private Pagination<ContentModel> pagination = new Pagination<ContentModel>(SearchParamUtils.getContentModelConditionMap());

	public String edit() {
		if (id > 0) {
			Model model = modelService.get(id);
			contentModelId = model.getContentModel().getId();
			modelName = model.getModelName();
		}
		return EDIT;
	}
	
	public String list() {
		contentModelService.findByAll(ApplicationUtils.getSite().getId(), false,
				pagination);
		return LIST;
	}

	public String save() {
		Model model = modelService.get(id);
		model.setContentModel(contentModelService.get(contentModelId));
		modelService.save(model);
		return SAVE;
	}
	
	public String editContent() {
		contentModel=contentModelService.get(id);
		return EDIT;
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				contentModel = contentModelService.get(id);
				if (contentModel == null)
					continue;
				contentModelService.delete(contentModel);
			}
		}
	}
	
	public String saveContent() {
		contentModelService.save(contentModel);
		return SAVE;
	}

	public List<SelectItem> getContentModelList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<ContentModel> cmlist = contentModelService.findByAll(null);
		if (!CollectionUtils.isEmpty(cmlist))
			for (ContentModel cm : cmlist) {
				list.add(new SelectItem( cm.getId(),cm.getObjectName()));
			}
		return list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getContentModelId() {
		return contentModelId;
	}

	public void setContentModelId(Integer contentModelId) {
		this.contentModelId = contentModelId;
	}

	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	@Resource
	public void setContentModelService(ContentModelService contentModelService) {
		this.contentModelService = contentModelService;
	}

	public void setPagination(Pagination<ContentModel> pagination) {
		this.pagination = pagination;
	}

	public Pagination<ContentModel> getPagination() {
		return pagination;
	}

	public void setContentModel(ContentModel contentModel) {
		this.contentModel = contentModel;
	}

	public ContentModel getContentModel() {
		return contentModel;
	}
	
	public void validateSaveContent() {
		if (ValidateUtils.isBlank(contentModel.getObjectName()))
			this.addFieldError("contentModel.objectName", "不能为空");
		if (ValidateUtils.isBlank(contentModel.getActionName()))
			this.addFieldError("contentModel.actionName", "不能为空");
	}
}
