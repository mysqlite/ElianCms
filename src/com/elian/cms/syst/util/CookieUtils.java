package com.elian.cms.syst.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

/**
 * Cookie 辅助类
 * 
 * @author Joe
 * 
 */
public class CookieUtils {

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 * @param domain
	 * @return
	 */
	public static Cookie addCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			Integer maxAge, String domain) {
		Cookie cookie = new Cookie(name, value);
		if (maxAge != null) {
			// 设定有效时间 以s为单位
			cookie.setMaxAge(maxAge);
		}
		if (StringUtils.isNotBlank(domain)) {
			// 设置Cookie域名
			cookie.setDomain(domain);
		}
		String ctx = request.getContextPath();
		// 设置Cookie路径
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		// 发送Cookie文件
		response.addCookie(cookie);
		return cookie;
	}
	
	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie name
	 * @return if exist return cookie, else return null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 取消cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void cancleCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}
