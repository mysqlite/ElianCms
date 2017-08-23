package com.elian.cms.admin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

/**
 * 内容功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class RecommendAction implements Serializable {
	private static final long serialVersionUID = 234096050500142416L;

	private ChannelService channelService;
	private ContentService contentService;
	private SiteService siteService;
	private AreaService areaService;
	private Integer controlId;

	public void recommendParentSite() {
		JSONObject json = new JSONObject();
		recommendParentSite(json);
		if (!json.isEmpty()) {
			ApplicationUtils.sendJsonStr(json.toString());
		}
	}

	/**
	 * 根据当前的站点获取推荐的父站点
	 */
	public void recommendParentSite(JSONObject json) {
		Site site = null;
		if (ApplicationUtils.isMainStation()) {
			json.put("SITE_NAME", "分站");
			json.put("SITE_ID", "");
			return;
		}
		else if (ApplicationUtils.isSubStation()) {
			site = siteService.getSiteByCompType(ElianUtils.COMP_TYPE_MAIN);
			json.put("SITE_NAME", "总站");
			json.put("SITE_ID", site.getId());
		}
		else if (ApplicationUtils.isHosp()) {
			Area area = areaService.get(ApplicationUtils.getHospital().getAreaId());
			List<Site> siteList = null;
			if (area != null&& area.getParentCode() != null&& !CollectionUtils.isEmpty(siteList = siteService.findSubSiteByArea(area.getParentCode()))) {
				site = siteList.get(0);
				json.put("SITE_NAME", site.getSiteName());
			}
			else {
				site = siteService.getSiteByCompType(ElianUtils.COMP_TYPE_MAIN);
				json.put("SITE_NAME", "总站");
			}
			json.put("SITE_ID", site.getId());
		}else if (ApplicationUtils.isCompany()) {
			Area area = areaService.get(ApplicationUtils.getCompany().getArea().getId());
			List<Site> siteList = null;
			if (area != null&& area.getParentCode() != null&& !CollectionUtils.isEmpty(siteList = siteService.findSubSiteByArea(area.getParentCode()))) {
				site = siteList.get(0);
				json.put("SITE_NAME", site.getSiteName());
			}
			else {
				site = siteService.getSiteByCompType(ElianUtils.COMP_TYPE_MAIN);
				json.put("SITE_NAME", "总站");
			}
			json.put("SITE_ID", site.getId());
		}
	}

	/*
	 * 获取栏目树结构
	 */
	public void channelTree() {
		Integer siteId = Integer.valueOf(ApplicationUtils.getRequest().getParameter("siteId"));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Channel> channelList = channelService.findByAll(siteId, null,true, false, null, false);
		if (channelList != null && channelList.size() > 0) {
			channelList = channelFilter(channelList, siteId, Integer.valueOf(ApplicationUtils.getRequest().getParameter("contentId")));
			Map<String, Object> map = null;
			for (Channel c : channelList) {
				map = new LinkedHashMap<String, Object>();
				map.put("id", c.getId());
				map.put("name", c.getChannelName());
				map.put("pId", c.getParentId());
				list.add(map);
			}
		}
		ApplicationUtils.sendJsonArray(list);
	}

	public List<Channel> channelFilter(List<Channel> channelList,Integer siteId, Integer contentId) {
		if (contentId == null) return channelList;
		Content content = contentService.get(contentId);
		if (content == null) return channelList;
		Integer modelId = content.getChannel().getModel().getId();
		Integer modelId2=0;
		if(modelId.equals(3)) modelId2=9;
		else if (modelId.equals(9))modelId2=3;
		List<Integer> channelIdList = new ArrayList<Integer>();
		for (Channel c : channelList) {
			if (c.getModel() != null && (c.getModel().getId().equals(modelId)|c.getModel().getId().equals(modelId2))) {
				channelIdList.add(c.getId());
			}
		}
		return channelService.getParentChannelList(siteId, channelIdList.toArray());
	}
	
//	public void addParentChannel(List<Channel> channelList,
//			List<Channel> newList) {
//		Integer tempId = null;
//		for (Channel c : newList) {
//			if (c.getParentId() != null && c.getParentId() > 0) {
//				tempId = c.getParentId();
//			}
//		}
//	}

	public void recommend() {
		String msg = "推荐成功!";
		try {
			Integer channelId = Integer.valueOf(ApplicationUtils.getRequest()
					.getParameter("channelId"));
			Integer contentId = Integer.valueOf(ApplicationUtils.getRequest()
					.getParameter("contentId"));
			Content content = contentService.get(contentId);
			if (existsRepeatData(channelId, content)) {
				return;
			}
			Channel channel = channelService.get(channelId);
			if (disaccordContentModel(channel, content)) {
				return;
			}
			Content c = createContent(channel, content);
			contentService.save(c, false);
		}
		catch (NumberFormatException ne) {
			ne.printStackTrace();
			msg = "栏目选择错误！";
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "推荐出现未知异常！";
		}
		ApplicationUtils.sendMsg(msg);
	}

	/**
	 * 存在的内容信息
	 */
	private boolean existsRepeatData(Integer channelId, Content content) {
		List<Integer> idList = new ArrayList<Integer>(1);
		idList.add(content.getEntityId());
		List<Content> contentList = contentService.findByIdsAndAction(channelId, idList, null);
		if (!CollectionUtils.isEmpty(contentList)) {
			ApplicationUtils.sendMsg("信息已推荐!");
			return true;
		}
		return false;
	}

	/**
	 * 内容模型不一致
	 */
	private boolean disaccordContentModel(Channel channel, Content content) {
		if (channel.getModel() == null|| content.getChannel().getModel() == null|| !channel.getModel().getId().equals(content.getChannel().getModel().getId())) {
			if(channel.getModel().getId().equals(3)||channel.getModel().getId().equals(9)) {
				if(content.getChannel().getModel().getId().equals(3)||content.getChannel().getModel().getId().equals(9))
				return false;
			}
			ApplicationUtils.sendMsg("内容模型不一致!");
			return true;
		}
		return false;
	}

	private Content createContent(Channel channel, Content content) {
		Content c = content.clone();
		c.setId(null);
		c.setVersion(null);
		c.setCreater(ApplicationUtils.getUser().getRealName());
		c.setCreateTime(new Date());
		c.setChannel(channel);
		c.setSite(channel.getSite());
		c.setStatus(ElianUtils.CONTENT_STATUS_1);
		c.setStaticStatus(ElianUtils.STATIC_STATUS_0);
		c.setHits(0);
		c.setRecommend(true);
		return c;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Integer getControlId() {
		return controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}
}
