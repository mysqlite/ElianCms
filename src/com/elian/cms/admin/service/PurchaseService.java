package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.PurchaseDao;
import com.elian.cms.admin.model.Purchase;
import com.elian.cms.syst.service.Service;

public interface PurchaseService extends Service<PurchaseDao, Purchase, Integer>
	,BasecontentService<Purchase> {

	public void save(Purchase purchase, boolean isEdit);
	public Integer save(Purchase purchase, boolean isEdit,boolean publish);
	
	public void delete(Purchase purchase);
	
	public List<Purchase> findByContentId(List<Integer> contentIdList,
			Integer siteId);
	
	public Purchase findStaticSpageData(Integer siteId, Integer channelId);
}
