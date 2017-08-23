<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=10" />
		<title>企业首页-产品列表</title>
		<meta name="Keywords" content="企业首页-产品列表" />
		<meta name="Description" content="企业首页-产品列表" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/base.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/header.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/main.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/comp/v2/blue/productList.css" type="text/css" media="screen" />
		<link rel="shortcut icon" type="image/x-icon" href="http://www.xxx.com/favicon.ico" />
	</head>
	<body>
		<c:import url="http://comp.elian.cc/${siteId}/include/head.shtml" charEncoding="utf-8"/>
		<c:import url="http://comp.elian.cc/${siteId}/include/nav.shtml" charEncoding="utf-8"/>
		<form id="searchCompanyInstrumentForm" method="post" action="${path}/front/searchCompanyInstrument!search.action">
			<input name="siteId" value="${siteId}" type="hidden"/>
			<input name="keyword" value="${keyword}" type="hidden"/>
			<div class="gutter"></div>
			<div class="section">
				<div class="main_con prd_show clearfix">
					<div class="gutter"></div>
					<div class="search_box_top_line"></div>
					<div class="mod_main_bd">
						<ul class="pt01">
							<c:forEach var="model" items="${pagination.list}" varStatus="e">
								<li>
									<a class="pic" href="${model.path}">
										<img src="${model.imgPath}" width="160" height="160" alt="" />
									</a>
									<span class="tit">
										<a href="${model.path}">【${model.title}】${model.summary}</a>
									</span>
									<span class="txt">抢购价：<em class="price">￥${model.price}</em> </span>
								</li>
							</c:forEach>
						</ul>
					</div>
					<h4 align="center" style="display:${empty pagination.list ? 'block' : 'none' }">很遗憾，没有找到您想要的，换个关键词再试试？</h4>
					<jsp:include page="../../page/common/pager.jsp"></jsp:include>
				</div>
				<div class="sidebar">
				<div class="aside comfile">
					<div class="aside_hd ui_comfile_hd">
						<h3 class="tit">
							企业档案
						</h3>
					</div>
					<div class="aside_bd">
						<h4 class="tit">
							漳州市兰陵药业有限公司
						</h4>
						<div class="contact_usr">
							<em>吴可过</em>先生
							<a class="qq_talk" target="_blank"
								href="http://wpa.qq.com/msgrd?v=3&uin=151747244&site=qq&menu=yes"><img
									border="0" src="http://wpa.qq.com/pa?p=2:151747244:51"
									alt="点击这里给我发消息" title="点击这里给我发消息" /> </a>
						</div>
						<div class="line"></div>
						<ul class="list">
							<li>
								<span class="tit">经营模式:</span>经销批发
							</li>
							<li>
								<span class="tit">所在地区:</span>福建漳州
							</li>
							<li>
								<span class="tit">供应产品:</span>36条
							</li>
							<li>
								<span class="tit">联系电话:</span>15841025410
							</li>
							<li>
								<span class="tit">经营模式:</span>经销批发
							</li>
						</ul>
						<div class="line"></div>
						<a href="#" class="ui_fav_btn">收藏本企业</a>
					</div>
				</div>
				<div class="gutter"></div>
				<div class="aside com_intro">
					<div class="aside_hd">
						<h3 class="tit">
							公司简介
						</h3>
					</div>
					<div class="aside_bd">
						<a href="#" class="pic"><img src="img/com230x120.jpg"
								width="230" /> </a>
						<h4 class="tit">
							<a href="#">漳州市兰陵药业有限公司</a>
						</h4>
						<p class="txt">
							公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材
						</p>
						<p class="txt">
							公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材公司主营中成药，中药材
							<a href="#" class="more">[更多]</a>
						</p>
					</div>
				</div>
			</div>
			</div>
		</form>
		<c:import url="http://comp.elian.cc/${siteId}/include/foot.shtml" charEncoding="utf-8"/>
	</body>
	<script type="text/javascript" src="http://script.elian.cc/main/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			 $("#headKeyword").attr("value", '${keyword}');
		});
		
		function submitForm(){
			document.getElementById("searchCompanyInstrumentForm").submit();
		}
	</script>
</html>