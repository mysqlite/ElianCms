package com.elian.cms.syst.util;

/**
 * 日志参数类
 * 
 * @author Joe
 * 
 */
public class LogParamUtils {
	/** 登陆成功 */
	public static final int LOGIN_SUCCESS = 1;
	/** 登陆失败 */
	public static final int LOGIN_FAIL = 2;
	/** 工序添加 */
	public static final int OPERATION_ADD = 3;
	/** 工序更新 */
	public static final int OPERATION_UPDATE = 4;
	/** 工序删除 */
	public static final int OPERATION_DELETE = 5;
	/** 工序审核*/
	public static final int OPERATION_CHECKED = 6;
	/** 工序退回*/
	public static final int OPERATION_EXIT=7;

	public static final String USER_ID = "user.id";
	public static final String USER_ACCOUNT = "account";
	public static final String LOGIN_SUCCESS_CN = "登陆成功";
	public static final String LOGIN_REG_SUCCESS_CN = "挂号登陆成功";
	public static final String LOGIN_REG_FAIL_CN = "挂号登陆成功";
	public static final String LOGIN_FAIL_CN = "登陆失败";

	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_ADD_CN = "角色添加";
	public static final String ROLE_UPDATE_CN = "角色更新";
	public static final String ROLE_DELETE_CN = "角色删除";

	public static final String GRADE_NAME = "gradeName";
	public static final String GRADE_ADD_CN = "等级添加";
	public static final String GRADE_UPDATE_CN = "等级更新";
	public static final String GRADE_DELETE_CN = "等级删除";

	public static final String MENU_NAME = "menuName";
	public static final String MENU_ADD_CN = "菜单添加";
	public static final String MENU_UPDATE_CN = "菜单更新";
	public static final String MENU_DELETE_CN = "菜单删除";
	public static final String MENU_CHECKED_CN = "菜单审核";

	public static final String USER_TYPE_NAME = "typeName";
	public static final String USER_TYPE_ADD_CN = "用户类型添加";
	public static final String USER_TYPE_UPDATE_CN = "用户类型更新";
	public static final String USER_TYPE_DELETE_CN = "用户类型删除";

	public static final String ACTION_NAME = "actionName";
	public static final String ACTION_ADD_CN = "权限添加";
	public static final String ACTION_UPDATE_CN = "权限更新";
	public static final String ACTION_DELETE_CN = "权限删除";

	public static final String RE_ACTION_NAME = "action.actionName";
	public static final String RE_ROLE_NAME = "role.roleName";
	public static final String ROLE_ACTION_ADD_CN = "角色权限添加";
	public static final String ROLE_ACTION_UPDATE_CN = "角色权限更新";
	public static final String ROLE_ACTION_DELETE_CN = "角色权限删除";

	public static final String AREA_CODE = "areaCode";
	public static final String AREA_NAME = "areaName";
	public static final String AREA_ADD_CN = "区域添加";
	public static final String AREA_UPDATE_CN = "区域更新";
	public static final String AREA_DELETE_CN = "区域删除";

	public static final String FTP_NAME = "ftpName";
	public static final String FTP_ADD_CN = "FTP添加";
	public static final String FTP_UPDATE_CN = "FTP更新";
	public static final String FTP_DELETE_CN = "FTP删除";

	public static final String USER_ADD_CN = "用户添加";
	public static final String USER_REG_ADD_CN = "挂号用户添加";
	public static final String USER_UPDATE_CN = "用户更新";
	public static final String USER_DELETE_CN = "用户删除";
	public static final String USER_CHECKED_CN = "用户审核";
	public static final String USER_INITIAL_PWD_CN = "密码初始化";
	public static final String USER_UPDATE_PWD_CN = "密码修改";

	public static final String USER_EXT_ID = "ID";
	public static final String USER_EXT_ADD_CN = "用户扩展添加";
	public static final String USER_EXT_UPDATE_CN = "用户扩展更新";
	public static final String USER_EXT_DELETE_CN = "用户扩展删除";

	public static final String LOG_TITLE = "title";
	public static final String LOG_DELETE_CN = "日志删除";

	public static final String RE_GRADE_NAME = "grade.gradeName";
	public static final String RE_TEMPLATE_URL = "templateUrl";
	public static final String GRADE_TEMPLATE_ADD_CN = "等级模板添加";
	public static final String GRADE_TEMPLATE_UPDATE_CN = "等级模板更新";
	public static final String GRADE_TEMPLATE_DELETE_CN = "等级模板删除";

	public static final String MODEL_NAME = "modelName";
	public static final String MODEL_ADD_CN = "模型添加";
	public static final String MODEL_UPDATE_CN = "模型更新";
	public static final String MODEL_DELETE_CN = "模型删除";

	public static final String GROUP_NAME = "modelName";
	public static final String GROUP_ADD_CN = "模型添加";
	public static final String GROUP_UPDATE_CN = "模型更新";
	public static final String GROUP_DELETE_CN = "模型删除";

	public static final String SITE_NAME = "siteName";
	public static final String SITE_ADD_CN = "站点添加";
	public static final String SITE_UPDATE_CN = "站点更新";
	public static final String SITE_DELETE_CN = "站点删除";
	
	public static final String SITE_USER_ID = "siteUserId";
	public static final String SITE_USER_ADD_CN = "站点用户映射添加";
	public static final String SITE_USER_UPDATE_CN = "站点用户映射更新";
	public static final String SITE_USER_DELETE_CN = "站点用户映射删除";
	
	public static final String RE_SITE_NAME = "site.siteName";
	public static final String SITE_ROLE_ADD_CN = "站点用户组添加";
	public static final String SITE_ROLE_UPDATE_CN = "站点用户组更新";
	public static final String SITE_ROLE_DELETE_CN = "站点用户组删除";
	
	public static final String HOSPITAI_NAME = "hospital.hospName";
	public static final String HOSPITAI_ADD_CN = "医院添加";
	public static final String HOSPITAI_UPDATE_CN = "医院更新";
	public static final String HOSPITAI_DELETE_CN = "医院删除";
	public static final String HOSPITAI_CHECKED_CN = "医院审核";
	
	public static final String SUBSTATION_NAME = "substation.subtationName";
	public static final String SUBSTATION_ADD_CN = "分站添加";
	public static final String SUBSTATION_UPDATE_CN = "分站更新";
	public static final String SUBSTATION_DELETE_CN = "分站删除";
	public static final String SUBSTATION_CHECKED_CN = "分站审核";
	
	public static final String USER_ROLE_ID = "user_role_id";
	public static final String USER_ROLE_ADD_CN = "用户角色添加";
	public static final String USER_ROLE_UPDATE_CN = "用户角色更新";
	public static final String USER_ROLE_DELETE_CN = "用户角色删除";

	public static final String TYPE_NAME = "typeName";
	public static final String TYPE_ADD_CN = "医院添加";
	public static final String TYPE_UPDATE_CN = "医院更新";
	public static final String TYPE_DELETE_CN = "医院删除";
	
	public static final String CHANNEL_NAME = "channelName";
	public static final String CHANNEL_ADD_CN = "栏目添加";
	public static final String CHANNEL_UPDATE_CN = "栏目更新";
	public static final String CHANNEL_DELETE_CN = "栏目删除";
	
	public static final String INFORMATION_TITLE = "title";
	public static final String INFORMATION_ADD_CN = "资讯添加";
	public static final String INFORMATION_UPDATE_CN = "资讯更新";
	public static final String INFORMATION_DELETE_CN = "资讯删除";
	
	public static final String CONTENT_TITLE = "title";
	public static final String CONTENT_ADD_CN = "内容添加";
	public static final String CONTENT_UPDATE_CN = "内容更新";
	public static final String CONTENT_DELETE_CN = "内容删除";
	
	public static final String DEPT_TYPE_NAME = "typeName";
	public static final String DEPT_TYPE_ADD_CN = "科室类型添加";
	public static final String DEPT_TYPE_UPDATE_CN = "科室类型更新";
	public static final String DEPT_TYPE_DELETE_CN = "科室类型删除";
	
	public static final String DEPARTMENT_TITLE = "title";
	public static final String DEPARTMENT_ADD_CN = "资讯添加";
	public static final String DEPARTMENT_UPDATE_CN = "资讯更新";
	public static final String DEPARTMENT_DELETE_CN = "资讯删除";
	
	public static final String INVITATION_TITLE = "title";
	public static final String INVITATION_ADD_CN = "招标添加";
	public static final String INVITATION_UPDATE_CN = "招标更新";
	public static final String INVITATION_DELETE_CN = "招标删除";
	
	public static final String BIDDING_TITLE = "title";
	public static final String BIDDING_ADD_CN = "中标添加";
	public static final String BIDDING_UPDATE_CN = "中标更新";
	public static final String BIDDING_DELETE_CN = "中标删除";
	
	public static final String DOCTOR_TITLE = "title";
	public static final String DOCTOR_ADD_CN = "资讯添加";
	public static final String DOCTOR_UPDATE_CN = "资讯更新";
	public static final String DOCTOR_DELETE_CN = "资讯删除";
	
	public static final String CONTACT_NAME= "contactName";
	public static final String CONTACTER_ADD_CN = "联系人添加";
	public static final String CONTACTER_UPDATE_CN = "联系人更新";
	public static final String CONTACTER_DELETE_CN = "联系人删除";
	
	public static final String IMAGES_ID= "id";
	public static final String IMAGES_NAME= "imagesName";
	public static final String IMAGES_ADD_CN = "图片添加";
	public static final String IMAGES_DELETE_CN = "图片删除";
	
	public static final String JOB_ID= "id";
	public static final String JOB_NAME= "imagesName";
	public static final String JOB_ADD_CN = "招聘添加";
	public static final String JOB_DELETE_CN = "招聘删除";
	public static final String JOB_EDIT_CN = "招聘更新";
	
	public static final String TEMPLATE_NAME = "tempName";
	public static final String TEMPLATE_ADD_CN = "模板添加";
	public static final String TEMPLATE_UPDATE_CN = "模板更新";
	public static final String TEMPLATE_DELETE_CN = "模板删除";
	
	
	public static final String LINKS_NAME = "linksName";
	public static final String LINKS_ADD_CN = "友情链接添加";
	public static final String LINKS_UPDATE_CN = "友情链接更新";
	public static final String LINKS_DELETE_CN = "友情链接删除";
	
	public static final String PURCHASE_NAME = "purchaseName";
	public static final String PURCHASE_ADD_CN = "求购添加";
	public static final String PURCHASE_UPDATE_CN = "求购更新";
	public static final String PURCHASE_DELETE_CN = "求购删除";
	
	public static final String COMPANY_NAME = "company.name";
	public static final String COMPANY_ADD_CN = "企业添加";
	public static final String COMPANY_UPDATE_CN = "企业更新";
	public static final String COMPANY_DELETE_CN = "企业删除";
	public static final String COMPANY_CHECKED_CN = "企业审核";
}
