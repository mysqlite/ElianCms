<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title></title>
		<meta name="Keywords" content="" />
		<meta name="Description" content="" />
		<link rel="shortcut icon" type="image/x-icon" href="http://images.elian.cc/design/main/favicon.ico" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/base.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/header.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/main.css" type="text/css" media="screen">
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/jqzoom/jquery.jqzoom.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/jqzoom/skin.css" type="text/css">
		
		<link rel="stylesheet" href="http://style.elian.cc/public/header/base.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/public/header/sub_head.css" type="text/css">
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/search_result.css" type="text/css">
		<script language="javascript" type="text/javascript" src="http://script.elian.cc/public/base0530.js"></script>
	</head>
	<body>
		<c:import url="/page/front/topBar.shtml" charEncoding="utf-8"/>
		<div class="header">
			<div class="wrap_tit">
				<h1 class="ylmh_e_logo">
					<a href="http://www.elian.cc" target="_blank"><img alt="医联网"
							class="logo" src="http://images.elian.cc/design/main/e-logo.jpg"
							width="170" height="50">医联网</a>
				</h1>
			</div>
			<div class="search_bar">
				<div class="hd" id="tab100_hd">
					<h3 class="cur">
						医电商
					</h3>
				</div>
				<div class="bd" id="tab100_bd">
					<div class="ui_search_bar" style="display: block">
						<form method="post" action="${path}/front/searchMedicine!search.action">
							<input class="ipt" type="text" name="keyword" value=""/>
							<input class="ips" type="submit" value="搜索" />
						</form>
					</div>
				</div>
				<div class="hot_search">
					<span class="tit">热门搜索:</span>
					<a href="#">中国点心</a><a href="#">地方特产</a>
					<a href="#">医疗器械</a><a href="#">医疗器械</a>
				</div>
			</div>
		</div>