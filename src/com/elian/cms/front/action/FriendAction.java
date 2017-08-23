package com.elian.cms.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;

/**
 * 栏目功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class FriendAction {
	private Integer contentId;
	private Integer channelId;
	private Integer siteId;
	private String path;

	private ContentService contentService;

	public void list() {
		List<Content> contentList = contentService.findForListStaticPage(channelId, siteId, null);
		StringBuilder html = new StringBuilder();
		appendLiContent(html, contentList);

		JSONObject obj = new JSONObject();
		obj.put("list", html.toString());
		obj.put("m", lastList(contentList));
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	private List<ListModel> lastList(List<Content> list) {
		if (CollectionUtils.isEmpty(list))
			return null;
		Iterator<Content> its = list.iterator();
		Content content = null;
		for (; its.hasNext();) {
			content = its.next();
			if (content.getId().equals(contentId))
				break;
		}
		if (content == null)
			return null;
		List<ListModel> models=new ArrayList<ListModel>();
		int index = list.indexOf(content);
		if (index != 0) {
			content = list.get(index - 1);
			models.add(new ListModel(content.getId().toString(),content.getTitle(),path + ElianCodes.SPRIT + content.getId()+ ElianCodes.SUFFIX_SHTML,true));
		}
		if (index != list.size() - 1) {
			content = list.get(index + 1);
			models.add(new ListModel(content.getId().toString(),content.getTitle(),path + ElianCodes.SPRIT + content.getId()+ ElianCodes.SUFFIX_SHTML,false));
		}
		return models;
	}

	private void appendLiContent(StringBuilder html, List<Content> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		Iterator<Content> its = list.iterator();
		Content content = null;
		for (; its.hasNext();) {
			content = its.next();
			if (content.getId().equals(contentId))
				break;
		}
		if (content == null)
			return;
		int index = list.indexOf(content);
		if (index != 0) {
			content = list.get(index - 1);
			html.append(" <div><a class='prev' href='").append(
					path + ElianCodes.SPRIT + content.getId()
							+ ElianCodes.SUFFIX_SHTML).append("'><em>上一篇：</em>")
					.append(content.getTitle()).append("</a></div> ");
		}
		if (index != list.size() - 1) {
			content = list.get(index + 1);
			html.append(" <div><a class='next' href='").append(
					path + ElianCodes.SPRIT + content.getId()
							+ ElianCodes.SUFFIX_SHTML).append("'><em>下一篇：</em>")
					.append(content.getTitle()).append("</a></div> ");
		}
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
}
