package com.elian.cms.admin.dao;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.Pagination;

public interface ContentDao extends Dao<Content, Integer> {
	public List<Content> findByAll(Integer siteId, Integer channelId,
			Integer status, String userName, Pagination<Content> p);

	public Content findByIdAndAction(Integer channelId, Integer entityId,
			String actionName);
	
	public List<Content> findByIdsAndAction(Integer channelId, Collection<Integer> entityIds,
			String actionName);

	public List<Content> findByIdAndAction(Integer entityId, String actionName);
	
	public List<Content> findByIdAndAction(Integer entityId, String actionName,Integer siteId) ;

	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId);

	public List<Content> findForListStaticPage(Integer channelId,
			Integer siteId, Pagination<Content> p);

	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId, Integer size);

	public List<Content> findTopHitsList(Integer channelId, Integer siteId,
			Integer size);

	public List<Content> findStaticPages(Integer channelId, Integer status,
			Integer siteId);

	public List<Content> getAllEntityIds(Integer siteId, List<Channel> channelList,
			boolean isStatic);

	public List<Content> getByActionName(Integer siteId, String actionName,
			boolean isStatic, Integer size);

	public Content getByEntityId(Integer id, String actionName, Integer entityId);
	
	public List<Content> findIndexContent(Integer siteId, String actionName);

	public List<Content> findBySite(Integer siteId,
			List<? extends BaseContent> list, String actionName,
			Boolean isStatic);

	public List<Content> findNewestSaticList(int siteId, int size);
	
	public List<Content> findJobBySite(Integer siteId, String actionName,
			boolean isStatic, List<Area> list, String jobName,
			Pagination<Content> p);
}
