package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.UserTypeDao;
import com.elian.cms.admin.model.UserType;
import com.elian.cms.admin.service.UserTypeService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("userTypeService")
public class UserTypeServiceImpl extends

ServiceImpl<UserTypeDao, UserType, Integer> implements UserTypeService {

	public List<UserType> findUserTypeByAll() {
		return dao.findUserTypeByAll();
	}

	public List<UserType> findUserTypes(Pagination<UserType> p) {
		return dao.findUserTypeList(p);
	}

	@Resource
	public void setDao(UserTypeDao dao) {
		this.dao = dao;
	}
	
	public List<Map<String, Object>> findAjaxUserTypeByAll() {
		List<UserType> a = dao.findUserTypeByAll(true);
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (UserType userType : a) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", userType.getId());
			map.put("pId", userType.getParentId());
			map.put("name", userType.getTypeName());
			treeModels.add(map);
		}
		return treeModels;
	}

}
