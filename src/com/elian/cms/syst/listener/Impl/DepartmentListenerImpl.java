package com.elian.cms.syst.listener.Impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.admin.service.SiteFileService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SysXmlUtils;

/**
 * 科室创建监听器，创建科室也为医生创建栏目
 * 
 * @author Joe
 * 
 */
@Component("departmentListener")
public class DepartmentListenerImpl implements
		SpecialContentListener<Department> {
	private ContentService contentService;
	private SiteFileService siteFileService;

	public void afterUpdate(Department department) {
		List<Content> contentList = getDbContent(department);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content content : contentList) {
			if (ElianUtils.CONTENT_STATUS_3 == content.getStatus()) {
				content.setStatus(ElianUtils.CONTENT_STATUS_2);
				if (content.getStaticStatus() == ElianUtils.STATIC_STATUS_1)
					content.setStaticStatus(ElianUtils.STATIC_STATUS_2);
			}
			//此处使用为【保存且通过|保存且发布】
			else if (content.getSite().getId().equals(ApplicationUtils.getSite().getId())&& department.getContentStatus() != null) {
				content.setStatus(department.getContentStatus());
			}
			content.setTitle(department.getDeptName());
			contentService.save(content, department, true);
		}
	}
	
	public void afterUpdate(Department department,boolean publish) {
		List<Content> contentList = getDbContent(department);
		if (CollectionUtils.isEmpty(contentList))
			return;
		for (Content content : contentList) {
			//此处使用为【保存且通过|保存且发布】
			 if (content.getSite().getId().equals(ApplicationUtils.getSite().getId())&& department.getContentStatus() != null) {
				content.setStatus(department.getContentStatus());
			}
			content.setTitle(department.getDeptName());
			contentService.save(content, department, true);
		}
	}

	public void afterDelete(Department department,
			Collection<Integer> contentIdList) {
		List<Content> contentList = null;
		if (department != null) {
			contentList = getDbContent(department);
			siteFileService.deleteImgs(department);
		}
		else {
			contentList = contentService.get(contentIdList);
		}
		deleteContent(contentList);
	}

	private List<Content> getDbContent(Department department) {
		return contentService.findByIdAndAction(department.getId(), SysXmlUtils
				.getContentActionName("contentType", "department").getValue());
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
