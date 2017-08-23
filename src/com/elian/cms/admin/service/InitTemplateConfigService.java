package com.elian.cms.admin.service;

import java.util.List;
import java.util.Map;

import com.elian.cms.admin.dao.InitTemplateConfigDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface InitTemplateConfigService extends
		Service<InitTemplateConfigDao, InitTemplateConfig, Integer> {

	List<InitTemplateConfig> getByInitChannelId(Integer initChannelId,
			Pagination<InitTemplateConfig> pagination);

	List<Integer> getTempAreaIds(Integer tempId, Integer initChannelId);
	
	List<InitTemplateConfig> findByTempIdAndChannelId(Integer tempId, Integer initChannelId);

	List<Map<String, Object>> getChannelTree(Integer initTempId,TemplateSet templateSet);

	List<InitTemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId);
	
	List<InitTemplateConfig> getByTempIdAreaId(Integer initChannelId,Integer tempId, Integer areaId);

	List<InitTemplateConfig> getAllConfig(InitChannel initChannelSet);
	
}
