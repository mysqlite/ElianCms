package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.TemplateConfigDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface TemplateConfigService extends
		Service<TemplateConfigDao, TemplateConfig, Integer> {

	List<TemplateConfig> getByChannelId(Integer channelId,
			Pagination<TemplateConfig> pagination);

	List<Integer> getTempAreaIds(Integer tempId, Integer channelId);
	
	List<TemplateConfig> findByTempIdAndChannelId(Integer tempId, Integer channelId);

	List<Map<String, Object>> getChannelTree(TemplateSet templateSet);

	List<TemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId);
	
	List<TemplateConfig> getByTempIdAreaId(Integer channelId,Integer tempId, Integer areaId);

	List<TemplateConfig> getAllConfig(Channel channelSet);

}
