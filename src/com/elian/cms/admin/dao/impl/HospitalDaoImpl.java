package com.elian.cms.admin.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.HospitalDao;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.reg.dto.HospitalNoScore;
import com.elian.cms.reg.dto.WorkTopDeparment;
import com.elian.cms.reg.dto.WorkTopDoctor;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.SpringUtils;
import com.elian.cms.syst.util.StringUtils;
import com.elian.cms.syst.util.ValidateUtils;

@Component("hospitalDao")
public class HospitalDaoImpl extends DaoImpl<Hospital, Integer> implements
		HospitalDao {
	public List<Hospital> findBypage(Pagination<Hospital> p,List<Integer> areaCodes){
		if(areaCodes!=null&&areaCodes.size()>0&&p!=null)
			return findByAreaCode(p, areaCodes,null);
		else {
			String hql=" from Hospital h order by  h.hospName asc,h.shortName asc";
			if(p!=null)
			    return pageByHql(hql, false, p);
			else
				return findByHql(hql, false);
		}
	}
	
	public List<Hospital> findByAreaCode(Pagination<Hospital> p,List<Integer> areaCodes,String hospName){
       StringBuffer hql=new StringBuffer(" from Hospital h where exists(select 1 from Area  a where h.areaId=a.areaCode and a.areaCode  in(") ;
		 if(areaCodes!=null&&areaCodes.size()>1)
          for (int i = 0,len= areaCodes.size(); i < len; i++) {
			 if(i==0||i<len-1)
				 hql.append(areaCodes.get(i)+",");
			 else
				 hql.append(areaCodes.get(i));
		   }
		 else if(areaCodes!=null&&areaCodes.size()==1)
		    hql.append(areaCodes.get(0));
		hql.append(")) ").append(StringUtils.isNotBlank(hospName)?" and (h.hospName like '%"+ValidateUtils.isSql(hospName)+"%'  or h.shortName like '%"+ValidateUtils.isSql(hospName)+"%')":"");
		hql.append(" order by  h.hospName asc,h.shortName asc");
		if(p!=null) {
			return	pageByHql(hql.toString(), false, p);
		}
      return findByHql(hql.toString(), true);
	}
	
	public List<Hospital> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT H.* ");
		sql.append("   FROM T_HOSPITAL H ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE C.TABLE_KEY_ID = H.hosp_id");
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
	
	public List<Hospital> findPageByHosp(Pagination<Hospital> p, Integer hospId) {
		StringBuffer hql=new StringBuffer();
		hql.append(" from Hospital d where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (hospId != null) {
			hql.append("and d.id=?");
			params.add(hospId);
		}
		hql.append(" order by d.id asc");
		if(p!=null){
			p.setAlias("d");
			return pageByHql(hql.toString(), false, p,params.toArray());
		}
		else
			return findByHql(hql.toString(), false,params.toArray());
	}

	public List<Hospital> findStaticImgList(Integer siteId, Integer channelId,
			int size) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT TOP "+ size);
		sql.append(		" H.* ");
		sql.append("   FROM T_HOSPITAL H left join T_CONTROL C  on H.hosp_id=c.table_key_id ");
		sql.append("  WHERE H.HOSP_IMg IS NOT NULL AND H.HOSP_IMg !='' AND H.hosp_id IN");
		sql.append("           (SELECT TABLE_KEY_ID ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("              WHERE 1=1 ");
		if (channelId!=null) {
			sql.append("               AND C.CHANNEL_ID=?");	
			params.add(channelId);
		}
		if (siteId != null) {
			sql.append(" 				AND C.SITE_ID = ?");
			params.add(siteId);
		}	
		addStaticSql(sql, params);
		sql.append(")");
		if (channelId!=null) {
			sql.append("               AND C.CHANNEL_ID=? ");	
			params.add(channelId);
		}
		addOrderSql(sql, "c");
		return findBySql(sql.toString(), false, params.toArray());
	}

	public Hospital findStaticSpageData(Integer siteId, Integer channelId,Boolean hasImg) {
		StringBuffer sql=new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append("select TOP 1 * from T_HOSPITAL H where hosp_id in( select table_key_id from t_control c where 1=1 ");
		addStaticSql(sql, params);
		if (siteId!=null) {
			sql.append(" and c.site_id=? ");	
			params.add(siteId);
		}		
		if(channelId!=null){
			sql.append(" and c.channel_id=? ");
			params.add(channelId);
		}		
		sql.append(")");
		if(hasImg!=null){
			if(hasImg)
				sql.append(" and ( (H.HOSP_IMg IS NOT NULL AND H.HOSP_IMg !='') or (H.map_img is not null and H.map_img<>'') ) ");
			else
				sql.append(" and (H.HOSP_IMg IS NULL OR H.HOSP_IMg =''  ) ");
		}
		return findBySqlFirst(sql.toString(), false, params.toArray());
	}
	
	public List<Hospital> search(String criteria,Integer areaId,
			Pagination<Hospital> pagination) {
		String function=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select * from T_HOSPITAL H where 1=1 ");
		if(areaId !=null){
			 function=new AreaDaoImpl().findChirdByParentFunction(areaId);
			 sql.append("AND H.AREA_ID IN (SELECT AREA_CODE FROM FINDSUBBYPARENTID)");
		}
		if(StringUtils.isNotBlank(criteria))
			sql.append(criteria);
		sql.append(" order by H.IS_REG desc,H.hosp_rank,H.HOSP_TYPE desc");
		if(pagination!=null)
			return pageByRecursionSql(sql.toString(), false, pagination,function);
		return	findByRecursionSql(sql.toString(),false, function);
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

	public List<Hospital> findByAreaCode(Integer size, Integer areaId) {
		StringBuffer function=new StringBuffer("with cte(AREA_CODE, PARENT_CODE) " + 
				"as ( " + 
				"select AREA_CODE, PARENT_CODE from t_area where AREA_CODE ="+areaId+" and is_disable=1 union all   " + 
				"select t.AREA_CODE,t.PARENT_CODE from t_area as t inner join cte as c on c.AREA_CODE=t.PARENT_CODE" + 
				")");
		StringBuffer sql=new StringBuffer();
		if(areaId==null)
			sql.append("select * from t_hospital h where h.area_id in(select AREA_CODE from cte)");
		else
			sql.append("select top "+size+" * from t_hospital h where h.area_id in(select AREA_CODE from cte)");

		return findByRecursionSql(sql.toString(), false, function.toString());
	}
	
	@SuppressWarnings("deprecation")
	public List<HospitalNoScore> findHospNoScore(Integer topNum,String startTime,String endTime, Integer endDay){
		List<Object> params = new ArrayList<Object>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql=new StringBuffer("SELECT ");
		        sql.append(topNum>0?" TOP "+topNum:" ");
		        sql.append(" H.HOSP_ID,MAX(H.SHORT_NAME) SHORT_NAME,SUM(W.NO_SOURCE) NO_SOURCE,MAX(H.HOSP_NAME) HOSP_NAME FROM T_DOCTOR D  "); 
				sql.append(" INNER JOIN T_DOCTOR_WORK W ON D.DOCT_ID=W.DOCT_ID  ");  
				sql.append(" INNER JOIN T_HOSP_DEPARTMENT HD ON HD.DEPA_ID=D.DEPA_ID ");  
				sql.append(" INNER JOIN T_HOSPITAL H ON H.HOSP_ID=HD.HOSP_ID  WHERE 1=1 " );  
				if(startTime!=null) {
			       sql.append("  AND CONVERT(VARCHAR(16),W.START_TIME,120)>=? "); 
			       params.add(startTime);
				}
				else {
					 sql.append("  AND CONVERT(VARCHAR(16),W.START_TIME,120)>=? "); 
				     params.add(sf.format(new Date()));
				}
				if(endTime!=null) {
					sql.append("    AND   CONVERT(VARCHAR(16),W.END_TIME,120)<=? ");  
					params.add(endTime);
				}
				else if(endDay!=null&&endDay>0) {
					Date endTimes=new Date();
					endTimes.setDate(new Date().getDate()+endDay);
					sql.append("    AND   CONVERT(VARCHAR(16),W.END_TIME,120)<=? ");  
					params.add(sf.format(endTimes));
				}
				sql.append(" GROUP BY H.HOSP_ID ORDER BY NO_SOURCE DESC ");  
				JdbcDao jdbcDao=SpringUtils.getBean("jdbcDao");
				List<Map<String, Object>> noInfos=jdbcDao.findSqlMapQuery(sql.toString(), params.toArray());
				List<HospitalNoScore> hospitalNoScores=new ArrayList<HospitalNoScore>();
				if(noInfos!=null) {
    				HospitalNoScore hns=null;
					for (int i = 0,len=noInfos.size(); i < len; i++) {
    					hns=new HospitalNoScore();
    					hns.setHospId(Integer.parseInt(noInfos.get(i).get("HOSP_ID").toString()));
    					hns.setHospName(noInfos.get(i).get("HOSP_NAME")!=null?noInfos.get(i).get("HOSP_NAME").toString():null);
    					hns.setNoScoreCount(Integer.parseInt(noInfos.get(i).get("NO_SOURCE")!=null?noInfos.get(i).get("NO_SOURCE").toString():"0"));
    					hns.setStoreName(noInfos.get(i).get("SHORT_NAME")!=null?noInfos.get(i).get("SHORT_NAME").toString():null);
    					hospitalNoScores.add(hns);
    				}
				}
				return hospitalNoScores;
	}
	private Object[]  findWorkDeparmentSql(Integer topNum,String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" SELECT ");
		sql.append(topNum>0?"  TOP  "+topNum:" ");
		sql.append(" HD.DEPA_ID,COUNT(REG.SCHEDULING_ID) NUM, T.TYPE_PARENT_ID,MAX(T.TYPE_PARENT_NAME) TYPE_PARENT_NAME, " );
		sql.append(" MAX(HD.DEPA_NAME) DEPA_NAME,MAX(H.HOSP_ID) HOSP_ID,MAX(H.HOSP_NAME) HOSP_NAME ");
		sql.append(" FROM T_USER_REGISTER REG ");
		sql.append(" INNER JOIN T_DOCTOR_WORK W ON REG.SCHEDULING_ID=W.SCHEDULING_ID ");
		sql.append(" INNER JOIN T_DOCTOR D ON D.DOCT_ID=W.DOCT_ID ");
		sql.append(" INNER JOIN T_HOSP_DEPARTMENT HD ON HD.DEPA_ID=D.DEPA_ID ");
		sql.append(" INNER JOIN T_HOSPITAL H ON HD.HOSP_ID=H.HOSP_ID ");
		sql.append(" INNER JOIN T_TYPE_PARENT T ON HD.DEPA_TYPE=T.TYPE_PARENT_ID ");
		sql.append(" WHERE H.IS_REG=1 ");
		if(startTime!=null) {
		   sql.append(" AND CONVERT(VARCHAR(10),W.START_TIME,120)>=?  ");
		   params.add(startTime);
		}
		if(endTime!=null) {
		   sql.append(" AND CONVERT(VARCHAR(10),W.START_TIME,120)<=? ");
		   params.add(endTime);
		}
		sql.append(" GROUP BY T.TYPE_PARENT_ID,HD.DEPA_ID ORDER BY NUM DESC ");
		return new Object[] {sql.toString(),params};
	}
	
	private Object[] findWorkDoctorSql(Integer topNum,String startTime,String endTime,Integer depaId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" SELECT ");
		sql.append(topNum>0?" TOP "+topNum:" ");
		sql.append(" D.DOCT_ID,MAX(D.DOCT_NAME) DOCT_NAME ,COUNT(REG.SCHEDULING_ID) NUM,");
		sql.append("MAX(D.JOB_TITLE) JOB_TITLE, MAX(D.DOCT_IMG) DOCT_IMG,MAX(D.SPECIALITY) SPECIALITY ");
		sql.append(" FROM  T_USER_REGISTER REG  "); 
		sql.append(" INNER JOIN T_DOCTOR_WORK W ON REG.SCHEDULING_ID=W.SCHEDULING_ID ");
		sql.append(" INNER JOIN T_DOCTOR D ON D.DOCT_ID=W.DOCT_ID ");
		sql.append(" WHERE 1=1 ");
		if(depaId!=null&&depaId>0) {
		   sql.append(" AND D.DEPA_ID=? ");
		   params.add(depaId);
		}
		if(startTime!=null) {
		   sql.append(" AND CONVERT(VARCHAR(10),W.START_TIME,120)>=? ");
		   params.add(startTime);
		}
		if(endTime!=null) {
		  sql.append(" AND CONVERT(VARCHAR(10),W.START_TIME,120)<=? ");
		  params.add(endTime);
		}
		sql.append(" GROUP BY D.DOCT_ID ORDER BY NUM DESC");
		
		return new Object[] {sql.toString(),params};
	}
	@SuppressWarnings("unchecked")
	public List<WorkTopDeparment> findTopWorkDoctor(Integer topDepaNum,Integer topDoctorNum,String startTime,String endTime){
				Object[] deparmentSql=findWorkDeparmentSql(topDepaNum, startTime, endTime);
				JdbcDao jdbcDao=SpringUtils.getBean("jdbcDao");
				List<Map<String, Object>> depaInfos=jdbcDao.findSqlMapQuery(deparmentSql[0].toString(), ((List<Object>)deparmentSql[1]).toArray());
				List<WorkTopDeparment> deparments=new ArrayList<WorkTopDeparment>();
				List<WorkTopDoctor> doctors=null;
				if(!CollectionUtils.isEmpty(depaInfos)) {
					WorkTopDeparment dep=null;
					WorkTopDoctor doctor=null;
					for (int i = 0,len=depaInfos.size(); i < len; i++) {
						dep=new WorkTopDeparment();
						dep.setDepaId(Integer.parseInt(depaInfos.get(i).get("DEPA_ID").toString()));
						dep.setDepaName(depaInfos.get(i).get("DEPA_NAME").toString());
						dep.setHospId(Integer.parseInt(depaInfos.get(i).get("HOSP_ID").toString()));
						dep.setHospName(depaInfos.get(i).get("HOSP_NAME").toString());
						dep.setDepaTypeName(depaInfos.get(i).get("TYPE_PARENT_NAME").toString());
						deparmentSql=findWorkDoctorSql(topDoctorNum, startTime, endTime,dep.getDepaId());
						List<Map<String, Object>> doctorInfos=jdbcDao.findSqlMapQuery(deparmentSql[0].toString(), ((List<Object>)deparmentSql[1]).toArray());
						if(!CollectionUtils.isEmpty(doctorInfos)) {
							 doctors=new ArrayList<WorkTopDoctor>();
							for (int j = 0,lens=doctorInfos.size(); j <lens ; j++) {
								doctor=new WorkTopDoctor();
								doctor.setDoctorId(Integer.parseInt(doctorInfos.get(j).get("DOCT_ID").toString()));
								doctor.setDoctorName(doctorInfos.get(j).get("DOCT_NAME").toString());
								doctor.setDoctImg(doctorInfos.get(j).get("DOCT_IMG").toString());
								doctor.setSpeciality(doctorInfos.get(j).get("SPECIALITY").toString());
								doctor.setHospId(Integer.parseInt(depaInfos.get(i).get("HOSP_ID").toString()));
								doctor.setHospName(depaInfos.get(i).get("HOSP_NAME").toString());
								doctor.setDepaId(dep.getDepaId());
								doctor.setJobTitle(doctorInfos.get(j).get("JOB_TITLE").toString());
								doctors.add(doctor);
							}
						}
						dep.setDoctors(doctors);
						deparments.add(dep);
    				}
				}
				return deparments;
	}
}
