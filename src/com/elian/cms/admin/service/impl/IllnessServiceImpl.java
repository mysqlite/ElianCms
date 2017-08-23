package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.IllnessDao;
import com.elian.cms.admin.model.Illness;
import com.elian.cms.admin.service.IllnessService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.StringUtils;

@Component("illnessService")
public class IllnessServiceImpl extends ServiceImpl<IllnessDao, Illness, Integer>
		implements IllnessService {

	@Resource
	public void setDao(IllnessDao dao) {
		this.dao = dao;
	}

	public List<Illness> getByIds(String illLink) {
		if(StringUtils.isBlank(illLink)) return null;
		return dao.getByIds(illLink);
	}

	public List<Illness> getByDepartmentName(String departmentName, boolean isEqueal) {
		if(StringUtils.isBlank(departmentName))
			return null;
		return dao.getByDepartmentName(departmentName,isEqueal);
	}
	
}
