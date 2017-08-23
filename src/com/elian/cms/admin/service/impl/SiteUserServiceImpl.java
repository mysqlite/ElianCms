package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteUserDao;
import com.elian.cms.admin.model.SiteUser;
import com.elian.cms.admin.service.SiteUserService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SearchParamUtils;
import com.elian.cms.syst.util.StringUtils;
@Component("siteUserService")
public class SiteUserServiceImpl extends
		ServiceImpl<SiteUserDao, SiteUser, Integer> implements SiteUserService {
	public List<SiteUser> findByPagination(Pagination<SiteUser> p) {
		return dao.findByPagination(p);
	}

	public SiteUser findByUserId(Integer userId) {
		return dao.findByUserId(userId);
	}

	public List<SiteUser> findAuditSiteBypagination(Pagination<SiteUser> p,Integer status,String comType){
		comTypeSearch(p);
		return dao.findAuditSiteBypagination(p,status,comType);
	}
	
	public List<SiteUser> findSiteUserBySite(Integer siteId){
		return dao.findSiteUserBySite(siteId);
	}
	
	private void comTypeSearch(Pagination<SiteUser> p) {
		if(p!=null&&SearchParamUtils.SITE_USER_SITE_COMPTYPE.equals(p.getConditionName())
				&&StringUtils.isNotBlank(p.getConditionContent())) {
			for(SelectItem item:ElianUtils.getCompTypeList()) {
				if(item.getValue().contains(p.getConditionContent())) {
					p.setConverterContent(item.getValue().toString());
					break;
				}
			}
		}
	}
	
	public List<SiteUser> findAuditSiteBySubArea(Pagination<SiteUser> p,List<Integer> areaCodes,Integer status,String comType){
		return dao.findAuditSiteBySubArea(p, areaCodes, status,comType);
	}
	
	@Resource
	public void setDao(SiteUserDao dao) {
		this.dao = dao;
	}
	
}
