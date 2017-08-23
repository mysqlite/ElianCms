package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.LeaveWordDao;
import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.admin.service.LeaveWordService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("leaveWordService")
public class LeaveWordServiceImpl extends
		ServiceImpl<LeaveWordDao, LeaveWord, Integer> implements
		LeaveWordService {
	
	@Resource
	public void setDao(LeaveWordDao dao) {
		this.dao = dao;
	}
	
	public List<LeaveWord> findByStatus(Integer siteId, Integer status,
			Pagination<LeaveWord> pagination) {
		return dao.findByStatus(siteId,status,pagination);
	}

	public void deleteAllSubList(List<Integer> idList,Integer siteId) {
		dao.deleteAllSubList(idList,siteId);		
	}

	public List<LeaveWord> getByParentId(Integer parentId, Integer siteId) {
		return dao.getByParentId(parentId,siteId);
	}

	public List<LeaveWord> findFrontList(Integer siteId,
			Pagination<LeaveWord> pagination) {
		return dao.findFrontList(siteId,pagination);
	}

	public List<LeaveWord> getFrontListByParent(Integer parentId, Integer siteId) {
		return dao.getFrontListByParent(parentId,siteId);
	}

	public void channgeStatus(List<Integer> idList, Integer status) {
		dao.channgeStatus(idList, status);
	}

	public LeaveWord getReply(Integer id) {
		return dao.getReply(id);
	}
	
}
