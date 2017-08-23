package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import accp.ld.LdMd5;

import com.elian.cms.admin.dao.UserDao;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.UserService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User, Integer>
		implements UserService {

	public List<User> findByAll(String account, String password,
			Pagination<User> pagination) {
		return dao.findByAll(account, LdMd5.MD5(password), pagination);
	}

	public List<User> findBySubstation(Pagination<User> p, Integer areaCode){
		return dao.findBySubstation(p, areaCode);
	}
	
	public List<User> findByAll(Pagination<User> p) {
		return dao.findByAll(p);
	}

	public User findByAccout(String account) {
		return dao.findByAccout(account);
	}

	public List<User> findBySiteAndRole(Integer siteId, Integer roleId,
			Pagination<User> pagination) {
		return dao.findBySiteAndRole(siteId, roleId, pagination);
	}
	
	public List<User> findBySiteId(Integer siteId){
		return dao.findBySiteId(siteId);
	}

	@Resource
	public void setDao(UserDao dao) {
		this.dao = dao;
	}
}
