package com.elian.cms.admin.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康资讯数据源
 * 
 */
public class HospNewsContentSideData extends MainDataSupport {
	
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		dataMap.put("side1", getDatas(264, 8, 1,false,true));   //院刊		
		return dataMap;
	}	
}
