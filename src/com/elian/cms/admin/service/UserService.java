package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.UserDao;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface UserService extends Service<UserDao, User, Integer> {
	
	public List<User> findByAll(String account, String password,Pagination<User> pagination);
	
	public List<User> findBySubstation(Pagination<User> p, Integer areaCode);

	public List<User> findByAll(Pagination<User> p);

	public User findByAccout(String account);

	public List<User> findBySiteAndRole(Integer siteId, Integer roleId,Pagination<User> pagination);
	
	public List<User> findBySiteId(Integer siteId);
}
