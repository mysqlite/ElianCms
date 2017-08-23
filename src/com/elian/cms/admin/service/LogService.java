package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.LogDao;
import com.elian.cms.admin.model.Log;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

/**
 * 记录操作日志业务逻辑层
 */
public interface LogService extends Service<LogDao, Log, Integer> {
	/**
	 * @param category
	 *            日志类型
	 * @param title
	 *            日志标题
	 * @param contents
	 *            日志详细内容键值对组合(key1,value1,key2,value2.....)的形式，可不填
	 */
	public void save(int category, String title, String... contents);
	
	public Log createLog(int category, String title, String content);

	public List<Log> findLogByAll(Pagination<Log> p,Integer siteId,Integer userId);

	public boolean deleteLogByBeforTime(int time);
}
