package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.TypeDao;
import com.elian.cms.admin.model.Type;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.model.SelectItem;
import com.elian.cms.syst.util.StringUtils;

@Component("TypeDao")
public class TypeDaoImpl extends DaoImpl<Type, Integer> implements TypeDao {
	/*
	 * 查询所有类别信息(分页)
	 */
	public List<Type> findByPage(Pagination<Type> p){
		String hql=" from Type t order by t.typeClass,t.typeSort asc ";
		if(p!=null)
			return pageByHql(hql, false, p);
		else 
			return findByHql(hql, false);
	}
	/**
	 * 根据类型集合查询类型（交集）
	 * @param p
	 * @param typeList
	 * @return
	 */
	public List<Type> findByTypeListPage(Pagination<Type> p,List<SelectItem> typeList){
		StringBuffer hql=new  StringBuffer("  from Type t where 1=1 ");
		List<Object> param=new ArrayList<Object>();
		
		for (int i = 0,len=typeList.size(); i < len; i++) {
			if(i==0)
				hql.append(" and ");
			else
				hql.append(" or ");
			hql.append(" t.typeClass=? ");
			param.add(typeList.get(i).getKey());
		}
		hql.append(" order by t.typeClass,t.typeSort asc ");
			if(p!=null)
				return pageByHql(hql.toString(), false, p,param.toArray());
			else 
				return findByHql(hql.toString(), false,param.toArray());
	}
	
	/**
	 * 根据类型查询
	 * @param p
	 * @param type
	 * @return
	 */
	public List<Type> findByTypePage(Pagination<Type> p,String type){
		StringBuffer hql=new  StringBuffer("  from Type t where 1=1  ");
		List<Object> param= new ArrayList<Object>();
		if(StringUtils.isNotBlank(type)) {
			hql.append(" and t.typeClass=? ");
			param.add(type);
		}
		hql.append(" order by t.typeClass,t.typeSort asc ");
		if(p!=null)
			return pageByHql(hql.toString(), false, p,type);
		else 
			return findByHql(hql.toString(), false,type);
	}
	
	public List<Type> findByType(boolean isDisable, String type) {
		if (StringUtils.isBlank(type))
			return null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"  from Type t where t.typeClass=? ");
		params.add(type);
		if (isDisable) {
			hql.append("and t.disable = ?");
			params.add(isDisable);
		}
		hql.append(" order by t.typeClass,t.typeSort asc ");
		return findByHql(hql.toString(), false, params.toArray());
	}
	
	public Type findByTypeName(String typeName) {
		String hql=" from Type t where t.typeName=? ";
		if(StringUtils.isNotBlank(typeName)) {
			List<Type> types=findByHql(hql, true, typeName);
			if(types!=null&&types.size()>0) {
				return findByHql(hql, true, typeName).get(0);
			}
		}
		return null;
	}
	
	public List<Type> findMedicineType(String typeClass,Integer compId){
		List<Object> params=new ArrayList<Object>();
		String hql=" from Type t where t.disable=? ";
		params.add(true);
		if(StringUtils.isNotBlank(typeClass)) {
			hql+=" and  t.typeClass=? ";
			params.add(typeClass);
		}
		if(compId!=null) {
			hql+=" and exists(select 1 from Medicine m where m.type.id=t.id and  company.id=? )";
			params.add(compId);
		}
		return findByHql(hql, false, params.toArray());
	}
	
}
