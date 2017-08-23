package com.elian.cms.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.elian.cms.syst.model.PersistentLogInterface;

@Entity
@Table(name="RE_INVIT_BID")
public class InvitBid implements PersistentLogInterface {
	private static final long serialVersionUID = -8914673512886473554L;
	
	private Integer id;
	
	private Invitation invitation;
	
	private Bidding bidding;

	@Id
	@Column(name = "invit_bid_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "invit_id")
	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	@ManyToOne
	@JoinColumn(name = "bid_id")
	public Bidding getBidding() {
		return bidding;
	}

	public void setBidding(Bidding bidding) {
		this.bidding = bidding;
	}
	
	@Transient
	public String[] getSysLog() {
		return new String[] { "找中标映射", "id=" + id };
	}
}
