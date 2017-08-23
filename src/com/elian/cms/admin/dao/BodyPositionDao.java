package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.BodyPosition;
import com.elian.cms.syst.dao.Dao;

public interface BodyPositionDao extends Dao<BodyPosition, Integer> {

	 List<BodyPosition> getAll();
}
