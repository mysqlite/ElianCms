package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ZmNewsDao;
import com.elian.cms.admin.model.ZmNews;
import com.elian.cms.syst.service.Service;

public interface ZmNewsService extends Service<ZmNewsDao, ZmNews, Integer> {
	
	public List<ZmNews> findByAll(List<String> c);
	
}
