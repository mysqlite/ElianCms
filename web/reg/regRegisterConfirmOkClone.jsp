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
	</head>
	<body>
		<div class="section">
			<div class="guahao_guide_bar ui_bar_02">
				<div class="step step_1">
					确认挂号信息
				</div>
				<div class="step step_2">
					预约成功
				</div>
				<div class="step step_3">
					评价
				</div>
			</div>
			<div class="success_tips">
				<h2 class="ui_tit">
					预约成功
				</h2>
				<div class="txt">
					您的订单已创建，订单号为
					<em class="red">${userRegister.registerCode}</em>
				</div>
				<div class="txt">
					挂号凭证已发送到
					<em class="red">${phone}</em>，请注意查收，并凭此短信前去就诊，若未收到，请稍后重发短信凭证
				</div>
				<div class="txt">
					挂号信息已经录入您的用户中心，您可以进入用户后台对您的挂号单进行管理
				</div>
			</div>
			<div class="guahao_confirm_bd">
				<table cellspacing="0" cellpadding="0" class="tbl">
					<thead>
						<tr>
							<th class="th01">
								医院名称
							</th>
							<th>
								科室
							</th>
							<th>
								医生
							</th>
							<th>
								就诊日期
							</th>
							<th>
								挂号类型
							</th>
							<th>
								挂号费
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								${doctorWork.doctor.dept.hospital.hospName}
							</td>
							<td>
								${doctorWork.doctor.dept.deptName}
							</td>
							<td>
								${doctorWork.doctor.doctName}
							</td>
							<td>
								<fmt:formatDate value="${doctorWork.startTime}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								<c:forEach var="item" items="${regTypeList}">
									<c:if test="${doctorWork.regType == item.key}">${item.value}</c:if>
								</c:forEach>
							</td>
							<td>
								${doctorWork.amount}
							</td>
						</tr>
					</tbody>
				</table>
				<div class="mob_ui_box">
					<h4>
						取号信息
					</h4>
					<div class="ui_bd">
						<table cellspacing="0" cellpadding="0"
							class="tbl success_tips_tbl">
							<tbody>
								<tr>
									<td>
										取号日期：<fmt:formatDate value="${doctorWork.startTime}" pattern="yyyy-MM-dd"/> 上午
									</td>
									<td>
										身份证号码：${user.identityCard}
									</td>
								</tr>
							</tbody>
						</table>
						<b class="left_border"></b>
						<b class="right_border"></b>
					</div>
				</div>
				<div class="mob_ui_box">
					<h4>
						挂号单详情
					</h4>
					<div class="ui_bd">
						<table cellspacing="0" cellpadding="0" class="tbl success_tips_tbl">
							<tbody>
								<tr>
									<td>
										就诊人姓名：${userRegister.user.realName}
									</td>
									<td>
										金额：${userRegister.amount}
									</td>
								</tr>
								<tr>
									<td>
										支付方式：
										<c:forEach var="item" items="${payTypeList}">
											<c:if test="${userRegister.payType == item.key}">${item.value}</c:if>
										</c:forEach>
									</td>
									<td>
										状态：
										<c:forEach var="item" items="${statusList}">
											<c:if test="${userRegister.status == item.key}">${item.value}</c:if>
										</c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
						<b class="left_border"></b>
						<b class="right_border"></b>
					</div>
				</div>
			</div>
		</div>

		<script src="assets/script/jquery-1.4.2.min.js"></script>
		<script src="assets/script/base.js"></script>
		<script src="http://script.elian.cc/main/reg/script/base.js"></script>
	</body>
</html>