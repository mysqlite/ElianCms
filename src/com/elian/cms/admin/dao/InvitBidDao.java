package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.InvitBid;
import com.elian.cms.syst.dao.Dao;

public interface InvitBidDao extends Dao<InvitBid, Integer> {
	public List<InvitBid> findInvitBids(Integer invitId,Integer bidId);
	public List<InvitBid> findInvitBids();
}
