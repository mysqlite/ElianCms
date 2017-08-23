package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Medicine;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface MedicineDao extends Dao<Medicine, Integer> {

	public List<Medicine> findByAll(Integer companyId,Integer typeId, Pagination<Medicine> p);

	public Medicine findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg) ;

	public List<Medicine>  findStaticImgList(Integer siteId, Integer channelId,int size);

}
