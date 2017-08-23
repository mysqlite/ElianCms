package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DepartmentDao extends Dao<Department, Integer> {
	public List<Department> findByAll(Integer hospId, String typeName);

	public List<Department> findByContentId(List<Integer> contentIdList,
			Integer siteId);
	
	public List<Department> findPageByHosp(Pagination<Department> p,Integer hospId);
	
    /**
     *  该功能用于在医院拿科室时使用
     * @param site 站点
     * @param hosp
     * @param channel 栏目
	 * @param status  状态
     * @param size 
	 * @param isStatic 是否静态化
	 * @param hasImg 是否有圖片
	 * @return  department
     */
	public List<Department> getData(Site site, Hospital hosp, Channel channel,
			Integer size,Integer status, Boolean isStatic,Boolean hasImg);

	public Department findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);	
	
}
