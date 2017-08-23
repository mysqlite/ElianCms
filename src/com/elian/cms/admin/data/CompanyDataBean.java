package com.elian.cms.admin.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.InformationService;
import com.elian.cms.admin.service.impl.StaticServiceImpl;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StaticPageUtils;

public class CompanyDataBean {
	/**
	 * 获取数据库导航下所有启用的子栏目
	 */
	public List<HospitalChannelData> getDbAllSubChannel(Channel channel,
			List<HospitalContentData> contentList,List<List<DataContent1>> specialContent) {
		List<Channel> subList = ((ChannelService) SpringUtils
				.getBean("channelService")).findAllSubByParentId(channel
				.getId(), ApplicationUtils.getSite().getId());
		if (subList == null)
			return null;
		List<HospitalChannelData> channelDataList = new ArrayList<HospitalChannelData>();
		for (Channel c : subList) {
			HospitalChannelData data = new HospitalChannelData();
			data.setChannelName(c.getChannelName());
			data.setParentId(c.getParentId());
			if (ElianUtils.CHANNEL_PARENT.equals(c.getChannelType()))
				data.setType(ElianUtils.CHANNEL_PARENT);
			else if (ElianUtils.CHANNEL_CONTENT.equals(c.getChannelType())) {
				if (ElianUtils.CONTENT_LIST.equals(c.getContentType())) {
					data.setType(ElianUtils.CONTENT_LIST);
					data.setTableUrl(StaticServiceImpl.getTableUrl(c));
				}
				else {
					data.setType(ElianUtils.CONTENT_SINGLE);
					List<Content> list = getDbContentList(c);
					if (list != null && list.size() == 1) {
						Content content = list.get(0);
						contentList.add(createCompanyData(content));
						data.setContentId(content.getId().toString());
					}
				}
				data.setPathUrl(StaticServiceImpl.getTableUrl(c,
						"pageLevel", "list").concat("&siteUrl=").concat(
						StaticPageUtils.getSiteUrl()).concat("&companyType=")
						.concat(ApplicationUtils.getSite().getComType()));
			}
			channelDataList.add(data);
		}
		return channelDataList;
	}

	private HospitalContentData createCompanyData(Content content) {
		HospitalContentData data = new HospitalContentData();
		data.setContentId(content.getId().toString());
		data.setEntityId(content.getEntityId().toString());
		data.setTitle(content.getTitle());
		data.setCreater(content.getCreater());
		data.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(content
				.getCreateTime()));
		if ("information_c".equals(content.getActionName())) {
			Information info = ((InformationService) SpringUtils
					.getBean("informationService")).get(content.getEntityId());
			if (info != null){
				data.setContent(FilePathUtils.setEditorOutPath(info.getContent()));
				data.setCreater(info.getAuthor());
			}
		}
		return data;
	}
	
	/**
	 * 获取数据库所有状态为：通过的内容数据
	 */
	private List<Content> getDbContentList(Channel channel) {
		List<Content> contentList = ((ContentService) SpringUtils
				.getBean("contentService")).findLeafByStatus(channel
				.getId(), ElianUtils.CONTENT_STATUS_3, ApplicationUtils
				.getSite().getId());
		if (contentList == null)
			contentList = new ArrayList<Content>(0);
		return contentList;
	}
}