package com.elian.cms.reg.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.model.Type;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.admin.service.TypeService;
import com.elian.cms.syst.action.BaseAction;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;
/**
 * 
 * @author thy
 *
 */

@Component
@Scope("prototype")
public class RegHospitalSearchAction extends BaseAction{
	private static final long serialVersionUID = 8055001706824881690L;
	
	private Integer parentAreaCode;
	private Integer subAreaCode;
	private Integer hosptialRank;
	private Integer hosptialType;
	private String hospitalName;
	
	private Integer day=null;
	
	private Pagination<Hospital> pagination=new Pagination<Hospital>();
	
	private AreaService areaService=null;
	private TypeService typeService=null;;
	private HospitalService hospitalService=null;
	private List<Type> rankList=null;
	
	private Integer hospId=null;
	public String search(){
		String criteria=createCriteria();
		hospitalService.search(criteria,null,pagination);
		return LIST;
	}
	
	public void searchJson(){
		if(StringUtils.isNotBlank(hospitalName)){
			try {
				hospitalName=URLDecoder.decode(hospitalName, "utf-8");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		String criteria=createCriteria();
		hospitalService.search(criteria,null,pagination);
		JSONObject obj = new JSONObject();
		obj.put("pagi", pagination);
		obj.put("hosp_rank_list", getHospRankList());
		obj.put("img_ftp", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl());
		ApplicationUtils.sendJsonpObj(obj);	
	}
	
	public void getHospitalJson() {
		if(hospId!=null){
			JSONObject obj = new JSONObject();
			obj.put("hosp", hospitalService.get(hospId));
			obj.put("img_ftp", EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP).getFtpUrl());
			ApplicationUtils.sendJsonpObj(obj);	
		}
	}
	public List<Area> getParentAreaList() {
		return areaService.findByParentCode(0);
	}
	
	public List<Area> getSubAreaList() {
		if(parentAreaCode != null && parentAreaCode!=0)
			return areaService.findByParentCode(parentAreaCode);
		return null;
	}

	public List<Type> getHospRankList(){
		if(rankList==null)
			rankList=typeService.findByType(true,ElianUtils.HOSP_RANK);
		return rankList;
	}
	
	public List<Type> getHospTypeList(){
		return typeService.findByType(true,ElianUtils.HOSP_TYPE);
	}
	
	public Integer getParentAreaCode() {
		return parentAreaCode;
	}

	public void setParentAreaCode(Integer parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	public Integer getSubAreaCode() {
		return subAreaCode;
	}

	public void setSubAreaCode(Integer subAreaCode) {
		this.subAreaCode = subAreaCode;
	}

	public Integer getHosptialRank() {
		return hosptialRank;
	}

	public void setHosptialRank(Integer hosptialRank) {
		this.hosptialRank = hosptialRank;
	}

	public Integer getHosptialType() {
		return hosptialType;
	}

	public void setHosptialType(Integer hosptialType) {
		this.hosptialType = hosptialType;
	}

	public Pagination<Hospital> getPagination() {
		return pagination;
	}

	public void setPagination(Pagination<Hospital> pagination) {
		this.pagination = pagination;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Integer getHospId() {
		return hospId;
	}

	public void setHospId(Integer hospId) {
		this.hospId = hospId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	@Resource
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	@Resource
	public void setTypeService(TypeService typeService) {
		this.typeService = typeService;
	}

	@Resource
	public void setHospitalService(HospitalService hospitalService) {
		this.hospitalService = hospitalService;
	}

	
	private String createCriteria(){
		StringBuilder criteria=new StringBuilder();
		if(subAreaCode!=null){
			if(subAreaCode!=0){
				criteria.append(" AND ( EXISTS( SELECT 1 FROM T_AREA A WHERE (A.AREA_CODE=H.AREA_ID " +
					"and A.PARENT_CODE=").append(subAreaCode).append(") OR H.AREA_ID=").append(subAreaCode).append("))");
			}
		}else{
			if(parentAreaCode!=null && parentAreaCode!=0){				
				criteria.append(" and ( EXISTS( SELECT 1 FROM T_AREA A WHERE (A.AREA_CODE=H.AREA_ID " +
					"and A.PARENT_CODE=").append(parentAreaCode).append(") OR H.AREA_ID=").append(parentAreaCode).append("))");
			}
		}
		if(hosptialRank!=null && 0!=hosptialRank)
			criteria.append(" and H.hosp_rank=").append(hosptialRank);
		if(hosptialType!=null && 0!=hosptialType)
			criteria.append(" and H.HOSP_TYPE=").append(hosptialType);
		if(StringUtils.isNotBlank(hospitalName))
			criteria.append(" and H.HOSP_NAME like('%").append(hospitalName).append("%')");
		if(day!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			Calendar beginDate=Calendar.getInstance();
			Calendar endDate=Calendar.getInstance();
			beginDate.set(beginDate.get(Calendar.YEAR), beginDate.get(Calendar.MONTH)
					,beginDate.get(Calendar.DAY_OF_MONTH)+(day==0?0:day-1), 0, 0, 0);
			endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)
					,endDate.get(Calendar.DAY_OF_MONTH)+(day==0?6:day-1)+1, 0, 0, 0);
			criteria.append(" AND ( EXISTS (SELECT 1 FROM T_HOSP_DEPARTMENT H_D WHERE H_D.HOSP_ID=H.HOSP_ID AND " +
					" EXISTS( SELECT 1 FROM T_DOCTOR T_D WHERE T_D.DEPA_ID=H_D.DEPA_ID AND EXISTS (SELECT 1 FROM T_DOCTOR_WORK D_W WHERE D_W.DOCT_ID=T_D.DOCT_ID ");
			criteria.append("		AND D_W.NO_SOURCE > 0  AND D_W.IS_WORK = 0 ");
			criteria.append(" 		AND CONVERT (VARCHAR (19),  D_W.START_TIME, 120) >='").append(df.format(beginDate.getTime())).append("'");
			criteria.append(" 		AND CONVERT (VARCHAR (19),  D_W.START_TIME, 120) <='").append(df.format(endDate.getTime())).append("'");
			
			criteria.append(		"))))");
		}
		return criteria.toString();
	}
}
