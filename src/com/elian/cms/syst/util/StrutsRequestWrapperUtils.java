package com.elian.cms.syst.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class StrutsRequestWrapperUtils extends HttpServletRequestWrapper {
	/*** The constructor* @param req The request */
	public StrutsRequestWrapperUtils(HttpServletRequest req) {
		super(req);
	}

	/***
	 * Gets the object, looking in the value stack if not found** @param s The
	 * attribute key
	 */
	public Object getAttribute(String s) {
		if (s != null && s.startsWith("javax.servlet")) {
			// don't bother with the standard javax.servlet attributes, we can
			// short-circuit this
			// see WW-953 and the forums post linked in that issue for more info
			return super.getAttribute(s);
		}
		ActionContext ctx = ActionContext.getContext();
		Object attribute = super.getAttribute(s);
		boolean alreadyIn = false;
		Boolean b = (Boolean) ctx.get("__requestWrapper.getAttribute");
		if (b != null) {
			alreadyIn = b.booleanValue();
		}
		// note: we don't let # come through or else a request for
		// #attr.foo or #request.foo could cause an endless loop
		if (!alreadyIn && attribute == null && s.indexOf("#") == -1) {
			try {
				// If not found, then try theValueStack
				ctx.put("__requestWrapper.getAttribute", Boolean.TRUE);
				ValueStack stack = ctx.getValueStack();
				if (stack != null) {
					attribute = stack.findValue(s);
				}
			}
			finally {
				ctx.put("__requestWrapper.getAttribute", Boolean.FALSE);
			}
		}
		return attribute;
	}
}
