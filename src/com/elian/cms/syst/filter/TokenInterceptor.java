package com.elian.cms.syst.filter;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * 重复提交拦截器
 * 
 * @author Joe
 * 
 */
public class TokenInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 6441360056271133290L;

	private static final String SESSION_TOKEN = "sessionToken";
	private static final String TOKEN = "token";
	private static final String INVALID_TOKEN = "invalidToken";

	/** 需要添加判断重复提交的方法 */
	protected Set<String> includeMethods = null;
	/** 需要检测重复提交的方法 */
	protected Set<String> checkTokenMethods = null;

	public void setIncludeMethods(String includeMethods) {
		this.includeMethods = TextParseUtil
				.commaDelimitedStringToSet(includeMethods);
	}

	public void setCheckTokenMethods(String checkTokenMethods) {
		this.checkTokenMethods = TextParseUtil
				.commaDelimitedStringToSet(checkTokenMethods);
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// 获取提交方法（POST、GET）
		// String method = ServletActionContext.getRequest().getMethod();
		String methodName = invocation.getProxy().getMethod();
		if (isIncludeMethod(includeMethods, methodName)
				|| checkTokenMethods.contains(methodName)) {
			Map<String, Object> session = invocation.getInvocationContext()
					.getSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			// 用UUID生成令牌
			String strGUID = UUID.randomUUID().toString();
			if (checkTokenMethods.contains(methodName)) {
				// 取出会话中的令牌
				String strRequestToken = (String) session.get(SESSION_TOKEN);
				// 获取页面中的令牌
				String strToken = request.getParameter(TOKEN);
				// 重复提交，重置令牌
				if (strRequestToken != null
						&& !strRequestToken.equals(strToken)) {
					session.put(SESSION_TOKEN, strGUID);
					request.setAttribute(TOKEN, strGUID);
					return INVALID_TOKEN;
				}
			}
			session.put(SESSION_TOKEN, strGUID);
			request.setAttribute(TOKEN, strGUID);
		}
		// 否则正常运行
		return invocation.invoke();
	}
	
	private boolean isIncludeMethod(Set<String> methods, String methodName) {
		for (String method : methods) {
			if (methodName.startsWith(method)) {
				return true;
			}
		}
		return false;
	}
}
