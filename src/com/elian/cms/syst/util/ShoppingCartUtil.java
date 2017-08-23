package com.elian.cms.syst.util;

import java.util.HashMap;
import java.util.Map;

import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.service.ContentService;
import com.elian.cms.syst.model.BaseContent;

public class ShoppingCartUtil {
	private ContentService contentService=null;
	@SuppressWarnings("unused")
	private Ftp imgFtp;
	private static ShoppingCartUtil shoppingCartUtil
		=new ShoppingCartUtil();
	
	public static ShoppingCartUtil getInstance(){
		if(shoppingCartUtil.contentService==null)
			shoppingCartUtil.contentService=
				(ContentService) SpringUtils.getEntityService(Content.class);
			shoppingCartUtil.imgFtp=EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP);
		return shoppingCartUtil;		
	}
	
	public Map<String,Object> addInfor(Map<String,Object> params,
			BaseContent baseContent, Map<String, String> jsonMap) {
		
		jsonMap.put(ShoppingCartCodes.COMMODITY_Id, baseContent.getId().toString());
		jsonMap.put(ShoppingCartCodes.COMMODITY_TYPE,params.get(ShoppingCartCodes.COMMODITY_TYPE).toString());
		
		if(params.get(ShoppingCartCodes.COMMODITY_TYPE).equals(ElianUtils.COMMODITY_TYPE_MEDICINE)){
			Medicine medicine= (Medicine) baseContent;
			return addMedicineInfo(params,medicine,jsonMap);
		}
		if(params.get(ShoppingCartCodes.COMMODITY_TYPE).equals(ElianUtils.COMMONDITY_TYPE_INSTRUMENT)){
			Instrument instrument=(Instrument) baseContent;
			return addInstrumentInfo(params,instrument,jsonMap);
		}
		
		return null;
	}

	private Map<String, Object> addInstrumentInfo(Map<String,Object> params,Instrument instrument,
			Map<String, String> jsonMap) {
		Content content=null;
		String path="#";
		String imgPath="";
		Map<String,Object> result=new HashMap<String, Object>(2);
		Object siteId=params.get(ShoppingCartCodes.COMMODITY_SITE_ID);
		
		if(siteId!=null){
			content=contentService.getByEntityId(Integer.parseInt(siteId.toString()), Instrument.class, instrument.getId());
			imgPath= FilePathUtils.setOutFilePath(instrument.getFrontsImg());
		}
		if(content!=null){
			String prePath="http://comp"+FreemarkerCodes.DOMAIN_SUFFIX;
			path=prePath+ElianCodes.SPRIT +content.getSite().getId()+content.getChannel().getPath()
			+ ElianCodes.SPRIT + content.getId() + ElianCodes.SUFFIX_SHTML;
		}
		
		jsonMap.put(ShoppingCartCodes.COMMODITY_IMG, imgPath);
		jsonMap.put(ShoppingCartCodes.COMMODITY_NAEM, instrument.getCnName());
		jsonMap.put(ShoppingCartCodes.COMMODITY_PRICE, instrument.getPrice()+"");
		jsonMap.put(ShoppingCartCodes.COMMODITY_COUNT, 
				params.get(ShoppingCartCodes.COMMODITY_COUNT).toString());
		jsonMap.put(ShoppingCartCodes.COMMODITY_PATH,path);	
		result.put(ShoppingCartCodes.COMMODITY_PRICE, instrument.getPrice());
		return 	result;	
	}

	private Map<String,Object> addMedicineInfo(Map<String,Object> params,
			Medicine medicine,Map<String, String> jsonMap) {
		Content content=null;
		String path="#";
		String imgPath="";
		Map<String,Object> result=new HashMap<String, Object>(2);
		Object siteId=params.get(ShoppingCartCodes.COMMODITY_SITE_ID);
		
		if(siteId!=null){
			content=contentService.getByEntityId(Integer.parseInt(siteId.toString()), Medicine.class, medicine.getId());;
			imgPath= FilePathUtils.setOutFilePath(medicine.getFrontsImg());
		}
		if(content!=null){
			String prePath="http://comp"+FreemarkerCodes.DOMAIN_SUFFIX;
			path=prePath+ElianCodes.SPRIT +content.getSite().getId()+content.getChannel().getPath()
			+ ElianCodes.SPRIT + content.getId() + ElianCodes.SUFFIX_SHTML;
		}
		jsonMap.put(ShoppingCartCodes.COMMODITY_IMG, imgPath);
		jsonMap.put(ShoppingCartCodes.COMMODITY_NAEM, medicine.getCnName());
		jsonMap.put(ShoppingCartCodes.COMMODITY_PRICE, medicine.getPrice()+"");
		jsonMap.put(ShoppingCartCodes.COMMODITY_COUNT, 
				params.get(ShoppingCartCodes.COMMODITY_COUNT).toString());
		jsonMap.put(ShoppingCartCodes.COMMODITY_PATH,path);	
		result.put(ShoppingCartCodes.COMMODITY_PRICE, medicine.getPrice());
		return 	result;	
	}
}
