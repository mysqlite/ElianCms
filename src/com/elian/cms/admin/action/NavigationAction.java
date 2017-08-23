package com.elian.cms.admin.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.util.StringUtils;

/**
 * 导航功能
 * 
 * @author Joe
 * 
 */
@Component
@Scope("prototype")
public class NavigationAction{
	private String Url;
	private String param;
	public String getParam() {
		
		return StringUtils.isBlank(param) ? "" : "?"+param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String execute() {
		return "success";
	}
}
