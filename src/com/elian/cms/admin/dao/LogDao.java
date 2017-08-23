package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Log;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface LogDao extends Dao<Log, Integer> {
	public List<Log> findLogByAll(Pagination<Log> p,Integer siteId,Integer userId);

	public boolean deleteLogByBeforTime(int time);
}
