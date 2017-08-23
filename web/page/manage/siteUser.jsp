<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>站点用户页</title>
		<meta content="text/html; charset=utf-8" http-equiv=content-type>
		<link rel=icon type=image/x-icon href="favicon.ico">
	</head>
	<frameset id=siteUserFrameset frameSpacing=0 name=siteUserFrameset border=0 cols=200,* frameBorder=no>
        <frame id="siteUserMenuFrame" noResize src="${path}/admin/navigation.action?url=siteUserLeft" frameBorder=no name=siteUserMenuFrame><!--菜单-->
        <frame id="siteUserMainFrame" noResize src="${path}/admin/siteUser!list.action" frameBorder=no name=siteUserMainFrame><!--主内容区域-->
    </frameset>
</html>
