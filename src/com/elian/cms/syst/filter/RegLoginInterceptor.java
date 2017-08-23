package com.elian.cms.syst.filter;

import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.EhcacheUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class RegLoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 4079179668226758007L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ApplicationUtils.setImgFtp(EhcacheUtils.getCacheFtp(EhcacheUtils.IMG_FTP));
		return invocation.invoke();
	}

}
