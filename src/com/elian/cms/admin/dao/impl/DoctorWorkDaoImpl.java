package com.elian.cms.admin.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctorWorkDao;
import com.elian.cms.admin.model.DoctorWork;
import com.elian.cms.syst.converter.DateTypeConverter;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("doctorWorkDao")
public class DoctorWorkDaoImpl extends
		DaoImpl<DoctorWork, Integer> implements DoctorWorkDao {
	
	public List<DoctorWork> findByAll(Integer doctorId,
			Pagination<DoctorWork> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DoctorWork d where 1=1");
		if (doctorId != null && doctorId > 0) {
			hql.append(" and d.doctor.id = ? ");
			params.add(doctorId);
		}
		hql.append("	order by d.startTime desc");
		if (p != null) {
			p.setAlias("d");
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), true, params.toArray());
	}

	public List<DoctorWork> getTingZhenList(Integer size, Integer areaId, Date startTime,
			Date endTime) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select ");
		if(size!=null) sql.append("top ").append(size);
		sql.append(" * from t_doctor_work w where w.is_work=1 ");
		
		if(startTime!=null){
			sql.append(" and w.start_time >= ? ");
			params.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and w.start_time <= ? ");
			params.add(endTime);
		}
		sql.append("	order by w.start_time desc");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<DoctorWork> findAllDoctorWork(Integer doctorId,
			String startTime, String endTime, Integer rank) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * ");
		sql.append("   FROM T_DOCTOR_WORK D ");
		sql.append("  WHERE     1 = 1 ");
		if (doctorId != null && doctorId > 0) {
			sql.append("        AND D.DOCT_ID = ? ");
			params.add(doctorId);
		}
		if (StringUtils.isNotBlank(startTime)
				&& StringUtils.isNotBlank(endTime)) {
			// sql.append("        AND CONVERT (VARCHAR (10), D.START_TIME, 120) >= ? ");
			sql.append("    	AND CONVERT (VARCHAR (19), D.START_TIME, 20) >= ? ");
			sql.append("        AND CONVERT (VARCHAR (10), D.END_TIME, 120) <= ? ");
			// params.add(startTime);
			params.add(new SimpleDateFormat(DateTypeConverter.DEFAULT_DATE_FORMAT).format(new Date()));
			params.add(endTime);
		}
		if (rank != null && rank > 0) {
			sql.append("        AND D.RANK = ? ");
			params.add(rank);
		}
		sql.append("        AND D.NO_SOURCE > 0 ");
		sql.append("        AND D.IS_WORK = 0 ");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public Integer findDoctorOverScore(Integer doctorId,String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT SUM(NO_SOURCE) NO_SOURCE  ");
		sql.append("   FROM T_DOCTOR_WORK D ");
		sql.append("  WHERE     1 = 1 ");
		if (doctorId != null && doctorId > 0) {
			sql.append("        AND D.DOCT_ID = ? ");
			params.add(doctorId);
		}
		if (StringUtils.isNotBlank(startTime)&& StringUtils.isNotBlank(endTime)) {
			sql.append("    AND CONVERT (VARCHAR (16), D.START_TIME, 120) >= ? ");
			sql.append("    AND CONVERT (VARCHAR (16), D.END_TIME, 120) <= ? ");
			params.add(startTime);
			params.add(endTime);
		}
		sql.append("  AND D.NO_SOURCE > 0 ");
		sql.append("  AND D.IS_WORK = 0 ");
		JdbcDao jdbcDao=SpringUtils.getBean("jdbcDao");
		List<Map<String, Object>> doctorWork=jdbcDao.findSqlMapQuery(sql.toString(), params.toArray());
		return Integer.parseInt(doctorWork.get(0).get("NO_SOURCE").toString());
	}
	
	public List<DoctorWork> findConfirmDoctorWork(Integer doctorId, int rank,
			String startTime, String endTime) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT * ");
		sql.append("   FROM T_DOCTOR_WORK D ");
		sql.append("  WHERE     1 = 1 ");
		if (doctorId != null && doctorId > 0) {
			sql.append("        AND D.DOCT_ID = ? ");
			params.add(doctorId);
		}
		if(rank>0){
			sql.append("        AND D.RANK = ? ");
			params.add(rank);
		}
		if (StringUtils.isNotBlank(startTime)) {
			sql.append("        AND CONVERT (VARCHAR (16), D.START_TIME, 120) = ? ");
			params.add(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append("        AND CONVERT (VARCHAR (16), D.END_TIME, 120) = ? ");
			params.add(endTime);
		}
		return findBySql(sql.toString(), false, params.toArray());
	}
}
