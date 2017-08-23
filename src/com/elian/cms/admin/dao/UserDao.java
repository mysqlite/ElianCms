package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.User;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface UserDao extends Dao<User, Integer> {
	public List<User> findByAll(String account, String password,
			Pagination<User> pagination);

	public List<User> findByAll(Pagination<User> pagination);
	
	public List<User> findBySubstation(Pagination<User> p, Integer areaCode);

	public User findByAccout(String account);
	
	public List<User> findBySiteAndRole(Integer siteId, Integer roleId,
			Pagination<User> pagination);
	
	public List<User> findBySiteId(Integer siteId);
}
