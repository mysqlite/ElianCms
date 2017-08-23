package com.elian.cms.syst.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.service.JdbcService;

/**
 * JdbcService访问结果集服务实现类
 * 
 * @author Joe
 * 
 */

@Component("jdbcService")
public class JdbcServiceImpl implements JdbcService {
	private static final long serialVersionUID = 1910595685923867409L;

	protected JdbcDao dao;

	@Resource
	public void setDao(JdbcDao dao) {
		this.dao = dao;
	}

	public int executeUpdate(String sql, Object... values) {
		return dao.executeUpdate(sql, values);
	}

	public int[] executeUpdate(String sql, List<Object[]> values) {
		return dao.executeUpdate(sql, values);
	}

	public List<Map<String, Object>> findSqlMapQuery(String sql,
			Object... values) {
		return dao.findSqlMapQuery(sql, values);
	}

	public List<Object[]> findSqlQuery(String sql, Object... values) {
		return dao.findSqlQuery(sql, values);
	}
}
