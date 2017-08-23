package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ZmNewsDao;
import com.elian.cms.admin.model.ZmNews;
import com.elian.cms.admin.service.ZmNewsService;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("zmNewsService")
public class ZmNewsServiceImpl extends
		ServiceImpl<ZmNewsDao, ZmNews, Integer> implements ZmNewsService {
	public List<ZmNews> findByAll(List<String> c) {
		return dao.findByAll(c);
	}

	@Resource
	public void setDao(ZmNewsDao dao) {
		this.dao = dao;
	}
}
