package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Hospital;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface HospitalDao extends Dao<Hospital, Integer> {
	
	public List<Hospital> findBypage(Pagination<Hospital> p,List<Integer> areaCodes);
	
	public List<Hospital> findByContentId(List<Integer> contentIdList,Integer siteId);
	public List<Hospital> findByAreaCode(Pagination<Hospital> p,List<Integer> areaCodes,String hospName);
	public List<Hospital> findPageByHosp(Pagination<Hospital> p, Integer hospId);
	
	public List<Hospital> findStaticImgList(Integer siteId, Integer channelId,
			int size);

	public Hospital findStaticSpageData(Integer siteId, Integer channelId, Boolean hasImg);

	public List<Hospital> search(String criteria, Integer areaId,
			Pagination<Hospital> pagination);

	public List<Hospital> findByAreaCode(Integer size, Integer areaId);
	
	public List<HospitalNoScore> findHospNoScore(Integer topNum,String startTime,String endTime, Integer endDay);
	
	public List<WorkTopDeparment> findTopWorkDoctor(Integer topDepaNum,Integer topDoctorNum,String startTime,String endTime);
}
