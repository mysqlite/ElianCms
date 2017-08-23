package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.InformationDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Information;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;

@Component("informationDao")
public class InformationDaoImpl extends DaoImpl<Information, Integer> implements
		InformationDao {
	public List<Information> findByAll(Integer siteId, Integer channelId,
			Pagination<Information> p) {
		StringBuffer hql = new StringBuffer("from Information c where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (siteId != null) {
			hql.append("  and c.site.id=?");
			params.add(siteId);
		}
		if (channelId != null) {
			hql.append("  and c.channel.id=?");
			params.add(channelId);
		}
		hql.append(" order by c.sort asc");
		if (p != null) {
			p.setAlias("c");
			return pageByHql(hql.toString(), false, p, params.toArray());
		}
		else
			return findByHql(hql.toString(), false, params.toArray());
	}

	public List<Information> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_INFORMATION I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE     C.TABLE_KEY_ID = I.INFO_ID ");
		if (!CollectionUtils.isEmpty(contentIdList)) {
			sql.append("                   AND C.CTRL_ID IN ( ");
			int i = 0;
			for (Integer id : contentIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" )");
		}
		if (siteId != null) {
			sql.append(" AND C.SITE_ID = ?");
			params.add(siteId);
		}
		sql.append(" )");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Information> findImgList(Integer channelId, Integer siteId,Integer size) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT TOP ");
		sql.append(size==null?10:size);
		sql.append(		" I.* ");
		sql.append("   FROM T_INFORMATION I left join T_CONTROL C  on i.info_id=c.table_key_id ");
		sql.append("   WHERE I.has_img!=2 and I.INFO_IMg IS NOT NULL AND I.INFO_IMg !=''");

		if (channelId!=null) {
			sql.append("               AND C.CHANNEL_ID=? ");	
			params.add(channelId);
		}
		if (siteId != null) {
			sql.append(" 				AND C.SITE_ID = ? ");
			params.add(siteId);
		}	
		addStaticSql(sql, params);
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Information> getPowerPointList(Site site,Channel channnelParent, Integer size){
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select top "+size+" * from T_INFORMATION I LEFT JOIN T_CONTROL C  on i.info_id=c.table_key_id where I.has_img=2 ");
		addStaticSql(sql, params);
		if(site!=null){
			sql.append("							and c.site_id=? ");
			params.add(site.getId());
		}
		if(channnelParent!=null){
			sql.append("							and c.channel_id in ( select channel_id from t_channel where parent_id=? or channel_id=?");
			params.add(channnelParent.getId());
			params.add(channnelParent.getId());
			if(site!=null){
				sql.append("							and site_id=? ");
				params.add(site.getId());
			}
			sql.append("													)");
		}
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<Information> getVideoList(Site site, Channel channnelParent, int size) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select top "+size+" * from T_INFORMATION I LEFT JOIN T_CONTROL C  on i.info_id=c.table_key_id where I.has_img=3 ");
		addStaticSql(sql, params);
		if(site!=null){
			sql.append("							and c.site_id=? ");
			params.add(site.getId());
		}
		if(channnelParent!=null){
			sql.append("							and c.channel_id in ( select channel_id from t_channel where parent_id=? or channel_id=?");
			params.add(channnelParent.getId());
			params.add(channnelParent.getId());
			if(site!=null){
				sql.append("							and site_id=? ");
				params.add(site.getId());
			}
			sql.append("													)");
		}
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public List<Information> findByParentChannelId(Integer siteId,Integer parentChannelId,
			Integer state, Boolean isSatatic) {
		
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select TOP 1 * from T_INFORMATION I  left join T_CONTROL C on I.info_id=c.table_key_id where 1=1 ");
		if (siteId!=null) {
			sql.append("               and C.site_id=? ");	
			params.add(siteId);
		}		
		if(state!=null){
			sql.append("               and C.status=? ");	
			params.add(state);
		}
		if(isSatatic!=null){
			addStaticSql(sql, params);
		}
		if(parentChannelId!=null){
			sql.append("               and C.channel_id in(select channel_id from t_channel where channel_id=? or parent_id=?");
			params.add(parentChannelId);
			params.add(parentChannelId);
		}
		if (siteId!=null) {
			sql.append("               and site_id=? ");	
			params.add(siteId);
		}		
		
		sql.append(						")");
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public Information findStaticSpageData(Integer siteId, Integer channelId,
			Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select * from T_INFORMATION I LEFT JOIN T_CONTROL C on I.info_id=c.table_key_id where 1=1 ");
		addStaticSql(sql, params);
		if (siteId!=null) {
			sql.append("               and c.site_id=? ");	
			params.add(siteId);
		}
		if(channelId!=null){
			sql.append("               and C.channel_id=? ");
			params.add(channelId);
		}
		if(hasImg!=null){
			if(hasImg)
				sql.append(" AND has_img=1 AND (I.INFO_IMg IS NOT NULL AND I.INFO_IMg !='')");
			else
				sql.append(" AND has_img=1 AND (I.INFO_IMg IS NULL OR I.INFO_IMg ='')");
								
		}
		addOrderSql(sql, "c");
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}
	
	private void addStaticSql(StringBuffer strBuffer,List<Object> params ){
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id desc ");
	}
}
