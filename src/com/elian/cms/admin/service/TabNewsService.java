package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.TabNewsDao;
import com.elian.cms.admin.model.TabNews;
import com.elian.cms.syst.service.Service;

public interface TabNewsService extends Service<TabNewsDao, TabNews, Integer> {
	
	public List<TabNews> findByAll();
	
}
