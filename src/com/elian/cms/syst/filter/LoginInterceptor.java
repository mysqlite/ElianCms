package com.elian.cms.syst.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.elian.cms.syst.util.ApplicationUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器 - 去除页面参数字符串两端的空格
 */

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2365641900033439481L;
	private final static String LOGIN = "login";
	private final static String LOGIN_PATH = "/login!login.action";
	private final static String JSON_LOGIN_PATH = "/login!loginJson.action";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		// 如果未进行登录，访问的不是登录界面，需要跳转到登录界面
		if (session.get(ApplicationUtils.USER) == null&& !uri.endsWith(LOGIN_PATH)) {
			if(uri.endsWith(JSON_LOGIN_PATH))
				return invocation.invoke();
			return LOGIN;
		}
		return invocation.invoke();
	}
	
	
	/*private List<Menu> getactionMenu(List<Menu> menuList,
			HttpServletRequest request) {
		Set<String> actionSet = ApplicationUtils.getActionSet(request);
		List<Menu> actionHeadMenu = new ArrayList<Menu>();
		if (null != menuList&actionSet!=null) {
			for (Menu menu : menuList) {
				if (menu.getParentId() == 0) {
					if (actionSet.contains(headMenuTrim(menu.getMenuUrl())))
						actionHeadMenu.add(menu);
				}
				else {
					if (actionSet.contains(menu.getMenuUrl()))
						actionHeadMenu.add(menu);
				}
			}
		}
		return actionHeadMenu;
	}
	
	private String headMenuTrim(String url) {
		return url.substring(url.lastIndexOf("&") + 1, url.length());
	}*/
}
