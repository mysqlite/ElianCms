package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.DoctorDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DoctorService extends Service<DoctorDao, Doctor, Integer> {

	public List<Doctor> findByPage(Pagination<Doctor> p, Integer hospId,
			Integer departId);

	public List<Doctor> findByContentId(List<Integer> contentIdList);

	public List<Doctor> findPageByHosp(Pagination<Doctor> p, Integer hospId);

	public void save(Doctor info, boolean isEdit);
	
	public void save(Doctor d, boolean isEdit,boolean publish);

	public void delete(Doctor d, Collection<Integer> contentIdList);

	public List<Doctor> findByHospAndMenu(Integer hospId, Integer menuId);

	/**
	 * 
	 * @param site
	 *            站点
	 * @param channel
	 *            栏目
	 * @param status
	 *            控制表中的状态
	 * @param isStatic
	 *            控制表中 是否是静态化
	 * @param hasImg
	 *            是否有图片
	 * @param size
	 *            要获取list的大小
	 * @return
	 */
	public List<Doctor> findList(Site site, Channel channel, Integer status,
			Boolean isStatic, Boolean hasImg, Integer size);

	public Doctor findStaticSpageData(Integer siteId, Integer channelId, Boolean hasImg);

	public List<Doctor> findStaticImgList(Integer siteId, Integer channelId, int size);

	public  List<Doctor> search(String criteria, Pagination<Doctor> pagination);

	public List<Doctor> findList(List<Hospital> hospitalList, int size);
}
