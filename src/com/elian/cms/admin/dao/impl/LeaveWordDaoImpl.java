package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.LeaveWordDao;
import com.elian.cms.admin.model.LeaveWord;
import com.elian.cms.syst.dao.JdbcDao;
import com.elian.cms.syst.dao.impl.DaoImpl;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.util.ElianUtils;

@Component("leaveWordDao")
public class LeaveWordDaoImpl extends DaoImpl<LeaveWord, Integer> implements
		LeaveWordDao {
	private JdbcDao jdbcDao;

	@Resource
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	private String getRecursionFunction(List<Integer> leaveWordIdList, Integer siteId) {
		StringBuilder function = new StringBuilder();
		if (!CollectionUtils.isEmpty(leaveWordIdList)) {
			function.append(" WITH FINDSUBBYPARENTID(GUESTBOOK_ID, PARENT_ID)  ");
			function.append(" AS ( ");
			function.append("     SELECT G1.GUESTBOOK_ID, G1.PARENT_ID  ");
			function.append("       FROM T_GUESTBOOK G1  ");
			function.append("      WHERE G1.GUESTBOOK_ID in(");
			Iterator<Integer> itor=leaveWordIdList.iterator();
			while (itor.hasNext()) {
				Integer id = (Integer) itor.next();
				function.append(id);
				if(itor.hasNext()) function.append(",");
			}
			function.append("			)");
			function.append("        AND G1.SITE_ID = " + siteId);
			function.append("     UNION ALL  ");
			function.append("     SELECT G2.GUESTBOOK_ID, G2.PARENT_ID  ");
			function.append("       FROM T_GUESTBOOK G2  ");
			function.append(" INNER JOIN FINDSUBBYPARENTID F ON G2.PARENT_ID = F.GUESTBOOK_ID");
			function.append(" ) ");
		}
		return function.toString();
	}
	
	
	public List<LeaveWord> findByStatus(Integer siteId, Integer status,
			Pagination<LeaveWord> pagination) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>(3);
		sql.append("SELECT * FROM T_GUESTBOOK G WHERE G.IS_REPLY=0 ");
		if(null!=siteId){
			sql.append(" AND G.SITE_ID=?");
			params.add(siteId);
		}
		if(null!=status){
			sql.append(" AND G.STATUS=?");
			params.add(status);
		}
//		if(ElianUtils.STATUS_1==status){
//			sql.append(" AND G.PARENT_ID=?");
//			params.add(0);
//		}
//		if(StringUtils.isNotBlank(pagination.getConditionContent())){
//			if(pagination.getConditionName().equals(SearchParamUtils.CONTENT_TITLE)){
//				sql.append(" AND G.MSG_TITLE LIKE '%"+pagination.getConditionContent()+"%'");
//			}
//		}
		sql.append(" ORDER BY G.CREATE_TIME DESC,G.GUESTbOOK_ID");
		return pageBySql(sql.toString(), false, pagination, params.toArray());
	}

	public void deleteAllSubList(List<Integer> idList,Integer siteId) {
		StringBuffer sql = new StringBuffer();
		String function = getRecursionFunction(idList, siteId);		
		sql.append(function);
		sql.append(" DELETE T_GUESTBOOK  WHERE GUESTBOOK_ID IN(SELECT GUESTBOOK_ID FROM FINDSUBBYPARENTID)");
		jdbcDao.executeUpdate(sql.toString());
	}


	public List<LeaveWord> getByParentId(Integer parentId, Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(parentId);
		String function = getRecursionFunction(ids, siteId);		
		sql.append(" SELECT * FROM T_GUESTBOOK G WHERE EXISTS (SELECT 1 FROM FINDSUBBYPARENTID F WHERE F.GUESTBOOK_ID=G.GUESTBOOK_ID )");
		sql.append(" ORDER BY G.CREATE_TIME,G.GUESTbOOK_ID");
		return findByRecursionSql(sql.toString(),false,function);
	}


	public List<LeaveWord> findFrontList(Integer siteId,
			Pagination<LeaveWord> pagination) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>(4);
		sql.append("SELECT * FROM T_GUESTBOOK G WHERE G.IS_REPLY=0 AND G.PARENT_ID=0 AND (G.STATUS=? OR G.STATUS=? OR G.STATUS=?)");
		params.add(ElianUtils.STATUS_1);
		params.add(ElianUtils.STATUS_2);
		params.add(ElianUtils.STATUS_4);
		if(null!=siteId){
			sql.append(" AND G.SITE_ID=?");
			params.add(siteId);
		}
		sql.append(" ORDER BY G.CREATE_TIME DESC,G.GUESTbOOK_ID");
		return pageBySql(sql.toString(), false, pagination, params.toArray());
	}

	public List<LeaveWord> getFrontListByParent(Integer parentId, Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(parentId);
		List<Object> params=new ArrayList<Object>(3);
		String function = getRecursionFunction(ids, siteId);		
		sql.append(" SELECT * FROM T_GUESTBOOK G WHERE EXISTS (SELECT 1 FROM FINDSUBBYPARENTID F WHERE F.GUESTBOOK_ID=G.GUESTBOOK_ID )");
		sql.append("	AND  (G.STATUS=? OR G.STATUS=? OR G.STATUS=?)");
		params.add(ElianUtils.STATUS_1);
		params.add(ElianUtils.STATUS_2);
		params.add(ElianUtils.STATUS_4);
		sql.append(" ORDER BY G.CREATE_TIME,G.GUESTbOOK_ID");
		return findByRecursionSql(sql.toString(),false,function,params.toArray());
	}

	public void channgeStatus(List<Integer> idList, Integer status) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE T_GUESTBOOK SET STATUS=? WHERE GUESTBOOK_ID IN(");
		Iterator<Integer> it=idList.iterator();
		while (it.hasNext()) {
			sql.append(it.next());
			if(it.hasNext()) sql.append(",");			
		}
		sql.append("	)");
		jdbcDao.executeUpdate(sql.toString(),status);
	}

	public LeaveWord getReply(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TOP 1 * FROM T_GUESTBOOK G WHERE G.PARENT_ID=? AND G.IS_REPLY=1");
		return findBySqlFirst(sql.toString(), false, id);
	}
}
