package com.elian.cms.admin.dao;

import java.util.List;

import com.elian.cms.reg.model.DoctorComment;
import com.elian.cms.syst.dao.Dao;
import com.elian.cms.syst.model.Pagination;

public interface DoctorCommentDao extends Dao<DoctorComment, Integer> {
  public List<DoctorComment> findByPageOrAll(Integer userId,Integer doctorId,Integer registerId,Pagination<DoctorComment> p);
}
