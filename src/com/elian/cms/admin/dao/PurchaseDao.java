package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Purchase;
import com.elian.cms.syst.dao.Dao;

public interface PurchaseDao extends Dao<Purchase, Integer> {
	public List<Purchase> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public Purchase findStaticSpageData(Integer siteId, Integer channelId);
}
