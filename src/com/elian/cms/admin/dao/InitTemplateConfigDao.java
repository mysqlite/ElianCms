package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface InitTemplateConfigDao extends Dao<InitTemplateConfig, Integer> {

	List<InitTemplateConfig> getByInitChannelId(Integer initChannelId, 
			Pagination<InitTemplateConfig> pagination);

	List<Object[]>  getTempAreaIds(Integer tempId, Integer initChannelId);

	List<Object[]> getTempParentAreaIds(Integer tempId);

	List<InitTemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId);

	List<InitTemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer initChannelId);

	List<InitTemplateConfig> getAllConfig(InitChannel initChannelSet);

	List<InitTemplateConfig> getByTempIdAreaId(Integer initChannelId, Integer tempId,
			Integer areaId);
}
