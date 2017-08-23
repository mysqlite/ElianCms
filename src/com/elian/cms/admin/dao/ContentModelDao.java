package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.ContentModel;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ContentModelDao extends Dao<ContentModel, Integer> {
	public List<ContentModel> findByAll(Pagination<ContentModel> p);
	
	public List<ContentModel> findByChannelId(Integer channelId);

	public List<ContentModel> findByAll(Integer id, boolean b, Pagination<ContentModel> pagination);
}
