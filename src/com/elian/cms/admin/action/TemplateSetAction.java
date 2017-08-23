package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.ModelService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 模板配置功能
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class TemplateSetAction extends BaseAction {	
	private static final long serialVersionUID = 6857262248073346998L;	
	
	private static final int STATUS_1_KEY=0;
	private static final String STATUS_1_VALUE="普通";
	private static final int STATUS_2_KEY=1;
	private static final String STATUS_2_VALUE="图文";
	private static final int STATUS_3_KEY=2;
	private static final String STATUS_3_VALUE="详细";	
	private static final int STATUS_4_KEY=3;
	private static final String STATUS_4_VALUE="幻灯片";	
	private static final int STATUS_5_KEY=4;
	private static final String STATUS_5_VALUE="点击排行榜";	
	private static final int STATUS_6_KEY=5;
	private static final String STATUS_6_VALUE="视频";	
	private static final int maxAreaId=30;
	
	private Pagination<TemplateSet> pagination = new Pagination<TemplateSet>();	

	private boolean isEdit = false;
	private boolean hasSubArea=false;
	
	private Integer tempId;	
	private TemplateSet templateSet=new TemplateSet();
	private Template template=new Template();
	
	private TemplateService templateService;
	private TemplateSetService templateSetService;
	private ModelService modelService;
	
	public String list(){
		template=templateService.get(tempId);		
		templateSetService.findByAll(tempId, pagination);	
		return LIST;
	}
	
	public String show() {
		templateSet=templateSetService.get(templateSet.getId());
		return SHOW;
	}
	
	public String edit() {
		if(isEdit){
			templateSet=templateSetService.get(templateSet.getId());
		}else{
			createDefaultTemplateSet();
		}
		return EDIT;
	}
	
	public String save() {
		templateSet.setModel(modelService.get(templateSet.getModel().getId()));			
		templateSet.setTempId(tempId);		
		templateSetService.save(templateSet);    		
		return list();
	}	
	
	/**
	 * delete方法
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		templateSetService.deleteById(idList);
	}
	
	public void validateSave() {
		if(null == templateSet.getModel().getId() || 0==templateSet.getModel().getId()){
			this.addFieldError("templateSet.model.id","请选择模型");
		}
		if(null==templateSet.getAreaId()){
			this.addFieldError("templateSet.areaId","区域的id不能为空");
		}
		
		if(null==templateSet.getMaxChannelSize()){
			this.addFieldError("templateSet.maxChannelSize","栏目的最大值不能为空");
		}else if(templateSet.getMaxChannelSize()>maxAreaId){
			this.addFieldError("templateSet.maxChannelSize","栏目的最大值不能>"+maxAreaId);
		}
		
		if(null==templateSet.getListSize()){
			this.addFieldError("templateSet.listSize","列表大小不能为空");
		}else if(templateSet.getListSize()>30){
			this.addFieldError("templateSet.listSize","最大值不能>30");
		}
		
		if(null != templateSet.getImgSize() && templateSet.getImgSize()>templateSet.getListSize()){
			this.addFieldError("templateSet.imgSize","最大值不能>列表大小");
		}	
	}
	
	private void createDefaultTemplateSet(){		
		templateSet.setChannelType(ElianUtils.CHANNEL_CONTENT);
		templateSet.setContentType(ElianUtils.CONTENT_LIST);		
		templateSet.setHasSubArea(false);
		templateSet.setMaxChannelSize(1);
		templateSet.setListSize(1);
		templateSet.setImgSize(0);
		templateSet.setSpecialContentType(0);
	}
	
	public List<Integer> getAreaIdList(){
		List<Integer> ids=templateSetService.getAreaIdList(tempId,maxAreaId);		
		return ids;
	}
	
	public Pagination<TemplateSet> getPagination() {
		return pagination;
	}
	
	public boolean isEdit() {
		return isEdit;
	}
	
	public Integer getTempId() {
		if(null!=tempId) return tempId;
		return templateSet.getTempId();
	}
	
	public TemplateSet getTemplateSet() {
		return templateSet;
	}
	
	public void setPagination(Pagination<TemplateSet> pagination) {
		this.pagination = pagination;
	}
		
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	
	public void setTemplateSet(TemplateSet templateSet) {
		this.templateSet = templateSet;
	}
	
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
	public boolean isHasSubArea() {
		return hasSubArea;
	}

	public void setHasSubArea(boolean hasSubArea) {
		this.hasSubArea = hasSubArea;
	}

	public List<SelectItem> getChannelTypeList(){		
		List<SelectItem> items=new ArrayList<SelectItem>();
		items.add(new SelectItem(ElianUtils.CHANNEL_PARENT, ElianUtils.CHANNEL_PARENT_CN));
		items.add(new SelectItem(ElianUtils.CHANNEL_CONTENT, ElianUtils.CHANNEL_CONTENT_CN));
		return items;
	}	
	
	public List<Model> getModelList() {
		String compType = ApplicationUtils.getSite().getComType();
		if (ElianUtils.COMP_TYPE_MAIN.equals(compType)
				|| ElianUtils.COMP_TYPE_SUBS.equals(compType)) {
			return modelService.findByComType(null, null);
		}
		else
			return modelService.findByComType(compType, null);
	}
	
	public List<SelectItem> getContentTypeList() {
		List<SelectItem> items=new ArrayList<SelectItem>();
		items.add(new SelectItem(ElianUtils.CONTENT_SINGLE, ElianUtils.CONTENT_SINGLE_CN));
		items.add(new SelectItem(ElianUtils.CONTENT_LIST, ElianUtils.CONTENT_LIST_CN));
		return items;
	}
	
	public List<SelectItem> getSpecialContentTypeList(){
		List<SelectItem> items=new ArrayList<SelectItem>(6);
		items.add(new SelectItem(STATUS_1_KEY,STATUS_1_VALUE));
		items.add(new SelectItem(STATUS_2_KEY,STATUS_2_VALUE));
		items.add(new SelectItem(STATUS_3_KEY,STATUS_3_VALUE));
		items.add(new SelectItem(STATUS_4_KEY,STATUS_4_VALUE));
		items.add(new SelectItem(STATUS_5_KEY,STATUS_5_VALUE));
		items.add(new SelectItem(STATUS_6_KEY,STATUS_6_VALUE));
		return items;
	}
	
	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	@Resource
	public void setTemplateSetService(TemplateSetService templateSetService) {
		this.templateSetService = templateSetService;
	}
	
	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}	
}
