package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.DoctCommentUserDao;
import com.elian.cms.admin.model.DoctCommentUser;
import com.elian.cms.syst.service.Service;

public interface DoctCommentUserService extends Service<DoctCommentUserDao, DoctCommentUser, Integer> {
	public List<DoctCommentUser> findByAll(Integer userId, Integer doctCommentId) ;
}
