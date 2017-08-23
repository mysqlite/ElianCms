package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.DoctCommentUserDao;
import com.elian.cms.admin.model.DoctCommentUser;
import com.elian.cms.admin.service.DoctCommentUserService;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("doctCommentUserService")
public class DoctCommentUserServiceImpl extends ServiceImpl<DoctCommentUserDao, DoctCommentUser, Integer>
		implements DoctCommentUserService {

	public List<DoctCommentUser> findByAll(Integer userId, Integer doctCommentId) {
		return dao.findByAll(userId, doctCommentId);
	}
	
	@Resource
	public void setDao(DoctCommentUserDao dao) {
		this.dao = dao;
	}

}
