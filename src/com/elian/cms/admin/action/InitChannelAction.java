package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.exception.TemplateConfigException;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.model.SiteInclude;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.InitChannelService;
import com.elian.cms.admin.service.InitSiteService;
import com.elian.cms.admin.service.ModelService;
import com.elian.cms.admin.service.SiteIncludeService;
import com.elian.cms.admin.service.StaticService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 默认栏目action 
 * @author thy
 */
@Component
@Scope("prototype")
public class InitChannelAction extends BaseAction {
	private static final long serialVersionUID = -4376394585267424068L;

	private final String TREE="tree";
	
	private Integer tempId=null;
	private Integer parentId=null;
	private boolean isEdit = false;
	private InitChannel initChannel=null;
	private Pagination<InitChannel> pagination=new Pagination<InitChannel>();
	
	private TemplateService templateService=null;
	private InitChannelService initChannelService=null;
	private ModelService modelService=null;
	private ChannelService channelService=null;
	private StaticService staticService=null;
	private SiteIncludeService siteIncludeService=null;
	private InitSiteService initSiteService=null;
	
	// 用于编辑时显示父栏目名称
	private String parentName = null;
	// 切换栏目类型的时候显示隐藏div
	private String channelType = null;
	// 父节点静态文件路径
	private String tempPath = "";
	
	// 栏目模板List
	private List<Template> channelTempList;
	// 首页模板List
	private List<Template> indexTempList;
	
	private List<SelectItem> contentTempList=null;
	private List<SelectItem> listTempList=null;
	
	
	public String tree(){
		return TREE;
	}

	public String list(){
		initChannelService.findByAll(tempId, parentId,pagination);
		return LIST;
	}
	
	public String edit() {
		if (isEdit && initChannel.getId() > 0) {
			initChannel = initChannelService.get(initChannel.getId());
			setDummyStaticPath(initChannel);
		}else {
			initChannel = createInitChannel();
		}
		channelType = initChannel.getChannelType();
		return EDIT;
	}
	
	public String save() {
		if (ElianUtils.CHANNEL_CONTENT.equals(initChannel.getChannelType()))
			initChannel.setModel(modelService.get(initChannel.getModel().getId()));
		else
			initChannel.setModel(null);
		
		if (initChannel.getChannelTemp() != null) {
			if (initChannel.getChannelTemp().getId() != null)
				initChannel.setChannelTemp(templateService.get(initChannel
						.getChannelTemp().getId()));
			else
				initChannel.setChannelTemp(null);
		}
		if (initChannel.getContentTemp() != null) {
			if (initChannel.getContentTemp().getId() != null)
				initChannel.setContentTemp(templateService.get(initChannel
						.getContentTemp().getId()));
			else
				initChannel.setContentTemp(null);
		}
		initChannel.setTemplateId(tempId);
		
		if(ElianUtils.CHANNEL_PARENT.equals(initChannel.getChannelType())) {
			initChannel.setContentType(null);
		}
		
		setRealStaticPath(initChannel);
		initChannelService.save(initChannel);
		return list();
	}
	
	/**
	 * 编辑的时候获取虚拟路径
	 */
	private void setDummyStaticPath(InitChannel channel) {
		if (StringUtils.isBlank(channel.getPath()))
			return;
		String[] paths = channel.getPath().split(ElianCodes.SPRIT);
		if (paths.length > 0)
			tempPath = paths[paths.length - 1];
	}
	
	/**
	 * 保存真实的静态文件路径
	 */
	private void setRealStaticPath(InitChannel channel) {
		String realPath = ElianCodes.SPRIT + tempPath;
		if (channel.getParentId() != null
				&& !Integer.valueOf(0).equals(channel.getParentId())) {
			InitChannel parent = initChannelService.get(channel.getParentId());
			realPath = parent.getPath() + realPath;
		}
		channel.setPath(realPath);
	}
	
	private InitChannel createInitChannel() {
		InitChannel ic = new InitChannel();
		if (parentId != null) {
			ic.setParentId(parentId);
		}
		ic.setChannelType(ElianUtils.CHANNEL_PARENT);
		ic.setContentType(ElianUtils.CONTENT_SINGLE);
		ic.setDisable(true);
		ic.setSort(99);
		ic.setCreateTime(new Date());
		ic.setCreater(ApplicationUtils.getUser().getId());
		return ic;
	}

	public Integer getTempId() {
		return tempId;
	}


	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Pagination<InitChannel> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<InitChannel> pagination) {
		this.pagination = pagination;
	}

	public InitChannel getInitChannel() {
		return initChannel;
	}

	public void setInitChannel(InitChannel initChannel) {
		this.initChannel = initChannel;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public void validateSave() {
		boolean error = false;
		if (ValidateUtils.isBlank(initChannel.getChannelName())) {
			this.addFieldError("initChannel.channelName", "栏目名称不能为空");
			error = true;
		}
		else if (ValidateUtils.isLengOut(initChannel.getChannelName(), 100)) {
			this.addFieldError("initChannel.channelName", "栏目名称必须在100字以内");
			error = true;
		}
		if(initChannel.getParentId()==null){
			this.addFieldError("initChannel.parentId", "父栏目不能为空");
			error = true;
		}
		if (ElianUtils.CHANNEL_CONTENT.equals(initChannel.getChannelType())
				|| ElianUtils.CHANNEL_PARENT.equals(initChannel.getChannelType())) {
			if (ValidateUtils.isBlank(tempPath)) {
				this.addFieldError("initChannel.path", "访问路径不能为空");
				error = true;
			}
			else if (ValidateUtils.isLengOut(tempPath, 30)) {
				this.addFieldError("initChannel.path", "访问路径必须在30字以内");
				error = true;
			}
			else if (!ValidateUtils.accountFilter(tempPath, 4, 16)) {
				this.addFieldError("initChannel.path", "访问路径只能为4-16位的小写字母及数字组合");
				error = true;
			}
		}
		if (ValidateUtils.isLengOut(initChannel.getKeywords(), 100)) {
			this.addFieldError("initChannel.keywords", "SEO关键字必须在100字以内");
			error = true;
		}
		if (ValidateUtils.isLengOut(initChannel.getTitle(), 100)) {
			this.addFieldError("initChannel.title", "SEO标题必须在100字以内");
			error = true;
		}
		if (ValidateUtils.isLengOut(initChannel.getDescription(), 255)) {
			this.addFieldError("initChannel.description", "SEO描述必须在255字以内");
			error = true;
		}
		if (ElianUtils.CHANNEL_CONTENT.equals(initChannel.getChannelType())) {
			if (initChannel.getModel().getId() == null) {
				this.addFieldError("initChannel.model.id", "请选择模型");
				error = true;
			}
			else if(error){
				initChannel.setModel(modelService.get(initChannel.getModel().getId()));
			}
		}
		channelType = initChannel.getChannelType();
	}
	
	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
	
	@Resource
	public void setInitChannelService(InitChannelService initChannelService) {
		this.initChannelService = initChannelService;
	}

	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setStaticService(StaticService staticService) {
		this.staticService = staticService;
	}

	@Resource
	public void setSiteIncludeService(SiteIncludeService siteIncludeService) {
		this.siteIncludeService = siteIncludeService;
	}
	
	@Resource
	public void setInitSiteService(InitSiteService initSiteService) {
		this.initSiteService = initSiteService;
	}

	public TrueFalseItem getNaviItem() {
		return ElianUtils.getTrueFalseItem();
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public List<SelectItem> getContentTypeList() {
		return ElianUtils.getContentTypeList();
	}

	public List<SelectItem> getChannelTypeList() {
		return ElianUtils.getChannelTypeList();
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
	

	public List<Template> getChannelTempList() {
		if (channelTempList == null) {
			channelTempList = new ArrayList<Template>();
			InvocationTemp(channelTempList,
					FreemarkerCodes.CHANNEL_TEMPLATE_URL,
					initChannel.getModel() == null ? null : initChannel.getModel()
							.getChannelTempPrefix());
		}
		return channelTempList;
	}
	
	public List<Template> getIndexTempList() {
		if (indexTempList == null) {
			indexTempList = new ArrayList<Template>();
			InvocationTemp(indexTempList, FreemarkerCodes.INDEX_TEMPLATE_URL,
					"");
		}
		return indexTempList;
	}
	
	public String getParentName() {
		if (initChannel == null || initChannel.getParentId() == null
				|| initChannel.getParentId().equals(0))
			parentName = ElianUtils.ROOT_URL;
		else
			parentName = initChannelService.get(initChannel.getParentId())
					.getChannelName();
		return parentName;
	}

	public List<SelectItem> getListTempList() {
		if (listTempList == null && initChannel.getModel() != null) {
			listTempList = new ArrayList<SelectItem>();
			InvocationTempItem(listTempList, FreemarkerCodes.LIST_TEMPLATE_URL,
					initChannel.getModel().getListTempPrefix());
		}
		return listTempList;
	}

	public List<SelectItem> getContentTempList() {
		if (contentTempList == null && initChannel.getModel() != null) {
			contentTempList = new ArrayList<SelectItem>();
			InvocationTempItem(contentTempList, initChannel.getModel()
					.getContentTempUrl(), initChannel.getModel()
					.getContentTempPrefix());
		}
		return contentTempList;
	}

	/*
	 * 为栏目树结构添加根节点
	 */
	private void addRootNode(List<Map<String, Object>> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", 0);
		map.put("name", ElianUtils.ROOT_URL);
		map.put("pId", -1);
		list.add(map);
	}
	
	public void navigetor() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean navigator = ApplicationUtils.getAjaxDisable();
		if (id != null && navigator != null) {
			initChannel = initChannelService.get(id);
			if (initChannel == null)
				return;
			initChannel.setNavigetor(!navigator);
			initChannelService.save(initChannel);
		}
	}
	
	public void treeAjax(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(tempId==null){
			List<Template> tempList=templateService.findByAll(0, true, null);
			if (tempList != null && tempList.size() > 0) {
				Map<String, Object> map = null;
				for (Template t : tempList) {
					map = new LinkedHashMap<String, Object>();
					map.put("ID", t.getId());
					map.put("NAME", t.getTempName());
					map.put("PID", t.getParentId());
					map.put("OPEN", ElianUtils.FALSE_STR);
					map.put("TYPE", "TEMP");
					list.add(map);
				}
			}
		}else{
			List<InitChannel> channelList = initChannelService.findByAll(tempId, null,null);
			if (channelList != null && channelList.size() > 0) {
				Map<String, Object> map = null;
				for (InitChannel c : channelList) {
					map = new LinkedHashMap<String, Object>();
					map.put("ID", c.getId());
					map.put("NAME", c.getChannelName());
					map.put("PID", c.getParentId());
					map.put("INIT_TEMP_ID", tempId);
					map.put("CHANNEL_TYPE", getChannelTypeKey(c));
					map.put("CHANNEL_TYPE_EN",c.getChannelType());
					map.put("CONTENT_TYPE", getContentTypeKey(c));
					map.put("CONTENT_TYPE_EN",c.getContentType());
					map.put("CHANNEL_TEMP_ID",null==c.getChannelTemp()?"":c.getChannelTemp().getId());
					map.put("CONTENT_TEMP_ID",null==c.getContentTemp()?"":c.getContentTemp().getId());
					map.put("TYPE", "CHANNEL");
					map.put("OPEN", ElianUtils.FALSE_STR);
					list.add(map);
				}
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}

	private String getChannelTypeKey(InitChannel c) {
		if (ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())) {
			return c.getModel().getModelName();
		}
		for (SelectItem item : getChannelTypeList()) {
			if (item.getKey().equals(c.getChannelType()))
				return item.getValue();
		}
		return "";
	}
	
	private String getContentTypeKey(InitChannel c) {
		if (!StringUtils.isBlank(c.getContentType()))
			for (SelectItem item : getContentTypeList()) {
				if (item.getKey().equals(c.getContentType()))
					return item.getValue();
			}
		return "";
	}
	
	public void parentTreeAjax(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addRootNode(list);
		List<InitChannel> channelList = initChannelService.findByAll(tempId,null,null);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (InitChannel c : channelList) {
				if(!ElianUtils.CHANNEL_PARENT.equals(c
						.getChannelType()))
					continue;
				map = new LinkedHashMap<String, Object>();
				map.put("id", c.getId());
				map.put("name", c.getChannelName());
				map.put("pId", c.getParentId());
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}
	

	public void changeSelectAjax() {
		Integer modelId = Integer.valueOf(ApplicationUtils
				.getAjaxSelectedValue());
		Model model = modelService.get(modelId);

		if (model != null) {
			contentTempList = new ArrayList<SelectItem>();
			InvocationTempItem(contentTempList, model.getContentTempUrl(), model
					.getContentTempPrefix());
			
			listTempList = new ArrayList<SelectItem>();
			InvocationTempItem(listTempList, FreemarkerCodes.LIST_TEMPLATE_URL,
					model.getListTempPrefix());
		}

		JSONObject obj = new JSONObject();
		obj.put(0, contentTempList);
		obj.put(1, listTempList);
		ApplicationUtils.sendJsonStr(obj.toString());
	}
	
	private void InvocationTempItem(List<SelectItem> tempList,
			String folderName, String tempPrefix) {
		List<Template> list = templateService.findByParentAndFolder(tempId, folderName
						.replace(ElianCodes.SPRIT, ""), true);
		if (CollectionUtils.isEmpty(list))
			return;
		for (Template t : list) {
			if (t.getFileName()
					.startsWith(tempPrefix == null ? "" : tempPrefix)) {
				tempList.add(new SelectItem(t.getTempName(), t.getId()
						.toString()));
			}
		}
	}
	
	private void InvocationTemp(List<Template> tempList, String folderName,
			String tempPrefix) {
		List<Template> list = templateService.findByParentAndFolder(tempId, folderName
						.replace(ElianCodes.SPRIT, ""), true);
		if (CollectionUtils.isEmpty(list))
			return;
		for (Template t : list) {
			if (t.getFileName()
					.startsWith(tempPrefix == null ? "" : tempPrefix))
				tempList.add(t);
		}
	}
	
	public void init(){
		JSONObject obj=new JSONObject();
		Site site= ApplicationUtils.getSite();
		boolean hasChannel= channelService.checkSiteHasChannel(site.getId());
		if(hasChannel){
			obj.put("msg", "您的站点已经包涵了栏目，不能进行初始化工作！");
		}else{
			try{
				//添加初始化栏目到新建立的站点
				Map<InitChannel,Channel> map=initSiteService.addInitChannelToSite(site);
				//初始化配置
				initSiteService.initTempConfig(map);
				//添加默认数据
				initSiteService.addDefaultData(map);
				//静态化内容数据
				initSiteService.staticContent(map);
				//静态化栏目数据
				//staticChannel(map);
				//静态化导航数据
				initSiteService.staticNav(map);
				//静态化包含数据
				initSiteService.staticIncludeFile();
			}catch (Exception e) {
				e.printStackTrace();
				obj.put("msg", "抱歉！初始化工作失败！");
				obj.put("exception",e.getMessage());
			}
			obj.put("msg", "初始化成功！");
		}
		
		ApplicationUtils.sendJsonStr(obj.toString());
	}

	public void staticIncludeFile() {
		SiteHeadAndFootAction sa=SpringUtils.getBean("siteHeadAndFootAction");
		sa.list();
		List<SiteInclude> includeList = siteIncludeService.getPageBySiteId(
				null, ApplicationUtils.getSite().getId());
		if(CollectionUtils.isEmpty(includeList)) return ;
		for(SiteInclude si:includeList){
			try {	
				SiteInclude siteInclude=siteIncludeService.get(si.getId());
				staticService.staticIncludeFile(siteInclude);
			}
			catch (TemplateConfigException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		
		List<InitChannel> initChannelList = initChannelService.findSubByParentId(idList,tempId);
		if (CollectionUtils.isEmpty(initChannelList))
			return;
		ListIterator<InitChannel> ites = initChannelList.listIterator(initChannelList
				.size());
		while (ites.hasPrevious()) {
			InitChannel c = ites.previous();
			initChannelService.delete(c);
		}
	}
	
	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			initChannel= initChannelService.get(id);
			if (initChannel == null)
				return;
			initChannel.setSort(sort);
			initChannelService.save(initChannel);
		}
	}
	
	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			List<InitChannel> initChannelList = null;
			if (disable) {
				ArrayList<Integer> idList = new ArrayList<Integer>();
				idList.add(id);
				initChannelList = initChannelService.findSubByParentId(idList,tempId);
			}else {
				initChannelList = new ArrayList<InitChannel>(1);
				initChannelList.add(initChannelService.get(id));
			}
			if (CollectionUtils.isEmpty(initChannelList))
				return;
			for (InitChannel ic : initChannelList) {
				ic.setDisable(!disable);
				initChannelService.save(ic);
			}
		}
	}
}
