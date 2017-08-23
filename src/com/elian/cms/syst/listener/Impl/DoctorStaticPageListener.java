package com.elian.cms.syst.listener.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 内容监听器的实现
 * 
 * @author Joe
 * 
 */
@Component("doctorStaticPageListener")
public class DoctorStaticPageListener extends
		StaticPageListener {
	/** 模板页对象名称 */
	public static final String DOCTOR= "doc";

	private DoctorService doctorService;

	/**
	 * 生成资讯内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		Channel channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
			Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(channel,channel.getContentTemp());
			Doctor doct = doctorService.get(content.getEntityId());
			StaticPageUtils.putResInMap(dataMap);
			StaticPageUtils.putSysInMap(dataMap);
			StaticPageUtils.putSeoInMap(dataMap, doct);

			String intorduction = doct.getIntroduction();
			String doctImg = doct.getDoctImg();
			doct.setIntroduction(FilePathUtils.setEditorOutPath(doct
					.getIntroduction()));
			doct.setDoctImg(FilePathUtils.setOutFilePath(doct.getDoctImg()));

			dataMap.put(DOCTOR, doct);
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

			doct.setIntroduction(intorduction);
			doct.setDoctImg(doctImg);
		}
		return path;
	}

	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
}
