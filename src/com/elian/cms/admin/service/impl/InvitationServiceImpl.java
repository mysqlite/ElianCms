package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InvitationDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Invitation;
import com.elian.cms.admin.service.InvitationService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("invitationService")
public class InvitationServiceImpl extends
		ServiceImpl<InvitationDao, Invitation, Integer> implements
		InvitationService {

	public List<Invitation> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	public Invitation getByrid(Integer rid) {
		return dao.getByrid(rid);
	}

	@Autowired
	public void setDao(InvitationDao dao) {
		this.dao = dao;
	}

	public List<Invitation> getTopList(Channel channel, int size) {
		return dao.getTopList(channel.getSite().getId(), channel.getId(), size);
	}

	private ContentListener contentListener;

	public void delete(Invitation invitation) {
		ContentListenerImpl.checkSource(invitation);
		if (invitation.isSource())
			super.delete(invitation);
		if (null != contentListener)
			contentListener.afterDelete(invitation);
	}

	public void save(Invitation invitation, boolean isEdit) {
		super.save(invitation);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(invitation);
			else
				contentListener.afterSave(invitation);
	}
	public Integer save(Invitation invitation, boolean isEdit,boolean publish) {
		super.save(invitation);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(invitation,publish);
			else
			 controlId=contentListener.afterSave(invitation,publish);
		return controlId;
	}

	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}

	public Invitation findStaticSpageData(Integer siteId, Integer channelId) {		
		return dao.findStaticSpageData(siteId,channelId);
	}
}
