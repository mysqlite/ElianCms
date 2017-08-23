package com.elian.cms.admin.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康资讯数据源
 * 
 */
public class MainInformationData extends MainDataSupport {
	
	public Map<String, Object> getAllDataMap() {
		Map<String, Object> dataMap = new HashMap<String, Object>();		
		dataMap.put("powerPoint", getPowerPointList(29,5));
		
		dataMap.put("main", getDatas(210, 10, 4,false,false));   //宣传教育
		
		dataMap.put("left1", getDatas(211, 5, 1,false,true));    //饮食健康
		dataMap.put("left2", getDatas(212, 5, 1,false,true));    //职业健康
		dataMap.put("left3", getDatas(213, 5, 1,false,true));    //白领丽人
		dataMap.put("left4", getDatas(215, 5, 1,false,true));    //亚健康
		dataMap.put("left5", getDatas(217, 5, 1,false,true));    //中医中药
		dataMap.put("left6", getDatas(218, 5, 1,false,true));    //中药良方
		
		dataMap.put("side1", getDatas(205, 11, 1,false,false));    //养生
		dataMap.put("side2", getDatas(231, 11, 1,false,false));    //保健
		dataMap.put("side3", getDatas(206, 11, 1,false,false));    //健身		
		dataMap.put("side4", getDatas(207, 11, 1,false,false));    //美容
		return dataMap;
	}	
}
