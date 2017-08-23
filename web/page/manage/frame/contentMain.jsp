<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>内容页</title>
		<meta content="text/html; charset=utf-8" http-equiv=content-type>
		<link rel=icon type=image/x-icon href="favicon.ico">
	</head>
	<frameset id=mainFrameset frameSpacing=0 name=mainFrameset border=0 cols=220,6,* frameBorder=no>
        <frame id=menuFrame noResize src="${path}/admin/navigation.action?url=contentLeft" frameBorder="0" name=menuFrame><!--菜单-->
        <frame id=middleFrame noResize src="${path}/admin/navigation.action?url=middle" frameBorder="0" name=middleFrame scrolling=no><!--点击收起按钮-->
        <frame id=mainFrame noResize src="${path}/admin/content!list.action" frameBorder="0" name=mainFrame><!--主内容区域-->
    </frameset>
</html>
