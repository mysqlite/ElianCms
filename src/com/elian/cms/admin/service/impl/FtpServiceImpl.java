package com.elian.cms.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.elian.cms.admin.dao.FtpDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.admin.service.FtpService;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.impl.ServiceImpl;
@Component("ftpService")
public class FtpServiceImpl extends ServiceImpl<FtpDao, Ftp, Integer>
		implements FtpService {
	public List<Ftp> findByAll(Pagination<Ftp> pagination) {
		return dao.findByAll(pagination);
	}
	
	public List<Ftp> findByTypeIdOrTypeName(Integer typeId,String typeName){
		return dao.findByTypeIdOrTypeName(typeId, typeName);
	}

	@Resource
	public void setDao(FtpDao dao) {
		this.dao = dao;
	}
}
