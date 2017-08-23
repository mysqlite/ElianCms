package com.elian.cms.syst.listener;

/**
 * doctorIndexInterface定义接口参数
 * 
 * @author Joe
 * 
 */
public interface DoctorIndexInterface {
	/** 医生Id */
	public final static String DOCT_ID = "doctId";
	/** 医生名称*/
	public final static String DOCT_NAME = "doctName";
	/** 医生图片*/
	public final static String DOCT_IMG = "doctImg";
	/** 职务 */
	public final static String DOCT_DUTY_NAME = "dutyName";
	/** 专长 */
	public final static String DOCT_SPECIALITY = "speciality";
	/** 是否开通挂号 */
	public final static String DOCT_IS_REG = "isReg";
	/** 医生Path */
	public final static String DOCT_PATH = "doctPath";
	
	/** 科室名称 */
	public final static String DEPT_ID = "deptID";
	/** 科室名称 */
	public final static String DEPT_NAME = "deptName";
	/**科室Path */
	public final static String DEPT_PATH = "deptPath";
	
	/** 医院Id */
	public final static String HOSP_Id = "hospId";
	/** 医院名称 */
	public final static String HOSP_NAME = "hospName";
}
