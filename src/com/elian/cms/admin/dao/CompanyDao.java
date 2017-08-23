package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Company;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface CompanyDao extends Dao<Company, Integer> {

	List<Company> getByAreaId(String function, Pagination<Company> pagination,String compName);

	Company findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg);

	List<Company> findStaticImgList(Integer siteId, Integer channelId, int size);

	List<Company> findByAraeName(String areaName, int status,
			Pagination<Company> pagination);

	List<Company> search(String criteria, Integer areaId,
			Pagination<Company> pagination);

}
