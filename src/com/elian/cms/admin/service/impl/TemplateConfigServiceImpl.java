package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.TemplateConfigDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.ChannelService;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.ElianUtils;

@Component("templateConfigService")
public class TemplateConfigServiceImpl extends
		ServiceImpl<TemplateConfigDao, TemplateConfig, Integer> implements
		TemplateConfigService {
	private ChannelService channelService;
	private JdbcService jdbcService;
	private TemplateSetService templateSetService;
	
	@Resource
	public void setDao(TemplateConfigDao dao) {
		this.dao = dao;
	}

	@Resource
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
	
	@Resource
	public void setTemplateSetService(TemplateSetService templateSetService) {
		this.templateSetService = templateSetService;
	}

	public List<TemplateConfig> getByChannelId(Integer channelId,
			Pagination<TemplateConfig> pagination) {
		return dao.getByChannelId(channelId,pagination);		
	}

	public List<Integer> getTempAreaIds(Integer tempId, Integer channelId) {
		List<Object[]> result=dao.getTempAreaIds(tempId,channelId) ;	
		List<TemplateSet> result2=templateSetService.getTempParentAreas(tempId);
		if(CollectionUtils.isEmpty(result) && CollectionUtils.isEmpty(result2)) return null;
		List<Integer> areaIds=new ArrayList<Integer>();		
		if(!CollectionUtils.isEmpty(result2)){
			for(TemplateSet s:result2){
				Integer size=getAreaIdSize(tempId,channelId,s.getAreaId());
				if(size !=null && s.getMaxChannelSize()>size)
					areaIds.add(s.getAreaId());
			}
		}
		for(Object obj:result)
			areaIds.add((Integer) obj);
		Collections.sort(areaIds);
		return areaIds;
	}

	public List<Map<String, Object>> getChannelTree(TemplateSet templateSet) {
		boolean channelParent=ElianUtils.CHANNEL_PARENT.endsWith(templateSet.getChannelType())?true:false;
		List<Channel> channelList;
		if(channelParent){
			channelList=channelService.findAllParent(ApplicationUtils.getSite().getId(),true);
			
		}else{			
			String channelType=templateSet.getChannelType();
			Integer modelId=templateSet.getModel().getId();
			String contentType=templateSet.getContentType();
			
			channelList=channelService.find(ApplicationUtils.getSite().getId(),channelType,
					modelId,contentType,true);		
			}			
		return getChannelTree(channelList);
	}	
	
	public List<Map<String, Object>> getChannelTree(List<Channel> channelList) {
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (Channel c: channelList) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", c.getId());
			map.put("pId", c.getParentId());
			map.put("name", c.getChannelName());
			treeModels.add(map);
		}
		return treeModels;				
	}
	
	private Integer getAreaIdSize(Integer tempId,Integer channelId,Integer areaId){
		String sql="select count(*) from T_TEMP_CONFIG where temp_id=? and channel_id=? and area_id=?";
		List<Object[]> r= jdbcService.findSqlQuery(sql, tempId,channelId,areaId);
		if(CollectionUtils.isEmpty(r)) return null;
		Object obj=r.get(0);
		return (Integer) obj;
	}

	public List<TemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId) {		
		return dao.getByTempIdAreaId(tempId,areaId);
	}

	public List<TemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer channelId) {		
		return dao.findByTempIdAndChannelId(tempId,channelId);
	}

	public List<TemplateConfig> getAllConfig(Channel channelSet) {
		if(null==channelSet) return null;
		return dao.getAllConfig(channelSet);
	}

	public List<TemplateConfig> getByTempIdAreaId(Integer channelId,
			Integer tempId, Integer areaId) {
		return dao.getByTempIdAreaId(channelId,tempId,areaId);
	}
	
}
