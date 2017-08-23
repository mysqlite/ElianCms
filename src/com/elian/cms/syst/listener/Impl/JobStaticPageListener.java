package com.elian.cms.syst.listener.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Job;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DefaultRecommendService;
import com.elian.cms.admin.service.JobService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.SysXmlUtils;

/**
 * 内容监听器的实现
 * 
 * @author Joe
 * 
 */
@Component("jobStaticPageListener")
public class JobStaticPageListener extends StaticPageListener {	
	/** 模板页对象名称 */
	public static final String JOB = "job";
	public static final String SITE = "site";
	public static final String AREA = "area";
	public static final String EDUCATION = "education";
	public static final String JOBNATURE = "jobNature";
	
	private Channel channel;
	
	private JobService jobService;
	private AreaService areaService;
//	private ChannelService channelService;	
	private ContentService contentService;
	private DefaultRecommendService defaultRecommendService;
	@Resource
	public void setDefaultRecommendService(DefaultRecommendService defaultRecommendService) {
		this.defaultRecommendService = defaultRecommendService;
	}
	/**
	 * 生成资讯内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		defaultRecommendService.defaultRecommend(content, 243, 244);
		channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
    		Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(channel,channel.getContentTemp());
    		Job job = jobService.get(content.getEntityId());
    		StaticPageUtils.putResInMap(dataMap);
    		StaticPageUtils.putSysInMap(dataMap);
    		StaticPageUtils.putSeoInMap(dataMap, job);
    		job.setPublishTime(new Date());
			if (StringUtils.isNotBlank(job.getJobNature())) {
				job.setJobNature(SysXmlUtils.getXMLSelect("jobnature",
						job.getJobNature()).getValue());
			}
			if (StringUtils.isNotBlank(job.getEducation())) {
				job.setEducation(SysXmlUtils.getXMLSelect("education",
						job.getEducation()).getValue());
			}
    		job.setJobRequ(FilePathUtils.setEditorOutPath(job.getJobRequ()));
    
    		
    		dataMap.put(JOB, job);
    		dataMap.put(SITE, ApplicationUtils.getSite());
    		dataMap.put(AREA, areaService.get(job.getAreaId()));
    		dataMap.put(EDUCATION,getJobEduction(job.getEducation()));
    		dataMap.put(JOBNATURE,getJobNature(job.getJobNature()));
    		dataMap.put("path", getContentPath(content.getId()));
    		dataMap.put(CONTENT_ID, content.getId());
    		dataMap.put(FreemarkerCodes.TABLE_URL, StaticServiceImpl.getTableUrl(channel, "friend", "list"));
			dataMap.put(FreemarkerCodes.PATH_URL, StaticServiceImpl.getTableUrl(channel, "pageLevel", "list").concat("&siteUrl=").concat(StaticPageUtils.getSiteUrl()).concat("&companyType=").concat(ApplicationUtils.getSite().getComType()));

    		List<Content> jobContentList = contentService.getByActionName(
    				ApplicationUtils.getSite().getId(), SysXmlUtils
    						.getContentActionName("contentType", "job").getValue(),
    				true, 4);
    		dataMap.put("jobList", getDataContentFromcontList(jobContentList));
    
    		String outputFileName = channel.getPath() + ElianCodes.SPRIT
    				+ content.getId() + ElianCodes.SUFFIX_SHTML;// 定义模板输出文件名
    		path = StaticServiceImpl.generateStaticPage(channel,
					FreemarkerCodes.CONTENT_OUTPUT_FOLDER,
    				FreemarkerCodes.CONTENT_TEMPLATE_URL.concat(
    						ElianCodes.SPRIT).concat(
    						channel.getContentTemp().getFileName()),
    				outputFileName, dataMap, ftp);
		}
		return path;
	}


	protected ArrayList<DataContent1> getDataContentFromcontList(List<Content> contentList) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {	
			String path = getContentPath(c.getId());
			String detial=null;			
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, "#",c.getCreateTime()));			
		}
		return dataContentList;
	}

	/*
	 * 获取内容的绝对路径
	 */
	public String getContentPath(int contentId) {
		return StaticPageUtils.getSiteUrl() + channel.getPath()
				+ ElianCodes.SPRIT + contentId + ElianCodes.SUFFIX_SHTML;
	}
//	/*
//	 * 获取栏目标题路径
//	 */
//	private String getNavPath(Channel channel, ChannelService channelService) {
//		List<Channel> channelList = channelService.findNavParentForStaticPage(
//				channel.getId(), ApplicationUtils.getSite().getId());
//		if (CollectionUtils.isEmpty(channelList))
//			return "";
//		for (Channel c : channelList) {
//			if (c.isNavigetor())
//				return c.getPath();
//		}
//		return "";
//	}

	@Resource
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

//	@Resource
//	public void setChannelService(ChannelService channelService) {
//		this.channelService = channelService;
//	}
	
	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}	
	
	private String getJobEduction(String eduction){
		if(eduction==null) return null;
		List<SelectItem> educationList=SysXmlUtils.getXMLSelect("education");
		Iterator<SelectItem> it=educationList.iterator();
		while (it.hasNext()) {
			SelectItem i= it.next();
			if(i.getKey().equals(eduction))
				return i.getValue();
		}
		return null;
	}
	

	private String getJobNature(String jobnature) {
		if(jobnature==null) return null;
		List<SelectItem> educationList=SysXmlUtils.getXMLSelect("jobnature");
		Iterator<SelectItem> it=educationList.iterator();
		while (it.hasNext()) {
			SelectItem i= it.next();
			if(i.getKey().equals(jobnature))
				return i.getValue();
		}
		return null;
	}
}
