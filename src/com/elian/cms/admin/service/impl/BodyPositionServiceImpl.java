package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.BodyPositionDao;
import com.elian.cms.admin.model.BodyPosition;
import com.elian.cms.admin.service.BodyPositionService;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("bodyPositionService")
public class BodyPositionServiceImpl extends ServiceImpl<BodyPositionDao, BodyPosition, Integer>
		implements BodyPositionService {

	@Resource
	public void setDao(BodyPositionDao dao) {
		this.dao = dao;
	}

	public List<BodyPosition> getAll() {
		return dao.getAll();
	}
	
}
