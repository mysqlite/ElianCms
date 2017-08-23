package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.ShoppingCartDao;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.model.BaseContent;
import com.elian.cms.syst.service.Service;

public interface ShoppingCartService extends
		Service<ShoppingCartDao, ShoppingCart, Integer> {

	BaseContent checkMarketable(String type, Integer id);

	Integer getGoodsPrice(String type, Integer id);

	List<ShoppingCart> getShoppingCartList(User user);

	BaseContent getGoods(String type, Integer id);
	
	ShoppingCart getShoppingCart(Integer userId,Integer id,String type);

	/**
	 * 清空购物车
	 * @param user
	 */
	void clear(User user);

	int getTotleRowByUser(User user);
	
}
