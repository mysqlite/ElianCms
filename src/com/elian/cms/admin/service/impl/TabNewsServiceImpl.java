package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TabNewsDao;
import com.elian.cms.admin.model.TabNews;
import com.elian.cms.admin.service.TabNewsService;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("tabNewsService")
public class TabNewsServiceImpl extends
		ServiceImpl<TabNewsDao, TabNews, Integer> implements TabNewsService {
	public List<TabNews> findByAll() {
		return dao.findByAll();
	}

	@Resource
	public void setDao(TabNewsDao dao) {
		this.dao = dao;
	}
}
