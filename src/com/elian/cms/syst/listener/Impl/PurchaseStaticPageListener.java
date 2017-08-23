package com.elian.cms.syst.listener.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Purchase;
import com.elian.cms.admin.service.PurchaseService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 求购 内容静态化
 * 
 * @author thy
 * 
 */
@Component("purchaseStaticPageListener")
public class PurchaseStaticPageListener extends StaticPageListener {
	/** 模板页对象名称 */
	public static final String PURC = "purc";
	private PurchaseService purchaseService=null;

	/**
	 * 生成企业内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		Channel channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
			Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(channel,channel.getContentTemp());
			Purchase purchase = purchaseService.get(content.getEntityId());
			StaticPageUtils.putResInMap(dataMap);
			StaticPageUtils.putSysInMap(dataMap);
			StaticPageUtils.putSeoInMap(dataMap, purchase);
			
			String desc=purchase.getDesc();
			String notice=purchase.getNotice();
			
			purchase.setDesc(FilePathUtils.setEditorOutPath(purchase.getDesc()));
			purchase.setNotice(FilePathUtils.setEditorOutPath(purchase.getNotice()));
			
			dataMap.put(PURC, purchase);
			dataMap.put(CONTENT_ID, content.getId());

			dataMap.put(FreemarkerCodes.TABLE_URL, StaticServiceImpl
					.getTableUrl(channel, "friend", "list"));
			dataMap.put(FreemarkerCodes.PATH_URL, StaticServiceImpl
					.getTableUrl(channel, "pageLevel", "list").concat(
							"&siteUrl=").concat(StaticPageUtils.getSiteUrl())
							.concat("&companyType=").concat(ApplicationUtils.getSite().getComType()));
			
			String outputFileName = channel.getPath() + ElianCodes.SPRIT
					+ content.getId() + ElianCodes.SUFFIX_SHTML;// 定义模板输出文件名
			path = StaticServiceImpl.generateStaticPage(channel,
					FreemarkerCodes.CONTENT_OUTPUT_FOLDER,
					FreemarkerCodes.CONTENT_TEMPLATE_URL.concat(
							ElianCodes.SPRIT).concat(
							channel.getContentTemp().getFileName()),
					outputFileName, dataMap, ftp);
			purchase.setDesc(desc);
			purchase.setNotice(notice);
		}
		return path;
	}

	@Resource
	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
}
