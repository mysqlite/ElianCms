package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.IllnessDao;
import com.elian.cms.admin.model.Illness;
import com.elian.cms.syst.service.Service;

public interface IllnessService extends	Service<IllnessDao, Illness, Integer> {

	List<Illness> getByIds(String illLink);

	List<Illness> getByDepartmentName(String departmentName, boolean isEqueal);
}
