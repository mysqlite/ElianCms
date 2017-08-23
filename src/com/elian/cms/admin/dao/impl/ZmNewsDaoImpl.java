package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ZmNewsDao;
import com.elian.cms.admin.model.ZmNews;
import com.elian.cms.syst.dao.impl.DaoImpl;
@Component("zmNewsDao")
public class ZmNewsDaoImpl extends DaoImpl<ZmNews, Integer> implements ZmNewsDao {

	public List<ZmNews> findByAll(List<String> c) {
		String HQL = "from ZmNews t ";
		
		
		return findByHql(HQL, true);
	}
	
}