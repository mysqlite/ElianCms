package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InstrumentDao;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.service.BasecontentService;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("instrumentService")
public class InstrumentServiceImpl extends
		ServiceImpl<InstrumentDao, Instrument, Integer> implements
		InstrumentService,BasecontentService<Instrument> {
	public List<Instrument> findByAll(Integer instrumentId, Pagination<Instrument> p){
		return dao.findByAll(instrumentId,null, p);
	}
	public List<Instrument> findByAll(Integer instrumentId,Integer typeId, Pagination<Instrument> p){
		return dao.findByAll(instrumentId,typeId, p);
	}
	@Resource
	public void setDao(InstrumentDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 内容信息监听器实现
	 */
	private SpecialContentListener<Instrument> instrumentListener;

	public void save(Instrument d) {
		super.save(d);
	}
	
	public void save(Instrument d, boolean isEdit) {
		super.save(d);
		if (isEdit && instrumentListener != null)
			instrumentListener.afterUpdate(d);
	}

	public void save(Instrument d, boolean isEdit, boolean publish) {
		super.save(d);
		if (isEdit && instrumentListener != null)
			instrumentListener.afterUpdate(d, publish);
	}

	public void delete(Instrument d, Collection<Integer> contentIdList) {
		if (d != null)
			super.delete(d);
		if (instrumentListener != null)
			instrumentListener.afterDelete(d, contentIdList);
	}
	
	public List<Instrument>  findStaticImgList(Integer siteId, Integer channelId,int size){
		return dao.findStaticImgList(siteId, channelId, size);
	}
	public Instrument findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg) {
		return dao.findStaticSpageData(siteId, channelId, hasImg);
	}

	@Resource
	public void setInstrumentListener(
			SpecialContentListener<Instrument> instrumentListener) {
		this.instrumentListener = instrumentListener;
	}
}
