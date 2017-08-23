package com.elian.cms.syst.action;

import java.util.Set;

import com.elian.cms.syst.util.ApplicationUtils;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -6590051213067211964L;
	/** 返回错误界面 */
	public static final String FAIL = "fail";
	/** 退出 */
	public static final String LOGIN_OUT = "loginOut";

	/** 执行查询返回列表 */
	public static final String LIST = "list";
	/** 编辑数据 */
	public static final String EDIT = "edit";
	/** 保存数据 */
	public static final String SAVE = "save";
	/** 查看数据 */
	public static final String SHOW = "show";
	
	/**登录前操作的页面*/
	public String forwardPage;
	
	public Integer navId;

	/**用于附件上传*/
	private String[] prevFile;
	
	private String[] editorPrevImg;
	
	public String[] getPrevFile() {
		return prevFile;
	}
	

	public void setPrevFile(String[] prevFile) {
		this.prevFile = prevFile;
	}
	
	public String[] getEditorPrevImg() {
		return editorPrevImg;
	}

	public void setEditorPrevImg(String[] editorPrevImg) {
		this.editorPrevImg = editorPrevImg;
	}

	public Integer getNavId() {
		return navId;
	}

	public void setNavId(Integer navId) {
		this.navId = navId;
	}

	protected String entityName;
	
	public String getEntityName() {
		return entityName;
	}
	
	public String getEN(Object t) {
		return t.getClass().getSimpleName();
	}
	protected BaseAction() {
		entityName = replaceProperty(getClass().getSimpleName());
	}

	private String replaceProperty(String str) {
		return str.substring(0, 1).toLowerCase()
				+ str.substring(1, str.length()-6);
	}
	
	private Integer controlId;
	private Integer controlStatus;
	public Integer getControlId() {
		return controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}
	
	public Integer getControlStatus() {
		return controlStatus;
	}

	public void setControlStatus(Integer controlStatus) {
		this.controlStatus = controlStatus;
	}

	/**
	 * 是否有添加权限
	 */
	public boolean isAdd() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(entityName + "!add");
	}

	/**
	 * 是否有更新权限
	 */
	public boolean isUpdate() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(entityName + "!update");
	}
	
	/**
	 * 是否有删除权限
	 */
	public boolean isDelete() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(entityName + "!delete");
	}
	
	/**
	 * 是否有审核权限
	 */
	public boolean isCheck() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(entityName + "!check");
	}
	
	/**
	 * 是否有查看权限
	 */
	public boolean isShow() {
		Set<String> actionSet = ApplicationUtils.getActionSet();
		return actionSet != null && actionSet.contains(entityName + "!show");
	}
	
	private boolean contentCheck;//内容审核权限
	private boolean contentGenerate;//内容发布权限
	private boolean publish;
	public boolean isContentCheck() {
		return contentCheck;
	}
	public void setContentCheck(boolean contentCheck) {
		this.contentCheck = contentCheck;
	}

	public boolean isContentGenerate() {
		return contentGenerate;
	}

	public void setContentGenerate(boolean contentGenerate) {
		this.contentGenerate = contentGenerate;
	}
	
	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	/**
	 * 验证重复提交
	 */
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getForwardPage() {
		return forwardPage;
	}

	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}
}
