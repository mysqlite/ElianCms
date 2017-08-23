package com.elian.cms.front.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 栏目功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class SubsListAction {
	private Pagination<Content> pagination = new Pagination<Content>();

	/** 每页行数 */
	private int rowSize = 35;
	/** 当前页码 */
	private int pageNo = 1;
	
	private Integer channelId;
	private Integer siteId;
	private String path;
	
	private ContentService contentService;
	
	//
	private Site site;	
	private AreaService areaService;
	private SiteService siteService;
	private JobService jobService;
//	private ChannelService channelService;	
	private List<Content> contentList;
	
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
			appendLiContent(html, list);		
		}
	}

	private void appendLiContent(StringBuilder html, List<Content> list) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Content content = null;	
		int ulSize=list.size()/5;
		if(list.size()%5!=0) ulSize++;
		for(int j=1;j<=ulSize;j++){		
			int size=5;
			if(j==ulSize) size=list.size()%5;
			html.append(" <ul> ");	
			for (int i = 0; i <size; i++) {
				content = list.get((j-1)*5+i);
				html.append(" <li> ");
				html.append(" 	<div class='list_txt_left'> ");
				html.append(" 		·<a  target='_blank' href='").append(
						path + ElianCodes.SPRIT + content.getId()
						+ ElianCodes.SUFFIX_SHTML).append("'>").append(
								content.getTitle()).append("</a>");
				html.append(" 	</div> ");
				html.append(" 	<div class='list_txt_right'>").append(
						format.format(content.getCreateTime())).append("</div> ");
				html.append(" <div class='clearfx'></div>");
				html.append(" </li> ");
			}
			html.append("<li>");
			html.append("<div class='list_xline'></div>");
			html.append("</li>");
			html.append(" </ul> ");
		}
	}
	
	public void jobList() {
		pagination.setRowSize(6);
		pagination.setPageNo(pageNo);		
		
		site=siteService.get(siteId);		
//		List<Channel> subChannelList=channelService.findSubByParentId(channelId, siteId);
		contentList=contentService.findForListStaticPage(channelId, siteId, pagination);
		
		List<Job> jobList=jobService.findFormContentList(contentList,siteId);
		
//		contentService.findForListStaticPage(channelId, siteId, pagination);
		StringBuilder html = new StringBuilder();
		appendListContent(html,jobList);
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

	private void appendListContent(StringBuilder html, List<Job> jobList) {
		if (jobList !=  null) {		
			appendTdContent(html, jobList);
		}
	}

	private void appendTdContent(StringBuilder html, List<Job> jobList) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");		
		 html.append("<table class='search_result_tbl' cellspacing='0' width='100%'>")
         .append("<thead>")
             .append("<tr class='th'>")
                 .append("<th class='th01'>操作</th>")
                 .append("<th class='th02'>职位名称</th>")
                 .append("<th class='th03'>公司名称</th>")
                 .append("<th class='th04'>性别</th>")
                 .append("<th class='th05'>工作地点</th>")
                 .append("<th class='th06'>工作经验</th>")
                 .append("<th class='th07'>发布时间</th>")
                 .append("<th class='th08'>有效日期</th>")
                 .append("<th class='th09'></th>")
         	.append("</tr>")
			.append("</thead>");				 
		 html.append("<tbody id='zebra_lists'>");
		
		for (int i = 0; i < jobList.size(); i++) {
				 Content c=getContentByJob(contentList,jobList.get(i));
				 if(null==c) c=contentService.getByEntityId(site, Job.class, jobList.get(i).getId());				 
				
				 html.append("<tr class='tr0'>");
				 html.append("  <td><input type='checkbox' class='ipc'/></td>");
				 html.append("<td class='job_tit'>");
				 html.append("  <a href='").append(path + ElianCodes.SPRIT +c.getId()
							+ ElianCodes.SUFFIX_SHTML).append("'>");
				 html.append(		jobList.get(i).getTitle());
				 html.append("  </a>");
				 html.append("  <img src='http://images.elian.cc/design/main/jobs/ico_jipin.png' alt='急聘'/>");
				 html.append("</td>");
				 html.append("<td class='com_name'>");
				 html.append("  <a href='").append(path + ElianCodes.SPRIT +c.getId()
							+ ElianCodes.SUFFIX_SHTML).append("'>");
				 html.append(		site.getShortName());
				 html.append("  </a>");
				 html.append("</td>");
				 html.append("<td>");
				 html.append(		ElianUtils.getSexCnName(jobList.get(i).getGender()));
				 html.append("</td>");
				 html.append("<td>");
				 html.append(		(jobList.get(i).getAreaId()==0?"不限":areaService.get(jobList.get(i).getAreaId())));
				 html.append("</td>");
				 html.append("<td>");				
				 html.append(		(StringUtils.isBlank(jobList.get(i).getWorkExpe())?"无":jobList.get(i).getWorkExpe()));
				 html.append("</td>");  
				 html.append("<td>");				
				 html.append(		format.format(jobList.get(i).getPublishTime()));
				 html.append("</td>");  
				 html.append("<td>");				
				 html.append(		format.format(jobList.get(i).getExpireTime()));
				 html.append("</td>");  
				 html.append("<td class='detail_btn' height='40'>");	
				 html.append("	 <div>职位描述<b></b></div>");
				 html.append("</td>");  
				 html.append("</tr>"); 
				 
				 html.append("<tr class='tr1'>");
				 html.append("  <td colspan='7' class='details_bd'>");
				 html.append("		<div class='details'>");
				 html.append("			职位类型："); html.append(jobList.get(i).getJobNature()); html.append(" | ");
				 html.append("			学历要求："); html.append(StringUtils.isBlank(jobList.get(i).getEducation())?"不限":jobList.get(i).getEducation()); html.append(" | ");
				 html.append("			住宿情况："); html.append(jobList.get(i).getHousing()); html.append(" | ");
				 html.append("		             提供月薪："); html.append(jobList.get(i).getSalary()); 
				 html.append("		             其他要求：暂无");  
				 html.append("		</div>");				 
				 html.append("  </td>"); 
				 html.append("  <td colspan='2'><a class='rapid_appoint_btn' href='#'></a></td>");
				 html.append("</tr>");      
				 
				 html.append("<tr class='tr2'><td colspan='9'></td></tr>"); 		
		}
		html.append("</tbody>");
		html.append("</table>");
	}
	
	private Content getContentByJob(List<Content> contentList,Job job){
		for(Content c:contentList){
			if(c.getEntityId().equals(job.getId()))
				return c;
		}
		return null;
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
		jobList();
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
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

//	@Resource
//	public void setChannelService(ChannelService channelService) {
//		this.channelService = channelService;
//	}
}
