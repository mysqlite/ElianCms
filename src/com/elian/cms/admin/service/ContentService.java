package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.ContentDao;
import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ContentService extends Service<ContentDao, Content, Integer> {
	public List<Content> findByAll(Integer siteId, Integer channelId,
			Integer status, Pagination<Content> p);

	public Content findByIdAndAction(Integer channelId, Integer entityId,
			String actionName);
	
	public List<Content> findByIdsAndAction(Integer channelId, Collection<Integer> entityIds,
			String actionName);

	public List<Content> findByIdAndAction(Integer entityId, String actionName);
	
	public List<Content> findByIdAndAction(Integer entityId, String actionName,Integer siteId) ;

	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId);
	
	public List<Content> findLeafByStatus(Integer channelId, Integer status,
			Integer siteId,Integer size);

	public List<Content> findForListStaticPage(Integer channelId,
			Integer siteId, Pagination<Content> p);	
	
	/**
	 * 
	 * @param siteId  站点的id
	 * @param entityId 实体的id
	 * @param channelId 栏目的id  可以为空
	 * @return
	 */
	public Integer getChannelIdByEntityId(Integer siteId,Integer entityId,Integer channelId);		
	
	/**
	 * 
	 * @param site  站点
	 * @param contentClass 内容的class
	 * @param entityId 实体的id
	 * @return
	 */
	public Content getByEntityId(Site site,Class<? extends BaseContent> contentClass,Integer entityId);
	public Content getByEntityId(Integer siteId,Class<? extends BaseContent> contentClass,Integer entityId);
	
	/**
	 * 该方法最好不要调用      现在只在栏目类型为单页是用于获取对应的内容表中的值
	 * @param site  站点实体
	 * @param channel 栏目实体
	 * @param status 内容表中对应的状态
	 * @param isStatic 是否静态化
	 * @return 符合条件的内容列表     
	 */
	public List<Content> getByChannel(Site site,Channel channel,Integer status,Boolean isStatic);
	
	public List<Content> findTopHitsList(Integer channelId,Integer siteId,Integer size);

	public List<Content> findStaticPages(Integer id,Integer id2, Integer contentListSize);

	public List<Content> getAllEntityIds(Integer siteId, List<Channel> channelList,boolean isStatic);	
	
	public  List<Content> getByActionName(Integer siteId,String actionName,boolean isStatic,Integer size);
	
	public List<Content> findIndexContent(Integer siteId, String actionName);
	
	public int getContentListLength(Integer channelId);
	
	/**
	 * 生成静态化内容调用方法
	 */
	public void generate(Content content);
	
	/**
	 * 为建立索引调用的保存方法
	 */
	public void save(Collection<Content> ts, boolean isEdit);
	
	public void save(Content content, boolean isEdit);

	public void save(Content content, BaseContent bc, boolean isEdit);
	
	/**
	 * 删除静态化文件
	 */
	public void deleteStaticPage(Content content);

	@SuppressWarnings("unchecked")
	public List<Content> findBySite(Integer siteId,
			List<? extends BaseContent> list, Class contentClass,
			Boolean isStatic);

	public List<Content> findNewestSaticList(int siteId, int size);
	
	public List<Content> findJobBySite(Integer siteId, Class<?> clazz,
			boolean isStatic, List<Area> list, String jobName,
			Pagination<Content> p);
}
