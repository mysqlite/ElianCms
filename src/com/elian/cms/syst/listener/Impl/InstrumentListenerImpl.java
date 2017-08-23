package com.elian.cms.syst.listener.Impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SysXmlUtils;

/**
 * 器械创建监听器
 * 
 * @author Joe
 * 
 */
@Component("instrumentListener")
public class InstrumentListenerImpl implements SpecialContentListener<Instrument> {
	private ContentService contentService;
	private SiteFileService siteFileService;

	public void afterUpdate(Instrument instrument) {
		List<Content> contentList = getDbContent(instrument);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content c : contentList) {
			if (c.getStatus() == ElianUtils.CONTENT_STATUS_3) {
				c.setStatus(ElianUtils.CONTENT_STATUS_2);
				if (c.getStaticStatus() == ElianUtils.STATIC_STATUS_1)
					c.setStaticStatus(ElianUtils.STATIC_STATUS_2);
			}
			//此处使用为【保存且通过|保存且发布】
			else if (c.getSite().getId().equals(ApplicationUtils.getSite().getId())&& instrument.getContentStatus() != null) {
				c.setStatus(instrument.getContentStatus());
			}
			c.setTitle(instrument.getCnName());
			contentService.save(c, instrument, true);
		}
	}
	
	public void afterUpdate(Instrument instrument,boolean publish) {
		List<Content> contentList = getDbContent(instrument);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content c : contentList) {
		    if (c.getSite().getId().equals(ApplicationUtils.getSite().getId())&& instrument.getContentStatus() != null) {
				c.setStatus(instrument.getContentStatus());
			}
			c.setTitle(instrument.getCnName());
			contentService.save(c, instrument, true);
		}
	}

	public void afterDelete(Instrument instrument, Collection<Integer> contentIdList) {
		List<Content> contentList = null;
		if (instrument != null) {
			contentList = getDbContent(instrument);
			 siteFileService.deleteImgs(instrument);
		}
		else {
			contentList = contentService.get(contentIdList);
		}
		deleteContent(contentList);
	}

	private List<Content> getDbContent(Instrument instrument) {
		return contentService.findByIdAndAction(instrument.getId(), SysXmlUtils
				.getContentActionName("contentType", "instrument").getValue());
	}

	private void deleteContent(List<Content> contentList) {
		if (CollectionUtils.isEmpty(contentList))
			return;
		Iterator<Content> its = contentList.iterator();
		for (; its.hasNext();) {
			Content content = its.next();
			if (content != null) {
				contentService.delete(content);
			}
		}
	}

	@Resource
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Resource
	public void setSiteFileService(SiteFileService siteFileService) {
		this.siteFileService = siteFileService;
	}

}
