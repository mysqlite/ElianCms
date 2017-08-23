package com.elian.cms.doctorsSpace.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.elian.cms.syst.action.BaseAction;

/**
 * 医家空间
 * 
 * @author znice
 * 
 */
@Component
@Scope("prototype")
public class DoctorsSpaceAction extends BaseAction {
	private static final long serialVersionUID = 501211879836100728L;
	public String toDoctorsSpace(){
		return SUCCESS;
	}
	
}
