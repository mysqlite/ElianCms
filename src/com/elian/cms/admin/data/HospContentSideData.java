package com.elian.cms.admin.data;

import java.util.HashMap;
import java.util.Map;

import com.elian.cms.admin.model.Hospital;
import com.elian.cms.admin.service.HospitalService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 健康资讯数据源
 * 
 */
public class HospContentSideData extends MainDataSupport {
	private HospitalService hospitalService;
	
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		hospitalService=(HospitalService) SpringUtils.getEntityService(Hospital.class);
		if(ApplicationUtils.getHospital()!=null) {
		Hospital hosp=hospitalService.get(ApplicationUtils.getHospital().getId());
		//String hospMap=hosp.getMapImg();
		hosp.setMapImg(FilePathUtils.setOutFilePath(hosp.getMapImg()));
			dataMap.put("hosp",hosp);   //医院信息	
		//hosp.setMapImg(hospMap);
		}
		return dataMap;
	}	
}
