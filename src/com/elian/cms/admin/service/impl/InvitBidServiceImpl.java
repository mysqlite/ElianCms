package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InvitBidDao;
import com.elian.cms.admin.model.InvitBid;
import com.elian.cms.admin.service.InvitBidService;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("invitBidService")
public class InvitBidServiceImpl extends
		ServiceImpl<InvitBidDao, InvitBid, Integer> implements
		InvitBidService {
	public List<InvitBid> findInvitBids(Integer invitId,Integer bidId){
		return  dao.findInvitBids(invitId, bidId);
	}
	public List<InvitBid> findInvitBids(){
		return dao.findInvitBids();
	}

	@Resource
	public void setDao(InvitBidDao dao) {
		this.dao = dao;
	}
}
