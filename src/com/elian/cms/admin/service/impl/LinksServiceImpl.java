package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.LinksDao;
import com.elian.cms.admin.model.Links;
import com.elian.cms.admin.service.LinksService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("linksService")
public class LinksServiceImpl extends ServiceImpl<LinksDao, Links, Integer> implements
		LinksService {
	
	public List<Links> findByContentId(List<Integer> contentIdList,
			Integer siteId){
		return dao.findByContentId(contentIdList, siteId);
	}

	@Resource
	public void setDao(LinksDao dao) {
		this.dao = dao;
	}

	private ContentListener contentListener;

	public void delete(Links links) {
		ContentListenerImpl.checkSource(links);
		if (links.isSource())
			super.delete(links);
		if (null != contentListener)
			contentListener.afterDelete(links);
	}

	public void save(Links links, boolean isEdit) {
		super.save(links);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(links);
			else
				contentListener.afterSave(links);
	}
	
	public Integer save(Links links, boolean isEdit,boolean publish) {
		super.save(links);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(links,publish);
			else
				controlId=contentListener.afterSave(links,publish);
		return controlId;
	}
	
	public List<Links> findByParentChannelId(Integer siteId,Integer parentChannelId,Integer state, Boolean isSatatic) {
		return dao.findByParentChannelId(siteId, parentChannelId, state, isSatatic);
	}

	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}
}
