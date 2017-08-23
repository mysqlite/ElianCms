package com.elian.cms.syst.listener.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.data.DataContent1;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Links;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 内容监听器的实现
 * 
 * @author Joe
 * 
 */
@Component("linksStaticPageListener")
public class LinksStaticPageListener extends StaticPageListener {	
	/** 模板页对象名称 */
	public static final String LINKS = "links";
	private String navPath;
	
/*	private LinksService linksService;
	private ChannelService channelService;	
	private ContentService contentService;*/
	/**
	 * 生成资讯内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
//		navPath=getNavPath(channel, channelService);
//		
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		StaticPageUtils.putResInMap(dataMap);
//		
//		Links links = linksService.get(entityId);
//		putSeoInMap(dataMap, links);
//		StaticPageUtils.putSysInMap(dataMap,"../");
//		dataMap.put(LINKS, links);		
//		dataMap.put("path",getContentPath(contentId.toString(),navPath));		
//		
//		List<Content> linksConTentList=contentService.getByActionName(ApplicationUtils.getSite().getId(),SysXmlUtils.getContentActionName("contentType", "links").getValue(), true, 4);
//		
//		dataMap.put("linksList", getDataContentFromcontList(linksConTentList));
//		
//		dataMap.put(FreemarkerCodes.PATH_LIST, pathList);
//		dataMap.put(FreemarkerCodes.NAV_URL, "../"+ FreemarkerCodes.NAV_OUTPUT_NAME);
//		dataMap.put(FreemarkerCodes.CHANNEL_URL, "../" + nav.getPath()+ ElianCodes.SPRIT + FreemarkerCodes.SUB_CHANNEL_NAME);
//		String templateName = ApplicationUtils.getSite().getTempUrl()+ ElianCodes.SPRIT + channel.getModel().getContentTempUrl()+ ElianCodes.SPRIT + channel.getContentTempUrl();// 定义模板名
//		String outputFileName = ApplicationUtils.getSite().getId()+ ElianCodes.SPRIT + nav.getPath() + ElianCodes.SPRIT+ contentId + ElianCodes.SUFFIX_HTML;// 定义模板输出文件名
//		
//		FreemarkerUtils.generateStaticFile(templateName, outputFileName,dataMap, ftp);
		return  ElianCodes.SHAFT;//nav.getPath() + ElianCodes.SPRIT + contentId+ ElianCodes.SUFFIX_HTML;
	}

	protected ArrayList<DataContent1> getDataContentFromcontList(List<Content> contentList) {		
		if (CollectionUtils.isEmpty(contentList)) {
			return null;
		}			
		ArrayList<DataContent1> dataContentList = new ArrayList<DataContent1>();
		for (Content c : contentList) {	
			String path = getContentPath(c.getId().toString(),navPath);
			String detial=null;			
			dataContentList.add(new DataContent1(c.getTitle(),detial, path, "#",c.getCreateTime()));			
		}
		return dataContentList;
	}
	
	/**
	 * 添加内容SEO标题、SEO关键字、SEO描述
	 */
	public void putSeoInMap(Map<String, Object> dataMap, Links links) {
		dataMap.put(FreemarkerCodes.SEO_TITLE, links.getTitle());
		dataMap.put(FreemarkerCodes.SEO_DESCRIPTION, links.getDescription());
	}

	/*
	 * 获取内容的绝对路径
	 */
	private String getContentPath(String contentId, String navPath) {
		return StaticPageUtils.getSiteUrl() + ElianCodes.SPRIT + navPath
				+ ElianCodes.SPRIT + contentId + ElianCodes.SUFFIX_SHTML;
	}
	/*
	 * 获取栏目标题路径
	 */
	/*private String getNavPath(Channel channel, ChannelService channelService) {
		List<Channel> channelList = channelService.findNavParentForStaticPage(
				channel.getId(), ApplicationUtils.getSite().getId());
		if (CollectionUtils.isEmpty(channelList))
			return "";
		for (Channel c : channelList) {
			if (c.isNavigetor())
				return c.getPath();
		}
		return "";
	}*/

	/*@Resource
	public void setLinksService(LinksService linksService) {
		this.linksService = linksService;
	}
	

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	
	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}	*/
}
