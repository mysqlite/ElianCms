package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DoctorDao;
import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("doctorDao")
public class DoctorDaoImpl extends DaoImpl<Doctor, Integer> implements
		DoctorDao {

	public List<Doctor> findByPage(Pagination<Doctor> p,Integer hospId,Integer departId){
		StringBuffer hql=new StringBuffer();
		hql.append(" from Doctor d where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(hospId!=null) {
			hql.append(" and exists (select 1 from Department de where de.id=d.dept.id  and de.hospital.id=?) ");
			params.add(hospId);
		}
		if(departId!=null) {
			hql.append(" and d.dept.id=? ");
			params.add(departId);
		}
		hql.append(" order by d.dept.id,d.doctSort,d.id asc");
		if(p!=null){
			p.setAlias("d");
			return pageByHql(hql.toString(), false, p,params.toArray());
		}
		else
			return findByHql(hql.toString(), false,params.toArray());
	}
	
	public List<Doctor> findByContentId(List<Integer> contentIdList) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT D.* ");
		sql.append("   FROM T_DOCTOR D ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE     C.TABLE_KEY_ID = D.DOCT_ID ");
		sql.append("               AND     C.ACTION_NAME = 'doctor_c' ");
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
		sql.append(" )");
		return findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<Doctor> findPageByHosp(Pagination<Doctor> p,Integer hospId){
		StringBuffer hql=new StringBuffer();
		hql.append(" from Doctor d where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(hospId!=null) {
			hql.append("and exists (select 1 from Department de where de.id=d.dept.id  and de.hospital.id=?)");
			params.add(hospId);
		}
		hql.append(" order by d.dept.id,d.doctSort asc");
		if(p!=null){
			p.setAlias("d");
			return pageByHql(hql.toString(), false, p,params.toArray());
		}
		else
			return findByHql(hql.toString(), false,params.toArray());
	}
	
	public List<Doctor> findList(Site site, Channel channel, Integer status,
			Boolean isStatic, Boolean hasImg, Integer size) {		
		StringBuffer sql = new StringBuffer();		
		List<Object> params = new ArrayList<Object>();
		sql.append(" select top ");sql.append(size==null?10:size);sql.append(" * from T_DOCTOR where 1=1 ");
		if(null!=hasImg){
			if(hasImg)
				sql.append("		 and doct_img is not null and doct_img != '' ");
			else
				sql.append("		 and doct_img is null or doct_img ='' ");
		}		
		sql.append("				 and doct_id in( select table_key_id from t_control c where 1=1 ");
		if(null!=site){
			sql.append("									and c.site_id=?");
			params.add(site.getId());
		}
		if(null!=channel){
			sql.append("									and c.channel_id=?");
			params.add(channel.getId());
		}

		if(null!=isStatic){
			addStaticSql(sql, params);
		}		
		sql.append("								)");
		return  findBySql(sql.toString(), false, params.toArray());
	}
	
	public List<Doctor> findByHospAndMenu(Integer hospId,Integer menuId){
		String hql=" from Doctor d where exists(select 1 from TbDoctor t where t.hospId=? and t.menuId=? and d.rid=t.id)";
		List<Object> paramList=new ArrayList<Object>();
		paramList.add(hospId);
		paramList.add(menuId);
		if(hospId!=null&&menuId!=null) {
			return findByHql(hql, false, paramList.toArray());
		}
		return null;
	}

	public Doctor findStaticSpageData(Integer siteId, Integer channelId,Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select TOP 1 * from T_DOCTOR where doct_id in( select table_key_id from t_control c where 1=1 ");
		addStaticSql(sql, params);	
		if (siteId!=null) {
			sql.append(" and site_id=? ");	
			params.add(siteId);
		}		
		if(channelId!=null){
			sql.append(" and channel_id=? ");
			params.add(channelId);
		}		
		sql.append(")");
		if(null != hasImg){
			if(hasImg)
				sql.append("		 and ( doct_img is not null and doct_img != '' ) ");
			else
				sql.append("		 and ( doct_img is null or doct_img ='' ) ");
		}
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}

	public List<Doctor> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		StringBuffer sql = new StringBuffer();		
		List<Object> params = new ArrayList<Object>();
		sql.append(" select top ");sql.append(size);sql.append(" * from T_DOCTOR D left join T_CONTROL C  on D.DOCT_ID=c.table_key_id   where ");
		sql.append("		 D.DOCT_IMG IS NOT NULL AND D.DOCT_IMG  != '' ");
		sql.append("			and D.DOCT_ID in( select C1.table_key_id from t_control C1 where 1=1 ");
		if(null!=siteId){
			sql.append("									and  C1.site_id=?");
			params.add(siteId);
		}
		if(null!=channelId){
			sql.append("									and  C1.channel_id=?");
			params.add(channelId);
		}
		addStaticSql(sql, params);
		sql.append("							)");
		if(null!=channelId){
			sql.append("									and C.CHANNEL_ID=? ");
			params.add(channelId);
		}		
		addOrderSql(sql,"C");
		return  findBySql(sql.toString(), false, params.toArray());
	}
	
	private void addStaticSql(StringBuffer strBuffer,List<Object> params ){
		strBuffer.append("  and( (c.status = ? and c.static_status =?) or (c.status=? and c.static_status=?) )");
		params.add(ElianUtils.CONTENT_STATUS_3);
		params.add(ElianUtils.STATIC_STATUS_1);
		params.add(ElianUtils.CONTENT_STATUS_2);
		params.add(ElianUtils.STATIC_STATUS_2);	
	}
	
	private void addOrderSql(StringBuffer strBuffer,String tableName){
		strBuffer.append(" order by "+tableName+".top_most desc, "+tableName+".ctrl_sort, "+tableName+".ctrl_id ");
	}
/*
	public List<Doctor> search(String criteria, Pagination<Doctor> pagination) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * FROM T_DOCTOR D WHERE 1=1 ");
		if(StringUtils.isNotBlank(criteria))
			sql.append(criteria);
		sql.append(" ORDER BY D.HITS DESC,D.DOCT_SORT,D.DOCT_ID");
		return pageBySql(sql.toString(), false, pagination);
	}*/

	public List<Doctor> search(String criteria, List<Object> params,
			String order, Pagination<Doctor> pagination) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_DOCTOR D WHERE 1=1 ");
		if (StringUtils.isNotBlank(criteria))
			sql.append(criteria);
		if (StringUtils.isBlank(order))
			sql.append(" ORDER BY D.HITS DESC,D.DOCT_SORT,D.DOCT_ID");
		else
			sql.append(order);
		if (CollectionUtils.isEmpty(params))
			return pageBySql(sql.toString(), false, pagination);
		return pageBySql(sql.toString(), false, pagination, params);
	}

	public List<Doctor> findList(List<Hospital> hospitalList, int size) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT").append(" top ").append(size).append(" * FROM T_DOCTOR D WHERE 1=1 ");
		if(!CollectionUtils.isEmpty(hospitalList)){
			sql.append(" and exists( select 1 from T_HOSP_DEPARTMENT m where m.depa_id=D.depa_id ");
			sql.append("	and m.hosp_id in(");
			for(int i=0,length=hospitalList.size();i<length;i++){
				sql.append(		hospitalList.get(i).getId());
				if(i!=length-1)
					sql.append(",");
			}
			sql.append("					))");
		}
		return findBySql(sql.toString(), false);
	}
}
