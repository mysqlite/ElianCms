package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.dao.Dao;

public interface ShoppingCartDao extends Dao<ShoppingCart, Integer> {

	List<ShoppingCart> getShoppingCartList(User user);

	void clear(User user);

	ShoppingCart getShoppingCart(Integer userId, Integer id, String type);

}
