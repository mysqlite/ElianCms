package com.elian.cms.syst.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 搜索条件参数类
 * 
 */
public class SearchParamUtils {
	/** 角色定义界面(RoleAction) */
	public static final String ROLE_NAME_CN = "角色名称";
	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_DESC_CN = "角色描述";
	public static final String ROLE_DESC = "roleDesc";

	/** 角色定义搜索条件 */
	public static Map<String, String> getRoleConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(ROLE_NAME_CN, ROLE_NAME);
		conditionMap.put(ROLE_DESC_CN, ROLE_DESC);
		return conditionMap;
	}
	
	/** 行业定义界面(RoleAction) */
	public static final String INDUSTRY_NAME_CN = "行业名称";
	public static final String INDUSTRY_NAME = "industryName";
	public static final String INDUSTRY_DESC_CN = "行业描述";
	public static final String INDUSTRY_DESC = "industryDesc";
	
	/** 行业定义搜索条件 */
	public static Map<String, String> getIndustryConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(INDUSTRY_NAME_CN, INDUSTRY_NAME);
		conditionMap.put(INDUSTRY_DESC_CN, INDUSTRY_DESC);
		return conditionMap;
	}


	/** 用户类型定义界面(RoleAction) */
	public static final String USER_TYPE_NAME_CN = " 类型名称";
	public static final String USER_TYPE_NAME = "typeName";
	public static final String USER_TYPE_DESC_CN = "类型描述";
	public static final String USER_TYPE_DESC = "typeDesc";

	/** 用户类型定义搜索条件 */
	public static Map<String, String> getUserTypeConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(USER_TYPE_NAME_CN, USER_TYPE_NAME);
		conditionMap.put(USER_TYPE_DESC_CN, USER_TYPE_DESC);
		return conditionMap;
	}

	/** 菜单定义界面(RoleAction) */
	public static final String MENU_NAME_CN = " 菜单名称";
	public static final String MENU_NAME = "menuName";
	public static final String MENU_DESC_CN = "菜单描述";
	public static final String MENU_DESC = "menuDesc";

	/** 菜单定义搜索条件 */
	public static Map<String, String> getMenuConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(MENU_NAME_CN, MENU_NAME);
		conditionMap.put(MENU_DESC_CN, MENU_DESC);
		return conditionMap;
	}

	/** 权限定义界面(ActionAction) */
	public static final String ACTION_NAME_CN = "权限名称";
	public static final String ACTION_NAME = "actionName";
	public static final String ACTION_DESC_CN = "权限描述";
	public static final String ACTION_DESC = "actionDesc";

	/** 权限定义搜索条件 */
	public static Map<String, String> getActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(ACTION_NAME_CN, ACTION_NAME);
		conditionMap.put(ACTION_DESC_CN, ACTION_DESC);
		return conditionMap;
	}

	/** 角色权限管理界面(RoleActionAction) */
	public static final String RE_ROLE_NAME_CN = "角色名称";
	public static final String RE_ROLE_NAME = "roleName";
	public static final String RE_ROLE_TYPE_CN = "角色类型";
	public static final String RE_ROLE_TYPE = "roleType";

	/** 角色权限管理搜索条件 */
	public static Map<String, String> getRoleActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(RE_ROLE_NAME_CN, RE_ROLE_NAME);
		conditionMap.put(RE_ROLE_TYPE_CN, RE_ROLE_TYPE);
		return conditionMap;
	}

	/** 区域列表界面(RoleActionAction) */
	public static final String AREA_CN = "区域名称";
	public static final String AREA_NAME = "areaName";
	public static final String AREA_CODE_CN = "区域编码";
	public static final String AREA_CODE_NAME = "areaCode";

	/** 区域列表界面搜索条件 */
	public static Map<String, String> getAreaActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(AREA_CN, AREA_NAME);
		conditionMap.put(AREA_CODE_CN, AREA_CODE_NAME);
		return conditionMap;
	}

	/** 站点等级列表界面(GradeAction) */
	public static final String GRADE_CN = "等级名称";
	public static final String GRADE_NAME = "gradeName";
	public static final String GRADE_DESC_CN = "区域编码";
	public static final String GRADE_DESC_NAME = "gradeDesc";

	/** 站点等级列表界面搜索条件 */
	public static Map<String, String> getGradeActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(GRADE_CN, GRADE_NAME);
		conditionMap.put(GRADE_DESC_CN, GRADE_DESC_NAME);
		return conditionMap;
	}
	
	/** 等级列表界面搜索条件 */
	public static Map<String, String> getGradeConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(GRADE_CN, GRADE_NAME);
		return conditionMap;
	}

	/** FTP列表界面(FtpAction) */
	public static final String FTP_CN = "FTP名称";
	public static final String FTP_NAME = "ftpName";
	public static final String FTP_IP_CN = "服务器地址";
	public static final String FTP_IP_NAME = "serverIp";

	/** FTP列表界面搜索条件 */
	public static Map<String, String> getFtpActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(FTP_CN, FTP_NAME);
		conditionMap.put(FTP_IP_CN, FTP_IP_NAME);
		return conditionMap;
	}

	/** 站点列表界面(SiteAction) */
	public static final String SITE_CN = "站点名称";
	public static final String SITE_NAME = "siteName";
	public static final String SITE_DOMAIN_CN = "域名";
	public static final String SITE_DOMAIN_NAME = "domain";

	/** 站点列表界面搜索条件 */
	public static Map<String, String> getSiteActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(SITE_CN, SITE_NAME);
		conditionMap.put(SITE_DOMAIN_CN, SITE_DOMAIN_NAME);
		return conditionMap;
	}

	/** USER列表界面(UserAction) */
	public static final String USER_CN = "账号名称";
	public static final String USER_NAME = "account";
	public static final String USER_TYPE_CN = "用户类型";
	public static final String USER_TYPE = "userType.typeName";
	public static final String IDENTITY_CARD_CN = "身份证号";
	public static final String IDENTITY_CARD = "userExt.identityCard";
	public static final String REAL_NAME_CN = "真实名字";
	public static final String REAL_NAME = "realName";
	public static final String LAST_LOGIN_IP_CN = "最后登录IP";
	public static final String LAST_LOGIN_IP = "lastLoginIp";

	/** USER列表界面搜索条件 */
	public static Map<String, String> getUserActionConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(5);
		conditionMap.put(USER_CN, USER_NAME);
		conditionMap.put(USER_TYPE_CN, USER_TYPE);
		conditionMap.put(IDENTITY_CARD_CN, IDENTITY_CARD);
		conditionMap.put(REAL_NAME_CN, REAL_NAME);
		conditionMap.put(LAST_LOGIN_IP_CN, LAST_LOGIN_IP);
		return conditionMap;
	}

	/** 日志管理界面(RoleAction) */
	public static final String LOG_TITLE_CN = "日志标题";
	public static final String LOG_TITLE = "title";
	public static final String USER_NAME_CN = "操作用户";
	public static final String USER_NAME1 = "user.account";
	public static final String LOG_IP_CN = "操作IP";
	public static final String LOG_IP = "ip";

	/** 日志管理搜索条件 */
	public static Map<String, String> getLogConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(3);
		conditionMap.put(LOG_TITLE_CN, LOG_TITLE);
		conditionMap.put(USER_NAME_CN, USER_NAME1);
		conditionMap.put(LOG_IP_CN, LOG_IP);
		return conditionMap;
	}

	/** 等级模板管理界面(GradeTemplateAction) */
	public static final String RE_GRADE_NAME_CN = "等级名称";
	public static final String RE_GRADE_NAME = "gradeName";

	/** 等级模板管理搜索条件 */
	public static Map<String, String> getGradeTemplateConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(RE_GRADE_NAME_CN, RE_GRADE_NAME);
		return conditionMap;
	}

	/** 模型定义界面(ModelAction) */
	public static final String MODEL_NAME_CN = "模型名称";
	public static final String MODEL_NAME = "modelName";

	/** 模型定义搜索条件 */
	public static Map<String, String> getModelConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(MODEL_NAME_CN, MODEL_NAME);
		return conditionMap;
	}
	
	/**站点用户映射页面*/
	public static final String SITE_USER_SITE_NAME="site.siteName";
	public static final String SITE_USER_SITE_NAME_CN="站点名称";
	public static final String SITE_USER_USER_NAME="user.realName";
	public static final String SITE_USER_USER_NAME_CN="用户实名";
	public static final String SITE_USER_USER_ACCOUNT="user.account";
	public static final String SITE_USER_USER_ACCOUNT_CN="登录名";
	public static final String SITE_ROLE_ROLE_NAME="role.roleName";
	public static final String SITE_ROLE_ROLE_NAME_CN="角色名称";
   /**站点用户映射页面搜索条件*/
	public static Map<String,String> getSiteUserConditionMap(){
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(3);
		conditionMap.put(SITE_USER_SITE_NAME_CN, SITE_USER_SITE_NAME);
		conditionMap.put(SITE_USER_USER_NAME_CN, SITE_USER_USER_NAME);
		conditionMap.put(SITE_USER_USER_ACCOUNT_CN, SITE_USER_USER_ACCOUNT);
		return conditionMap;
	}
	
	public static final String SITE_USER_USER_NAME_CN_1="申请人";
	public static final String SITE_USER_SITE_COMPTYPE="site.comType";
	public static final String SITE_USER_SITE_COMPTYPE_CN="组织类型";
	
	public static Map<String,String> getAuditSiteUserConditionMap(){
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(4);
		conditionMap.put(SITE_USER_SITE_NAME_CN, SITE_USER_SITE_NAME);
		conditionMap.put(SITE_USER_USER_NAME_CN_1, SITE_USER_USER_NAME);
		conditionMap.put(SITE_USER_USER_ACCOUNT_CN, SITE_USER_USER_ACCOUNT);
		conditionMap.put(SITE_USER_SITE_COMPTYPE_CN, SITE_USER_SITE_COMPTYPE);
		return conditionMap;
	}
	
	
	
	/** 站点用户组界面(RoleAction) */
	public static final String RE_SITE_NAME_CN = "站点名称";
	public static final String RE_SITE_NAME = "site.siteName";
	public static final String RE_ROLE_NAME1_CN = "角色名称";
	public static final String RE_ROLE_NAME1 = "role.roleName";

	/** 站点用户组搜索条件 */
	public static Map<String, String> getSiteRoleConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(RE_SITE_NAME_CN, RE_SITE_NAME);
		conditionMap.put(RE_ROLE_NAME1_CN, RE_ROLE_NAME1);
		return conditionMap;
	}
	/** 分站界面(SubstationAction) */
	public static final String SUBSTATION_NAME_CN = "分站名称";
	public static final String SUBSTATION_NAME = "subName";
	public static final String SUBSTATION_SHORT_NAME_CN = "分站简称";
	public static final String SUBSTATION_SHORT_NAME = "shortName";
	public static final String SUBSTATION_AUDITOR_CN = "审核人";
	public static final String SUBSTATION_AUDITOR = "auditor";
	public static final String SUBSTATION_ADDRESS_CN = "分站地址";
	public static final String SUBSTATION_ADDRESS = "address";
	/**分站搜索条件 */
	public static Map<String, String> getSubstationConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>();
		conditionMap.put(SUBSTATION_NAME_CN, SUBSTATION_NAME);
		conditionMap.put(SUBSTATION_SHORT_NAME_CN, SUBSTATION_SHORT_NAME);
		conditionMap.put(SUBSTATION_AUDITOR_CN, SUBSTATION_AUDITOR);
		conditionMap.put(SUBSTATION_ADDRESS_CN, SUBSTATION_ADDRESS);
		return conditionMap;
	}
	
	/**医院界面(HospitalAction) */
	public static final String HOSPITAL_NAME_CN = "医院名称";
	public static final String HOSPITAL_NAME = "hospName";
	public static final String HOSPITAL_SHORT_NAME_CN = "医院简称";
	public static final String HOSPITAL_SHORT_NAME = "shortName";
	public static final String HOSPITAL_PHONE_CN = "医院电话";
	public static final String HOSPITAL_PHONE = "phone";
	public static final String HOSPITAL_ADDRESS_CN = "医院地址";
	public static final String HOSPITAL_ADDRESS = "address";
	/**医院搜索条件 */
	public static Map<String, String> getHospitalConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(4);
		conditionMap.put(HOSPITAL_NAME_CN, HOSPITAL_NAME);
		conditionMap.put(HOSPITAL_SHORT_NAME_CN, HOSPITAL_SHORT_NAME);
		conditionMap.put(HOSPITAL_PHONE_CN, HOSPITAL_PHONE);
		conditionMap.put(HOSPITAL_ADDRESS_CN, HOSPITAL_ADDRESS);
		return conditionMap;
	}
	
	/*医院类型、性质、等级*/
	public static final String HOSPTYPE_NAME_CN = "类型名称";
	public static final String HOSPTYPE_NAME="typeName";
	public static Map<String, String> getTypeConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(HOSPTYPE_NAME_CN, HOSPTYPE_NAME);
		return conditionMap;
	}
	
	/** 栏目界面(ChannelAction) */
	public static final String CHANNEL_NAME_CN = "栏目名称";
	public static final String CHANNEL_NAME = "channelName";

	/** 栏目搜索条件 */
	public static Map<String, String> getChannelConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(CHANNEL_NAME_CN, CHANNEL_NAME);
		return conditionMap;
	}
	
	/** 内容界面(ContentAction) */
	public static final String CONTENT_TITLE_CN = "标题";
	public static final String CONTENT_TITLE = "content_title";

	/** 内容搜索条件 */
	public static Map<String, String> getContentConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(CONTENT_TITLE_CN, CONTENT_TITLE);
		return conditionMap;
	}
	
	/** 科室类型界面(DeptTypeAction) */
	public static final String DEPT_TYPE_NAME_CN = "类型名称";
	public static final String DEPT_TYPE_NAME="typeName";
	public static final String DEPT_TYPE_CN = "类别";
	public static final String DEPT_TYPE ="type";
	
	/** 科室类型搜索条件 */
	public static Map<String, String> getDeptTypeConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(2);
		conditionMap.put(DEPT_TYPE_NAME_CN, DEPT_TYPE_NAME);
		conditionMap.put(DEPT_TYPE_CN, DEPT_TYPE);
		return conditionMap;
	}
	
	/**联系人(ContacterAction) */
	public static final String CONTACTER_NAME_CN = "联系名称";
	public static final String CONTACTER_NAME="contactName";
	public static final String CONTACTER_PHONE_CN = "联系电话";
	public static final String CONTACTER_PHONE="phone";
	public static final String CONTACTER_QQ_CN = "QQ号码";
	public static final String CONTACTER_QQ="qq";
	public static final String CONTACTER_POSTCODE_CN = "邮编";
	public static final String CONTACTER_POSTCODE="postcode";
	public static final String CONTACTER_ADDRESS_CN = "地址";
	public static final String CONTACTER_ADDRESS="address";
	public static final String CONTACTER_DEPPARTENT_CN = "部门";
	public static final String CONTACTER_DEPPARTENT="department";
	
	/** 科室类型搜索条件 */
	public static Map<String, String> getContacterConditionMap() {
		Map<String, String> conditionMap = new LinkedHashMap<String, String>(6);
		conditionMap.put(CONTACTER_NAME_CN, CONTACTER_NAME);
		conditionMap.put(DEPT_TYPE_CN, DEPT_TYPE);
		conditionMap.put(CONTACTER_PHONE_CN, CONTACTER_PHONE);
		conditionMap.put(CONTACTER_QQ_CN, CONTACTER_QQ);
		conditionMap.put(CONTACTER_ADDRESS_CN, CONTACTER_ADDRESS);
		conditionMap.put(CONTACTER_DEPPARTENT_CN, CONTACTER_DEPPARTENT);
		return conditionMap;
	}
	
	/** 图片界面(DeptTypeAction) */
	public static final String IMAGES_NAME_CN = "图片名称";
	public static final String IMAGES_NAME="imagesName";
	/** 图片搜索条件 */
	public static Map<String, String> getImagesConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(IMAGES_NAME_CN, IMAGES_NAME);
		return conditionMap;
	}
	/** 科室界面(DeptTypeAction) */
	public static final String DEPART_NAME_CN = "科室名称";
	public static final String DEPART_NAME="deptName";
	/** 科室搜索条件 */
	public static Map<String, String> getDepartConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(DEPART_NAME_CN, DEPART_NAME);
		return conditionMap;
	}
	
	/** 科室界面(DeptTypeAction) */
	public static final String DOCTOR_NAME_CN = "医生姓名";
	public static final String DOCTOR_NAME="doctName";
	/** 科室搜索条件 */
	public static Map<String, String> getDoctorConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(DOCTOR_NAME_CN, DOCTOR_NAME);
		return conditionMap;
	}
	
	/**栏目-模板配置*/
	public static final String TEMPLATE_NAME_CN="模板文件"; 
	public static final String TEMPLATE_NAME="c.template.tempName"; 
	public static final String TEMPLATE_CHANNEL_NAME_CN="栏目名称"; 
	public static final String TEMPLATE_CHANNEL_NAME="c.channelSet.channelName"; 
	/** 栏目-模板配置搜索条件 */
	public static Map<String, String> getTemplateConfigConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(TEMPLATE_NAME_CN, TEMPLATE_NAME);
		conditionMap.put(TEMPLATE_CHANNEL_NAME_CN, TEMPLATE_CHANNEL_NAME);
		return conditionMap;
	}

	/**默认栏目-模板配置*/
	public static final String INIT_CHANNEL_NAME_CN="栏目名称"; 
	public static final String INIT_CHANNEL_NAME="c.initChannelSet.channelName"; 
	/** 默认栏目-模板配置搜索条件 */
	public static Map<String, String> getTemplateInitConfigConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(TEMPLATE_NAME_CN, TEMPLATE_NAME);
		conditionMap.put(INIT_CHANNEL_NAME_CN, INIT_CHANNEL_NAME);
		return conditionMap;
	}
	
	/**企业列表页*/
	public static final String COMPANY_NAME_CN="企业名称"; 
	public static final String COMPANY_NAME="c.full_name"; 
	/**企业列表页搜索条件 */
	public static Map<String, String> getCompanyConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(COMPANY_NAME_CN, COMPANY_NAME);
		return conditionMap;
	}
	
	/**企业药品列表*/
	public static final String MEDICINE_NAME_CN="中文名称"; 
	public static final String MEDICINE_NAME="cnName"; 
	public static final String MEDICINE_EN_NAME_CN="英文名称"; 
	public static final String MEDICINE_EN_NAME="enName"; 
	public static final String MEDICINE_APPROVAL_NUMBER_CN="批准文号"; 
	public static final String MEDICINE_APPROVAL_NUMBER="approvalNumber"; 
	public static final String MEDICINE_ALIAS_CN="别名"; 
	public static final String MEDICINE_ALIAS="alias"; 
	public static final String MEDICINE_TYPE_CN="类型名称"; 
	public static final String MEDICINE_TYPE="type.typeName"; 
	
	/**企业药品列表 */
	public static Map<String, String> getMedicineConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(MEDICINE_NAME_CN, MEDICINE_NAME);
		conditionMap.put(MEDICINE_EN_NAME_CN, MEDICINE_EN_NAME);
		conditionMap.put(MEDICINE_APPROVAL_NUMBER_CN, MEDICINE_APPROVAL_NUMBER);
		conditionMap.put(MEDICINE_ALIAS_CN, MEDICINE_ALIAS);
		conditionMap.put(MEDICINE_TYPE_CN, MEDICINE_TYPE);
		return conditionMap;
	}
	
	/**企业药品列表*/
	public static final String INSTRUMENT_NAME_CN="中文名称"; 
	public static final String INSTRUMENT_NAME="cnName"; 
	public static final String INSTRUMENT_EN_NAME_CN="英文名称"; 
	public static final String INSTRUMENT_EN_NAME="enName"; 
	public static final String INSTRUMENT_APPROVAL_NUMBER_CN="批准文号"; 
	public static final String INSTRUMENT_APPROVAL_NUMBER="approvalNumber"; 
	public static final String INSTRUMENT_TYPE_CN="产品类型"; 
	public static final String INSTRUMENT_TYPE="type.typeName"; 
	public static final String INSTRUMENT_ALIAS_CN="别名"; 
	public static final String INSTRUMENT_ALIAS="alias"; 
	
	/**企业药品列表 */
	public static Map<String, String> getInstrumentConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(INSTRUMENT_NAME_CN, INSTRUMENT_NAME);
		conditionMap.put(INSTRUMENT_EN_NAME_CN, INSTRUMENT_EN_NAME);
		conditionMap.put(INSTRUMENT_ALIAS_CN, INSTRUMENT_ALIAS);
		conditionMap.put(INSTRUMENT_APPROVAL_NUMBER_CN, INSTRUMENT_APPROVAL_NUMBER);
		conditionMap.put(INSTRUMENT_TYPE_CN, INSTRUMENT_TYPE);
		return conditionMap;
	}
	
	/** 订单界面(OrderAction) */
	public static final String ORDER_CODE_CN = "订单编码";
	public static final String ORDER_CODE = "orderCode";

	/** 角色定义搜索条件 */
	public static Map<String, String> getOrderConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(ORDER_CODE_CN, ORDER_CODE);
		return conditionMap;
	}

	/** 内容模型搜索条件() */
	public static final String CONTENT_MODEL_CN = "对象名称";
	public static final String CONTENT_MODEL_CODE = "objectName";

	public static Map<String, String> getContentModelConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(1);
		conditionMap.put(CONTENT_MODEL_CN, CONTENT_MODEL_CODE);
		return conditionMap;
	}
	
	/** 站点流量统计搜索条件() refererSite*/
	public static final String REFERER_KEYWORD_CN = "关键字";
	public static final String REFERER_KEYWORD= "accessPage";
	public static final String ACCESS_PAGE_CN = "访问页面";
	public static final String ACCESS_PAGE = "accessPage";
	public static final String REFERER_SITE_CN = "来访者域名";
	public static final String REFERER_SITE= "refererSite";
	public static Map<String, String> getSiteFlowConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>(3);
		conditionMap.put(REFERER_KEYWORD_CN, REFERER_KEYWORD);
		conditionMap.put(ACCESS_PAGE_CN, ACCESS_PAGE);
		conditionMap.put(REFERER_SITE_CN, REFERER_SITE);
		return conditionMap;
	}
	
}
