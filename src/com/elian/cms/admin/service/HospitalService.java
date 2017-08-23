package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.HospitalDao;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface HospitalService extends Service<HospitalDao, Hospital, Integer> {
	
	public void save(Hospital hospital, boolean isEdit);
	
	public void save(Hospital hospital, boolean isEdit,boolean publish) ;
	
	public void delete(Hospital hospital, Collection<Integer> contentIdList);
	
	public List<Hospital> findByContentId(List<Integer> contentIdList,
			Integer siteId);
	
	public List<Hospital> findPageByHosp(Pagination<Hospital> p, Integer hospId);
	
	public List<Hospital> findBypage(Pagination<Hospital> p,List<Integer> areaCodes);
	
	public List<Hospital> findByAreaCode(List<Integer> areaCodes,String hospName);
	
	//public List<Hospital> findByAreaCode(List<Integer> areaCodes);

	/**获取静态化的有图片的数据*/
	public List<Hospital> findStaticImgList(Integer siteId, Integer channelId, int size);

	/**获取静态化的单页数据
	 * @param hasImg */
	public Hospital findStaticSpageData(Integer siteId, Integer channelId, Boolean hasImg);

	public List<Hospital> search(String criteria,Integer areaId, Pagination<Hospital> pagination);
	
	/**
	 * 通过区域id（包含子区域）获取医院
	 */
	public List<Hospital> findByAreaCode(Integer size,Integer areaId);

	public List<Hospital> findRegHospital(Integer areaId,Integer hospRank);
	
	/**查询医院放号排行*/
	public List<HospitalNoScore> findHospNoScore(Integer topNum,String startTime,String endTime, Integer endDay);
	
	/**本周热门医生*/
	public List<WorkTopDeparment> findTopWorkDoctor(Integer topDepaNum,Integer topDoctorNum,String startTime,String endTime);
	
}
