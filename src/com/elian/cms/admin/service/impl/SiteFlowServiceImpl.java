package com.elian.cms.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.SiteFlowDao;
import com.elian.cms.admin.dto.FlowBeanDto;
import com.elian.cms.admin.model.SiteFlow;
import com.elian.cms.admin.service.SiteFlowService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.DateFormatUtils;
import com.elian.cms.syst.util.ValidateUtils;
@Component("siteFlowService")
public class SiteFlowServiceImpl extends ServiceImpl<SiteFlowDao, SiteFlow, Integer>
		implements SiteFlowService {
	private JdbcService jdbcService=null;
	
	@Resource
	public void setDao(SiteFlowDao dao) {
		this.dao = dao;
	}
	
	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate,
			String sessionId, String page) {
		return dao.findUniqueByProperties(siteId, accessDate, sessionId, page);
	}

	public List<SiteFlow> findByPage(Pagination<SiteFlow> p, Integer siteId){
		return dao.findByPage(p, siteId);
	}
	
	@SuppressWarnings("unchecked")
	public int freshCacheToDB(Ehcache cache) {
		int count = 0;
		List<FlowBeanDto> list = cache.getKeys();
		for (FlowBeanDto bean : list) {
			Element element = cache.get(bean);
			if (element == null) {
				return count;
			}
			SiteFlow siteFlow = (SiteFlow) element.getValue();
			if (siteFlow.getId() == null&& siteFlow.getSessionId() != null) {
				if(siteFlow.getAccessPage()!=null&&siteFlow.getAccessPage().length()>255)
					siteFlow.setAccessPage(siteFlow.getAccessPage());
				if(siteFlow.getRefererSite()!=null&&siteFlow.getRefererSite().length()>255)
					siteFlow.setRefererSite(siteFlow.getRefererSite());
				if(siteFlow.getRefererPage()!=null&&siteFlow.getRefererPage().length()>1024)
					siteFlow.setRefererPage(siteFlow.getRefererPage());
				if(siteFlow.getRefererKeyword()!=null&&siteFlow.getRefererKeyword().length()>255)
					siteFlow.setRefererKeyword(siteFlow.getRefererKeyword());
				dao.save(siteFlow);
				System.out.println("保存流量统计");
			}
		}
		return count;
	}

	public SiteFlow save(Integer siteId, String ip, String page,
			String sessionId) {
		SiteFlow siteFlow = new SiteFlow();
		Date now = new Timestamp(System.currentTimeMillis());
		siteFlow.setSiteId(siteId);
		siteFlow.setAccessIp(ip);
		siteFlow.setAccessPage(page);
		siteFlow.setAccessTime(now);
		siteFlow.setAccessDate(DateFormatUtils.formatDate(now));
		siteFlow.setSessionId(sessionId);
		dao.save(siteFlow);
		return siteFlow;
	}

	public int getSiteFlowCount(Integer siteId) {
		String sql="select count( distinct (sf.session_id)) from T_SITE_FLOW sf where sf.site_id=? ";
		Object[] result=jdbcService.findSqlQuery(sql, siteId).toArray();
		if(result!=null)
			return (Integer) result[0];
		return 0;
	}
	
	/***
	 * 特殊站点入口访问
	 */
	public int getRefererSite(Integer siteId,String refererSite,String time) {
		String sql=" select count(*) from t_site_flow sf where 1=1 ";
		List<Object> params=new ArrayList<Object>(2);
	if(siteId!=null) {
		sql+=" and sf.site_id=? ";
		params.add(siteId);
	}
	if(refererSite!=null) {
		sql+=" and referer_site like '%"+ValidateUtils.isSql(refererSite)+"%'  ";
	}
	if(time!=null) {
		sql+="  and convert(varchar(10),sf.access_date,120)=? ";
		params.add(time);
	}
	Object[] result=jdbcService.findSqlQuery(sql, params.toArray()).toArray();
	if(result!=null)
		return Integer.parseInt(result[0].toString());
	return 0;
	}
	/**IP数*/
	public int getSiteIP(Integer siteId,String time) {
		String sql=" select count(distinct (sf.access_ip)) as IP from T_SITE_FLOW sf where 1=1 ";
		List<Object> params=new ArrayList<Object>(2);
	if(siteId!=null) {
		sql+=" and sf.site_id=? ";
		params.add(siteId);
	}
	if(time!=null) {
		sql+="  and convert(varchar(10),sf.access_date,120)=? ";
		params.add(time);
	}
	Object[] result=jdbcService.findSqlQuery(sql, params.toArray()).toArray();
	if(result!=null)
		return Integer.parseInt(result[0].toString());
	return 0;
	}
	/**访客数*/
	public int getSiteUV(Integer siteId,String time) {
		String sql=" select count(distinct (sf.session_id)) as UV from T_SITE_FLOW sf where 1=1 ";
			List<Object> params=new ArrayList<Object>(2);
		if(siteId!=null) {
			sql+=" and sf.site_id=? ";
			params.add(siteId);
		}
		if(time!=null) {
			sql+="  and convert(varchar(10),sf.access_date,120)=? ";
			params.add(time);
		}
		Object[] result=jdbcService.findSqlQuery(sql, params.toArray()).toArray();
		if(result!=null)
			return Integer.parseInt(result[0].toString());
		return 0;
	}
	/**浏览量*/
	public int getSitePV(Integer siteId,String time) {
		String sql="select count(*) as pv from T_SITE_FLOW sf where 1=1";
		List<Object> params=new ArrayList<Object>(2);
		if(siteId!=null) {
			sql+=" and sf.site_id=? ";
			params.add(siteId);
		}
		if(time!=null) {
			sql+="  and convert(varchar(10),sf.access_date,120)=? ";
			params.add(time);
		}
		Object[] result=jdbcService.findSqlQuery(sql, params.toArray()).toArray();
		if(result!=null)
			return Integer.parseInt(result[0].toString());
		return 0;
	}
}