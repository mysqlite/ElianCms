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

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;

/** 
 * 
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class TempConfigAction extends BaseAction {	
	
	private static final long serialVersionUID = 254409857933923005L;
	
	private boolean isEdit=false;
	
	private TemplateService templateService;
	private TemplateSetService templateSetService;
	private TemplateConfigService templateConfigService;
	private ChannelService channelService;
	
	private Pagination<TemplateConfig> pagination = new Pagination<TemplateConfig>(
			SearchParamUtils.getTemplateConfigConditionMap());

	private final String TREE="tree";
	private TemplateConfig templateConfig=new TemplateConfig();
	private List<Template> templateList=null;
	private Integer tempId;
	
	private Integer channelId;
	private String channelType;
	private String contentType;
	private Integer channelTempId;
	private Integer contentTempId;
	
	public String tree() {		
		return TREE;
	}
	
	public String list(){
		if(channelType.equals(ElianUtils.CHANNEL_OUT)) return LIST;		
		templateConfigService.getByChannelId(channelId, pagination);		
		return LIST;		
	}		
	
	/**
	 * 保存相同栏目模板的栏目模板配置
	 * @return
	 */
	public String saveSameTempConfig() {
		if(channelId==null||channelId.equals(0))
			return LIST;
		Channel channel=channelService.get(channelId);
		if(channel==null)
			return LIST;
		if(channel.getChannelType().equals("index"))
			return LIST;
		List<Channel> channels=channelService.findBySameTemplatChannel(ApplicationUtils.getSite().getId(), channel, false, false);
		if(!CollectionUtils.isEmpty(channels))
		templateConfigService.save(addConfig(channels));
	return LIST;	
	}
	
	/**
	 * 保存同级相同栏目模板的栏目模板配置
	 * @return
	 */
	public String saveMoreConfig() {
		if(channelId==null||channelId.equals(0))
			return LIST;
		Channel channel=channelService.get(channelId);
		if(channel==null)
			return LIST;
		if(channel.getChannelType().equals("index"))
			return LIST;
		List<Channel> channels=channelService.findBySameTemplatChannel(ApplicationUtils.getSite().getId(), channel, false, true);
		templateConfigService.save(addConfig(channels));
		return LIST;
	}
	
	private List<TemplateConfig> addConfig(List<Channel> channels) {
		List<TemplateConfig> soureConfigs=templateConfigService.getByChannelId(channelId,null);
		List<TemplateConfig> newConfigs=new ArrayList<TemplateConfig>();
		TemplateConfig config=null;
		for (Channel c : channels) {
			for (TemplateConfig conf : soureConfigs) {
				config=new TemplateConfig();
				config.setAreaId(conf.getAreaId());
				config.setChannel(c);
				config.setChannelSet(conf.getChannelSet());
				config.setTemplate(conf.getTemplate());
				config.setCreater(ApplicationUtils.getUser().getRealName());
				config.setCreateTime(new Date());
				newConfigs.add(config);
			}
		}
		return newConfigs;
	}
	
	public String edit(){
		if(isEdit){
			if(null != templateConfig.getId())
				templateConfig=templateConfigService.get(templateConfig.getId());
		}else{
			createDefaultTemplateConfig();
		}
		return EDIT;
	}
	
	/**
	 * delete方法
	 */
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		templateConfigService.deleteById(idList);
	}
	
	public String show(){
		if(null != templateConfig.getId())
			templateConfig=templateConfigService.get(templateConfig.getId());
		return SHOW;
	}
	
	public String save(){
		templateConfig.setChannel(channelService.get(templateConfig.getChannel().getId()));
		templateConfig.setChannelSet(channelService.get(templateConfig.getChannelSet().getId()));
		templateConfig.setTemplate(templateService.get(templateConfig.getTemplate().getId()));
		templateConfigService.save(templateConfig);	
		return list();
	}
	
	public void getTempAreaIds(){
		if(null == tempId) return;
		List<Integer> areaIds=templateConfigService.getTempAreaIds(tempId,channelId);		
		ApplicationUtils.sendJsonArray(areaIds);		
	}
	
	public void channelTree(){
		if(null == tempId || null == templateConfig.getAreaId()) return;
		TemplateSet templateSet= templateSetService.getByTempIdAndAreaId(tempId, templateConfig.getAreaId());
		List<Map<String, Object>> treeMap=templateConfigService.getChannelTree(templateSet);
		JSONArray ar = JSONArray.fromObject(treeMap);
		ApplicationUtils.sendJsonStr(ar.toString());
		return;
	}
	
	private void createDefaultTemplateConfig() {
		templateConfig.setChannel(channelService.get(channelId));
		templateConfig.setCreateTime(new Date());
		templateConfig.setCreater(ApplicationUtils.getUser().getRealName());		
	}
	
	public void validateSave() {
		if(null==templateConfig.getAreaId()){
			this.addFieldError("templateConfig.areaId","区域的编号不能为空");
		}
		if(null==templateConfig.getTemplate().getId()){
			this.addFieldError("templateConfig.template.id","请选择模板文件");
		}
		if(null==templateConfig.getChannelSet().getId()){
			this.addFieldError("templateConfig.channelSet.id","请选择栏目");
		}		
		if(null==templateConfig.getChannel().getId()){
			this.addFieldError("templateConfig.channel.id","栏目不能为空");
		}		
		if(null==templateConfig.getCreater())
			templateConfig.setCreater(ApplicationUtils.getUser().getRealName());
		if(null==templateConfig.getCreateTime())
			templateConfig.setCreateTime(new Date());
	}
	
	public Pagination<TemplateConfig> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<TemplateConfig> pagination) {
		this.pagination = pagination;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
	
	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
		
	public TemplateConfig getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(TemplateConfig templateConfig) {
		this.templateConfig = templateConfig;
	}	
	
	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
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
	public void setTemplateConfigService(TemplateConfigService templateConfigService) {
		this.templateConfigService = templateConfigService;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
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
}
