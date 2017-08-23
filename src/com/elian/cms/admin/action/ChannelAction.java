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

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Model;
import com.elian.cms.admin.model.Template;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ModelService;
import com.elian.cms.admin.service.TemplateService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

/**
 * 栏目功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class ChannelAction extends BaseAction {
	private static final long serialVersionUID = -5977280814504027426L;

	private Channel channel;
	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;
	private Pagination<Channel> pagination = new Pagination<Channel>(
			SearchParamUtils.getChannelConditionMap());
	private final String FRONTlIST="frontList";

	// 用于导航显示列表数据
	private Integer navigateId = Integer.valueOf(0);
	// 父节点静态文件路径
	private String tempPath = "";
	
	// 用于编辑时显示父栏目名称
	private String parentName = null;
	// 切换栏目类型的时候显示隐藏div
	private String channelType = null;

	// 栏目模板List
	private List<Template> channelTempList;
	// 内容模板List
	private List<SelectItem> contentTempList;
	// 特殊页模板List
	private List<Template> otherTempList;
	// 首页模板List
	private List<Template> indexTempList;
	// 列表模板List
	private List<SelectItem> listTempList;
	private ChannelService channelService;
	private ModelService modelService;
	private TemplateService templateService;

	public String list() {
		channelService.findByAll(ApplicationUtils.getSite().getId(),
				navigateId, false, false, pagination, false);
		return LIST;
	}

	public String frontList() {
		channelService.findFrontList(ApplicationUtils.getSite().getId(),
				pagination);
		return FRONTlIST;
	}

	public void frontSort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			channel = channelService.get(id);
			if (channel == null)
				return;
			channel.setFrontSort(sort);
			channelService.save(channel);
		}
	}

	public String edit() {
		if (isEdit && id > 0) {
			channel = channelService.get(id);
			setDummyStaticPath(channel);
		}
		else {
			channel = createChannel();
		}
		channelType = channel.getChannelType();
		return EDIT;
	}

	private Channel createChannel() {
		Channel channel = new Channel();
		if (navigateId != null) {
			channel.setParentId(navigateId);
		}
		channel.setChannelType(ElianUtils.CHANNEL_PARENT);
		channel.setContentType(ElianUtils.CONTENT_SINGLE);
		channel.setDisable(true);
		// channel.setLeaf(true);
		channel.setSort(99);
		channel.setCreateTime(new Date());
		channel.setCreater(ApplicationUtils.getUser().getId());
		return channel;
	}

	public void validateSave() {
		if (ValidateUtils.isBlank(channel.getChannelName())) {
			this.addFieldError("channel.channelName", "栏目名称不能为空");
		}
		else if (ValidateUtils.isLengOut(channel.getChannelName(), 100)) {
			this.addFieldError("channel.channelName", "栏目名称必须在100字以内");
		}
		if(channel.getParentId()==null){
			this.addFieldError("channel.parentId", "父栏目不能为空");
		}
		if (ElianUtils.CHANNEL_CONTENT.equals(channel.getChannelType())
				|| ElianUtils.CHANNEL_PARENT.equals(channel.getChannelType())) {
			if (ValidateUtils.isBlank(tempPath)) {
				this.addFieldError("channel.path", "访问路径不能为空");
			}
			else if (ValidateUtils.isLengOut(tempPath, 30)) {
				this.addFieldError("channel.path", "访问路径必须在30字以内");
			}
			else if (!ValidateUtils.accountFilter(tempPath, 4, 16)&&!isEdit) {
				this.addFieldError("channel.path", "访问路径只能为4-16位的小写字母及数字组合");
			}
		}
		if (ValidateUtils.isLengOut(channel.getKeywords(), 100)) {
			this.addFieldError("channel.keywords", "SEO关键字必须在100字以内");
		}
		if (ValidateUtils.isLengOut(channel.getTitle(), 100)) {
			this.addFieldError("channel.title", "SEO标题必须在100字以内");
		}
		if (ValidateUtils.isLengOut(channel.getDescription(), 255)) {
			this.addFieldError("channel.description", "SEO描述必须在255字以内");
		}
		if (ElianUtils.CHANNEL_INDEX.equals(channel.getChannelType())
				&& channelService.existIndexChannel(ApplicationUtils.getSite().getId(), channel.getId())) {
			this.addFieldError("channel.indexTempUrl", "已经存在首页栏目，首页栏目有且只能有一个");
		}
		if (ElianUtils.CHANNEL_CONTENT.equals(channel.getChannelType())) {
			if (channel.getModel().getId() == null) {
				this.addFieldError("channel.model.id", "请选择模型");
			}
			// else if (ValidateUtils.isBlank(channel.getContentTempUrl())) {
			// this.addFieldError("channel.contentTempUrl", "请选择内容模板");
			// error = true;
			// }
			// else if (ElianUtils.CONTENT_LIST.equals(channel.getContentType())
			// && ValidateUtils.isBlank(channel.getTypeUrl())) {
			// this.addFieldError("channel.listTempUrl", "请选择列表模板");
			// error = true;
			// }
			else if(this.hasErrors()){
				channel.setModel(modelService.get(channel.getModel().getId()));
			}
		}
		// else if (ElianUtils.CHANNEL_PARENT.equals(channel.getChannelType()))
		// {
		// if (StringUtils.isBlank(channel.getTypeUrl()))
		// this.addFieldError("channel.channelTempUrl", "请选择子栏目模板");
		// }
		// else if (ElianUtils.CHANNEL_OUT.equals(channel.getChannelType())) {
		// if (StringUtils.isBlank(channel.getTypeUrl()))
		// this.addFieldError("channel.outUrl", "外部链接不能为空");
		// }
		// else if (ElianUtils.CHANNEL_SPECIAL.equals(channel.getChannelType()))
		// {
		// if (StringUtils.isBlank(channel.getTypeUrl()))
		// this.addFieldError("channel.specialTempUrl", "请选择特殊页模板");
		// }
		// else if (ElianUtils.CHANNEL_INDEX.equals(channel.getChannelType())) {
		// if (StringUtils.isBlank(channel.getTypeUrl()))
		// this.addFieldError("channel.indexTempUrl", "请选择首页模板");
		// }
		channelType = channel.getChannelType();
		//channel.getChannelTemp().getId();
	}

	public String save() {
		// if (navigateId != null && navigateId > 0) {
		// Channel c = channelService.get(navigateId);
		// if (c.isLeaf()) {
		// c.setLeaf(false);
		// channelService.save(c);
		// }
		// }
		
		if (ElianUtils.CHANNEL_CONTENT.equals(channel.getChannelType()))
			channel.setModel(modelService.get(channel.getModel().getId()));
		else
			channel.setModel(null);
		
		if (channel.getChannelTemp() != null) {
			if (channel.getChannelTemp().getId() != null)
				channel.setChannelTemp(templateService.get(channel
						.getChannelTemp().getId()));
			else
				channel.setChannelTemp(null);
		}
		if (channel.getContentTemp() != null) {
			if (channel.getContentTemp().getId() != null)
				channel.setContentTemp(templateService.get(channel
						.getContentTemp().getId()));
			else
				channel.setContentTemp(null);
		}
		channel.setSite(ApplicationUtils.getSite());
		
		if(ElianUtils.CHANNEL_PARENT.equals(channel.getChannelType())) {
			channel.setContentType(null);
		}
		
		setRealStaticPath(channel);
		channelService.save(channel);
		return SAVE;
	}
	
	/**
	 * 编辑的时候获取虚拟路径
	 */
	private void setDummyStaticPath(Channel channel) {
		if (StringUtils.isBlank(channel.getPath()))
			return;
		String[] paths = channel.getPath().split(ElianCodes.SPRIT);
		if (paths.length > 0)
			tempPath = paths[paths.length - 1];
	}
	
	/**
	 * 保存真实的静态文件路径
	 */
	private void setRealStaticPath(Channel channel) {
		String realPath = ElianCodes.SPRIT + tempPath;
		if (channel.getParentId() != null
				&& !Integer.valueOf(0).equals(channel.getParentId())) {
			Channel parent = channelService.get(channel.getParentId());
			realPath = parent.getPath() + realPath;
		}
		channel.setPath(realPath);
	}

	public void delete() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		// changeParentLeaf(idList);
		
		List<Channel> channelList = channelService.findSubByParentId(idList,
				ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(channelList))
			return;
		ListIterator<Channel> ites = channelList.listIterator(channelList
				.size());
		while (ites.hasPrevious()) {
			Channel c = ites.previous();
			channelService.delete(c);
		}
	}
	
	/**
	 * 删除子栏目时，把相应的父栏目设置为叶子节点
	 */
	// private void changeParentLeaf(List<Integer> idList) {
	// List<Channel> parentList = channelService.findIsLeafBySub(idList,
	// ApplicationUtils.getSite().getId());
	// if (CollectionUtils.isEmpty(parentList))
	// return;
	// Iterator<Channel> ites = parentList.iterator();
	// while (ites.hasNext()) {
	// Channel c = ites.next();
	// c.setLeaf(true);
	// channelService.save(c);
	// }
	// }

	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			channel = channelService.get(id);
			if (channel == null)
				return;
			channel.setSort(sort);
			channelService.save(channel);
		}
	}

	public void navigetor() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean navigator = ApplicationUtils.getAjaxDisable();
		if (id != null && navigator != null) {
			channel = channelService.get(id);
			if (channel == null)
				return;
			channel.setNavigetor(!navigator);
			channelService.save(channel);
		}
	}

	public void disable() {
		Integer id = ApplicationUtils.getAjaxId();
		Boolean disable = ApplicationUtils.getAjaxDisable();
		if (id != null && disable != null) {
			List<Channel> channelList = null;
			if (disable) {
				ArrayList<Integer> idList = new ArrayList<Integer>();
				idList.add(id);
				channelList = channelService.findSubByParentId(idList,
						ApplicationUtils.getSite().getId());
			}
			else {
				channelList = new ArrayList<Channel>(1);
				channelList.add(channelService.get(id));
			}
			if (CollectionUtils.isEmpty(channelList))
				return;
			for (Channel c : channelList) {
				c.setDisable(!disable);
				channelService.save(c);
			}
		}
	}

	public void changeSelect() {
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

	public List<Template> getChannelTempList() {
		if (channelTempList == null) {
			channelTempList = new ArrayList<Template>();
			InvocationTemp(channelTempList,
					FreemarkerCodes.CHANNEL_TEMPLATE_URL,
					channel.getModel() == null ? null : channel.getModel()
							.getChannelTempPrefix());
		}
		return channelTempList;
	}

	public List<SelectItem> getListTempList() {
		if (listTempList == null && channel.getModel() != null) {
			listTempList = new ArrayList<SelectItem>();
			InvocationTempItem(listTempList, FreemarkerCodes.LIST_TEMPLATE_URL,
					channel.getModel().getListTempPrefix());
		}
		return listTempList;
	}

	public List<SelectItem> getContentTempList() {
		if (contentTempList == null && channel.getModel() != null) {
			contentTempList = new ArrayList<SelectItem>();
			InvocationTempItem(contentTempList, channel.getModel()
					.getContentTempUrl(), channel.getModel()
					.getContentTempPrefix());
		}
		return contentTempList;
	}

	public List<Template> getOtherTempList() {
		if (otherTempList == null) {
			otherTempList = new ArrayList<Template>();
			InvocationTemp(otherTempList, FreemarkerCodes.OTHER_TEMPLATE_URL,
					"");
		}
		return otherTempList;
	}

	public List<Template> getIndexTempList() {
		if (indexTempList == null) {
			indexTempList = new ArrayList<Template>();
			InvocationTemp(indexTempList, FreemarkerCodes.INDEX_TEMPLATE_URL,
					"");
		}
		return indexTempList;
	}
	
	private void InvocationTempItem(List<SelectItem> tempList,
			String folderName, String tempPrefix) {
		List<Template> list = templateService.findByParentAndFolder(
				ApplicationUtils.getSite().getTemplate().getId(), folderName
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
		List<Template> list = templateService.findByParentAndFolder(
				ApplicationUtils.getSite().getTemplate().getId(), folderName
						.replace(ElianCodes.SPRIT, ""), true);
		if (CollectionUtils.isEmpty(list))
			return;
		for (Template t : list) {
			if (t.getFileName()
					.startsWith(tempPrefix == null ? "" : tempPrefix))
				tempList.add(t);
		}
		// String siteUrl = ApplicationUtils.getRealPath(null,
		// FreemarkerCodes.TEMPLATE_URL)
		// + "//" + ApplicationUtils.getSite().getTemplate().getFileName();
		// File file = new File(siteUrl + "//" + tempUrl);
		// if (file != null && file.isDirectory()) {
		// File[] childs = file.listFiles();
		// addTemp(tempList, childs, tempPrefix);
		// }
	}

	// private void addTemp(List<SelectItem> tempList, File[] childs, String
	// prefix) {
	// if (ArrayUtils.isEmpty(childs)) {
	// return;
	// }
	// prefix = (prefix == null ? "" : prefix);
	// for (File file : childs) {
	// if (file.getName().startsWith(prefix)) {
	// tempList.add(new SelectItem(file.getName(), file.getName()));
	// }
	// }
	// }

	public List<Model> getModelList() {
		String compType = ApplicationUtils.getSite().getComType();
		if (ElianUtils.COMP_TYPE_MAIN.equals(compType)
				|| ElianUtils.COMP_TYPE_SUBS.equals(compType)) {
			return modelService.findByComType(null, null);
		}
		else
			return modelService.findByComType(compType, null);
	}
	
	/*
	 * 获取栏目树结构
	 */
	public void parentTree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addRootNode(list);
		List<Channel> channelList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, false, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (Channel c : channelList) {
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

	/*
	 * 获取栏目树结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addRootNode1(list);

		List<Channel> channelList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, false, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (Channel c : channelList) {
				map = new LinkedHashMap<String, Object>();
				map.put("ID", c.getId());
				map.put("NAME", c.getChannelName());
				map.put("PID", c.getParentId());
				map.put("CHANNEL_TYPE", getChannelTypeKey(c));
				map.put("CONTENT_TYPE", getContentTypeKey(c));
				map.put("PARENT", ElianUtils.CHANNEL_PARENT.equals(c
						.getChannelType()));
				// map.put("IS_LEAF", c.isLeaf());
				// map.put("PARENT_STATIC_PATH", getParentStaticPath(c));
				map.put("OPEN", ElianUtils.FALSE_STR);
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}
	
	// public String getParentStaticPath(Channel c) {
	// String parentPath = null;
	// if (ElianUtils.CHANNEL_PARENT.equals(c.getChannelType())) {
	// parentPath = ElianCodes.SPRIT.equals(c.getPath()) ? "" : c
	// .getPath();
	// }
	// else {
	// if (c.getParentId() != null && c.getParentId() > 0) {
	// parentPath = channelService.get(c.getParentId()).getPath();
	// }
	// }
	// return StringUtils.isBlank(parentPath) ? ElianCodes.SPRIT : parentPath
	// .concat(ElianCodes.SPRIT);
	// }

	public void tempSetTree() {		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addTempRootNode(list);

		List<Channel> channelList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, false, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (Channel c : channelList) {
				map = new LinkedHashMap<String, Object>();
				map.put("ID", c.getId());
				map.put("NAME", c.getChannelName());
				map.put("PID", c.getParentId());
				map.put("CHANNEL_TYPE", getChannelTypeKey(c));
				map.put("CONTENT_TYPE", getContentTypeKey(c));	
				map.put("CHANNEL_TYPE_EN", c.getChannelType());
				map.put("CONTENT_TYPE_EN", c.getContentType());
				if(null!=c.getChannelTemp())
					map.put("CHANNEL_TEMP_ID", c.getChannelTemp().getId());
				else
					map.put("CHANNEL_TEMP_ID","");
				if(null!=c.getContentTemp())
					map.put("CONTENT_TEMP_ID", c.getContentTemp().getId());
				else
					map.put("CONTENT_TEMP_ID", "");
				map.put("OPEN", ElianUtils.FALSE_STR);
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}
	
	private void addTempRootNode(List<Map<String, Object>> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ID", 0);
		map.put("NAME", ElianUtils.ROOT_URL);
		map.put("PID", -1);
		map.put("CHANNEL_TYPE","");
		map.put("CONTENT_TYPE","");	
		map.put("CHANNEL_TYPE_EN","");
		map.put("CONTENT_TYPE_EN","");
		map.put("CHANNEL_TEMP_ID","");
		map.put("CONTENT_TEMP_ID", "");
		map.put("OPEN", ElianUtils.TRUE_STR);
		list.add(map);		
	}

	private String getChannelTypeKey(Channel c) {
		if (ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())) {
			return c.getModel().getModelName();
		}
		for (SelectItem item : getChannelTypeList()) {
			if (item.getKey().equals(c.getChannelType()))
				return item.getValue();
		}
		return "";
	}
	
	private String getContentTypeKey(Channel c) {
		if (!StringUtils.isBlank(c.getContentType()))
			for (SelectItem item : getContentTypeList()) {
				if (item.getKey().equals(c.getContentType()))
					return item.getValue();
			}
		return "";
	}

	/*
	 * 为栏目树结构添加根节点
	 */
	private void addRootNode1(List<Map<String, Object>> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ID", 0);
		map.put("NAME", ElianUtils.ROOT_URL);
		map.put("PID", -1);
		map.put("IS_LEAF", false);
		map.put("PARENT", ElianUtils.TRUE_STR);
		map.put("PARENT_STATIC_PATH", ElianCodes.SPRIT);
		map.put("OPEN", ElianUtils.TRUE_STR);
		list.add(map);
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

	public List<SelectItem> getContentTypeList() {
		return ElianUtils.getContentTypeList();
	}

	public List<SelectItem> getChannelTypeList() {
		return ElianUtils.getChannelTypeList();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
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
	
	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public Integer getNavigateId() {
		return navigateId;
	}

	public void setNavigateId(Integer navigateId) {
		this.navigateId = navigateId;
	}

	public String getParentName() {
		if (channel == null || channel.getParentId() == null
				|| channel.getParentId().equals(0))
			parentName = ElianUtils.ROOT_URL;
		else
			parentName = channelService.get(channel.getParentId())
					.getChannelName();
		return parentName;
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

	public TrueFalseItem getNaviItem() {
		return ElianUtils.getTrueFalseItem();
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getDisableItem();
	}

	public Pagination<Channel> getPagination() {
		return pagination;
	}


	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
	
	@Resource
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}
}
