package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.admin.model.Ftp;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface FtpDao extends Dao<Ftp, Integer> {
	public List<Ftp> findByAll(Pagination<Ftp> pagination);
	
	public List<Ftp> findByTypeIdOrTypeName(Integer typeId,String typeName);
}
