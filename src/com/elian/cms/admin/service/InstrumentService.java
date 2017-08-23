package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.InstrumentDao;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface InstrumentService extends
		Service<InstrumentDao, Instrument, Integer>,BasecontentService<Instrument> {
	public List<Instrument> findByAll(Integer companyId, Pagination<Instrument> p);
	
	public List<Instrument> findByAll(Integer instrumentId,Integer typeId, Pagination<Instrument> p);
	
	public void save(Instrument d);
	
	public void save(Instrument info, boolean isEdit);

	public void save(Instrument d, boolean isEdit, boolean publish);

	public void delete(Instrument d, Collection<Integer> contentIdList);
	
	public List<Instrument>  findStaticImgList(Integer siteId, Integer channelId,int size);
	
	public Instrument findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg);
}
