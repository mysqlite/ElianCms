package com.elian.cms.reg.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Joe
 *
 */
@Component
@Scope("prototype")
public class RegDoctorCloneAction extends RegDoctorAction{
	private static final long serialVersionUID = 4524116663575032604L;
	
	private String cssStr;
	
	public String detial(){
		return super.detial();
	}

	public String getCssStr() {
		return cssStr;
	}

	public void setCssStr(String cssStr) {
		this.cssStr = cssStr;
	}

}
