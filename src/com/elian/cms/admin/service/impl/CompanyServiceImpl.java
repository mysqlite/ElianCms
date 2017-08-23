package com.elian.cms.admin.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.AreaDao;
import com.elian.cms.admin.dao.CompanyDao;
import com.elian.cms.admin.model.Company;
import com.elian.cms.admin.service.CompanyService;
import com.elian.cms.syst.listener.Impl.SpecialContentListener;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;

@Component("companyService")
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company, Integer> implements
	CompanyService {
	private AreaDao araeDao=null;
	
	@Autowired
	public void setDao(CompanyDao dao) {
		this.dao = dao;
	}

	@Resource
	public void setAraeDao(AreaDao araeDao) {
		this.araeDao = araeDao;
	}


	public List<Company> getByAreaId(Integer areaId,
			Pagination<Company> pagination,String compName) {
		String function=araeDao.findChirdByParentFunction(areaId==null?0:areaId);
		return dao.getByAreaId(function,pagination,compName);
	}

	public Company findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		return dao.findStaticSpageData(siteId,channelId,hasImg);
	}
	
	/**
	 * 内容信息监听器实现
	 */
	private SpecialContentListener<Company> companyListener;

	public void save(Company d, boolean isEdit) {
		super.save(d);
		if (isEdit && companyListener != null)
			companyListener.afterUpdate(d);
	}
	
	public void save(Company d, boolean isEdit,boolean publish) {
		super.save(d);
		if (isEdit && companyListener != null)
			companyListener.afterUpdate(d,publish);
	}
	

	public void delete(Company d, Collection<Integer> contentIdList) {
		if (d != null)
			super.delete(d);
		if (companyListener != null)
			companyListener.afterDelete(d, contentIdList);
	}

	@Resource
	public void setCompanyListener(SpecialContentListener<Company> companyListener) {
		this.companyListener = companyListener;
	}

	public List<Company> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		return  dao.findStaticImgList(siteId,channelId,size);
	}

	public List<Company> findByAraeName(String areaName, int status,
			Pagination<Company> pagination) {
		return  dao.findByAraeName(areaName,status,pagination);
	}

	public List<Company> search(String criteria, Integer areaId,
			Pagination<Company> pagination) {
		return dao.search(criteria,areaId,pagination);		
	}
}
