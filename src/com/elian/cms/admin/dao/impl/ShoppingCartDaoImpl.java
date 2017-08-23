package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.ShoppingCartDao;
import com.elian.cms.admin.model.ShoppingCart;
import com.elian.cms.admin.model.User;
import com.elian.cms.syst.dao.impl.DaoImpl;

@Component("shoppingCartDao")
public class ShoppingCartDaoImpl extends DaoImpl<ShoppingCart, Integer>
		implements ShoppingCartDao {

	public List<ShoppingCart> getShoppingCartList(User user) {
		String hql="from ShoppingCart sc where sc.userId=?";
		return findByHql(hql, false, user.getId());
	}

	public void clear(User user) {
		String hql="delete from ShoppingCart sc where sc.userId=?";
		deleteOrUpdate(hql, user.getId());
	}

	public ShoppingCart getShoppingCart(Integer userId, Integer id, String type) {
		String hql="from ShoppingCart sc where sc.userId=? and sc.commodityId=? and sc.commodityType=?";
		return findByHqlFirst(hql,false,userId,id,type);
	}
}