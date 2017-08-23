package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.LinksDao;
import com.elian.cms.admin.model.Links;
import com.elian.cms.syst.service.Service;

public interface LinksService extends Service<LinksDao, Links, Integer>
	,BasecontentService<Links> {

	public void save(Links links, boolean isEdit);
	public Integer save(Links links, boolean isEdit,boolean publish);
	
	public void delete(Links links) ;
	
	public List<Links> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Links> findByParentChannelId(Integer siteId,Integer parentChannelId,Integer state, Boolean isSatatic) ;
}
