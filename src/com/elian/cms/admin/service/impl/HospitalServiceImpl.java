package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.HospitalDao;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("hospitalService")
public class HospitalServiceImpl extends
		ServiceImpl<HospitalDao, Hospital, Integer> implements HospitalService {

	public void save(Hospital hospital) {
		super.save(hospital);
	}

	public List<Hospital> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	public List<Hospital> findPageByHosp(Pagination<Hospital> p, Integer hospId) {
		return dao.findPageByHosp(p, hospId);
	}
	
	public List<Hospital> findByAreaCode(Pagination<Hospital> p,List<Integer> areaCodes){
		return dao.findByAreaCode(p, areaCodes,null);
	}
	
	public List<Hospital> findByAreaCode(List<Integer> areaCodes,String hospName){
		return dao.findByAreaCode(null, areaCodes,hospName);
	}
	
	public List<Hospital> findBypage(Pagination<Hospital> p,List<Integer> areaCodes) {
		return dao.findBypage(p, areaCodes);
	}
	
	@Resource
	public void setDao(HospitalDao dao) {
		this.dao = dao;
	}

	private SpecialContentListener<Hospital> hospitalListener;

	public void delete(Hospital hospital, Collection<Integer> contentIdList) {
		if (hospital != null)
			super.delete(hospital);
		if (hospitalListener != null)
			hospitalListener.afterDelete(hospital, contentIdList);
	}

	public void save(Hospital hospital, boolean isEdit) {
		super.save(hospital);
		if (isEdit && hospitalListener != null)
			hospitalListener.afterUpdate(hospital);
	}
	
	public void save(Hospital hospital, boolean isEdit,boolean publish) {
		super.save(hospital);
		if (isEdit && hospitalListener != null)
			hospitalListener.afterUpdate(hospital,publish);
	}

	@Resource
	public void setHospitalListener(
			SpecialContentListener<Hospital> hospitalListener) {
		this.hospitalListener = hospitalListener;
	}

	public List<Hospital> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		return dao.findStaticImgList(siteId,channelId,size);
	}

	public Hospital findStaticSpageData(Integer siteId, Integer channelId,Boolean hasImg) {
		return dao.findStaticSpageData(siteId,channelId,hasImg);
	}

	public List<Hospital> search(String criteria,Integer areaId,
			Pagination<Hospital> pagination) {
		return dao.search(criteria,areaId,pagination);
	}

	public List<Hospital> findByAreaCode(Integer size, Integer areaId) {
		return dao.findByAreaCode(size, areaId);
	}
	
	public List<HospitalNoScore> findHospNoScore(Integer topNum,String startTime,String endTime, Integer endDay){
		return dao.findHospNoScore(topNum, startTime, endTime, endDay);
	}
	
	public List<WorkTopDeparment> findTopWorkDoctor(Integer topDepaNum,Integer topDoctorNum,String startTime,String endTime){
		return dao.findTopWorkDoctor(topDepaNum, topDoctorNum, startTime, endTime);
	}

	public List<Hospital> findRegHospital(Integer areaId,Integer hospRank) {
		StringBuffer sql=new StringBuffer();
		sql.append(" AND H.IS_REG =1 ");
		if(hospRank!=null && hospRank!=0)
			sql.append(" AND H.HOSP_RANK="+hospRank);
		System.out.println(sql);
		return dao.search(sql.toString(),areaId, null);
	}
}
