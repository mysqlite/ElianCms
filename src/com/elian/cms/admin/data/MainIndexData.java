package com.elian.cms.admin.data;

import java.util.LinkedHashMap;
import java.util.Map;

import com.elian.cms.syst.model.BaseStaticPageData;

public class MainIndexData extends BaseStaticPageData {
	private MainInformationData infoData=new MainInformationData();		
	private MainNewsData newsData=new MainNewsData();		
	private MainInviteData inviData=new MainInviteData();
	private MainJobData jobData=new MainJobData();
	
	public Map<String, Object> getAllDataMap() {
		 Map<String, Object> dataMap=new LinkedHashMap<String, Object>();		 
		 Map<String, Object> subDataMap=null;
		 
		 subDataMap=new LinkedHashMap<String, Object>();
		 subDataMap.put("1", newsData.getDatas(219, 10, 0,true,false));//热点新闻
		 subDataMap.put("2", newsData.getDatas(223, 10, 0,true,false));//国内新闻         行业新闻 
		 subDataMap.put("3", newsData.getDatas(229, 10, 0,true,false));//医疗专题		
		 dataMap.put("top1", subDataMap);		 
		 
		 subDataMap=new LinkedHashMap<String, Object>();
		 subDataMap.put("1", inviData.getInvitList(233, 8));//招标信息
		 subDataMap.put("2", inviData.getBiddList(233, 8));//中标信息
		 dataMap.put("top2", subDataMap);
		 
		 dataMap.put("top3", newsData.getDatas(230, 8, 2,false,false));     //聚医改革
		 
		 subDataMap=new LinkedHashMap<String, Object>();
		 subDataMap.put("1", infoData.getDatas(231, 8, 1,false,false));    //保健
		 subDataMap.put("2", infoData.getDatas(205, 8, 1,false,false));    //养生
		 subDataMap.put("3", infoData.getDatas(210, 8, 1,false,false));   //宣传教育
		 dataMap.put("bottom1", subDataMap);
		 
		 subDataMap=new LinkedHashMap<String, Object>();		
		 subDataMap.put("1", infoData.getDatas(213, 8, 1,false,false));    //白领丽人
		 subDataMap.put("2", infoData.getDatas(216, 8, 1,false,false));    //情感专线
		 subDataMap.put("3", infoData.getDatas(206, 8, 1,false,false));    //健身
		 dataMap.put("bottom2", subDataMap);
		 
		 subDataMap=new LinkedHashMap<String, Object>();
		 subDataMap.put("1", infoData.getDatas(215, 8, 1,false,false));    //亚健康
		 subDataMap.put("2", infoData.getDatas(211, 8, 1,false,false));    //饮食健康
		 subDataMap.put("3", infoData.getDatas(212, 8, 1,false,false));   //职业健康
		 dataMap.put("bottom3", subDataMap);
		 
		 subDataMap=new LinkedHashMap<String, Object>();
		 subDataMap.put("1", infoData.getDatas(217, 8, 1,false,false));    //中医养生
		 subDataMap.put("2", infoData.getDatas(218, 8, 1,false,false));    //中医良药
		 subDataMap.put("3", infoData.getDatas(207, 8, 1,false,false));    //美容 
		 dataMap.put("bottom4", subDataMap);
		 
		 dataMap.put("bottom5",jobData.getDatas(243, 10, 0, true,false,true));	//医院招聘
		 dataMap.put("bottom6",jobData.getDatas(244, 10, 0, true,false,true));	//企业招聘	
		 dataMap.put("bottom7",jobData.getTopHitsDatas(244,null,8));	//点击排行榜		 
		
		return dataMap;
	}
	
}
