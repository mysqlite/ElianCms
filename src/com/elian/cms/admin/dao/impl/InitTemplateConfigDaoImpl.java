package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.InitTemplateConfigDao;
import com.elian.cms.admin.model.InitChannel;
import com.elian.cms.admin.model.InitTemplateConfig;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;

@Component("initTemplateConfigDao")
public class InitTemplateConfigDaoImpl extends DaoImpl<InitTemplateConfig, Integer>
		implements InitTemplateConfigDao {
	private JdbcDao jdbcDao;

	public List<InitTemplateConfig> getByInitChannelId(Integer initChannelId, 
			Pagination<InitTemplateConfig> pagination) {
		StringBuffer hql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();		
		hql.append("from InitTemplateConfig c where 1=1 ");	
		if(null!=initChannelId){
			hql.append(" and c.initChannel.id=?");
			params.add(initChannelId);
		}
		if(null != pagination)
			return pageByHql(hql.toString(), false, pagination, params.toArray());
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Object[]> getTempAreaIds(Integer tempId, Integer initChannelId) {	
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();				
		sql.append("select area_Id from T_TEMPLATE_SET where ( has_child_area is null or has_child_area =0 )");
		if(null != tempId){
			sql.append("	and  temp_id=? ");
			params.add(tempId);
		}
		if(null != initChannelId){
			sql.append("	and area_id not in");
			sql.append("		(");
			sql.append("			select area_id from T_INIT_TEMP_CONFIG where init_channel_id=? ");
			params.add(initChannelId);
			
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

	public List<InitTemplateConfig> getByTempIdAreaId(Integer tempId, Integer areaId) {
		String hql="from TemplateConfig c where c.template.id=? and c.areaId=?";
		return findByHql(hql.toString(), false, tempId,areaId);		
	}

	public List<InitTemplateConfig> findByTempIdAndChannelId(Integer tempId,
			Integer initChannelId) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();	
		sql.append("select * from t_init_temp_config where  1=1 ");
		if(null != tempId){
			sql.append(" and temp_id=? ");
			params.add(tempId);
		}
		if(null != initChannelId){
			sql.append("	and init_channel_id=? ");
			params.add(initChannelId);
		}
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<InitTemplateConfig> getAllConfig(InitChannel channelSet) {
		StringBuffer hql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		hql.append("from TemplateConfig c where 1=1 ");
		if(null!=channelSet){
			hql.append(" and c.channelSet.id=? ");
			params.add(channelSet.getId());
		}
		return findByHql(hql.toString(), false,params.toArray());		
	}

	public List<InitTemplateConfig> getByTempIdAreaId(Integer initChannelId,
			Integer tempId, Integer areaId) {
		String hql="from TemplateConfig c where c.channel.id=? and c.template.id=? and c.areaId=?";
		return findByHql(hql.toString(), false, initChannelId,tempId,areaId);		
	}
}
