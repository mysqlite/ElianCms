package com.elian.cms.admin.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.model.TrueFalseItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.IndexUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 内容功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class ContentAction extends BaseAction {
	private static final long serialVersionUID = 6033036352175944729L;

	private Integer id = Integer.valueOf(0);
	private boolean isEdit = false;

	private Pagination<Content> pagination = new Pagination<Content>(
			SearchParamUtils.getContentConditionMap());

	/** 树节点传递过来的是否叶子节点(内容类型节点) */
	private boolean isLeaf = false;
	/** 树节点传递过来的栏目ID */
	private Integer channelId = Integer.valueOf(0);
	/** 树节点传递过来的action名称 */
	private String action;
	/** 模型的组织类型是否为：公有 */
	private boolean isPublic = true;
	/** 判断如果是单页只能添加一条数据 */
	private Boolean isSingle;

	private Integer status = Integer.valueOf(-1);

	private ChannelService channelService;
	private ContentService contentService;

	public String list() {
		if(isPublish()) {
			generate();
		}
		contentService.findByAll(ApplicationUtils.getSite().getId(),
				channelId > 0 ? channelId : null, status, pagination);
		return LIST;
	}

	public void sort() {
		Integer id = ApplicationUtils.getAjaxId();
		Integer sort = ApplicationUtils.getAjaxSort();
		if (id != null && sort != null) {
			Content content = contentService.get(id);
			if (content == null)
				return;
			content.setSort(sort);
			contentService.save(content);
		}
	}

	public void topmost() {
		Integer id = ApplicationUtils.getAjaxId();
		int topLevel = ApplicationUtils.getAjaxStatus();
		if (id != null && topLevel > -1) {
			Content content = contentService.get(id);
			if (content == null)
				return;
			content.setTopmost(topLevel);
			contentService.save(content);
		}
	}

	public void status() {
		Integer id = ApplicationUtils.getAjaxId();
		int status = ApplicationUtils.getAjaxStatus();
		if (id != null && status > -1) {
			Content content = contentService.get(id);
			if (content == null)
				return;
			content.setStatus(status);
			contentService.save(content, true);
		}
	}
	
	public void check() {
		List<Integer> idList = ApplicationUtils.getAjaxIds();
		if (CollectionUtils.isEmpty(idList))
			return;
		for (Integer id : idList) {
			Content content = contentService.get(id);
			content.setStatus(status);
			// 如果传递过来的内容状态为：未通过，表示点击的是退回按钮,删除已静态化文件
			if (status.equals(ElianUtils.CONTENT_STATUS_4)) {
				contentService.deleteStaticPage(content);
				content.setStaticStatus(ElianUtils.STATIC_STATUS_0);
			}
			contentService.save(content, true);
		}
	}
	
	public String checkShowData() {
		checkOneData(ElianUtils.CONTENT_STATUS_3);
		return SAVE;
	}

	public String outShowData() {
		checkOneData(ElianUtils.CONTENT_STATUS_4);
		return SAVE;
	}

	private void checkOneData(int contentStatus) {
		Content content = contentService.findByIdAndAction(channelId, id,
				action);
		if (content != null && !content.getStatus().equals(contentStatus)) {
			content.setStatus(contentStatus);
			contentService.save(content, true);
		}
	}
	
	public void generate() {
		Integer id =(getControlId()!=null&&getControlId()!=0)?getControlId():ApplicationUtils.getAjaxId();
		if (id != null) {
			Content content = contentService.get(id);
			if (content == null)
				return;
			contentService.generate(content);
			if (content.getStaticStatus() != ElianUtils.STATIC_STATUS_1) {
				content.setStaticStatus(ElianUtils.STATIC_STATUS_1);
				contentService.save(content, true);
			}
		}
	}
	
	/*
	 * 获取栏目树结构
	 */
	public void tree() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addRootNode(list);

		List<Channel> channelList = channelService.findByAll(ApplicationUtils
				.getSite().getId(), null, true, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			Map<String, Object> map = null;
			for (Channel c : channelList) {
				map = new LinkedHashMap<String, Object>();
				map.put("ID", c.getId());
				map.put("NAME", c.getChannelName());
				map.put("PID", c.getParentId());
				map.put("CHANNEL_TYPE", getChannelTypeKey(c));
				map.put("CONTENT_TYPE", getContentTypeKey(c));
				map.put("ACTION_NAME", c.getModel() != null ? c.getModel()
						.getContentModel().getActionName() : "");
				map.put("IS_LEAF", c.getModel() != null ? true : false);
				map.put("IS_PUBLIC",
						(c.getModel() != null && ElianUtils.COMP_TYPE_PUBL
								.equals(c.getModel().getCompType())) ? true
								: false);
				map.put("OPEN", ElianUtils.FALSE_STR);
				list.add(map);
			}
		}

		ApplicationUtils.sendJsonArray(list);
	}

	/*
	 * 为栏目树结构添加根节点
	 */
	private void addRootNode(List<Map<String, Object>> list) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ID", 0);
		map.put("NAME", ElianUtils.ROOT_URL);
		map.put("PID", -1);
		map.put("IS_LEAF", false);
		map.put("IS_PUBLIC", isPublic);
		map.put("OPEN", ElianUtils.TRUE_STR);
		list.add(map);
	}

	private String getChannelTypeKey(Channel c) {
		if (ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())) {
			return c.getModel().getModelName();
		}
		for (SelectItem item : ElianUtils.getChannelTypeList()) {
			if (item.getKey().equals(c.getChannelType()))
				return item.getValue();
		}
		return "";
	}
	
	private String getContentTypeKey(Channel c) {
		if (!StringUtils.isBlank(c.getContentType()))
			for (SelectItem item : ElianUtils.getContentTypeList()) {
				if (item.getKey().equals(c.getContentType()))
					return item.getValue();
			}
		return "";
	}

	public TrueFalseItem getDisableItem() {
		return ElianUtils.getTrueFalseItem();
	}

	public List<SelectItem> getContentStatusList() {
		return ElianUtils.getContentStatusList();
	}
	
	public List<SelectItem> getStaticStatusList() {
		return ElianUtils.getStaticStatusList();
	}

	public List<SelectItem> getTopmostList() {
		return ElianUtils.getTopmostList();
	}
	
	public List<Site> getSiteList() {
		return ((SiteService) SpringUtils.getBean("siteService"))
				.findAllSiteByCompType(null, ElianUtils.COMP_TYPE_SUBS);
	}
	
	public boolean isMainStation() {
		return ApplicationUtils.isMainStation();
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
	
	public boolean isSingle() {
		if (isSingle == null) {
			int length = contentService.getContentListLength(channelId);
			return isSingle = length > 0;
		}
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Pagination<Content> getPagination() {
		return pagination;
	}
	
	/**
	 * 是否生成权限
	 */
	public boolean isGenerate() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null
				&& actionSet.contains(entityName + "!generate");
	}
	
	public void createIndex() {
		String siteId = ApplicationUtils.getRequest().getParameter("siteId");
		String actionName = ApplicationUtils.getRequest().getParameter(
				"actionName");
		if (StringUtils.isBlank(siteId) || StringUtils.isBlank(actionName))
			return;
		List<Content> contentList = contentService.findIndexContent(Integer
				.valueOf(siteId), actionName);
		contentService.save(contentList, false);
	}
	
	public void deleteIndex() {
		IndexUtils.cleanIndex();
	}
	
	public void delete() {
		List<Integer> ids=ApplicationUtils.getAjaxIds();
		for (Integer id : ids) {
			if(id!=null&&id>0) {
				Content content=contentService.get(id);
				if(content!=null)
					contentService.delete(content);
			}
		}
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

}
