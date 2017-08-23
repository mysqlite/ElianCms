package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.AreaDao;
import com.elian.cms.admin.model.Area;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.SpringUtils;
@Component("areaDao")
public class AreaDaoImpl extends DaoImpl<Area, Integer> implements AreaDao {

	public List<Area> findAreaByAll() {
		String HQL = "from Area a where  a.disable=true and areaCode<>0 order by a.areaSort  asc,a.areaCode asc";
		return findByHql(HQL, true);
	}
	
	public List<Area> findByParentCode(Integer parentCode){
		String hql="from Area a where a.parentCode=? and a.disable=true order by a.areaCode asc,a.parentCode asc,a.areaSort asc ";
		if(parentCode!=null)
		  return findByHql(hql, true, parentCode);
		else
		  return null;
	}
	
	public List<Area> findByParentCodeNameAsc(Integer parentCode){
		String hql="from Area a where a.parentCode=? and a.disable=true order by a.areaName asc ";
		if(parentCode!=null)
		  return findByHql(hql, true, parentCode);
		else
		  return null;
	}

	public List<Area> findAreaByPage(Pagination<Area> p) {
		String HQL = "from Area a order by a.areaCode asc,a.parentCode asc,a.areaSort asc";
		if (p != null) {
			return pageByHql(HQL, false, p);
		}
		else {
			return findByHql(HQL, false);
		}
	}
	public List<Area> initializationArea() {
		return findByHql("from Area a where parentCode=0 order by a.areaSort,a.areaCode asc",true);
	}
	
	public List <Area> getParentAreas(){
		return findByHql("from Area a where parentCode=0  and areaCode <>440000  order by a.areaCode",true);
	}
	/**
	 * 
	 * @param areaCode
	 * @return 当前节点的所有父节点，包含本身
	 */
	public List<Area> findAreaNameByAreaCode(Integer areaCode) {
		/*StringBuffer sql = new StringBuffer("select a.*  from  t_area  a start with a.area_code=? connect by prior a.parent_code= a.area_code");*/
		String sql="with cte(AREA_CODE, AREA_NAME, PARENT_CODE, NOTE, AREA_SORT, IS_DISABLE, VERSION) " + 
				"as (select * from t_area where AREA_CODE = ? and is_disable=1 union all select t.* from t_area as t inner join cte as c on t.AREA_CODE=c.PARENT_CODE) " + 
				"select * from cte where AREA_CODE<>0 ";
		List<Object> param=new ArrayList<Object>();
		param.add(areaCode);
		return findBySql(sql.toString(), true, param.toArray());
	}
	
	public List<Integer> findChirdByParent(Integer parentCode){
		JdbcDao jdbcDao=SpringUtils.getBean("jdbcDao");
		StringBuffer sql=new StringBuffer("with cte(AREA_CODE, PARENT_CODE) " + 
				"as ( " + 
				"select AREA_CODE, PARENT_CODE from t_area where AREA_CODE =? and is_disable=1 union all   " + 
				"select t.AREA_CODE,t.PARENT_CODE from t_area as t inner join cte as c on c.AREA_CODE=t.PARENT_CODE" + 
				") select AREA_CODE from cte");
    	 List<Map<String, Object>> a=jdbcDao.findSqlMapQuery(sql.toString(), parentCode);
    	  List<Integer> result=new ArrayList<Integer>(); 
    	 for (int i = 0; i < a.size(); i++) {
			result.add( Integer.parseInt(a.get(i).get("AREA_CODE").toString()));
		}
		return result;
	}
	public Area findByName(String areaName) {
		String hql=" from Area a where  a.areaName=?";
		if(areaName!=null) {
			List<Area> areas=findByHql(hql, true, areaName);
			if(!areas.isEmpty())
			  return areas.get(0);
		}
		return null;
	}
	
	public Area findByLikeName(String areaName) {
		String hql=" from Area a where  a.areaName like '%"+areaName+"%'";
		if(areaName!=null) {
			List<Area> areas=findByHql(hql, true);
			if(!areas.isEmpty())
			  return areas.get(0);
		}
		return null;
	}

	public String findParentByChirdFunction(Integer chirdCode){
		StringBuffer function=new StringBuffer();
		if(chirdCode !=null){
			function.append(" WITH FINDPARENTBYSUBID(AREA_CODE,PARENT_CODE)  ");
			function.append(" AS ( ");
			function.append("     SELECT A1.AREA_CODE, A1.PARENT_CODE  ");
			function.append("          FROM T_AREA A1  ");
			function.append("        WHERE A1.AREA_CODE = " + chirdCode);
			function.append("     UNION ALL  ");
			function.append("     SELECT T.AREA_CODE, T.PARENT_CODE  ");
			function.append("         FROM T_AREA T  ");
			function.append(" 			INNER JOIN FINDPARENTBYSUBID F ON F.PARENT_CODE = T.AREA_CODE ");
			function.append(" ) ");
		}
		return function.toString();
	}
	
	public String findChirdByParentFunction(Integer parentCode){
		StringBuffer function=new StringBuffer();
		if(parentCode !=null){
			function.append(" WITH FINDSUBBYPARENTID(AREA_CODE,PARENT_CODE)  ");
			function.append(" AS ( ");
			function.append("     SELECT A1.AREA_CODE, A1.PARENT_CODE  ");
			function.append("          FROM T_AREA A1  ");
			function.append("        WHERE A1.AREA_CODE = " + parentCode);
			function.append("     UNION ALL  ");
			function.append("     SELECT T.AREA_CODE, T.PARENT_CODE  ");
			function.append("         FROM T_AREA T  ");
			function.append(" 			INNER JOIN FINDSUBBYPARENTID F ON F.AREA_CODE = T.PARENT_CODE ");
			function.append(" ) ");
		}
		return function.toString();
	}
}