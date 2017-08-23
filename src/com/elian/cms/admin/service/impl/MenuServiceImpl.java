package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.MenuDao;
import com.elian.cms.admin.model.Menu;
import com.elian.cms.admin.service.MenuService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu, Integer>
		implements MenuService {

	public List<Menu> findMenuByAll() {
		return dao.findMenuByAll();
	}

	public List<Map<String, Object>> findAjaxMenuByAll() {
		List<Menu> a = dao.findMenuByAll();
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Menu menu : a) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", menu.getId());
			map.put("pId", menu.getParentId());
			map.put("name", menu.getMenuName());
			treeModels.add(map);
		}
		return treeModels;
	}

	public List<Menu> findMenuByPage(Pagination<Menu> p) {
		return dao.findMenyByPage(p);
	}

	public List<Menu> findMenuByParentId(Integer pid) {
		return dao.findMenuByParentId(pid);
	}

	public List<Menu> findMenuByParentId() {
		return dao.findMenuByParentId();
	}

	public List<Menu> findMenuNavigationById(Integer id) {
		return dao.findMenuNavigationById(id);
	}

	@Resource
	public void setDao(MenuDao dao) {
		this.dao = dao;
	}
}
