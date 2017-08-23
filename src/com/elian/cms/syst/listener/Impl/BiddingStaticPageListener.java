package com.elian.cms.syst.listener.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Bidding;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.BiddingService;
import com.elian.cms.admin.service.DefaultRecommendService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 招标内容静态页
 * 
 * @author Joe
 * 
 */
@Component("biddingStaticPageListener")
public class BiddingStaticPageListener extends
		StaticPageListener {
	/** 模板页对象名称 */
	public static final String BIDD = "bidd";

	private BiddingService biddingService;
	private DefaultRecommendService defaultRecommendService;
	@Resource
	public void setDefaultRecommendService(DefaultRecommendService defaultRecommendService) {
		this.defaultRecommendService = defaultRecommendService;
	}
	/**
	 * 生成招标内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		defaultRecommendService.defaultRecommend(content, 234, 236);
		Channel channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
    		Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(channel,channel.getContentTemp());
    		Bidding bidd = biddingService.get(content.getEntityId());
    		StaticPageUtils.putResInMap(dataMap);
    		StaticPageUtils.putSysInMap(dataMap);
    		StaticPageUtils.putSeoInMap(dataMap, bidd);
    		String bidDesc = bidd.getBidDesc();
    		bidd.setBidDesc(FilePathUtils.setEditorOutPath(bidd.getBidDesc()));
    
    		dataMap.put(BIDD, bidd);
    		dataMap.put(CONTENT_ID, content.getId());
    		dataMap.put(FreemarkerCodes.TABLE_URL, StaticServiceImpl.getTableUrl(
    				channel, "friend", "list"));
    		dataMap.put(FreemarkerCodes.PATH_URL, StaticServiceImpl.getTableUrl(
    				channel, "pageLevel", "list").concat("&siteUrl=").concat(
    				StaticPageUtils.getSiteUrl())
    				.concat("&companyType=").concat(ApplicationUtils.getSite().getComType()));
    
    		String outputFileName = channel.getPath() + ElianCodes.SPRIT
    				+ content.getId() + ElianCodes.SUFFIX_SHTML;// 定义模板输出文件名
    		path = StaticServiceImpl.generateStaticPage(channel,
					FreemarkerCodes.CONTENT_OUTPUT_FOLDER,
    				FreemarkerCodes.CONTENT_TEMPLATE_URL.concat(ElianCodes.SPRIT)
    						.concat(channel.getContentTemp().getFileName()), outputFileName,
    				dataMap, ftp);
    
    		bidd.setBidDesc(bidDesc);
		}
		return path;
	}
	
	@Resource
	public void setBiddingService(BiddingService biddingService) {
		this.biddingService = biddingService;
	}
}
