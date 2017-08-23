package com.elian.cms.front.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StringUtils;

/**
 * 【医院】列表页action
 * @author Gechuanyi
 * 
 */
@Component
@Scope("prototype")
public class HospListAction {
	private Pagination<Content> pagination = new Pagination<Content>();
	/** 每页行数 */
	private int rowSize = 20;
	/** 当前页码 */
	private int pageNo = 1;
	
	private Integer channelId;
	private Integer siteId;
	private String path;
	
	private ContentService contentService;
	private DoctorService doctorService;

	public void list() {
		pagination.setRowSize(rowSize);
		pagination.setPageNo(pageNo);
		contentService.findForListStaticPage(channelId, siteId, pagination);
		StringBuilder html = new StringBuilder();
		appendListContent(html);
		html.append(pagination.getPageInfo());
		JSONObject obj = new JSONObject();
		obj.put("m", listModel(pagination.getList()));
		obj.put("list", html.toString());
		pagination.setList(null);
		obj.put("page",pagination);
		ApplicationUtils.sendJsonpObj(obj);
	}
	
	
	private List<ListModel> listModel(List<Content> list){
		List<ListModel> listModels=new ArrayList<ListModel>();
		ListModel model=null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Content c : list) {
			model=new ListModel(c.getId().toString(),c.getTitle(),path+ElianCodes.SPRIT+c.getId()+ElianCodes.SUFFIX_SHTML,format.format(c.getCreateTime()));
			listModels.add(model);
		}
		return listModels;
	}

	private void appendListContent(StringBuilder html) {
		List<Content> list = null;
		if ((list = pagination.getList()) != null) {
			html.append("   <ul class=\"list01_aico\"> ");
			appendLiContent(html, list);
			html.append(" </ul> ");
		}
	}
	private void appendDoctorContent(StringBuilder html) {
		List<Content> list = null;
		if ((list = pagination.getList()) != null) {
			html.append("   <ul class=\"pt03\"> ");
			appendDoctorContent(html, list);
			html.append(" </ul> ");
		}
	}

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
			if (i == 10)
				html.append("<br/>");
		}
	}
	
	private void appendDoctorContent(StringBuilder html, List<Content> list) {
		Iterator<Content> its = list.iterator();
		Content con=null;
		Doctor doc = null;
		for (; its.hasNext();) {
			con=its.next();
			doc = doctorService.get(con.getEntityId());
			String imgUrl="";
			if(doc.getDoctImg().contains("hosp"+"/"+siteId+"/"+"doctImg"))
				imgUrl=FreemarkerCodes.IMG_URL+doc.getDoctImg();
			else
				imgUrl=doc.getDoctImg();
			html.append("<li>");
			html.append("<a class=\"pic\" href=\"").append(path+ ElianCodes.SPRIT+ con.getId()+ElianCodes.SUFFIX_SHTML).append("\">");
			html.append("<img src=\"").append( imgUrl);   
			html.append("\" width=\"120\" height=\"160\" alt=\"").append(doc.getDoctName()).append("\"/>" );
			html.append("</a>");
			html.append("<h3  class=\"tit\"><em>").append(doc.getDoctName()).append("</em>&nbsp&nbsp").append(doc.getJobTitle()).append("</h3>");
			html.append("<div class=\"meta\">");
			html.append("<span class=\"tit\">专长：").append("</span>").append(doc.getSpeciality());			
			html.append("</div>");
			html.append("<div class=\"txt\">");
			html.append("<span class=\"tit\">详细介绍：</span>");
			String docIntroduction=StringUtils.replaceHtml(doc.getIntroduction());
			html.append(docIntroduction.length()>200?docIntroduction.substring(0, 200):docIntroduction);
			html.append("<a class=\"more\" href='").append(path+ ElianCodes.SPRIT+ con.getId()+ElianCodes.SUFFIX_SHTML);
			html.append("'  target='_blank'>查看&gt;&gt;</a>");
			html.append("</div>");
			html.append("</li>");
		}
	}
	
	
	public void doctor() {
		pagination.setRowSize(5);
		pagination.setPageNo(pageNo);
		contentService.findForListStaticPage(channelId, siteId, pagination);
		StringBuilder html = new StringBuilder();
		appendDoctorContent(html);
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
	
	public void information() {
	    list();
	}
	
	public void invitation() {
	    list();
	}
	
	public void bidding() {
	    list();
	}
	
	public void job() {
	    list();
	}
	public void department() {
	    list();
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

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
}
