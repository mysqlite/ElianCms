<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>注册成功！</title>
	</head>
	<body class="input">
	 <div class="main-top-hd clearfix">
       <h3 class="cur">注册成功</h3>
    </div>
		<div class="body">
			<h3>
			<p>恭喜用户<span style="color:green">${user.account}</span>
			   注册<span style="color:red;">${site.siteName}</span>站点注册成功!</p>
			</h3>
			<a href="${path}/admin/login!login.action">登陆</a>
		</div>
	</body>
</html>