package com.elian.cms.admin.service;

import java.util.Collection;
import java.util.List;

import com.elian.cms.admin.dao.CompanyDao;
import com.elian.cms.admin.model.Company;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface CompanyService extends Service<CompanyDao, Company, Integer> {

	/**
	 * 通过区域id获取企业
	 * @param areaId 区域id
	 * @param pagination 分页对象
	 * @return
	 */
	public List<Company> getByAreaId(Integer areaId,
			Pagination<Company> pagination,String compName);

	public Company findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);
	
	public void save(Company info, boolean isEdit);
	
	public void save(Company d, boolean isEdit,boolean publish);

	public void delete(Company d, Collection<Integer> contentIdList);

	/**获取静态化的有图片的数据*/
	public List<Company> findStaticImgList(Integer siteId, Integer channelId, int size);

	/**
	 * 根据区域名称找企业
	 * @param areaName 区域名称
	 * @param status 站点状态
	 * @param pagination 
	 */
	public List<Company> findByAraeName(String areaName, int status, Pagination<Company> pagination);

	public List<Company>  search(String criteria, Integer areaId,
			Pagination<Company> pagination);
}
