package com.elian.cms.syst.listener.Impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.stereotype.Component;

import com.elian.cms.admin.model.Channel;
import com.elian.cms.admin.model.Content;
import com.elian.cms.admin.model.Doctor;
import com.elian.cms.admin.model.Site;
import com.elian.cms.syst.listener.DoctorIndexInterface;
import com.elian.cms.syst.listener.IndexListener;
import com.elian.cms.syst.util.ElianCodes;
import com.elian.cms.syst.util.FilePathUtils;
import com.elian.cms.syst.util.StringUtils;

/**
 * IndexListener的默认实现，方便调用删除索引公用方法
 * 
 * @author Joe
 * 
 */
@Component("doctorIndexListener")
public class DoctorIndexListener extends IndexListener<Doctor> implements
		DoctorIndexInterface {
	
	@Override
	public void createIndexDetail(Doctor doctor, Content content, Document doc) {
		Site site=content.getSite();
		Channel doctChannel=content.getChannel();
		doc.add(new Field(DOCT_ID, doctor.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(DOCT_NAME, doctor.getDoctName(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field(DOCT_PATH, site.getId() + doctChannel.getPath()
				+ ElianCodes.SPRIT + content.getId() + ElianCodes.SUFFIX_SHTML,
				Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(DOCT_IS_REG,doctor.isReg()?"true":"false",Field.Store.YES, Field.Index.NOT_ANALYZED));
		if (StringUtils.isNotBlank(doctor.getDoctImg())) {
			doc.add(new Field(DOCT_IMG, FilePathUtils.setOutFilePath(doctor
					.getDoctImg()), Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		if (StringUtils.isNotBlank(doctor.getDutyName())) {
			doc.add(new Field(DOCT_DUTY_NAME, doctor.getDutyName(),
					Field.Store.YES, Field.Index.ANALYZED));
		}
		if (StringUtils.isNotBlank(doctor.getSpeciality())) {
			doc.add(new Field(DOCT_SPECIALITY, doctor.getSpeciality(),
					Field.Store.YES, Field.Index.ANALYZED));
		}
		if (doctor.getDept() != null) {
			doc.add(new Field(DEPT_ID, doctor.getDept().getId().toString(),
					Field.Store.YES, Field.Index.NOT_ANALYZED));
			if (StringUtils.isNotBlank(doctor.getDept().getDeptName())) {
				doc.add(new Field(DEPT_NAME, doctor.getDept().getDeptName(),
						Field.Store.YES, Field.Index.ANALYZED));
			}
			if (doctor.getDept().getHospital() != null) {
				doc.add(new Field(HOSP_Id, doctor.getDept().getHospital()
						.getId().toString(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				if (StringUtils.isNotBlank(doctor.getDept().getHospital()
						.getHospName())) {
					doc.add(new Field(HOSP_NAME, doctor.getDept().getHospital()
							.getHospName(), Field.Store.YES,
							Field.Index.ANALYZED));
				}
			}
		}
	}

}
