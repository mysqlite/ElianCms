package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.BiddingDao;
import com.elian.cms.admin.model.Bidding;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.syst.service.Service;

public interface BiddingService extends Service<BiddingDao, Bidding, Integer> {
	
	public void save(Bidding bidding, boolean isEdit);
	public Integer save(Bidding bidding, boolean isEdit,boolean publish);
	
	public void delete(Bidding bidding) ;
	
	public List<Bidding> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Bidding> getTopList(Channel channel,int size);

	public Bidding findStaticSpageData(Integer siteId, Integer channelId);
	
//	public List<Bidding> findImgList(Integer channelId,Integer siteId,
//			Integer state,Integer size);
}
