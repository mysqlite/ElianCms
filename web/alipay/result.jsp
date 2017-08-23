<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   String path = request.getContextPath();
   String basePath = request.getScheme() + "://" + request.getLocalAddr() + ":"
                              + request.getServerPort() + path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
</head>
<body>
谢谢光临 欢迎继续购物  <a href="<%=basePath%>">商场首页</a>
</body>
</html>