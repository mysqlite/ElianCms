package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.LogDao;
import com.elian.cms.admin.model.Log;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;

@Component("logDao")
public class LogDaoImpl extends DaoImpl<Log, Integer> implements LogDao {

	private JdbcService jdbcService;

	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public List<Log> findLogByAll(Pagination<Log> p,Integer siteId,Integer userId) {
		StringBuffer hql =new StringBuffer( "from Log a  where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(null!=siteId&&siteId>0) {
			hql.append(" and a.siteId=? ");
			paramList.add(siteId);
		}
		if(null!=userId&&userId>0) {
			hql.append("  and a.user.id=? ");
			paramList.add(userId);
		}
		hql.append(" order by a.createDate desc");
		if (p != null) {
			return pageByHql(hql.toString(), false, p,paramList.toArray());
		}
		else {
			return findByHql(hql.toString(), false,paramList.toArray());
		}
	}

	public boolean deleteLogByBeforTime(int time) {
		String sql = "";
		int i = 0;
		if (time == 0) {
			sql = "delete T_log";
			i = jdbcService.executeUpdate(sql);
		}
		else {
			sql = "delete T_log where log_time < sysdate-?";
			i = jdbcService.executeUpdate(sql, time);
		}
		if (i == 0)
			return false;
		else
			return true;
	}
}
