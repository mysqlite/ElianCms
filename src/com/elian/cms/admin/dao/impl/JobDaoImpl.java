package com.elian.cms.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.JobDao;
import com.elian.cms.admin.model.Job;
import com.elian.cms.syst.dao.impl.DaoImpl;

@Component("jobDao")
public class JobDaoImpl extends DaoImpl<Job, Integer> implements JobDao {

	public List<Job> findByContentId(List<Integer> contentIdList,
			Integer siteId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_JOB I ");
		sql.append("  WHERE EXISTS ");
		sql.append("           (SELECT 1 ");
		sql.append("              FROM T_CONTROL C ");
		sql.append("             WHERE    C.TABLE_KEY_ID = I.JOB_ID ");
		if (!CollectionUtils.isEmpty(contentIdList)) {
			sql.append("                   AND C.CTRL_ID IN ( ");
			int i = 0;
			for (Integer id : contentIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" )");
		}
		if (siteId != null) {
			sql.append(" AND C.SITE_ID = ?");
			params.add(siteId);
		}
		sql.append(" )");
		return findBySql(sql.toString(), false, params.toArray());
	}
	public List<Job> getByContentId(List<Integer> contentIdList) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT I.* ");
		sql.append("   FROM T_JOB I ");
		sql.append("  WHERE  1=1");		
		if (!CollectionUtils.isEmpty(contentIdList)) {
			sql.append("   AND I.JOB_ID IN ( ");
			int i = 0;
			for (Integer id : contentIdList) {
				if (i != 0) {
					sql.append(" , ");
				}
				sql.append(" ? ");
				params.add(id);
				i++;
			}
			sql.append(" )");
		}
		return findBySql(sql.toString(), false, params.toArray());
	}
}
