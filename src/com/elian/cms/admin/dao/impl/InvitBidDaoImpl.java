package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InvitBidDao;
import com.elian.cms.admin.model.InvitBid;
import com.elian.cms.syst.dao.impl.DaoImpl;
@Component("invitBidDao")
public class InvitBidDaoImpl extends DaoImpl<InvitBid, Integer> implements InvitBidDao {
	public List<InvitBid> findInvitBids(){
		String hql=" from InvitBid i ";
		return findByHql(hql, true);
	}
	public List<InvitBid> findInvitBids(Integer invitId,Integer bidId){
		String hql=" from InvitBid i where 1=1   ";
		List<Object> param=new ArrayList<Object>();
		if(invitId!=null) {
			hql+=" and  i.invitation.id =?  ";
		}
		if(bidId!=null) {
			hql+=" and i.bidding.id=?  ";
		}
		return findByHql(hql, true,param.toArray());
	}
}
