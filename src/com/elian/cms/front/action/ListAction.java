package com.elian.cms.front.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.model.Pagination;
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
public class ListAction {
	private Pagination<Content> pagination = new Pagination<Content>();

	/** 每页行数 */
	private int rowSize = 20;
	/** 当前页码 */
	private int pageNo = 1;
	
	private Integer channelId;
	private Integer siteId;
	private String path;
	
	private ContentService contentService;

	public void list() {
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		contentService.findForListStaticPage(channelId, siteId, pagination);
		StringBuilder html = new StringBuilder();
		appendListContent(html);
		html.append(pagination.getPageInfo());

		JSONObject obj = new JSONObject();
		obj.put("list", html.toString());
		StringBuffer sb = new StringBuffer();
		String cb = ApplicationUtils.getRequest().getParameter("callback");
		if (cb != null) {// 如果是跨域
			sb.append(cb);
			sb.append("(");
			sb.append(obj.toString());
			sb.append(")");
		}
		else {
			sb.append(obj.toString());
		}
		ApplicationUtils.sendJsonStr(sb.toString());
	}

	private void appendListContent(StringBuilder html) {
		List<Content> list = null;
		if ((list = pagination.getList()) != null) {
			html.append(" <ul class='list01_lico'> ");
			appendLiContent(html, list);
			html.append(" </ul> ");
		}
	}

	/*private void appendLiContent(StringBuilder html, List<Content> list) {
		Iterator<Content> its = list.iterator();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Content content = null;
		for (; its.hasNext();) {
			content = its.next();
			html.append(" <li> ");
			html.append(" 	<div class='txt'> ");
			html.append(" 		<a  target='_blank' href='").append(
					path + ElianCodes.SPRIT
							+ content.getId()
							+ ElianCodes.SUFFIX_HTML).append("'>").append(
					content.getTitle()).append("</a>");
			html.append(" 	</div> ");
			html.append(" 	<span class='date'>").append(
					format.format(content.getCreateTime())).append("</span> ");
			html.append(" </li> ");
		}
	}*/

	private void appendLiContent(StringBuilder html, List<Content> list) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Content content = null;
		for (int i = 0; i < list.size(); i++) {
			content = list.get(i);
			html.append(" <li> ");
			html.append(" 	<div class='txt'> ");
			html.append(" 		<a  target='_blank' href='").append(
					path + ElianCodes.SPRIT + content.getId()
							+ ElianCodes.SUFFIX_SHTML).append("'>").append(
					content.getTitle()).append("</a>");
			html.append(" 	</div> ");
			html.append(" 	<span class='date'>").append(
					format.format(content.getCreateTime())).append("</span> ");
			html.append(" </li> ");
			if (i == 9)
				html.append("<br/>");
		}
	}
	
	public void jobList() {
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		contentService.findForListStaticPage(channelId, siteId, pagination);
		StringBuilder html = new StringBuilder();
		appendListContent(html);
		html.append(pagination.getPageInfo());

		JSONObject obj = new JSONObject();
		obj.put("list", html.toString());
		StringBuffer sb = new StringBuffer();
		String cb = ApplicationUtils.getRequest().getParameter("callback");
		if (cb != null) {// 如果是跨域
			sb.append(cb);
			sb.append("(");
			sb.append(obj.toString());
			sb.append(")");
		}
		else {
			sb.append(obj.toString());
		}
		ApplicationUtils.sendJsonStr(sb.toString());
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

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public Pagination<Content> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Content> pagination) {
		this.pagination = pagination;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
}
