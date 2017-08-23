<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.elian.cms.syst.service.JdbcService"%>
<%@page import="com.elian.cms.syst.util.SpringUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.elian.cms.admin.service.AreaService"%>
<%@page import="com.elian.cms.admin.service.TemplateService"%>
<%@page import="com.elian.cms.admin.action.AreaAction"%>
<%@page import="com.elian.cms.admin.service.MenuService"%>
<%@page import="com.elian.cms.admin.service.UserTypeService"%>
<%@page import="com.elian.cms.admin.service.ActionService"%>
<%
	String type = request.getParameter("type");
	JdbcService jdbcService = SpringUtils.getBean("jdbcService");
	List<Map<String, Object>> dataList = null;
	if ("action".equals(type)) {
		ActionService actionService=SpringUtils.getBean("actionService");
		dataList=actionService.findAjaxActionByAll();
	}
	else if ("userTypeTree".equals(type)) {
        UserTypeService userTypeService=SpringUtils.getBean("userTypeService");
        dataList=userTypeService.findAjaxUserTypeByAll();
    }
	else if ("areaParent".equals(type)) {
        AreaService  areaService = SpringUtils.getBean("areaService");
        dataList = areaService.findAjaxParentAreal();
    }
	else if("menuTree".equals(type)){
		MenuService  menuService = SpringUtils.getBean("menuService");
        dataList = menuService.findAjaxMenuByAll();		
	}
	JSONArray ar = JSONArray.fromObject(dataList);

	response.setCharacterEncoding("UTF-8");
	response.setContentType("application/json;charset=UTF-8");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	response.getWriter().write(ar.toString());
	response.getWriter().flush();
%>