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
			<div class="guahao_guide_bar ui_bar_01">
				<div class="step step_1">
					确认挂号信息
				</div>
				<div class="step step_2">
					挂号成功
				</div>
				<div class="step step_3">
					评价
				</div>
			</div>
			<form id="regRegisterConfirmForm" name="regRegisterConfirmForm" action="${path}/reg/regRegisterConfirmClone!save.action" method="post">
				<input type="hidden" name="doctorWork.id" value="${doctorWork.id}">
				<input type="hidden" name="doctorWork.version" value="${doctorWork.version}">
				<input type="hidden" name="token" value="${token}"/>
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
									<input type="hidden" name="doctorWork.doctor.dept.hospital.hospName" value="${doctorWork.doctor.dept.hospital.hospName}">
									${doctorWork.doctor.dept.hospital.hospName}
								</td>
								<td>
									<input type="hidden" name="doctorWork.doctor.dept.deptName" value="${doctorWork.doctor.dept.deptName}">
									${doctorWork.doctor.dept.deptName}
								</td>
								<td>
									<input type="hidden" name="doctorWork.doctor.doctName" value="${doctorWork.doctor.doctName}">
									${doctorWork.doctor.doctName}
								</td>
								<td>
									<input type="hidden" name="doctorWork.startTime" value="${doctorWork.startTime}">
									<fmt:formatDate value="${doctorWork.startTime}" pattern="yyyy-MM-dd"/>
								</td>
								<td>
									<input type="hidden" name="doctorWork.regType" value="${doctorWork.regType}">
									<c:forEach var="item" items="${regTypeList}">
										<c:if test="${doctorWork.regType == item.key}">${item.value}</c:if>
									</c:forEach>
								</td>
								<td>
									<input type="hidden" name="doctorWork.amount" value="${doctorWork.amount}">
									${doctorWork.amount}
								</td>
							</tr>
						</tbody>
					</table>
					<div class="mob_ui_box">
						<h4>
							个人信息
						</h4>
						<div class="ui_bd">
							<table cellspacing="0" cellpadding="0" class="tbl">
								<tbody>
									<tr>
										<td>
											姓名：${user.realName}
										</td>
										<td>
											身份证号码:${user.identityCard}
										</td>
										<td>
											健康卡:${user.medicalCard}
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
							联系方式
						</h4>
						<div class="ui_bd tel_num">
							<span class="tit">手机号码：</span>
							<input id="phoneData" name="phone" type="text" class="ipt" maxlength="13" value="${phone eq '' ? '输入联系手机号' : phone}"
								onfocus="if (this.value=='输入联系手机号'){this.value='';this.style.color='#333'}" />
							<span id="phoneDataError" style="display: none">手机格式错误</span>
							<b class="left_border"></b>
							<b class="right_border"></b>
						</div>
					</div>
					<div class="wrap_action_btn" id="wrap_action_btn">
						<script type="text/javascript">
							function isMobile(value){
								if(/^13\d{9}$/g.test(value)||(/^15[0-35-9]\d{8}$/g.test(value))||  
									(/^18[05-9]\d{8}$/g.test(value))){    
										return true;  
								}else{  
								        return false;  
								}  
							}
							
							function submitForm(formId){
								var phone = document.getElementById("phoneData").value;
								if(isMobile(phone)){
									document.getElementById(formId).submit();
								}
								else{
									var phoneDataError =  document.getElementById("phoneDataError");
									phoneDataError.style.display='';
									phoneDataError.className='reg_tips reg_error_tips';
								}
							}
						</script>
						<input class="btn ok" value="确认挂号" onclick="submitForm('regRegisterConfirmForm')"/>
						<a href="#" onclick="window.history.back(); return false;" class="btn cancel">取消挂号</a>
					</div>
				</div>
			</form>
            <script type="text/javascript">
             function submitGologin(){
                 G("forwardPage").value=document.location.href;
                 return true;
             }
            </script>
		</div>
		<script src="http://script.elian.cc/main/reg/script/base.js"></script>
	</body>
</html>