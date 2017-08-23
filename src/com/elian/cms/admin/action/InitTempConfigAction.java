package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.InitChannelService;
import com.elian.cms.admin.service.InitTemplateConfigService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;

/** 
 * 
 * @author thy
 * 
 */
@Component
@Scope("prototype")
public class InitTempConfigAction extends BaseAction {	
	
	private static final long serialVersionUID = 254409857933923005L;
	
	private boolean isEdit=false;
	
	private TemplateService templateService;
	private TemplateSetService templateSetService;
	private InitTemplateConfigService initTemplateConfigService;
	private InitChannelService initChannelService;
	
	private Pagination<InitTemplateConfig> pagination = new Pagination<InitTemplateConfig>(
			SearchParamUtils.getTemplateInitConfigConditionMap());	
	private InitTemplateConfig initTemplateConfig=new InitTemplateConfig();

	private final String TREE="tree";
	private List<Template> templateList=null;
	private Integer tempId;
	private Integer initTempId;
	
	private Integer initChannelId;
	private String channelType;
	private String contentType;
	private Integer channelTempId;
	private Integer contentTempId;

	public String tree() {		
		return TREE;
	}
	
	public String list(){
		if(channelType.equals(ElianUtils.CHANNEL_OUT)) return LIST;		
		initTemplateConfigService.getByInitChannelId(initChannelId, pagination);		
		return LIST;		
	}	
	
	
	public String edit(){
		if(isEdit){
			if(null != initTemplateConfig.getId())
				initTemplateConfig=initTemplateConfigService.get(initTemplateConfig.getId());
		}else{
			createDefaultTemplateConfig();
		}
		return EDIT;
	}
	
	public String save(){
		initTemplateConfig.setInitChannel(initChannelService.get(initTemplateConfig.getInitChannel().getId()));
		initTemplateConfig.setInitChannelSet(initChannelService.get(initTemplateConfig.getInitChannelSet().getId()));
		initTemplateConfig.setTemplate(templateService.get(initTemplateConfig.getTemplate().getId()));
		initTemplateConfigService.save(initTemplateConfig);	
		return list();
	}
	
	public String show(){
		if(null != initTemplateConfig.getId())
			initTemplateConfig=initTemplateConfigService.get(initTemplateConfig.getId());
		return SHOW;
	}
	
	private void createDefaultTemplateConfig() {
		initTemplateConfig.setInitChannel(initChannelService.get(initChannelId));
		initTemplateConfig.setCreateTime(new Date());
		initTemplateConfig.setCreater(ApplicationUtils.getUser().getRealName());		
	}

	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public Pagination<InitTemplateConfig> getPagination() {
		return pagination;
	}
	public void setPagination(Pagination<InitTemplateConfig> pagination) {
		this.pagination = pagination;
	}
	public Integer getInitChannelId() {
		return initChannelId;
	}
	
	public void setInitChannelId(Integer initChannelId) {
		this.initChannelId = initChannelId;
	}
	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public Integer getChannelTempId() {
		return channelTempId;
	}

	public void setChannelTempId(Integer channelTempId) {
		this.channelTempId = channelTempId;
	}

	public Integer getContentTempId() {
		return contentTempId;
	}

	public void setContentTempId(Integer contentTempId) {
		this.contentTempId = contentTempId;
	}
	
	public InitTemplateConfig getInitTemplateConfig() {
		return initTemplateConfig;
	}

	public void setInitTemplateConfig(InitTemplateConfig initTemplateConfig) {
		this.initTemplateConfig = initTemplateConfig;
	}
	
	public Integer getInitTempId() {
		return initTempId;
	}

	public void setInitTempId(Integer initTempId) {
		this.initTempId = initTempId;
	}

	public void validateSave() {
		if(null==initTemplateConfig.getAreaId()){
			this.addFieldError("initTemplateConfig.areaId","区域的编号不能为空");
		}
		if(null==initTemplateConfig.getTemplate().getId()){
			this.addFieldError("initTemplateConfig.template.id","请选择模板文件");
		}
		if(null==initTemplateConfig.getInitChannelSet().getId()){
			this.addFieldError("initTemplateConfig.initChannelSet.id","请选择栏目");
		}		
		if(null==initTemplateConfig.getInitChannel().getId()){
			this.addFieldError("initTemplateConfig.initChannel.id","栏目不能为空");
		}		
		if(null==initTemplateConfig.getCreater())
			initTemplateConfig.setCreater(ApplicationUtils.getUser().getRealName());
		if(null==initTemplateConfig.getCreateTime())
			initTemplateConfig.setCreateTime(new Date());
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
	public void setInitTemplateConfigService(
			InitTemplateConfigService initTemplateConfigService) {
		this.initTemplateConfigService = initTemplateConfigService;
	}
	
	@Resource
	public void setInitChannelService(InitChannelService initChannelService) {
		this.initChannelService = initChannelService;
	}

	public List<Template> getTemplateList() {	
		List<Integer> tempIds=new ArrayList<Integer>();
		if(null != channelTempId)
			tempIds.add(channelTempId);
		if(null != contentTempId)
			tempIds.add(contentTempId);
		templateList=templateService.get(tempIds);
		return templateList;
	}	
	
	//-------------------------
	//           AJAX
	//-------------------------
	
	public void getTempAreaIds(){
		if(null == tempId) return;
		List<Integer> areaIds=initTemplateConfigService.getTempAreaIds(tempId,initChannelId);		
		ApplicationUtils.sendJsonArray(areaIds);	
	}
	
	public void channelTree(){
		if(null == tempId || null == initTemplateConfig.getAreaId()) return;
		TemplateSet templateSet= templateSetService.getByTempIdAndAreaId(tempId, initTemplateConfig.getAreaId());
		List<Map<String, Object>> treeMap=initTemplateConfigService.getChannelTree(initTempId,templateSet);
		JSONArray ar = JSONArray.fromObject(treeMap);
		ApplicationUtils.sendJsonStr(ar.toString());
		return;
	}
	
	/**
	 * delete方法
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		initTemplateConfigService.deleteById(idList);
	}
}
