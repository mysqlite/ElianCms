<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<link rel="stylesheet" href="http://style.elian.cc/hosp/cmsbuild/${cssStr}/reg_doc_details.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="http://style.elian.cc/main/reg/style/search_result.css" type="text/css" media="screen" />
		<link rel="shortcut icon" type="image/x-icon" href="http://www.1915800.com/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="http://style.elian.cc/main/pager.css" />
		<script src="http://script.elian.cc/public/jquery-1.4.2.min.js"></script>
		<script src="${path}/js/manage/editCommon.js"></script>
	</head>
	<body>
		<div class="section">
			<div class="w730 doc_details" id="doc-list">
				<div class="doc_details_txt">
		            <div class="hd">医生介绍</div>
		            <div class="warp_rate clearfix" id="more">
						<div class="rate_details">
							<ul>
								<li>
									<span class="tit">诊疗效果</span>
									<span class="wrap_star"> 
										<span class="star star-<fmt:formatNumber value='${cureScore/2}' pattern='######'/>"></span>
									</span>
									<span class="percent">
										<img src="http://style.elian.cc/main/reg/style/rate/percent.gif"
											width="<fmt:formatNumber value='${cureScore*10}' pattern='######'/>%" />
									</span>
									<span class="rate_comment"> 
										<em>
											<fmt:formatNumber value="${cureScore*10}" pattern="######" />%
										</em>
										<b>
											<c:forEach var="score_" items="${scoreList}" varStatus="f">
												<c:if test="${score_.key==cureScore/2}">${score_.value}</c:if>
											</c:forEach>
										</b>
									</span>
								</li>
								<li>
									<span class="tit">服务态度</span>
									<span class="wrap_star">
										<span class="star star-<fmt:formatNumber value='${serveScore/2}' pattern='######'/>"></span>
									</span>
									<span class="percent">
										<img src="http://style.elian.cc/main/reg/style/rate/percent.gif" width="<fmt:formatNumber value='${serveScore*10}' pattern='######' />%" />
									</span>
									<span class="rate_comment">
										<em>
											<fmt:formatNumber value="${serveScore*10}" pattern="######" />%
										</em>
										<b>
											<c:forEach var="score_" items="${scoreList}" varStatus="f">
												<c:if test="${score_.key==serveScore/2}">${score_.value}</c:if>
											</c:forEach>
										</b>
									</span>
								</li>
								<li>
									<span class="tit">医&nbsp;&nbsp;&nbsp;&nbsp;德</span>
									<span class="wrap_star">
										<span class="star star-<fmt:formatNumber value='${ethiceScore/2}' pattern='######'/>"></span>
									</span>
									<span class="percent">
										<img src="http://style.elian.cc/main/reg/style/rate/percent.gif"
											width="<fmt:formatNumber value='${ethiceScore*10}' pattern='######'/>%"/>
									</span>
									<span class="rate_comment">
										<em>
											<fmt:formatNumber value="${ethiceScore*10}" pattern="######" />%
										</em>
										<b>
											<c:forEach var="score_" items="${scoreList}" varStatus="f">
												<c:if test="${score_.key==ethiceScore/2}">
													${score_.value}
												</c:if>
											</c:forEach> 
										</b>
									</span>
								</li>
							</ul>
						</div>
						<div class="rate_total">
							<h4 class="point">
								<span class="point_tit">综合评分</span><em>${aveScore}</em>分
							</h4>
							<div class="rate_total_area clearfix">
								<span class="wrap_star"> <span
									class="star star-<fmt:formatNumber value='${aveScore/2}' pattern='######'/>"></span>
								</span>
								<a href="#reply_area" class="link_btn">我要点评</a>
							</div>
						</div>
					</div>
		            <div class="bd">
		                ${doctor.introduction}
		                <ul class="list">
		                    <li><span class="tit">职称：</span><span class="txt">
		                    	<C:if test="${empty doctor.jobTitle || doctor.jobTitle==''}">暂无信息</C:if> ${doctor.jobTitle}</span></li>
		                    <li><span class="tit">毕业院校：</span><span class="txt">
		                    	<C:if test="${empty doctor.graduateSchool || doctor.graduateSchool==''}">暂无信息</C:if>${doctor.graduateSchool}</span></li>
		                    <li><span class="tit">擅长疾病：</span><span class="txt">
		                    	<C:if test="${empty doctor.speciality || doctor.speciality==''}">暂无信息</C:if>${doctor.speciality}</span></li>
		                   <!-- <li><span class="tit">论文发表：</span><span class="txt">教授</span></li>
		                    <li><span class="tit">学术专著：</span><span class="txt">教授</span></li> --> 
		                </ul>
		            </div>
		        </div>
				<iframe id="duty_tbl" data-id="${doctor.id}" src="" frameborder="0" scrolling="no" width="750" height="245" class="iframe_duty_tbl"></iframe>
				<jsp:include page="include/doctorCommentAddClone.jsp"></jsp:include><%--//医生评论添加--%>
				<jsp:include page="include/doctorCommentListClone.jsp"></jsp:include><%--//医生评论列表--%>
			</div>
		</div>
		<script src="http://style.elian.cc/main/reg/script/jquery.raty.min.js"></script>
		<script type="text/javascript">
			(function($) {
				$('#star').raty( {
					//cancel       : true,//取消按钮
					//cancelHint   : 'Sure?',//取消提示
					//score: 3,//默认分数//初始化显示分数
					//number:10,//星星的数量//
					target : '#hint',//目标分数文字
					targetFormat : '{score}',//分数显示的格式
					targetText : '待评分',//目标默认分数
					targetKeep : true,//分数默认不消失
					path : "http://style.elian.cc/main/reg/style/rate/",//自定义星星图片位置
					hints : [ "很差(2分)", "差(4分)", "一般(6分)", "好(8分)", "很好(10分)" ]
				//分数提示文字
						});
				$('#star2').raty( {
					target : '#hint2',
					targetFormat : '{score}',
					targetText : '待评分',
					targetKeep : true,
					path : "http://style.elian.cc/main/reg/style/rate/",
					hints : [ "很差(2分)", "差(4分)", "一般(6分)", "好(8分)", "很好(10分)" ]
				});
				$('#star3').raty( {
					target : '#hint3',
					targetFormat : '{score}',
					targetText : '待评分',
					targetKeep : true,
					path : "http://style.elian.cc/main/reg/style/rate/",
					hints : [ "很差(2分)", "差(4分)", "一般(6分)", "好(8分)", "很好(10分)" ]
				});
			})(jQuery)
		</script>
		<script src="http://style.elian.cc/main/reg/script/base.js" type="text/javascript"></script>
		<script src="${path}/js/proscenium/duty_tbl.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var ifrDataId = $(".iframe_duty_tbl").attr("data-id");//自定义属性
					$(".iframe_duty_tbl").attr("src",
							"${path}/reg/regDoctorWorkClone!list.action?doctorId=" + ifrDataId);
			});
		</script>
	</body>
</html>
