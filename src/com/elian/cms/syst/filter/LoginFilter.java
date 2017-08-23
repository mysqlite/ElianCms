package com.elian.cms.syst.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elian.cms.syst.util.ApplicationUtils;

/**
 * 
 * @author Gechuanyi
 * @2012-7-11 14:00 用户登录拦截 拦截未登录/登录已过期的用户跳转到登录页面进行登录
 */
public class LoginFilter implements Filter {
	private final static String IMAGE_PATH = "image.jsp";
	private final static String LOGIN_PATH = "/login!login.action";
	private final static String TREE_JSP = "tree.jsp";

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		if (session.getAttribute("path") == null) {
			session.setAttribute("path", request.getContextPath());
		}
		String uri = request.getRequestURI();
		// 如果是加载验证码，继续往下执行
		if (uri.endsWith(IMAGE_PATH)) {
			chain.doFilter(req, resp);
		}
		else if (uri.endsWith(TREE_JSP)) {
			chain.doFilter(req, resp);
		}
		// 如果未进行登录，访问的不是登录界面，需要跳转到登录界面
		else if (request.getSession().getAttribute(ApplicationUtils.USER) == null
				&& !uri.endsWith(LOGIN_PATH)) {
			response.sendRedirect(request.getContextPath() + LOGIN_PATH);
		}
		// 用户已登录，正常的界面请求
		else
			chain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
