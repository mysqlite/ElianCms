package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Action;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface ActionDao extends Dao<Action, Integer> {
	public List<Action> findByAll(Integer depth, Pagination<Action> pagination);
	
	public List<Action> findByCompType(String compType, boolean isMainStation);

	public List<Action> findByUser(Integer userId);

	public Action getActionByActionName(String ActionName,String actionUrl);
	
	public List<Action> findByAll(boolean disable);
}
