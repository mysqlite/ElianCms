package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.ShoppingCartDao;
import com.elian.cms.admin.model.Instrument;
import com.elian.cms.admin.model.Medicine;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.admin.service.InstrumentService;
import com.elian.cms.admin.service.MedicineService;
import com.elian.cms.admin.service.ShoppingCartService;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.service.impl.ServiceImpl;
import com.elian.cms.syst.util.ElianUtils;
import com.elian.cms.syst.util.StringUtils;

@Component("shoppingCartService")
public class ShoppingCartServiceImpl extends 
	ServiceImpl<ShoppingCartDao,ShoppingCart, Integer> implements ShoppingCartService {

	private MedicineService medicineService=null;
	private InstrumentService instrumentService=null;
	
	@Resource
	public void setDao(ShoppingCartDao dao) {
		this.dao = dao;
	}

	@Resource
	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@Resource
	public void setInstrumentService(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}

	/**
	 * 不存在返回null
	 */
	public BaseContent checkMarketable(String type, Integer id) {
		if(StringUtils.isBlank(type) || id==null) 
			return null;
		if(ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type))
			return instrumentService.get(id);
		if(ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type))
			return medicineService.get(id);
		return null;
	}

	public Integer getGoodsPrice(String type, Integer id) {
		int price=-1;
		if(StringUtils.isBlank(type) || id==null) 
			return price;
		if(ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type)){
			Instrument instrument =instrumentService.get(id);
			price=(instrument==null?0:instrument.getPrice());
		}
		if(ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type)){
			Medicine medicine  =medicineService.get(id);
			price=(medicine==null?0:medicine.getPrice());
		}
		return price;
	}

	public List<ShoppingCart> getShoppingCartList(User user) {
		if(user==null) return null;
		return dao.getShoppingCartList(user);
	}

	public BaseContent getGoods(String type, Integer id) {
		if(StringUtils.isBlank(type) || id==null) 
			return null;
		if(ElianUtils.COMMONDITY_TYPE_INSTRUMENT.equals(type))
			return instrumentService.get(id);
		if(ElianUtils.COMMODITY_TYPE_MEDICINE.equals(type))
			return medicineService.get(id);
		return null;
	}

	public void clear(User user) {
		if(user==null) return;
		dao.clear(user);
	}

	public ShoppingCart getShoppingCart(Integer userId, Integer id, String type) {
		return dao.getShoppingCart(userId,id,type);
	}

	public int getTotleRowByUser(User user) {
		List<ShoppingCart> list=getShoppingCartList(user);
		if(CollectionUtils.isEmpty(list)) return 0;
		return list.size();
	}
}
