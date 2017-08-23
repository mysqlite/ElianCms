package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Illness;
import com.elian.cms.syst.dao.Dao;

public interface IllnessDao extends Dao<Illness, Integer> {

	List<Illness> getByIds(String illLink);

	List<Illness> getByDepartmentName(String departmentName, boolean isEqueal);
}
