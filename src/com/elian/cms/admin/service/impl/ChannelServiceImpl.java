package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ChannelDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.syst.listener.ChannelListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("channelService")
public class ChannelServiceImpl extends
		ServiceImpl<ChannelDao, Channel, Integer> implements ChannelService {

	private JdbcService jdbcService=null;
	
	public List<Channel> findByAll(Integer siteId, Integer parentId,boolean isDisable, boolean isNavigator, Pagination<Channel> p,boolean isFront) {
		return dao.findByAll(siteId, parentId, isDisable, isNavigator, p,isFront);
	}

	public List<Channel> findNameAndParentId(String typeName, Integer parentId,Integer siteId) {
		return dao.findNameAndParentId(typeName, parentId, siteId);
	}

	public List<Channel> findSubByParentId(Integer parentId, Integer siteId) {
		return dao.findSubByParentId(parentId, siteId);
	}

	public List<Channel> findSubByParentIdForStaticPage(Integer parentId,
			Integer siteId) {
		return dao.findSubByParentIdForStaticPage(parentId, siteId);
	}

	public List<Channel> findSubByParentId(List<Integer> parentIdList,
			Integer siteId) {
		return dao.findSubByParentId(parentIdList, siteId);
	}
	
	public List<Channel> findAllSubByForStaticPage(Integer channelId,
			Integer siteId) {
		return dao.findAllSubByForStaticPage(channelId, siteId);
	}

	public List<Channel> findByAllParentForStaticPage(Integer channelId,
			Integer siteId) {
		return dao.findByAllParentForStaticPage(channelId, siteId);
	}

	public List<Channel> findNavParentForStaticPage(Integer channelId,
			Integer siteId) {
		return dao.findNavParentForStaticPage(channelId, siteId);
	}

	public List<Channel> findIsLeafBySub(List<Integer> subIdList, Integer siteId) {
		return dao.findIsLeafBySub(subIdList, siteId);
	}
	
	public List<Channel> findAllSubByParentId(Integer channelId, Integer siteId) {
		return dao.findAllSubByParentId(channelId, siteId);
	}
	
	public List<Channel> findIndexChannelByType(String channelType,
			Integer siteId) {
		return dao.findIndexChannelByType(channelType, siteId);
	}
	
	public List<Channel> findChannelByTemp(String tempName, Integer siteId) {
		return dao.findChannelByTemp(tempName, siteId);
	}
	
	public void findFrontList(Integer siteId, Pagination<Channel> pagination) {
		dao.findFrontList(siteId, pagination);		
	}
	
	/**
	 * 获取相同内容、列表页、父栏目且未配置模板数据的栏目 
	 * @param siteId 站点ID
	 * @param channel 预copy配置的栏目
	 * @param isHaveConfig 是否有配置
	 * @param isSameLevel 是否为同级
	 * @return
	 */
	public List<Channel> findBySameTemplatChannel(Integer siteId,Channel channel,
			boolean isHaveConfig,boolean isSameLevel){
		return dao.findBySameTemplatChannel(siteId, channel, isHaveConfig, isSameLevel);
	}

	public List<Channel> findByParentIdSiteId(int pid,int siteId){
		return dao.findByParentIdSiteId(pid, siteId,null);
	}
	/**
	 * 获取启用了的子栏目列表
	 */
	public List<Channel> findByParentIdSiteId(int pid,int siteId,Boolean isDisable){
		return dao.findByParentIdSiteId(pid, siteId,isDisable);
	}

	/**
	 * 静态化页面监听器实现
	 */
	private ChannelListener channelListener;

	public void delete(Channel channel) {
		if (channelListener != null)
			channelListener.delete(channel);
		super.delete(channel);
	}

	public void delete(Collection<Channel> ts) {
		for (Channel channel : ts) {
			this.delete(channel);
		}
	}

	public Channel findByEntityId(Integer siteId, String actionName,
			Integer entityId) {
		return dao.findByEntityId(siteId,actionName,entityId);
	}

	public List<Channel> getParentChannelList(Integer siteId,Integer channelId) {
		if(null==siteId || null==channelId)
			return null;
		return dao.getParentChannelList(siteId,channelId);
	}
	
	public List<Channel> getParentChannelList(Integer siteId, Object... channelId){
		return dao.getParentChannelList(siteId, channelId);
	}
	
	@Autowired
	public void setDao(ChannelDao dao) {
		this.dao = dao;
	}
	
	@Resource
	public void setChannelListener(ChannelListener channelListener) {
		this.channelListener = channelListener;
	}
	
	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public List<Channel> findAllParent(Integer siteId, Boolean isDisable) {		
		List<Channel> subList= dao.findAllParent(siteId,isDisable);
		if(CollectionUtils.isEmpty(subList)) return null;
		List<Integer> channelIds=new ArrayList<Integer>();
		for(Channel c:subList)
			channelIds.add(c.getId());
		return dao.getParentChannelList(siteId, channelIds.toArray());
	}

	/**
	 * 该函数功能未完善，改进时应该修改     bug
	 */
	public List<Channel> find(Integer siteId, String channelType,
			Integer modelId, String contentType, boolean isDisable) {	
		List<Channel> subList=null;
		if(ElianUtils.CHANNEL_CONTENT.equals(channelType))
			subList=dao.find(siteId,channelType,modelId,contentType,isDisable);
		if(ElianUtils.CHANNEL_PARENT.equals(channelType))
			//subList=dao.find(siteId,)
		if(CollectionUtils.isEmpty(subList)) return null;
		return subList;
//		List<Integer> channelIds=new ArrayList<Integer>();
//		for(Channel c:subList)
//			channelIds.add(c.getId());
//		return dao.getParentChannelList(siteId, channelIds.toArray());
	}

	public List<Channel> getBySiteId(int siteId) {		
		return dao.getBySiteId(siteId);
	}

	public List<Channel> findAllParent(Integer siteId, Integer modelId) {
		return dao.findAllParent(siteId,modelId);
	}

	public Boolean checkSiteHasChannel(Integer siteId) {
		List<Object[]> list=jdbcService.findSqlQuery("select count(c.channel_id) from t_channel c where c.site_id=?", siteId);
		if(!CollectionUtils.isEmpty(list)){
			if((Integer)list.toArray()[0]>0)
				return true;
		}
		return false;
	}
	
	public boolean existIndexChannel(Integer siteId, Integer channelId) {
		String sql = "select count(c.channel_id) from t_channel c where c.channel_Type = 'index' and c.site_id=?";
		if (channelId != null) {
			sql += " and c.channel_id !=" + channelId;
		}
		List<Object[]> list = jdbcService.findSqlQuery(sql, siteId);
		if (!CollectionUtils.isEmpty(list) && (Integer) list.toArray()[0] > 0) {
			return true;
		}
		return false;
	}
}
