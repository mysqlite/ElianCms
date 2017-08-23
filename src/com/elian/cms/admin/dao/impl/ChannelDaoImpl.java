package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ChannelDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("channelDao")
public class ChannelDaoImpl extends DaoImpl<Channel, Integer> implements
		ChannelDao {
	private String getSqlFunction(Integer siteId,Integer channelId){
		StringBuffer function=new StringBuffer();
		function.append("WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID, CHANNEL_NAME)");
		function.append("AS ( ");
		function.append("		SELECT C.CHANNEL_ID, C.PARENT_ID ,C.CHANNEL_NAME FROM T_CHANNEL C  ");
		function.append("			WHERE C.CHANNEL_ID ="+channelId+"	AND C.SITE_ID = "+siteId);
		function.append("		UNION ALL");
		function.append("		SELECT T.CHANNEL_ID, T.PARENT_ID ,T.CHANNEL_NAME FROM T_CHANNEL T ");
		function.append("		INNER JOIN FINDSUBBYPARENTID F ON  F.PARENT_ID =  T.CHANNEL_ID");
		function.append("	)");
		return function.toString();
	}
	/**
	 * 重复的方法
	 * @param siteId
	 * @param channelId
	 * @return
	 */
	private String getSqlFunction(Integer siteId,Object... channelId){
		StringBuffer function=new StringBuffer();
		function.append("WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID, CHANNEL_NAME)");
		function.append("AS ( ");
		function.append("		SELECT C.CHANNEL_ID, C.PARENT_ID ,C.CHANNEL_NAME FROM T_CHANNEL C  ");
		function.append("			WHERE C.CHANNEL_ID in (");
		for(int i=0;i<channelId.length;i++){
			if(0!=i) function.append(",");
			function.append(channelId[i]);
		}
		function.append(		")	AND C.SITE_ID = "+siteId);
		function.append("		UNION ALL");
		function.append("		SELECT T.CHANNEL_ID, T.PARENT_ID ,T.CHANNEL_NAME FROM T_CHANNEL T ");
		function.append("		INNER JOIN FINDSUBBYPARENTID F ON  F.PARENT_ID =  T.CHANNEL_ID");
		function.append("	)");
		return function.toString();
	}
	
	/**
	 * 获取相同内容、列表页、父栏目且未配置模板数据的栏目 
	 * @param siteId 站点ID
	 * @param channel 预copy配置的栏目
	 * @param isHaveConfig 是否有配置
	 * @param isSameLevel 是否为同级
	 * @return
	 */
	public List<Channel> findBySameTemplatChannel(Integer siteId,Channel channel,boolean isHaveConfig,boolean isSameLevel){
		String hql=" from Channel c where 1=1 ";
		List<Object> params=new ArrayList<Object>();
		if(siteId!=null) {
			hql+=" and c.site.id=? ";
			params.add(siteId);
		}
		if(channel!=null) {
			hql+=" and c.channelType=? ";
			params.add(channel.getChannelType());
			if(channel.getContentType()!=null) {
    			hql+=" and c.contentType=? ";
    			params.add(channel.getContentType());
			}
			if(channel.getChannelTemp()!=null) {
				hql+=" and c.channelTemp.id=? ";
				params.add(channel.getChannelTemp().getId());
			}
			if(channel.getContentTemp()!=null) {
				hql+=" and c.contentTemp.id=? ";
				params.add(channel.getContentTemp().getId());
			}
			if(isSameLevel) {
				hql+=" and c.parentId=? ";
				params.add(channel.getParentId());
			}
		}
		if(!isHaveConfig) {
			hql+=" and not exists(select 1 from TemplateConfig t where t.channel.id=c.id ) ";
		}
		return findByHql(hql, false, params.toArray());
	}
	
	public List<Channel> findByAll(Integer siteId, Integer parentId,boolean isDisable, boolean isNavigator, Pagination<Channel> p,boolean isFront) {
		StringBuffer hql = new StringBuffer("from Channel c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (parentId != null) {
			hql.append("  and c.parentId=?");
			params.add(parentId);
		}
		if (isDisable) {
			hql.append("  and c.disable= true");
		}
		if (isNavigator) {
			hql.append("  and c.navigetor= true");
		}
		if(isFront)
			hql.append(" order by c.parentId,c.frontSort,c.createTime");
		else
			hql.append(" order by c.sort,c.createTime");
		if (p != null) {
			p.setAlias("c");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}
	
	/**
	 * 获取静态页需要生成的子节点  不包含本身
	 */
	public List<Channel> findSubByParentIdForStaticPage(Integer parentId,
			Integer siteId) {
		if (parentId == null || siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		/*sql.append(" SELECT A.* FROM T_CHANNEL A ");
		sql.append(" START WITH A.PARENT_ID = ? AND A.IS_DISABLE = 1 AND A.SITE_ID = ? ");
		sql.append(" CONNECT BY PRIOR A.CHANNEL_ID = A.PARENT_ID ");
		sql.append(" ORDER BY A.SORT,A.CREATE_TIME");*/
		sql.append("with findSubByParentIdForStaticPage(CHANNEL_ID,title,keywords,description,site_id,model_id,channel_name,");
	    sql.append("		creater,create_time,parent_id,channel_sort,static_path,is_channel_static," );
		sql.append("		is_content_static,channel_temp_url,content_temp_url,checker,check_time," );
		sql.append("		is_disable,version,front_sort,channel_type,channel_content_type,IS_LEAF,is_navigetor) "); 
		sql.append("as  (" );
		sql.append("select * from T_CHANNEL where PARENT_ID = ? and is_disable=1 AND SITE_ID = ? union all " );
		sql.append("select t.* from T_CHANNEL as t inner join findSubByParentIdForStaticPage as c on t.PARENT_ID = c.CHANNEL_ID " );
		sql.append(") " ); 
		sql.append("select * from findSubByParentIdForStaticPage ORDER BY channel_SORT,CREATE_TIME");
		params.add(parentId);
		params.add(siteId);
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	/**
	 * 获取子节点对应的所有父节点 包含本身
	 */
	public List<Channel> findByAllParentForStaticPage(Integer channelId,
			Integer siteId) {
		if (channelId == null || siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		/*sql.append(" SELECT A.* FROM T_CHANNEL A ");
		sql.append(" START WITH A.CHANNEL_ID = ? AND A.IS_DISABLE = 1 AND A.SITE_ID = ? ");
		sql.append(" CONNECT BY PRIOR A.PARENT_ID = A.CHANNEL_ID ");*/
		sql.append("with findByAllParentForStaticPage(CHANNEL_ID,title,keywords,description,site_id,model_id,channel_name," + 
				"		creater,create_time,parent_id,channel_sort,static_path,is_channel_static," + 
				"		is_content_static,channel_temp_url,content_temp_url,checker,check_time," + 
				"		is_disable,version,front_sort,channel_type,channel_content_type,IS_LEAF,is_navigetor) " + 
				"as (" + 
				"select * from T_CHANNEL where CHANNEL_ID = ? AND IS_DISABLE = 1 AND SITE_ID = ?  union all " + 
				"select t.* from T_CHANNEL as t inner join findByAllParentForStaticPage as c on t.CHANNEL_ID=c.PARENT_ID " + 
				" ) " + 
				"select * from findByAllParentForStaticPage order by PARENT_ID asc,channel_sort asc ");
		
		return findBySql(sql.toString(), false, channelId, siteId);
	}
	
	/**
	 * 为生成静态内容页获取子节点对应的启用的父节点 包含本身
	 */
	public List<Channel> findNavParentForStaticPage(Integer channelId,
			Integer siteId) {
		if (channelId == null || siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		sql.append(" WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)  ");
		sql.append(" AS ( ");
		sql.append("     SELECT C.CHANNEL_ID, C.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL C  ");
		sql.append("      WHERE C.CHANNEL_ID = ? ");
		sql.append("        AND C.SITE_ID = ?   ");
		sql.append("     UNION ALL  ");
		sql.append("     SELECT T.CHANNEL_ID, T.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL T  ");
		sql.append(" INNER JOIN FINDSUBBYPARENTID F ON F.PARENT_ID = T.CHANNEL_ID  ");
		sql.append(" 	  WHERE T.IS_DISABLE = 1 ");
		sql.append(" ) ");
		sql.append(" SELECT C.* FROM T_CHANNEL C  ");
		sql.append(" 		   WHERE C.CHANNEL_ID IN (SELECT CHANNEL_ID FROM FINDSUBBYPARENTID)  ");
		return findBySql(sql.toString(), false, channelId, siteId);
	}

	/**
	 * 不包含本身  查子节点
	 */
	public List<Channel> findSubByParentId(Integer parentId, Integer siteId) {
		if (parentId == null || siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		/*sql.append(" SELECT A.* FROM T_CHANNEL A ");
		sql.append(" START WITH A.PARENT_ID = ? AND A.SITE_ID = ? ");
		sql.append(" CONNECT BY PRIOR A.CHANNEL_ID = A.PARENT_ID ");
		sql.append(" ORDER BY A.SORT,A.CREATE_TIME");*/
		sql.append("with findSubByParentId(CHANNEL_ID,title,keywords,description,site_id,model_id,channel_name," + 
				"		creater,create_time,parent_id,channel_sort,static_path,is_channel_static," + 
				"		is_content_static,channel_temp_url,content_temp_url,checker,check_time," + 
				"		is_disable,version,front_sort,channel_type,channel_content_type,IS_LEAF,is_navigetor) " + 
				"as " + 
				"(" + 
				"select * from T_CHANNEL where parent_id = ? and SITE_ID = ?  union all " + 
				"select t.* from T_CHANNEL as t inner join findSubByParentId as c on t.parent_id = c.CHANNEL_ID " + 
				") " + 
				"select * from findSubByParentId order by parent_id asc,channel_sort asc ");
		params.add(parentId);
		params.add(siteId);
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	/**
	 * 获取当前节点对应的所有启用子节点，包含本身,并且栏目类型为：内容类型
	 */
	public List<Channel> findAllSubByForStaticPage(Integer channelId,
			Integer siteId) {
		if (siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)  ");
		sql.append(" AS ( ");
		sql.append("     SELECT C.CHANNEL_ID, C.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL C  ");
		sql.append("      WHERE C.CHANNEL_ID = ?  ");
		params.add(channelId);
		sql.append("        AND C.SITE_ID = ?   ");
		params.add(siteId);
		sql.append("     UNION ALL  ");
		sql.append("     SELECT T.CHANNEL_ID, T.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL T  ");
		sql.append(" INNER JOIN FINDSUBBYPARENTID F ON T.PARENT_ID = F.CHANNEL_ID  ");
		sql.append(" ) ");
		sql.append(" SELECT C.* FROM T_CHANNEL C  ");
		sql.append(" 		   WHERE C.CHANNEL_ID IN (SELECT CHANNEL_ID FROM FINDSUBBYPARENTID)  ");
		sql.append("             AND C.IS_DISABLE = 1 ");
		sql.append("             AND C.CHANNEL_TYPE = '").append(
				ElianUtils.CHANNEL_CONTENT).append("' ");
		return findBySql(sql.toString(), false, params.toArray());
	}

	/**
	 * 包含本身 查子节点
	 */
	public List<Channel> findSubByParentId(List<Integer> parentIdList,
			Integer siteId) {
		if (siteId == null || parentIdList.isEmpty())
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)  ");
		sql.append(" AS ( ");
		sql.append("     SELECT C.CHANNEL_ID, C.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL C  ");
		sql.append("      WHERE C.CHANNEL_ID IN(  ");

		int i = 0;
		for (Integer id : parentIdList) {
			if (i != 0) {
				sql.append(" , ");
			}
			sql.append(" ? ");
			params.add(id);
			i++;
		}
		sql.append(" )");

		sql.append("        AND C.SITE_ID = ?   ");
		params.add(siteId);
		sql.append("     UNION ALL  ");
		sql.append("     SELECT T.CHANNEL_ID, T.PARENT_ID  ");
		sql.append("       FROM T_CHANNEL T  ");
		sql.append(" INNER JOIN FINDSUBBYPARENTID F ON T.PARENT_ID = F.CHANNEL_ID  ");
		sql.append(" ) ");
		sql.append(" SELECT C.* FROM T_CHANNEL C  ");
		sql.append(" 		   WHERE C.CHANNEL_ID IN (SELECT CHANNEL_ID FROM FINDSUBBYPARENTID)  ");
		return findBySql(sql.toString(), false, params.toArray());
		// sql.append(" SELECT A.* FROM T_CHANNEL A ");
		// if (!CollectionUtils.isEmpty(parentIdList)) {
		// sql.append(" START WITH A.CHANNEL_ID IN (");
		// int i = 0;
		// for (Integer id : parentIdList) {
		// if (i != 0) {
		// sql.append(" , ");
		// }
		// sql.append(" ? ");
		// params.add(id);
		// i++;
		// }
		// sql.append(" )");
		// }
		// sql.append(" AND A.SITE_ID = ? ");
		// sql.append(" CONNECT BY PRIOR A.CHANNEL_ID = A.PARENT_ID ");
		// params.add(siteId);
		// return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Channel> findNameAndParentId(String typeName, Integer parentId,
			Integer siteId) {
		StringBuffer hql = new StringBuffer("from Channel c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(typeName)) {
			hql.append("  and c.channelName=?");
			params.add(typeName);
		}
		if (parentId != null) {
			hql.append("  and c.parentId=?");
			params.add(parentId);
		}
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Channel> findIsLeafBySub(List<Integer> subIdList, Integer siteId) {
		if (siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT C.* ");
		sql.append("   FROM T_CHANNEL C ");
		sql.append("  WHERE     1 = 1 ");
		if (!CollectionUtils.isEmpty(subIdList)) {
			sql.append("        AND NOT EXISTS ");
			sql.append("               (SELECT 1 ");
			sql.append("                  FROM T_CHANNEL CH ");
			sql
					.append("                 WHERE     CH.PARENT_ID = C.CHANNEL_ID ");
			sql.append("                       AND CH.CHANNEL_ID NOT IN ( ");
			int i = 0;
			for (Integer id : subIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" ))");

			sql.append("        AND C.CHANNEL_ID IN (SELECT C.PARENT_ID ");
			sql.append("                               FROM T_CHANNEL C ");
			sql
					.append("                              WHERE C.CHANNEL_ID IN ( ");
			i = 0;
			for (Integer id : subIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" ))");
		}
		sql.append("        AND C.SITE_ID = ? ");
		params.add(siteId);
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	/**
	 * 根据父ID获取所有启用的下一级子节点
	 */
	public List<Channel> findAllSubByParentId(Integer channelId,
			Integer siteId) {
		if (channelId == null || siteId == null)
			return null;
		StringBuffer sql = new StringBuffer();
		sql.append("  WITH FINDSUBBYPARENTID(CHANNEL_ID, PARENT_ID)   ");
		sql.append("  AS (  ");
		sql.append("      SELECT C.CHANNEL_ID, C.PARENT_ID   ");
		sql.append("        FROM T_CHANNEL C   ");
		sql.append("       WHERE C.PARENT_ID = ? ");
		sql.append("         AND C.SITE_ID = ?  ");
		sql.append("      UNION ALL   ");
		sql.append("      SELECT T.CHANNEL_ID, T.PARENT_ID   ");
		sql.append("        FROM T_CHANNEL T   ");
		sql.append("  INNER JOIN FINDSUBBYPARENTID F ON F.CHANNEL_ID = T.PARENT_ID   ");
		sql.append("        WHERE T.IS_DISABLE = 1  ");
		sql.append("  )  ");
		sql.append("  SELECT C.* FROM T_CHANNEL C   ");
		sql.append("             WHERE C.CHANNEL_ID IN (SELECT CHANNEL_ID FROM FINDSUBBYPARENTID)   ORDER BY C.CHANNEL_SORT ASC ");
		return findBySql(sql.toString(), false, channelId, siteId);
	}
	
	/**
	 * 获取站点下的首页
	 */
	public List<Channel> findIndexChannelByType(String channelType,
			Integer siteId) {
		StringBuffer hql = new StringBuffer("from Channel c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(channelType)) {
			hql.append("  and c.channelType=?");
			params.add(channelType);
		}
		hql.append("  and c.disable = true order by c.sort,c.createTime asc");
		return findByHql(hql.toString(), false, params.toArray());
	}
	
	/**
	 * 根据特殊页模板获取特殊页栏目
	 */
	public List<Channel> findChannelByTemp(String tempName, Integer siteId) {
		StringBuffer hql = new StringBuffer("from Channel c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (StringUtils.isNotBlank(tempName)) {
			hql.append("  and c.typeUrl=?");
			params.add(tempName);
		}
		hql.append(" and c.channelType = 'special' and c.disable = true");
		hql.append(" order by c.sort,c.createTime asc");
		return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Channel> findFrontList(Integer siteId, Pagination<Channel> pagination) {
		StringBuffer hql = new StringBuffer("from Channel c where c.disable= true and c.navigetor= true ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}		
		hql.append(" order by c.frontSort,c.createTime asc");
		if (pagination != null) {
			pagination.setAlias("c");
			return pageByHql(hql.toString(), false, pagination, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());		
		}
	public List<Channel> findByParentIdSiteId(int pid,int siteId,Boolean isDisable){
		String hql=null;
		if(null==isDisable) hql=" from Channel c where c.parentId=? and c.site.id=? order by c.sort,c.createTime";
		else hql=" from Channel c where c.parentId=? and c.site.id=? and c.disable="+isDisable+"  order by c.sort,c.createTime";
		List<Object> params = new ArrayList<Object>();
		params.add(pid);
		params.add(siteId);
		return findByHql(hql, true, params.toArray());
	}

	public Channel findByEntityId(Integer siteId, String actionName,
			Integer entityId) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select * from t_channel where channel_id=(select top 1 channel_id from t_control where 1=1");
		if(null!=siteId){
			sql.append(" 		and site_id=?");
			params.add(siteId);
		}
		if(null!=actionName){
			sql.append(" 	    and action_name=?");
			params.add(actionName);
		}
		if(null!=entityId){
			sql.append(" 	    and table_key_id=?");
			params.add(entityId);
		}
		sql.append(")");
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}

	public List<Channel> getParentChannelList(Integer siteId, Integer channelId) {
		String function=getSqlFunction(siteId,channelId);		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from t_channel where channel_id in");
		sql.append("	(");
		sql.append("		select channel_id from FINDSUBBYPARENTID");
		sql.append("	)");
		List<Object> params=new ArrayList<Object>();
		if(null != siteId){
			sql.append("  and site_id=?");
			params.add(siteId);
		}			
		sql.append("		order by channel_id asc ");
		return findByRecursionSql(sql.toString(), false,function, params.toArray());
	}
	/**
	 * 重复的方法
	 */
	public List<Channel> getParentChannelList(Integer siteId, Object... channelId) {
		String function=getSqlFunction(siteId,channelId);		
		StringBuffer sql=new StringBuffer();
		sql.append("select * from t_channel where channel_id in");
		sql.append("	(");
		sql.append("		select channel_id from FINDSUBBYPARENTID");
		sql.append("	)");
		List<Object> params=new ArrayList<Object>();
		if(null != siteId){
			sql.append("  and site_id=?");
			params.add(siteId);
		}			
		sql.append("		order by channel_id asc ");
		return findByRecursionSql(sql.toString(), false,function, params.toArray());
	}
	
	public List<Channel> findAllParent(Integer siteId, Boolean isDisable) {		
		String hql="from Channel c where c.site.id=? and c.channelType=? and c.disable=?";
		return findByHql(hql, false, siteId,ElianUtils.CHANNEL_PARENT,isDisable);
	}

	public List<Channel> find(Integer siteId, String channelType,
			Integer modelId, String contentType, boolean isDisable) {
		String hql="from Channel c where c.site.id=? and c.channelType=? and c.model.id=? and c.contentType=? and c.disable=?";
		return findByHql(hql, false, siteId,channelType,modelId,contentType,isDisable);
	}
	public List<Channel> getBySiteId(Integer siteId) {
		StringBuffer hql=new StringBuffer();
		hql.append("from Channel c where 1=1");
		List<Object> param=new ArrayList<Object>();
		if(null != siteId){
			hql.append(" and c.site.id=?");	
			param.add(siteId);
		}
		return findByHql(hql.toString(), false, param.toArray());
	}
	public List<Channel> findAllParent(Integer siteId, Integer modelId) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public List<Channel> getParentChannelList(Integer siteId,
//			List<Integer> channelIds, boolean isDisable) {
//		String function=getSqlFunction(siteId,channelIds.toArray());		
//		StringBuffer sql=new StringBuffer();
//		sql.append("select * from t_channel where channel_id in");
//		sql.append("	(");
//		sql.append("		select channel_id from FINDSUBBYPARENTID");
//		sql.append("	)");
//		List<Object> params=new ArrayList<Object>();
//		if(null != siteId){
//			sql.append("  and site_id=?");
//			params.add(siteId);
//		}			
//		sql.append("		order by channel_id asc ");
//		return findByRecursionSql(sql.toString(), false,function, params.toArray());
//	}
}
