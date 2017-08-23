package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.ActionDao;
import com.elian.cms.admin.model.Action;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ActionService extends Service<ActionDao, Action, Integer> {
	public List<Action> findByAll(Integer depth, Pagination<Action> pagination);

	public List<Action> findByCompType(String compType, boolean isMainStation);

	public List<Action> findByUser(Integer userId);

	public Action getActionByActionName(String ActionName,String actionUrl);
	
	public List<Map<String, Object>> findAjaxActionByAll();
}
