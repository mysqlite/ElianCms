package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.TabNews;
import com.elian.cms.syst.dao.Dao;

public interface TabNewsDao extends Dao<TabNews, Integer> {
	public List<TabNews> findByAll();
}
