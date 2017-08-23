package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.InvitBidDao;
import com.elian.cms.admin.model.InvitBid;
import com.elian.cms.syst.service.Service;

public interface InvitBidService extends
		Service<InvitBidDao, InvitBid, Integer> {
	public List<InvitBid> findInvitBids(Integer invitId,Integer bidId);
	public List<InvitBid> findInvitBids();
}
