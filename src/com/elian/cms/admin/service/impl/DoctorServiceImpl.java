package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DoctorDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("doctorService")
public class DoctorServiceImpl extends ServiceImpl<DoctorDao, Doctor, Integer>
		implements DoctorService {

	public List<Doctor> findByPage(Pagination<Doctor> p,Integer hospId,Integer departId){
		return dao.findByPage(p, hospId, departId);
	}
	
	public List<Doctor> findByContentId(List<Integer> contentIdList) {
		return dao.findByContentId(contentIdList);
	}
	
	public List<Doctor> findByHospAndMenu(Integer hospId,Integer menuId){
		return dao.findByHospAndMenu(hospId, menuId);
	}
	
	@Autowired
	public void setDao(DoctorDao dao) {
		this.dao = dao;
	}

	/**
	 * 内容信息监听器实现
	 */
	private SpecialContentListener<Doctor> doctorListener;

	public void save(Doctor d, boolean isEdit) {
		super.save(d);
		if (isEdit && doctorListener != null)
			doctorListener.afterUpdate(d);
	}
	
	public void save(Doctor d, boolean isEdit,boolean publish) {
		super.save(d);
		if (isEdit && doctorListener != null)
			doctorListener.afterUpdate(d,publish);
	}
	

	public void delete(Doctor d, Collection<Integer> contentIdList) {
		if (d != null)
			super.delete(d);
		if (doctorListener != null)
			doctorListener.afterDelete(d, contentIdList);
	}
	
	public List<Doctor> findPageByHosp(Pagination<Doctor> p, Integer hospId) {
		return dao.findPageByHosp(p, hospId);
	}

	@Resource
	public void setDoctorListener(SpecialContentListener<Doctor> doctorListener) {
		this.doctorListener = doctorListener;
	}

	public List<Doctor> findList(Site site, Channel channel, Integer status,
			Boolean isStatic, Boolean hasImg ,Integer size) {
		return dao.findList(site,channel,status,isStatic,hasImg,size);
	}

	public Doctor findStaticSpageData(Integer siteId, Integer channelId,Boolean hasImg) {		
		return dao.findStaticSpageData(siteId,channelId,hasImg);
	}

	public List<Doctor> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		return dao.findStaticImgList(siteId,channelId,size);
	}

	public List<Doctor> search(String criteria, Pagination<Doctor> pagination) {
		return dao.search(criteria,null,null,pagination);
	}

	public List<Doctor> findList(List<Hospital> hospitalList, int size) {
		if(!CollectionUtils.isEmpty(hospitalList))
			return dao.findList(hospitalList,size);
		return null;
	}	
	
}
