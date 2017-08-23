package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TabNewsDao;
import com.elian.cms.admin.model.TabNews;
import com.elian.cms.syst.dao.impl.DaoImpl;
@Component("tabNewsDao")
public class TabNewsDaoImpl extends DaoImpl<TabNews, Integer> implements TabNewsDao {

	public List<TabNews> findByAll() {
		String HQL = "from TabNews t ";
		return findByHql(HQL, true);
	}
	
}