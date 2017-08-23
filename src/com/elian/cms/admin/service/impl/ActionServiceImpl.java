package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ActionDao;
import com.elian.cms.admin.model.Action;
import com.elian.cms.admin.service.ActionService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("actionService")
public class ActionServiceImpl extends ServiceImpl<ActionDao, Action, Integer>
		implements ActionService {
	public List<Action> findByAll(Integer depth, Pagination<Action> pagination) {
		return dao.findByAll(depth, pagination);
	}
	
	public List<Action> findByCompType(String compType, boolean isMainStation) {
		return dao.findByCompType(compType, isMainStation);
	}

	public List<Action> findByUser(Integer userId) {
		return dao.findByUser(userId);
	}

	public Action getActionByActionName(String ActionName,String actionUrl) {
		return dao.getActionByActionName(ActionName,actionUrl);
	}
	
	public List<Map<String, Object>> findAjaxActionByAll() {
		List<Action> a = dao.findByAll(true);
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (Action area : a) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", area.getId());
			map.put("pId", area.getParentId());
			map.put("name", area.getActionName());
			treeModels.add(map);
		}
		return treeModels;
	}

	@Resource
	public void setDao(ActionDao dao) {
		this.dao = dao;
	}

}
