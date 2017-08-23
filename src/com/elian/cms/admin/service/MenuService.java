package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.MenuDao;
import com.elian.cms.admin.model.Menu;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface MenuService extends Service<MenuDao, Menu, Integer> {
	public List<Menu> findMenuByAll();
	public List<Menu> findMenuByPage(Pagination<Menu> p);
	public List<Menu> findMenuByParentId(Integer pid);
	public List<Menu> findMenuByParentId();
	public List<Menu> findMenuNavigationById(Integer id);
	public List<Map<String, Object>> findAjaxMenuByAll();
}
