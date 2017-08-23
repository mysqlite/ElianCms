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
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 模型定义功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class ModelAction extends BaseAction {
	private static final long serialVersionUID = -6852165287391822716L;

	private Model model;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Model> pagination = new Pagination<Model>(
			SearchParamUtils.getModelConditionMap());
	
	private List<SelectItem> itemList;

	private ModelService modelService;
	private ContentModelService contentModelService;

	public String list() {
		modelService.findByAll(pagination);
		return LIST;
	}

	public String edit() {
		if (isEdit && id > 0) {
			model = modelService.get(id);
		}
		else {
			model = createModel();
		}
		return EDIT;
	}

	public Model createModel() {
		Model model = new Model();
		model.setDisable(true);
		model.setModelSort(99);
		return model;
	}

	public String sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			model = modelService.get(id);
			if (model == null)
				return NONE;
			model.setModelSort(sort);
			modelService.save(model);
		}
		return NONE;
	}

	public String disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			model = modelService.get(id);
			if (model == null)
				return NONE;
			model.setDisable(!disable);
			modelService.save(model);
		}
		return NONE;
	}

	public String save() {
		if (model.getContentModel().getId() != null)
			model.setContentModel(contentModelService.get(model
					.getContentModel().getId()));
		modelService.save(model);
		return SAVE;
	}

	public String delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (idList != null) {
			for (Integer id : idList) {
				model = modelService.get(id);
				if (model == null)
					continue;
				modelService.delete(model);
			}
		}
		return NONE;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
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

	public Pagination<Model> getPagination() {
		return pagination;
	}

	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	@Resource
	public void setContentModelService(ContentModelService contentModelService) {
		this.contentModelService = contentModelService;
	}
	
	public List<SelectItem> getCompTypeList() {
		if (itemList == null) {
			itemList = new ArrayList<SelectItem>(3);
			itemList.add(new SelectItem(ElianUtils.COMP_TYPE_PUBL,
					ElianUtils.COMP_TYPE_PUBL_CN));
			itemList.add(new SelectItem(ElianUtils.COMP_TYPE_HOSP,
					ElianUtils.COMP_TYPE_HOSP_CN));
			itemList.add(new SelectItem(ElianUtils.COMP_TYPE_COMP,
					ElianUtils.COMP_TYPE_COMP_CN));
			itemList.add(new SelectItem(ElianUtils.COMP_TYPE_MEDICINE_COMP,
					ElianUtils.COMP_TYPE_MEDICINE_COMP_CN));
			itemList.add(new SelectItem(ElianUtils.COMP_TYPE_INSTRUMENT_COMP,
					ElianUtils.COMP_TYPE_INSTRUMENT_COMP_CN));
		}
		return itemList;
	}
	
	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public List<SelectItem> getModelTypeList() {
		return ElianUtils.getCompTypeList();
	}
	
	public List<SelectItem> getContentModelList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<ContentModel> cmlist = contentModelService.findByAll(null);
		if (!CollectionUtils.isEmpty(cmlist))
			for (ContentModel cm : cmlist) {
				list.add(new SelectItem(cm.getId(),cm.getObjectName()));
			}
		return list;
	}

	public void validateSave() {
		if (ValidateUtils.isBlank(model.getModelName()))
			this.addFieldError("model.modelName", "模型名称不能为空");
		else if (ValidateUtils.isLengOut(model.getModelName(), 50))
			this.addFieldError("model.modelName", "模型名称必须在50字以内");
//		if (ValidateUtils.isBlank(model.getChannelTempPrefix()))
//			this.addFieldError("model.channelTempPrefix", "栏目模板前缀不能为空");
//		else if (ValidateUtils.isLengOut(model.getChannelTempPrefix(), 50))
//			this.addFieldError("model.channelTempPrefix", "栏目模板前缀必须在50字以内");
//		if (ValidateUtils.isBlank(model.getContentTempPrefix()))
//			this.addFieldError("model.contentTempPrefix", "内容模板前缀不能为空");
//		else if (ValidateUtils.isLengOut(model.getContentTempPrefix(), 50))
//			this.addFieldError("model.contentTempPrefix", "内容模板前缀必须在50字以内");
//		if (ValidateUtils.isBlank(model.getListTempPrefix()))
//			this.addFieldError("model.listTempPrefix", "列表模板前缀不能为空");
//		else if (ValidateUtils.isLengOut(model.getListTempPrefix(), 50))
//			this.addFieldError("model.listTempPrefix", "列表模板前缀必须在50字以内");
		if (ValidateUtils.isBlank(model.getChannelTempUrl()))
			this.addFieldError("model.channelTempUrl", "栏目模板路径不能为空");
		else if (ValidateUtils.isLengOut(model.getChannelTempUrl(), 20))
			this.addFieldError("model.channelTempUrl", "栏目模板路径必须在20字以内");
		if (ValidateUtils.isBlank(model.getContentTempUrl()))
			this.addFieldError("model.contentTempUrl", "内容模板路径不能为空");
		else if (ValidateUtils.isLengOut(model.getContentTempUrl(), 20))
			this.addFieldError("model.contentTempUrl", "内容模板路径必须在20字以内");
		if (ValidateUtils.isBlank(model.getListTempUrl()))
			this.addFieldError("model.listTempUrl", "列表模板路径不能为空");
		else if (ValidateUtils.isLengOut(model.getListTempUrl(), 20))
			this.addFieldError("model.listTempUrl", "列表模板路径必须在20字以内");
		if (model.getContentModel() == null
				|| model.getContentModel().getId() == null)
			this.addFieldError("model.contentModel.id", "请选择内容模型");
	}
}
