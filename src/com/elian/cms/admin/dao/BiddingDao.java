package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Bidding;
import com.elian.cms.syst.dao.Dao;

public interface BiddingDao extends Dao<Bidding, Integer> {
	
	public List<Bidding> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Bidding> getTopList(Integer siteId, Integer channnelParentId, int size);

	public Bidding findStaticSpageData(Integer siteId, Integer channelId);
}
