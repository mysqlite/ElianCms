package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Invitation;
import com.elian.cms.syst.dao.Dao;

public interface InvitationDao extends Dao<Invitation, Integer> {
	public List<Invitation> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public List<Invitation> getTopList(Integer id, Integer id2, int size);

	public Invitation getByrid(Integer rid);

	public Invitation findStaticSpageData(Integer siteId, Integer channelId);
}
