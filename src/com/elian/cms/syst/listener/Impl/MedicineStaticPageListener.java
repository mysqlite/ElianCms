package com.elian.cms.syst.listener.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * 企业药品 内容静态化
 * 
 * @author Gechuanyi
 * 
 */
@Component("medicineStaticPageListener")
public class MedicineStaticPageListener extends StaticPageListener {
	/** 模板页对象名称 */
	public static final String MED = "med";
	private MedicineService medicineService;

	/**
	 * 生成企业药品内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		Channel channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
			Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(
					channel, channel.getContentTemp());
			Medicine medicine = new Medicine();
			BeanUtils.copyProperties(medicineService.get(content.getEntityId()), medicine);
			StaticPageUtils.putResInMap(dataMap);
			StaticPageUtils.putSysInMap(dataMap);
			StaticPageUtils.putSeoInMap(dataMap, medicine);
			
			dataMap.put(MED, medicine);
			dataMap.put(CONTENT_ID, content.getId());
			dataMap.put(FreemarkerCodes.TABLE_URL, StaticServiceImpl
					.getTableUrl(channel, "friend", "list"));
			dataMap.put(FreemarkerCodes.PATH_URL, StaticServiceImpl
					.getTableUrl(channel, "pageLevel", "list").concat(
							"&siteUrl=").concat(StaticPageUtils.getSiteUrl()).concat("&companyType=").concat(ApplicationUtils.getSite().getComType()));
			dataMap.put(FreemarkerCodes.PRODUCT_TYPE, StringUtils.getENL(medicine));
			String outputFileName = channel.getPath() + ElianCodes.SPRIT
					+ content.getId() + ElianCodes.SUFFIX_SHTML;// 定义模板输出文件名
			path = StaticServiceImpl.generateStaticPage(channel,
					FreemarkerCodes.CONTENT_OUTPUT_FOLDER,
					FreemarkerCodes.CONTENT_TEMPLATE_URL.concat(
							ElianCodes.SPRIT).concat(channel.getContentTemp().getFileName()),
					outputFileName, dataMap, ftp);
		}
		return path;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
}
