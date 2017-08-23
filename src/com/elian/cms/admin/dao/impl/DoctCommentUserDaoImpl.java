package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctCommentUserDao;
import com.elian.cms.admin.model.DoctCommentUser;
import com.elian.cms.syst.dao.impl.DaoImpl;
@Component("doctCommentUserDao")
public class DoctCommentUserDaoImpl extends DaoImpl<DoctCommentUser, Integer> implements
	DoctCommentUserDao {

	public List<DoctCommentUser> findByAll(Integer userId, Integer doctCommentId) {
		String hql=" from DoctCommentUser d where 1=1 ";
		List<Object> params=new ArrayList<Object>();
		if(userId!=null) {
			hql+=" and d.user.id =?  ";
			params.add(userId);
		}
		if(doctCommentId!=null) {
			hql+=" and d.comment.id=? ";
			params.add(doctCommentId);
		}
		return findByHql(hql, true,params.toArray());
	}
}
