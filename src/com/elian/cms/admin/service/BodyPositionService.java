package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.BodyPositionDao;
import com.elian.cms.admin.model.BodyPosition;
import com.elian.cms.syst.service.Service;

public interface BodyPositionService extends Service<BodyPositionDao, BodyPosition, Integer> {

	public List<BodyPosition> getAll();
}
