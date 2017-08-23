package com.elian.cms.admin.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elian.cms.admin.model.Content;

/**
 * 健康资讯数据源
 * 
 */
public class MainNewsData extends MainDataSupport {
	
	public Map<String, Object> getAllDataMap(){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> mainMap = new HashMap<String, Object>();
		
		dataMap.put("powerPoint", getPowerPointList(214,5));
		
		mainMap.put("1", getDatas(219, 14, 0,true,false));		
		//mainMap.put("2", getDatas(223, 14, 0,true,false));		
		dataMap.put("main",mainMap);	
		
		dataMap.put("middle", getDatas(229, 0, 3,false,true));		//医疗专题
		dataMap.put("left1", getDatas(223, 13, 2,false,false));     //国内新闻         行业政策
		dataMap.put("left2", getTopHitsDatas(223, 1, 8));           //点击排行榜
		
		dataMap.put("bottom1", getDatas(223, 5, 1,false,true));   //国内新闻         行业新闻 
		dataMap.put("bottom2", getDatas(224, 5, 1,false,true));   //国际新闻         国际新闻 
		dataMap.put("bottom3", getDatas(225, 5, 1,false,true));   //医疗机构         医疗机构
		dataMap.put("bottom4", getDatas(226, 5, 1,false,true));   //企业动态         药企机构
	
		dataMap.put("side1", getDatas(230, 11, 0,false,false));     //聚医改革
		dataMap.put("side2", getDatas(210, 8, 0,false,false));     //健康质询  读的是该栏目下的宣传教育

		return dataMap;
	}
	
	private Data1 getTopHitsDatas(Integer channelId ,Integer siteId,Integer size){
		Data1 data = new Data1();
		initParams(channelId);
		List<Content> contentList=contentService.findTopHitsList(channelId, siteId, size);
		ArrayList<DataContent1>  list=getDataContentFromcontList(contentList,false);
		data.setContentList(list);
		return data;
	}
}
