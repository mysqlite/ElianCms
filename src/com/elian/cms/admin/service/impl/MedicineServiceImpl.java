package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.MedicineDao;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.service.BasecontentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("medicineService")
public class MedicineServiceImpl extends
		ServiceImpl<MedicineDao, Medicine, Integer> implements MedicineService,BasecontentService<Medicine> {
	public List<Medicine> findByAll(Integer medicineId, Pagination<Medicine> p) {
		return dao.findByAll(medicineId,null,p);
	}
	
	public List<Medicine> findByAll(Integer medicineId,Integer typeId, Pagination<Medicine> p) {
		return dao.findByAll(medicineId,typeId,p);
	}

	@Resource
	public void setDao(MedicineDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 内容信息监听器实现
	 */
	private SpecialContentListener<Medicine> medicineListener;

	public void save(Medicine d, boolean isEdit) {
		super.save(d);
		if (isEdit && medicineListener != null)
			medicineListener.afterUpdate(d);
	}
	
	public void save(Medicine d, boolean isEdit,boolean publish) {
		super.save(d);
		if (isEdit && medicineListener != null)
			medicineListener.afterUpdate(d,publish);
	}
	

	public void delete(Medicine d, Collection<Integer> contentIdList) {
		if (d != null)
			super.delete(d);
		if (medicineListener != null)
			medicineListener.afterDelete(d, contentIdList);
	}

	
	public Medicine findStaticSpageData(Integer siteId,Integer channelId,Boolean hasImg) {
		return dao.findStaticSpageData(siteId, channelId, hasImg);
	}
	
	public List<Medicine>  findStaticImgList(Integer siteId, Integer channelId,int size){
		return dao.findStaticImgList(siteId, channelId, size);
	}
	
	@Resource
	public void setMedicineListener(SpecialContentListener<Medicine> medicineListener) {
		this.medicineListener = medicineListener;
	}
}
