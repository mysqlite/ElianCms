package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Links;
import com.elian.cms.syst.dao.Dao;

public interface LinksDao extends Dao<Links, Integer> {

	public List<Links> findByContentId(List<Integer> contentIdList,
			Integer siteId);
	
	public List<Links> findByParentChannelId(Integer siteId,Integer parentChannelId,Integer state, Boolean isSatatic) ;
}
