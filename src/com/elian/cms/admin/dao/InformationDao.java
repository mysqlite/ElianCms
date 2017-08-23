package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface InformationDao extends Dao<Information, Integer> {
	public List<Information> findByAll(Integer siteId, Integer channelId,
			Pagination<Information> p);

	public List<Information> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Information> findImgList(Integer channelId, Integer siteId,
			Integer state);

	public List<Information> getPowerPointList(Site site,Channel channnelParent, Integer size);
	
	public List<Information> getVideoList(Site site,Channel channel,int size);

	public List<Information> findByParentChannelId(Integer siteId,Integer parentChannelId,
			Integer state, Boolean isSatatic);

	public Information findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);
}
