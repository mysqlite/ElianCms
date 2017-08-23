package com.elian.cms.syst.listener.Impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.listener.StaticPageListener;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.FreemarkerCodes;
import com.elian.cms.syst.util.StaticPageUtils;

/**
 * 企业 内容静态化
 * 
 * @author thy
 * 
 */
@Component("companyStaticPageListener")
public class CompanyStaticPageListener extends StaticPageListener {
	/** 模板页对象名称 */
	public static final String COMP = "comp";
	private CompanyService companyService=null;

	/**
	 * 生成企业内容静态页
	 */
	public String generateStaticPage(Content content, FTPClient ftp) {
		Channel channel = content.getChannel();
		String path = ElianCodes.SHAFT;
		if (channel.getContentTemp() != null) {
			Map<String, Object> dataMap = StaticServiceImpl.getConfigDataMap(channel,channel.getContentTemp());
			Company comp = companyService.get(content.getEntityId());
			StaticPageUtils.putResInMap(dataMap);
			StaticPageUtils.putSysInMap(dataMap);
			StaticPageUtils.putSeoInMap(dataMap, comp);
			
			String introduce=comp.getIntroduce();
			String companyImg=comp.getCompanyImg();
			String busLine= comp.getBusLine();
			
			comp.setIntroduce(FilePathUtils.setEditorOutPath(comp.getIntroduce()));
			comp.setCompanyImg(FilePathUtils.setOutFilePath(comp.getCompanyImg()));
			comp.setBusLine(FilePathUtils.setEditorOutPath(comp.getBusLine()));
			
			dataMap.put(COMP, comp);
			dataMap.put(CONTENT_ID, content.getId());

			String outputFileName = channel.getPath() + ElianCodes.SPRIT
					+ content.getId() + ElianCodes.SUFFIX_SHTML;// 定义模板输出文件名
			path = StaticServiceImpl.generateStaticPage(channel,
					FreemarkerCodes.CONTENT_OUTPUT_FOLDER,
					FreemarkerCodes.CONTENT_TEMPLATE_URL.concat(
							ElianCodes.SPRIT).concat(
							channel.getContentTemp().getFileName()),
					outputFileName, dataMap, ftp);

			comp.setIntroduce(introduce);
			comp.setCompanyImg(companyImg);
			comp.setBusLine(busLine);
		}
		return path;
	}

	@Resource
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
