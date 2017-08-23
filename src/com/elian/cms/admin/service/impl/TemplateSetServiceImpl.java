package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.TemplateSetDao;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.admin.model.TemplateSet;
import com.elian.cms.admin.service.TemplateConfigService;
import com.elian.cms.admin.service.TemplateSetService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.SpringUtils;

@Component("templateSetService")
public class TemplateSetServiceImpl extends
		ServiceImpl<TemplateSetDao, TemplateSet, Integer> implements
		TemplateSetService {
	
//	private JdbcService jdbcService;
	private TemplateConfigService templateConfigService;

	public List<TemplateSet> findByAll(Integer tempId,
			Pagination<TemplateSet> pagination) {
		return dao.findByAll(tempId, pagination);
	}

	@Resource
	public void setDao(TemplateSetDao dao) {
		this.dao = dao;
	}
	
//	@Resource
//	public void setJdbcService(JdbcService jdbcService) {
//		this.jdbcService = jdbcService;
//	}

	@Resource
	public void setTemplateConfigService(TemplateConfigService templateConfigService) {
		this.templateConfigService = templateConfigService;
	}

	public List<Integer> getAreaParentIdList(Integer tempId) {
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(0);
		JdbcService jdbcService=SpringUtils.getBean("jdbcService");
		if(null==tempId) return ids;
		List<Object[]> result=jdbcService.findSqlQuery("select area_id from T_TEMPLATE_SET where temp_id=?", tempId);
		if(null==result) return ids;
		for(Object obj: result.toArray()){
			if(null != obj) ids.add((Integer) obj);
		}
		return ids;
	}

	public TemplateSet getByTempIdAndAreaId(Integer tempId, Integer areaId) {		
		return dao.getByTempIdAndAreaId(tempId,areaId);
	}

	public List<Integer> getAreaIdList(Integer tempId,Integer maxareaid) {
		List<Integer> extendsIds=getAreaParentIdList(tempId);
		List<Integer> ids=new ArrayList<Integer>();
		for(int i=1;i<=maxareaid;i++){
			if(!extendsIds.contains(i))
				ids.add(i);
		}
		return ids;
	}

	public List<TemplateSet> getTempParentAreas(Integer tempId) {		
		return dao.getTempParentAreas(tempId);
	}

	public List<TemplateSet> deleteById(Collection<Integer> pks) {
		List<Integer> deleteIds=new ArrayList<Integer>();
		for(Integer pk:pks){
			TemplateSet s=dao.get(pk);
			List<TemplateConfig> list=templateConfigService.getByTempIdAreaId(s.getTempId(),s.getAreaId());
			if(CollectionUtils.isEmpty(list))
				deleteIds.add(pk);
		}
		return super.deleteById(deleteIds);
	}
}
