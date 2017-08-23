package com.elian.cms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.elian.cms.admin.dao.DoctorCommentDao;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.service.DoctorCommentService;
import com.elian.cms.admin.service.DoctorService;
import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.JdbcService;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("doctorCommentService")
public class DoctorCommentServiceImpl extends ServiceImpl<DoctorCommentDao, DoctorComment, Integer> implements
		DoctorCommentService {

	private JdbcService jdbcService=null;
	private DoctorService doctorService=null;
	
	public List<DoctorComment> findByPageOrAll(Integer userId,
			Integer doctorId,Integer registerId, Pagination<DoctorComment> p) {
		return dao.findByPageOrAll(userId, doctorId,registerId, p);
	}
	@Autowired
	public void setDao(DoctorCommentDao dao) {
		this.dao = dao;
	}
	
	@Resource
	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
	
	@Resource
	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	public List<Doctor> getCommentTopDoctor(int size) {
		String sql="select top "+size+" doct_id, count(1) as count from T_DOCT_COMMENT group by doct_id order by count";
		List<Map<String, Object>> r=jdbcService.findSqlMapQuery(sql);
		if(CollectionUtils.isEmpty(r)) 	return null;
		
		List<Integer> docIdList=new ArrayList<Integer>(size);
		for(Map<String, Object> item:r){
			docIdList.add(Integer.parseInt(item.get("doct_id").toString()));
		}
		if(CollectionUtils.isEmpty(docIdList)) 	return null;
		
		return doctorService.get(docIdList);
	} 
}
