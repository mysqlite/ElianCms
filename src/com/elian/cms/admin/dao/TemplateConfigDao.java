package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface TemplateConfigDao extends Dao<TemplateConfig, Integer> {

	List<TemplateConfig> getByChannelId(Integer channelId, 
			Pagination<TemplateConfig> pagination);

	List<Object[]>  getTempAreaIds(Integer tempId, Integer channelId);

	List<Object[]> getTempParentAreaIds(Integer tempId);

	List<TemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId);

	List<TemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer channelId);

	List<TemplateConfig> getAllConfig(Channel channelSet);

	List<TemplateConfig> getByTempIdAreaId(Integer channelId, Integer tempId,
			Integer areaId);
}
