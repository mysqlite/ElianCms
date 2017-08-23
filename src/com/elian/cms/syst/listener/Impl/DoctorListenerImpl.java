package com.elian.cms.syst.listener.Impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SysXmlUtils;

/**
 * 医生创建监听器
 * 
 * @author Joe
 * 
 */
@Component("doctorListener")
public class DoctorListenerImpl implements SpecialContentListener<Doctor> {
	private ContentService contentService;
	private SiteFileService siteFileService;

	public void afterUpdate(Doctor doctor) {
		List<Content> contentList = getDbContent(doctor);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content c : contentList) {
			if (c.getStatus() == ElianUtils.CONTENT_STATUS_3) {
				c.setStatus(ElianUtils.CONTENT_STATUS_2);
				if (c.getStaticStatus() == ElianUtils.STATIC_STATUS_1)
					c.setStaticStatus(ElianUtils.STATIC_STATUS_2);
			}
			//此处使用为【保存且通过|保存且发布】
			else if (c.getSite().getId().equals(ApplicationUtils.getSite().getId())&& doctor.getContentStatus() != null) {
				c.setStatus(doctor.getContentStatus());
			}
			c.setTitle(doctor.getDoctName());
			contentService.save(c, doctor, true);
		}
	}
	
	public void afterUpdate(Doctor doctor,boolean publish) {
		List<Content> contentList = getDbContent(doctor);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content c : contentList) {
		    if (c.getSite().getId().equals(ApplicationUtils.getSite().getId())&& doctor.getContentStatus() != null) {
				c.setStatus(doctor.getContentStatus());
			}
			c.setTitle(doctor.getDoctName());
			contentService.save(c, doctor, true);
		}
	}

	public void afterDelete(Doctor doctor, Collection<Integer> contentIdList) {
		List<Content> contentList = null;
		if (doctor != null) {
			contentList = getDbContent(doctor);
		    siteFileService.deleteImgs(doctor);
		}
		else {
			contentList = contentService.get(contentIdList);
		}
		deleteContent(contentList);
	}

	private List<Content> getDbContent(Doctor doctor) {
		return contentService.findByIdAndAction(doctor.getId(), SysXmlUtils
				.getContentActionName("contentType", "doctor").getValue());
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
