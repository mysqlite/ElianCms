package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.InitTemplateConfigDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.InitChannelService;
import com.elian.cms.admin.service.InitTemplateConfigService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("initTemplateConfigService")
public class InitTemplateConfigServiceImpl extends
		ServiceImpl<InitTemplateConfigDao, InitTemplateConfig, Integer> implements
		InitTemplateConfigService {

	private TemplateSetService templateSetService;
	private InitChannelService initChannelService;
	private JdbcService jdbcService;
	
	public List<InitTemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer initChannelId) {
		return dao.findByTempIdAndChannelId(tempId,initChannelId);
	}

	public List<InitTemplateConfig> getAllConfig(InitChannel initChannelSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<InitTemplateConfig> getByInitChannelId(Integer initChannelId,
			Pagination<InitTemplateConfig> pagination) {
		return dao.getByInitChannelId(initChannelId,pagination);
	}

	public List<InitTemplateConfig> getByTempIdAreaId(Integer tempId,
			Integer areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<InitTemplateConfig> getByTempIdAreaId(Integer initChannelId,
			Integer tempId, Integer areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> getChannelTree(Integer initTempId, TemplateSet templateSet) {
		boolean channelParent=ElianUtils.CHANNEL_PARENT.endsWith(templateSet.getChannelType())?true:false;
		List<InitChannel> channelList;
		if(channelParent){
			channelList=initChannelService.findAllParent(initTempId,true);
		}else{			
			String channelType=templateSet.getChannelType();
			Integer modelId=templateSet.getModel().getId();
			String contentType=templateSet.getContentType();
			
			channelList=initChannelService.find(initTempId,channelType,modelId,contentType,true);		
			}			
		return getChannelTree(channelList);
	}

	public List<Map<String, Object>> getChannelTree(List<InitChannel> channelList) {
		List<Map<String, Object>> treeModels = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		for (InitChannel c: channelList) {
			map = new LinkedHashMap<String, Object>();
			map.put("id", c.getId());
			map.put("pId", c.getParentId());
			map.put("name", c.getChannelName());
			treeModels.add(map);
		}
		return treeModels;				
	}
	
	public List<Integer> getTempAreaIds(Integer tempId, Integer initChannelId) {
		List<Object[]> result=dao.getTempAreaIds(tempId,initChannelId) ;	
		List<TemplateSet> result2=templateSetService.getTempParentAreas(tempId);
		if(CollectionUtils.isEmpty(result) && CollectionUtils.isEmpty(result2)) return null;
		List<Integer> areaIds=new ArrayList<Integer>();		
		if(!CollectionUtils.isEmpty(result2)){
			for(TemplateSet s:result2){
				Integer size=getAreaIdSize(tempId,initChannelId,s.getAreaId());
				if(size !=null && s.getMaxChannelSize()>size)
					areaIds.add(s.getAreaId());
			}
		}
		for(Object obj:result)
			areaIds.add((Integer) obj);
		Collections.sort(areaIds);
		return areaIds;
	}
	
	private Integer getAreaIdSize(Integer tempId,Integer channelId,Integer areaId){
		String sql="select count(*) from T_INIT_TEMP_CONFIG where temp_id=? and init_channel_id=? and area_id=?";
		List<Object[]> r= jdbcService.findSqlQuery(sql, tempId,channelId,areaId);
		if(CollectionUtils.isEmpty(r)) return null;
		Object obj=r.get(0);
		return (Integer) obj;
	}
	
	@Resource
	public void setDao(InitTemplateConfigDao dao) {
		super.setDao(dao);
	}
	
	@Resource
	public void setTemplateSetService(TemplateSetService templateSetService) {
		this.templateSetService = templateSetService;
	}

	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	@Resource
	public void setInitChannelService(InitChannelService initChannelService) {
		this.initChannelService = initChannelService;
	}
}
