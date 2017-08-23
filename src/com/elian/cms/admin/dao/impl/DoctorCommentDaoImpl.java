package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctorCommentDao;
import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("doctorCommentDao")
public class DoctorCommentDaoImpl extends DaoImpl<DoctorComment, Integer> implements DoctorCommentDao{

	public List<DoctorComment> findByPageOrAll(Integer userId, Integer doctorId,Integer registerId,
			Pagination<DoctorComment> p) {
		StringBuffer hql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		hql.append("from DoctorComment d where 1=1 ");
		if(userId!=null) {
			hql.append(" and d.user.id=? ");
			params.add(userId);
		}
		if(doctorId!=null) {
			hql.append(" and d.doctor.id=? ");
			params.add(doctorId);
		}
		if(registerId!=null) {
			hql.append(" and d.register.id=? ");
			params.add(registerId);
		}
		hql.append("  order by d.commSort asc,d.createTime desc,d.id desc ");
		if(p!=null) {
			return pageByHql(hql.toString(), true, p, params.toArray());
		}
		return findByHql(hql.toString(), true, params.toArray());
	}
}
