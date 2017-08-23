package com.elian.cms.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.DefaultRecommendService;
import com.elian.cms.admin.service.SiteService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
@Component("defaultRecommendService")
public class DefaultRecommendServiceImpl implements DefaultRecommendService {
	private ChannelService channelService;
	private SiteService siteService;
	private ContentService contentService;
	
	/***
	 * 默认推荐到总站对应栏目
	 * @param content
	 */
	public void defaultRecommend(Content content,Integer hospChannelId,Integer compChannelId) {
		if(ApplicationUtils.isHosp()||ApplicationUtils.isCompany()) {
			if(ElianUtils.STATIC_STATUS_0==content.getStaticStatus())
			contentService.save(createContent(content,hospChannelId,compChannelId));
		}
	}
	private Content createContent(Content con,Integer hospChannelId,Integer compChannelId) {
		Content c=new Content();
		BeanUtils.copyProperties(con, c);
		c.setId(null);
		c.setSite(siteService.get(1));//总站
		c.setStatus(ElianUtils.CONTENT_STATUS_1);//未审核状态
		c.setStaticStatus(ElianUtils.STATIC_STATUS_0);//未发布状态
		c.setRecommend(true);//是推荐
		if(con.getSite().getComType().equals(ElianUtils.COMP_TYPE_HOSP)) {
			c.setChannel(channelService.get(hospChannelId));//医疗机构
		}else if (con.getSite().getComType().startsWith(ElianUtils.COMP_TYPE_COMP)) {
			c.setChannel(channelService.get(compChannelId));//企业动态
		}
		return c;
	}
	
	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

}
