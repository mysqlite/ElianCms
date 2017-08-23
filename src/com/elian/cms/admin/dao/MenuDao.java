package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Menu;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface MenuDao extends Dao<Menu, Integer> {

	public List<Menu> findMenuByAll();
	
	public List<Menu> findMenyByPage(Pagination<Menu> p);
	
	public List<Menu> findMenuByParentId(Integer pid);
	
	public List<Menu> findMenuByParentId();
	
	public List<Menu> findMenuNavigationById(Integer id);
}
