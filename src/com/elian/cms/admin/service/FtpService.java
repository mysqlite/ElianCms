package com.elian.cms.admin.service;

import java.util.List;

import com.elian.cms.admin.dao.FtpDao;
import com.elian.cms.admin.model.Ftp;
import com.elian.cms.syst.model.Pagination;
import com.elian.cms.syst.service.Service;

public interface FtpService extends Service<FtpDao, Ftp, Integer> {
	public List<Ftp> findByAll(Pagination<Ftp> pagination);
	
	public List<Ftp> findByTypeIdOrTypeName(Integer typeId,String typeName);
}
