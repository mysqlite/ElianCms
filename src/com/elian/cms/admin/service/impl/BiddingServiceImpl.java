package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.BiddingDao;
import com.elian.cms.admin.model.Bidding;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.service.BiddingService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("biddingService")
public class BiddingServiceImpl extends
		ServiceImpl<BiddingDao, Bidding, Integer> implements BiddingService {

	public List<Bidding> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	@Autowired
	public void setDao(BiddingDao dao) {
		this.dao = dao;
	}

	public List<Bidding> getTopList(Channel channel, int size) {
		return dao.getTopList(channel.getSite().getId(), channel.getId(), size);
	}

	private ContentListener contentListener;

	public void delete(Bidding bidding) {
		ContentListenerImpl.checkSource(bidding);
		if (bidding.isSource())
			super.delete(bidding);
		if (null != contentListener)
			contentListener.afterDelete(bidding);
	}

	public void save(Bidding bidding, boolean isEdit) {
		super.save(bidding);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(bidding);
			else
				contentListener.afterSave(bidding);
	}
	
	public Integer save(Bidding bidding, boolean isEdit,boolean publish) {
		super.save(bidding);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(bidding,publish);
			else
				controlId=contentListener.afterSave(bidding,publish);
		return controlId;
	}

	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}

	public Bidding findStaticSpageData(Integer siteId, Integer channelId) {		
		return dao.findStaticSpageData(siteId,channelId);
	}
}
