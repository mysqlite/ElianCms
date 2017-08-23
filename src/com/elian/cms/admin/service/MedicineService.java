package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.MedicineDao;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface MedicineService extends
		Service<MedicineDao, Medicine, Integer>, BasecontentService<Medicine> {
	public List<Medicine> findByAll(Integer companyId, Pagination<Medicine> p);

	public List<Medicine> findByAll(Integer medicineId,Integer typeId, Pagination<Medicine> p);
	
	public void save(Medicine info, boolean isEdit);

	public void save(Medicine d, boolean isEdit, boolean publish);

	public void delete(Medicine d, Collection<Integer> contentIdList);

	public Medicine findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);

	public List<Medicine> findStaticImgList(Integer siteId, Integer channelId,
			int size);
	
	
}
