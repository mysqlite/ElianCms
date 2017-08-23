package com.elian.cms.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.MenuDao;
import com.elian.cms.admin.model.Menu;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
@Component("menuDao")
public class MenuDaoImpl extends DaoImpl<Menu, Integer> implements MenuDao {
	/**
	 * 查询所有启用的菜单
	 */
	public List<Menu> findMenuByAll() {
		String hql = "from Menu m where disable=true  order by parentId,menuSort asc, id desc";
		return findByHql(hql, false);
	}
	/**
	 * 分页查询菜单信息
	 */
	public List<Menu> findMenyByPage(Pagination<Menu> p) {
		String hql = "from Menu m  order by m.parentId,m.menuSort asc,m.depth asc, m.id desc";
		if (p == null) {
			return findByHql(hql, false);
		}
		else {
			return pageByHql(hql, false, p);
		}
	}
	/**
	 * 查询当前菜单及所有子菜单
	 */
	public List<Menu> findMenuByParentId(Integer pid){
		/*String sql="select a.*  from  t_menu a where a.is_disable=1  start with a.parent_Id=? connect by prior  a.menu_id =a.parent_id order siblings by menu_sort asc";*/
		String sql="with cte(MENU_ID, MENU_NAME, MENU_DESC, PARENT_ID, DEPTH, MENU_URL, CREATE_TIME, MENU_SORT, IS_DISABLE, VERSION) " + 
				"as ( select * from t_menu where menu_id = ? and is_disable=1 union all select t.* from t_menu as t inner join cte as c on t.parent_id = c.menu_id ) " + 
				"select * from cte where is_disable=1 order by parent_id asc,menu_sort asc ";
		return findBySql(sql, false, pid);
	}
	/**
	 * 查询所有顶级菜单
	 */
	public List<Menu> findMenuByParentId(){
		String hql="from Menu m where disable=true and parentId=0 order by menuSort asc";
		return findByHql(hql, false);
	}
	/**
	 * 查询当前菜单及当前菜单父级
	 * @return
	 */
	public List<Menu> findMenuNavigationById(Integer id){
		/*String sql="select a.*  from  t_menu  a start with a.menu_id=? connect by prior a.parent_id= a.menu_id order by parent_Id asc";*/
		String sql="with cte(MENU_ID, MENU_NAME, MENU_DESC, PARENT_ID, DEPTH, MENU_URL, CREATE_TIME, MENU_SORT, IS_DISABLE, VERSION) " + 
				" as (select * from t_menu where menu_id = 51 and is_disable=? union all select t.* from t_menu as t inner join cte as c on t.menu_id=c.parent_id ) " + 
				" select * from cte order by parent_id asc,menu_sort asc ";
		return findBySql(sql, false,id);
	}
}
