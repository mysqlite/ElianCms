package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.DoctCommentUser;
import com.elian.cms.syst.dao.Dao;

public interface DoctCommentUserDao extends Dao<DoctCommentUser, Integer> {
	public List<DoctCommentUser> findByAll(Integer userId,Integer doctCommentId);
}
