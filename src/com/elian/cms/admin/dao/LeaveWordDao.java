package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface LeaveWordDao extends Dao<LeaveWord, Integer> {

	public List<LeaveWord> findByStatus(Integer siteId, Integer status,
			Pagination<LeaveWord> pagination);

	public void deleteAllSubList(List<Integer> idList,Integer siteId);

	public List<LeaveWord> getByParentId(Integer parentId, Integer siteId);

	public List<LeaveWord> findFrontList(Integer siteId,
			Pagination<LeaveWord> pagination);

	public List<LeaveWord> getFrontListByParent(Integer parentId, Integer siteId);

	public void channgeStatus(List<Integer> idList, Integer status);

	public LeaveWord getReply(Integer id);
	
}
