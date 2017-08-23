package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ChannelDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface ChannelService extends Service<ChannelDao, Channel, Integer> {
	public List<Channel> findByAll(Integer siteId, Integer parentId,
			boolean isDisable, boolean isNavigator, Pagination<Channel> p,
			boolean isFront);

	public List<Channel> findNameAndParentId(String typeName, Integer parentId,
			Integer siteId);
	
	public List<Channel> findSubByParentIdForStaticPage(Integer parentId,
			Integer siteId);

	public List<Channel> findSubByParentId(Integer parentId, Integer siteId);

	public List<Channel> findSubByParentId(List<Integer> parentIdList,
			Integer siteId);
	
	public List<Channel> findAllSubByForStaticPage(Integer channelId,
			Integer siteId);
	
	public List<Channel> findByAllParentForStaticPage(Integer channelId,
			Integer siteId);
	
	public List<Channel> findNavParentForStaticPage(Integer channelId,
			Integer siteId);

	public List<Channel> findIsLeafBySub(List<Integer> subIdList, Integer siteId);
	
	public List<Channel> findAllSubByParentId(Integer channelId,
			Integer siteId);
	
	public List<Channel> findIndexChannelByType(String channelType,
			Integer siteId);
	
	public List<Channel> findChannelByTemp(String tempName, Integer siteId);

	public void findFrontList(Integer siteId,Pagination<Channel> pagination);
	
	public List<Channel> findByParentIdSiteId(int pid,int siteId);
	
	public List<Channel> findByParentIdSiteId(int pid,int siteId,Boolean isDisable);	
	
	/**
	 * 获取相同内容、列表页、父栏目且未配置模板数据的栏目 
	 * @param siteId 站点ID
	 * @param channel 预copy配置的栏目
	 * @param isHaveConfig 是否有配置
	 * @param isSameLevel 是否为同级
	 * @return
	 */
	public List<Channel> findBySameTemplatChannel(Integer siteId,Channel channel,
			boolean isHaveConfig,boolean isSameLevel);
	/**
	 * 
	 * @param siteId  站点id
	 * @param actionName 实体对应的action名称
	 * @param entityId	实体的id
	 * @return 对应的栏目
	 */
	public Channel findByEntityId(Integer siteId,String actionName,Integer entityId);
	
	/**
	 * 
	 * @param site	站点
	 * @param channel 栏目
	 * @return
	 */
	public List<Channel> getParentChannelList(Integer siteId,Integer channelId);
	
	public List<Channel> getParentChannelList(Integer siteId, Object... channelId);

	/**
	 * 找到所有的父栏目
	 * @param siteId
	 * @param isDisable 是否可用
	 * @return
	 */
	public List<Channel> findAllParent(Integer siteId, Boolean isDisable);

	/**
	 * 
	 * @param siteId 站点id
	 * @param channelType 栏目类型
	 * @param modelId 模型id
	 * @param contentType 内容类型
	 * @param isDisable 是否可用
	 * @return
	 */
	public List<Channel> find(Integer siteId, String channelType, Integer modelId,
			String contentType, boolean isDisable);

	public  List<Channel> getBySiteId(int siteId);

	public List<Channel> findAllParent(Integer siteId, Integer modelId);	
	
	public Boolean checkSiteHasChannel(Integer siteId);
	
	public boolean existIndexChannel(Integer siteId, Integer channelId);
}
