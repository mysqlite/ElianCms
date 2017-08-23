package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ContentModelDao;
import com.elian.cms.admin.model.ContentModel;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ContentModelService extends
		Service<ContentModelDao, ContentModel, Integer> {
	public List<ContentModel> findByAll(Pagination<ContentModel> p);
	
	public List<ContentModel> findByChannelId(Integer channelId);

	public List<ContentModel> findByAll(Integer id, boolean b, Pagination<ContentModel> pagination);
}
