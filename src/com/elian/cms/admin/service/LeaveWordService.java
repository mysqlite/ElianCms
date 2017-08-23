package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.LeaveWordDao;
import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface LeaveWordService extends Service<LeaveWordDao, LeaveWord, Integer> {

	public List<LeaveWord> findByStatus(Integer siteId, Integer status,
			Pagination<LeaveWord> pagination);

	public void deleteAllSubList(List<Integer> idList,Integer siteId);

	public List<LeaveWord> getByParentId(Integer parentId, Integer siteId);
	
	/**
	 * 获取显示到前台的最顶层的留言
	 */
	public List<LeaveWord> findFrontList(Integer siteId, Pagination<LeaveWord> pagination);

	/**
	 * 根据父id获取所有显示到前台的追问
	 */
	public List<LeaveWord> getFrontListByParent(Integer parentId, Integer siteId);
	
	/**
	 * 改变留言状态     不改变子留言状态
	 */
	public void channgeStatus(List<Integer> idList, Integer status);

	/**
	 * 获取回复
	 */
	public LeaveWord getReply(Integer id);
}
	
	
