package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.DepartmentDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface DepartmentService extends
		Service<DepartmentDao, Department, Integer> {
	
	public List<Department> findByAll(Integer hospId, String typeName);

	public List<Department> findByContentId(List<Integer> contentIdList,
			Integer siteId);

	public void save(Department info, boolean isEdit);
	public void save(Department info, boolean isEdit,boolean publish);
	
	public void delete(Department d, Collection<Integer> contentIdList);
	
	public List<Department> findPageByHosp(Pagination<Department> p,Integer hospId);
	
	/**
	 * 该方法只有在栏目内容模型为单页的时候才能使用，如果是列表页则返回null
	 * @param site 站点
	 * @param hosp 医院
	 * @param channel 栏目
	 * @param status  状态
	 * @param isStatic 是否静态化
	 * @param hasImg 是否有圖片
	 * @return department
	 */
	public Department getSingleData(Site site,Hospital hosp,Channel channel,
			Integer status,Boolean isStatic,Boolean hasImg);
	
	/**
	 * 该方法只有在栏目内容模型为单页的时候才能使用，如果是列表页则返回null
	 * @param site 站点
	 * @param hosp 医院
	 * @param channel 栏目
	 * @param size 要获取的大小
	 * @param status  状态
	 * @param isStatic 是否静态化
	 * @param hasImg 是否有圖片
	 * @return department
	 */
	public List<Department> getListData(Site site,Hospital hosp,Channel channel,Integer size
			,Integer status,Boolean isStatic,Boolean hasImg);

	public Department findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);
}
