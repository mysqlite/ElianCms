package com.elian.cms.syst.listener.Impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 内容监听器的实现
 * 
 * @author Joe
 * 
 */
@Component("contentListener")
public class ContentListenerImpl implements ContentListener {
	private ContentService contentService;
	private ChannelService channelService;
	private SiteFileService siteFileService;

	private Content createContent() {
		Content content = new Content();
		content.setSite(ApplicationUtils.getSite());
		content.setSort(99);
		content.setCreater(ApplicationUtils.getUser().getRealName());
		content.setCreateTime(new Date());
		return content;
	}

	public void afterSave(BaseContent bc) {
		Content content = createContent();
		content.setEntityId(bc.getId());
		content.setTitle(bc.getTitle());
		content.setStatus(bc.getContentStatus());
		content.setActionName(bc.getActionName());
		Channel c = channelService.get(bc.getChannelId());
		content.setChannel(c);
		content.setPath(c.getPath());

		contentService.save(content, bc, false);
	}
	
	public Integer afterSave(BaseContent bc,boolean publish) {
		Content content = createContent();
		content.setEntityId(bc.getId());
		content.setTitle(bc.getTitle());
		content.setStatus(bc.getContentStatus());
		content.setActionName(bc.getActionName());
		Channel c = channelService.get(bc.getChannelId());
		content.setChannel(c);
		content.setPath(c.getPath());

		contentService.save(content, bc, false);
		return content.getId();
	}
	
	public void afterUpdate(BaseContent bc,boolean publish) {
		List<Content> contentList = contentService.findByIdAndAction(bc.getId(), bc.getActionName());
		if (CollectionUtils.isEmpty(contentList))
			return;
		Iterator<Content> its = contentList.iterator();
		for (; its.hasNext();) {
			Content content = its.next();
			//此处使用为【保存且通过|保存且发布】
			 if (content.getSite().getId().equals(ApplicationUtils.getSite().getId())&& bc.getContentStatus() != null) {
				content.setStatus(bc.getContentStatus());
			}
			content.setTitle(bc.getTitle());
			contentService.save(content, bc, true);
		}
	}
	

	public void afterUpdate(BaseContent bc) {
		// 更新的时候更新所有栏目中的内容,因为医生可能在科室栏目
		List<Content> contentList = contentService.findByIdAndAction(bc.getId(), bc.getActionName());
		if (CollectionUtils.isEmpty(contentList))
			return;
		Iterator<Content> its = contentList.iterator();
		for (; its.hasNext();) {
			Content content = its.next();
			if (ElianUtils.CONTENT_STATUS_3 == content.getStatus()) {
				content.setStatus(ElianUtils.CONTENT_STATUS_2);
				if (content.getStaticStatus() == ElianUtils.STATIC_STATUS_1)
					content.setStaticStatus(ElianUtils.STATIC_STATUS_2);
			}
			//此处使用为【保存且通过|保存且发布】
			else if (content.getSite().getId().equals(ApplicationUtils.getSite().getId())&& bc.getContentStatus() != null) {
				content.setStatus(bc.getContentStatus());
			}
			content.setTitle(bc.getTitle());
			content.setCreateTime(bc.getCreateDate());
			contentService.save(content, bc, true);
		}
	}

	public void afterDelete(BaseContent bc) {
		List<Content> contentList = contentService.findByIdAndAction(bc.getId(), bc.getActionName());
		if (bc.isSource()) siteFileService.deleteImgs(bc);
		if (CollectionUtils.isEmpty(contentList))return;
		Iterator<Content> its = contentList.iterator();
		for (; its.hasNext();) {
			Content content = its.next();
			if (content != null) {
				if (bc.isSource()
						|| ApplicationUtils.getSite().getId().equals(
								content.getSite().getId())) {
					contentService.delete(content);
				}
			}
		}
	}
	
	/**
	 * 检测内容是否来源于当前站点
	 */
	public static void checkSource(BaseContent bc) {
		if (bc.getCreater() == null)
			return;
		SiteUser su = ((SiteUserService) SpringUtils.getBean("siteUserService"))
				.findByUserId(bc.getCreater().getId());
		if (su != null
				&& su.getSite().getId().equals(
						ApplicationUtils.getSite().getId())) {
			bc.setSource(true);
		}
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

}
