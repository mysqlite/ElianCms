package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.ZmNews;
import com.elian.cms.syst.dao.Dao;

public interface ZmNewsDao extends Dao<ZmNews, Integer> {
	public List<ZmNews> findByAll(List<String> c);
}
