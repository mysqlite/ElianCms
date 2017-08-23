package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.SiteDao;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
@Component("siteDao")
public class SiteDaoImpl extends DaoImpl<Site, Integer> implements SiteDao {
	
	public List<Site> findByAll(Pagination<Site> p) {
		if(p!=null) {
		p.setAlias("g");
		return pageByHql("from Site g order by g.siteSort asc,g.id desc",
				true, p);
		}
		else
			return findByHql("from Site g order by g.siteSort asc,g.id desc", false);
			
	}
	
	public long findImgSize(Integer siteId) {
		String sql="select sum(file_size) size from t_images where site_id=?";
		JdbcDao jdbcDao=SpringUtils.getBean("jdbcDao");
		List<Map<String, Object>> noInfos=jdbcDao.findSqlMapQuery(sql.toString(),siteId);
		if(noInfos.get(0).get("size")!=null)
			return Long.parseLong(noInfos.get(0).get("size").toString());
		else
			return 0;
	}
	/**
	 * 根据组织类型查询所有站点
	 * @param p
	 * @param compType
	 * @return
	 */
	public List<Site> findAllSiteByCompType(Pagination<Site> p,String compType){
		StringBuffer hql=new  StringBuffer("from Site s where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(compType)) {
			hql.append(" and  s.comType=? ");
			paramList.add(compType);
		}
		hql.append(" and s.comType<>?");
		paramList.add(ElianUtils.COMP_TYPE_MAIN);
		if(p!=null)
			return pageByHql(hql.toString(), true, p, paramList.toArray());
		else 
			return findByHql(hql.toString(), true, paramList.toArray());
	}

	public List<Site> findHospSiteByAreaCode(Pagination<Site> p,List<Integer> areaCodes){
		StringBuffer hql=new StringBuffer(" from Site  s where s.comId in( select h.id from Hospital h where h.areaId in ( ");
		 for (int i = 0,len= areaCodes.size(); i < len; i++) {
			 if(i==0)
				 hql.append(areaCodes.get(i)+",");
			 else if(i==len-1)
				 hql.append(areaCodes.get(i));
		   }
		 hql.append("))  and  s.comType=? ");
		 
		 List<Object> paramList = new ArrayList<Object>();
			paramList.add(ElianUtils.COMP_TYPE_HOSP);
		if(p!=null) {
			p.setAlias("s");
			return pageByHql(hql.toString(), true, p, paramList.toArray());
		}
		else 
			return findByHql(hql.toString(), true, paramList.toArray());
	}
	
	/**
	 *  根据区域id查询当前分站区域下的所有企业站点
	 * @param p
	 * @param areaId
	 * @return
	 */
	public List<Site> findCompSiteByArea(Pagination<Site> p,Integer areaId){
		
		return null;
	}
	
	public List<Site> findByAll(Boolean existAction, Pagination<Site> p) {
		StringBuffer hql = new StringBuffer("from Site s order by s.siteSort asc,s.id desc");
		List<Object> params = new ArrayList<Object>();
		if (p != null) {
			p.setAlias("s");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	
	public List<Site> findByUser(Integer userId) {
		if (userId == null || userId < 1)
			return null;
		String hql = " from Site s where 1=1 and exists ( select 1 from SiteUser su where su.site.id = s.id and su.user.id = ?) and s.status = 1";
		return findByHql(hql.toString(), false, userId);
	}
	
	public Site getSiteByCompType(String compType) {
		String hql="from Site s where s.comType=?";
		return findByHqlFirst(hql, false, compType);
	}
	
	/**
	 * 根据区域ID找分站，内容列表页推荐功能用到，RecommendAction
	 */
	public List<Site> findSubSiteByArea(Integer areaId) {
		if (areaId == null || areaId < 1)
			return null;
		String hql = " from Site s where s.comType='substation' and exists( select 1 from Substation su where su.id= s.comId and su.areaId = ? ) ";
		return findByHql(hql.toString(), false, areaId);
	}

	public Site findByByComp(String compType, Integer compId) {
		String hql = "select * from t_site where com_id=? ";
		if (StringUtils.isNotBlank(compType)) {
			 hql += " and com_type='" + compType + "'";
		}
		List<Site> siteList=findBySql(hql.toString(),false,compId);
		if(!CollectionUtils.isEmpty(siteList))
			return siteList.get(0);
		return null;
	}
}
