package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface InitChannelDao extends Dao<InitChannel, Integer> {

	List<InitChannel> findByAll(Integer tempId, Integer parentId,
			Pagination<InitChannel> p);

	List<InitChannel> findAllParent(Integer initTempId, Boolean isDisable);

	List<InitChannel> find(Integer initTempId, String channelType,
			Integer modelId, String contentType, boolean isDisable);

	List<InitChannel> findSubByParentId(List<Integer> idList, Integer tempId);
	
}
