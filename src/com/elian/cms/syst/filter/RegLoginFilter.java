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

import com.elian.cms.admin.model.Area;
import com.elian.cms.admin.service.AreaService;
import com.elian.cms.syst.util.ApplicationUtils;
import com.elian.cms.syst.util.SpringUtils;

/**
 * 
 * @author Gechuanyi
 * @2012-7-11 14:00 用户登录拦截 拦截未登录/登录已过期的用户跳转到登录页面进行登录
 */
public class RegLoginFilter implements Filter {
	AreaService  areaService=null;
	
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
		if(uri.contains("reg/regUserManager.jsp")) {
			if(ApplicationUtils.getUser(request)==null) {
				response.sendRedirect(request.getContextPath() + "/reg/login.jsp");
			}
		}
		
		//设置所在区域   区域id=440600代表佛山
		if(session.getAttribute("areaId")==null){ //session 创建时的默认值
			session.setAttribute("areaId", 440600);
			session.setAttribute("areaList", areaService.findAreaNameByAreaCode(440600));
		}else{
		/*	List<Area> list=(List<Area>) session.getAttribute("areaList");
			Integer areaId=(Integer) session.getAttribute("areaId");
			//判断用户是否切换了区域
			if(!(list.get(list.size()-1).getAreaCode().equals(areaId))){
				session.setAttribute("areaList", areaService.findAreaNameByAreaCode(440600));
			}*/
		}
		
		chain.doFilter(req, resp);
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		if(areaService==null)
			areaService=(AreaService) SpringUtils.getEntityService(Area.class);
	}
}
