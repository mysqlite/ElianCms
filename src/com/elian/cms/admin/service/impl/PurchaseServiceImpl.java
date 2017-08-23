package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.PurchaseDao;
import com.elian.cms.admin.model.Purchase;
import com.elian.cms.admin.service.PurchaseService;
import com.elian.cms.syst.listener.ContentListener;
import com.elian.cms.syst.listener.Impl.ContentListenerImpl;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("purchaseService")
public class PurchaseServiceImpl extends
		ServiceImpl<PurchaseDao, Purchase, Integer> implements PurchaseService {

	public List<Purchase> findByContentId(List<Integer> contentIdList,Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	@Resource
	public void setDao(PurchaseDao dao) {
		this.dao = dao;
	}

	private ContentListener contentListener;

	public void delete(Purchase purchase) {
		ContentListenerImpl.checkSource(purchase);
		if (purchase.isSource())
			super.delete(purchase);
		if (null != contentListener)
			contentListener.afterDelete(purchase);
	}

	public void save(Purchase purchase, boolean isEdit) {
		super.save(purchase);
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(purchase);
			else
				contentListener.afterSave(purchase);
	}
	public Integer save(Purchase purchase, boolean isEdit,boolean publish) {
		super.save(purchase);
		Integer controlId=0;
		if (null != contentListener)
			if (isEdit)
				contentListener.afterUpdate(purchase,publish);
			else
				controlId=contentListener.afterSave(purchase,publish);
		return controlId;
	}

	@Resource
	public void setContentListener(ContentListener contentListener) {
		this.contentListener = contentListener;
	}

	public Purchase findStaticSpageData(Integer siteId, Integer channelId) {
		return dao.findStaticSpageData(siteId,channelId);
	}
}
