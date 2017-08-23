<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML >
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=9" />
		<title>医生明细</title>
		<link rel="stylesheet" href="http://style.elian.cc/hosp/cmsbuild/blue/reg_cms_step.css" type="text/css" media="screen" />
		<link rel="shortcut icon" type="image/x-icon" href="http://www.1915800.com/favicon.ico" />
		<script src="http://script.elian.cc/public/jquery-1.4.2.min.js"></script>
		<script src="${path}/js/manage/editCommon.js"></script>
		<link rel="stylesheet" type="text/css" href="http://style.elian.cc/main/pager.css" />
	</head>
	<body>
		<div class="section">
		    <div class="guahao_confirm_bd error_page">
		        <div class="mob_ui_box">
		            <div class="ui_bd">
		            	<h3>系统提示</h3>
		            	<div class="wrap">
		                	<span class="tips">
		                    	该时间段您已存在挂号单，不能重复挂号!
		                    </span>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<script src="assets/script/jquery-1.4.2.min.js"></script>
		<script src="assets/script/base.js"></script>
		<script src="http://script.elian.cc/main/reg/script/base.js"></script>
	</body>
</html>