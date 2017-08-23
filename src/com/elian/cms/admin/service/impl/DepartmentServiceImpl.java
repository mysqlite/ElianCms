package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DepartmentDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Department;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.admin.service.DepartmentService;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;

@Component("departmentService")
public class DepartmentServiceImpl extends
		ServiceImpl<DepartmentDao, Department, Integer> implements
		DepartmentService {
	public List<Department> findByAll(Integer hospId, String typeName) {
		return dao.findByAll(hospId, typeName);
	}

	public List<Department> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		return dao.findByContentId(contentIdList, siteId);
	}

	@Autowired
	public void setDao(DepartmentDao dao) {
		this.dao = dao;
	}
	
	public List<Department> findPageByHosp(Pagination<Department> p,Integer hospId){
		return dao.findPageByHosp(p, hospId);
	}

	/**
	 * 内容信息监听器实现
	 */
	private SpecialContentListener<Department> departmentListener;

	public void save(Department d, boolean isEdit) {
		super.save(d);
		if (isEdit && departmentListener != null)
			departmentListener.afterUpdate(d);
	}
	
	public void save(Department d, boolean isEdit,boolean publish) {
		super.save(d);
		if (isEdit && departmentListener != null)
			departmentListener.afterUpdate(d,publish);
	}

	public void delete(Department d, Collection<Integer> contentIdList) {
		if (d != null)
			super.delete(d);
		if (departmentListener != null)
			departmentListener.afterDelete(d, contentIdList);
	}

	@Resource
	public void setDepartmentListener(
			SpecialContentListener<Department> departmentListener) {
		this.departmentListener = departmentListener;
	}

	public Department getSingleData(Site site,Hospital hosp, Channel channel, Integer status,
			Boolean isStatic,Boolean hasImg) {
		if(null==site || null==hosp || null==channel) return null;		
		if(ElianUtils.CONTENT_SINGLE.equals(channel.getContentType())){			
			if(ElianUtils.COMP_TYPE_MAIN.equals(site.getComType()) ||
					ElianUtils.COMP_TYPE_SUBS.equals(site.getComType())){			
				List<Department> departmentList=dao.getData(site,null,channel,null,status, isStatic,hasImg);			
				if(CollectionUtils.isEmpty(departmentList))
					return null;
				return departmentList.get(0);
			}else{
				List<Department> departmentList=dao.getData(site,hosp,channel,null,
						status, isStatic,hasImg);			
				if(CollectionUtils.isEmpty(departmentList))
					return null;
				return departmentList.get(0);
			}
		}
		return null;
				
	}

	public List<Department> getListData(Site site, Hospital hosp,
			Channel channel, Integer size, Integer status, Boolean isStatic,
			Boolean hasImg) {
		if(null==site || null==hosp || null==channel) return null;		
		if(ElianUtils.CONTENT_LIST.equals(channel.getContentType())){			
			if(ElianUtils.COMP_TYPE_MAIN.equals(site.getComType()) ||
					ElianUtils.COMP_TYPE_SUBS.equals(site.getComType())){			
				List<Department> departmentList=dao.getData(site,null,channel,size,status, isStatic,hasImg);			
				if(CollectionUtils.isEmpty(departmentList))
					return null;
				return departmentList;
			}else{
				List<Department> departmentList=dao.getData(site,hosp,channel,size,status, isStatic,hasImg);			
				if(CollectionUtils.isEmpty(departmentList))
					return null;
				return departmentList;
			}
		}
		return null;
	}

	public Department findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		return dao.findStaticSpageData(siteId,channelId,hasImg);
	}
}
