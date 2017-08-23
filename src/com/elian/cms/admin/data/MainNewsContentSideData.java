package com.elian.cms.admin.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康资讯数据源
 * 
 */
public class MainNewsContentSideData extends MainDataSupport {
	
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();	
		
		dataMap.put("side1", getDatas(210, 8, 0,false,false));   //宣传教育		
		dataMap.put("side2", getDatas(212, 8, 0,false,false));   //职业健康
		dataMap.put("side3", getDatas(222, 8, 0,false,false));   //心理健康
		dataMap.put("side4", getDatas(215, 8, 0,false,false));   //亚健康		
	
		return dataMap;
	}	
}
