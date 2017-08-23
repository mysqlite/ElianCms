package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.InvitationDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.syst.service.Service;

public interface InvitationService extends
		Service<InvitationDao, Invitation, Integer> {
	
	public void save(Invitation invitation, boolean isEdit);
	public Integer save(Invitation invitation, boolean isEdit,boolean publish);
	
	public void delete(Invitation invitation) ;
	
	public List<Invitation> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public Invitation getByrid(Integer rid);
	
	public List<Invitation> getTopList(Channel channel,int size);

	public Invitation findStaticSpageData(Integer siteId, Integer channelId);
}
