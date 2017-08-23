package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Instrument;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface InstrumentDao extends Dao<Instrument, Integer> {

	public List<Instrument> findByAll(Integer companyId,Integer typeId, Pagination<Instrument> p);

	public List<Instrument>  findStaticImgList(Integer siteId, Integer channelId,int size);
	
	public Instrument findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg);

}
