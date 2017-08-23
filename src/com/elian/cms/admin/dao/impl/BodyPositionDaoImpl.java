package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.BodyPositionDao;
import com.elian.cms.admin.model.BodyPosition;
import com.elian.cms.syst.dao.impl.DaoImpl;

@Component("bodyPositionDao")
public class BodyPositionDaoImpl extends DaoImpl<BodyPosition, Integer>
	implements BodyPositionDao {

	public List<BodyPosition> getAll() {
		String hql="from BodyPosition";
		return findByHql(hql, false);
	}
	
}