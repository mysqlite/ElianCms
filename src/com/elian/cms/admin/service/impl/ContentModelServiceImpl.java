package com.elian.cms.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ContentModelDao;
import com.elian.cms.admin.model.ContentModel;
import com.elian.cms.admin.service.ContentModelService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("contentModelService")
public class ContentModelServiceImpl extends ServiceImpl<ContentModelDao, ContentModel, Integer>
		implements ContentModelService {
	public List<ContentModel> findByAll(Pagination<ContentModel> p) {
		return dao.findByAll(p);
	}
	
	public List<ContentModel> findByChannelId(Integer channelId) {
		return dao.findByChannelId(channelId);
	}

	@Autowired
	public void setDao(ContentModelDao dao) {
		this.dao = dao;
	}

	public List<ContentModel> findByAll(Integer id, boolean b, Pagination<ContentModel> pagination) {
		return dao.findByAll(id, b, pagination);
	}

}
