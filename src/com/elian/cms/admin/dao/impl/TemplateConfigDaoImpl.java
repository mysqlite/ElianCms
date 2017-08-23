package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TemplateConfigDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.TemplateConfig;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("templateConfigDao")
public class TemplateConfigDaoImpl extends DaoImpl<TemplateConfig, Integer>
		implements TemplateConfigDao {
	private JdbcDao jdbcDao;

	public List<TemplateConfig> getByChannelId(Integer channelId,
			Pagination<TemplateConfig> pagination) {
		StringBuffer hql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();		
		hql.append("from TemplateConfig c where 1=1 ");	
		if(null!=channelId){
			hql.append(" and c.channel.id=?");
			params.add(channelId);
		}
		if(null != pagination)
			return pageByHql(hql.toString(), false, pagination, params.toArray());
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Object[]> getTempAreaIds(Integer tempId, Integer channelId) {	
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();				
		sql.append("select area_Id from T_TEMPLATE_SET where ( has_child_area is null or has_child_area =0 )");
		if(null != tempId){
			sql.append("	and  temp_id=? ");
			params.add(tempId);
		}
		if(null != channelId){
			sql.append("	and area_id not in");
			sql.append("		(");
			sql.append("			select area_id from T_TEMP_CONFIG where channel_id=? ");
			params.add(channelId);
			
			if(null != tempId){
				sql.append("			and  temp_id=? ");
				params.add(tempId);
			}
			sql.append("			)");
		}
		return jdbcDao.findSqlQuery(sql.toString(), params.toArray());		
	}

	@Resource
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public List<Object[]> getTempParentAreaIds(Integer tempId) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();				
		sql.append("select area_Id from T_TEMPLATE_SET where has_child_area=1 ");
		if(null != tempId){
			sql.append("	and  temp_id=? ");
			params.add(tempId);
		}		
		return jdbcDao.findSqlQuery(sql.toString(), params.toArray());		
	}

	public List<TemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId) {
		String hql="from TemplateConfig c where c.template.id=? and c.areaId=?";
		return findByHql(hql.toString(), false, tempId,areaId);		
	}

	public List<TemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer channelId) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();	
		sql.append("select * from t_temp_config where  1=1 ");
		if(null != tempId){
			sql.append(" and temp_id=? ");
			params.add(tempId);
		}
		if(null != channelId){
			sql.append("	and channel_id=? ");
			params.add(channelId);
		}
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<TemplateConfig> getAllConfig(Channel channelSet) {
		StringBuffer hql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		hql.append("from TemplateConfig c where 1=1 ");
		if(null!=channelSet){
			hql.append(" and c.channelSet.id=? ");
			params.add(channelSet.getId());
		}
		return findByHql(hql.toString(), false,params.toArray());		
	}

	public List<TemplateConfig> getByTempIdAreaId(Integer channelId,
			Integer tempId, Integer areaId) {
		String hql="from TemplateConfig c where c.channel.id=? and c.template.id=? and c.areaId=?";
		return findByHql(hql.toString(), false, channelId,tempId,areaId);		
	}	
}
