<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>密码修改</title>
        <jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
    <div class="main-top-hd clearfix">
        <h3 class="cur">密码修改</h3>
    </div>
<div class=body style="padding-top:100px;padding-left:30px;">
    <h3>
        <p>用户<span>${user.account}</span>[<span style="color:green">${user.realName}</span>]
               密码修改成功！</p>
     </h3>
      </div>
   </body>
</html>