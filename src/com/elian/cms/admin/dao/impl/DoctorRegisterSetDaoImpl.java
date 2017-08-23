package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctorRegisterSetDao;
import com.elian.cms.admin.model.DoctorRegisterSet;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("doctorRegisterSetDao")
public class DoctorRegisterSetDaoImpl extends
		DaoImpl<DoctorRegisterSet, Integer> implements DoctorRegisterSetDao {
	public List<DoctorRegisterSet> findByAll(Integer doctorId, Integer weeks,
			Pagination<DoctorRegisterSet> p) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DoctorRegisterSet d where 1=1");
		if (doctorId != null && doctorId > 0) {
			hql.append(" and d.doctor.id = ? ");
			params.add(doctorId);
		}
		if (weeks != null && weeks > -1) {
			hql.append(" and d.weeks = ? ");
			params.add(weeks);
		}
		hql.append(" order by d.weeks, d.rank");
		if (p != null) {
			p.setAlias("d");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}

	public List<DoctorRegisterSet> findForDoctorWork(Integer weeks) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		hql.append(" from DoctorRegisterSet d where 1=1");
		if (weeks != null && weeks > -1) {
			hql.append(" and d.weeks = ? ");
			params.add(weeks);
		}
		hql.append(" and d.cycle = 0 or (d.closeTime > ? and d.cycle = 2) ");
		params.add(getTodayStart());
		hql.append(" order by d.weeks, d.rank");
		return findByHql(hql.toString(), false, params.toArray());
	}

	private Date getTodayStart() {
		Calendar temp = Calendar.getInstance();
		GregorianCalendar c = new GregorianCalendar(temp.get(Calendar.YEAR),
				temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
}
