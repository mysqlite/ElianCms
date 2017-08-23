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
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
			<h3 class="cur">
				医生排班修改
			</h3>
		</div>
		<div class=body>
			<form id="editDoctorWorkForm" name="editDoctorWorkForm"
				method="post" action="${path}/admin/doctorWork!save.action">
				<input type="hidden" name="doctorId" value="${doctorId}">
				<input type="hidden" name="doctorWork.id" value="${doctorWork.id}">
				<input type="hidden" name="doctorWork.version" value="${doctorWork.version}">
				<input type="hidden" name="doctorWork.createTime" value="${doctorWork.createTime}"/>
				<input type="hidden" name="doctorWork.creater.id" value="${doctorWork.creater.id}"/>
				<input type="hidden" name="edit" value="${edit}" />
				<input type="hidden" name="token" value="${token}" />
				<div class="divInputTable">
					<h3 class="main-tit-bar" id="typeBtn">
						基本信息
					</h3>
					<div id="typeDiv">
						<ul class="inputList">
							<li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>班次：
		                     		</label>
		                     		<c:if test="${!edit}">
			                     		<div class="select">
					                        <select class="listbar-sel" name="doctorWork.rank">
												<c:forEach var="item" items="${rankList}">
													<option value="${item.key}" <c:if test="${doctorWork.rank == item.key}">selected="selected"</c:if>>${item.value}</option>
												</c:forEach>
											</select>
										</div>
									</c:if>
									<c:if test="${edit}">
										<input type="hidden" name="doctorWork.rank" value="${doctorWork.rank}">
										<c:forEach var="item" items="${rankList}">
											<c:if test="${doctorWork.rank == item.key}">${item.value}</c:if>
										</c:forEach>
									</c:if>
				                    <span class="errortip">${errors['doctorWork.rank'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
			                   	<div class="listItem txtarea">
				                	<label class="lbl-ipt-tit">
				                		<span class="star">*</span>开始时间：
				                	</label>
				                	${doctorWork.startTime}
				                 	<input name="doctorWork.startTime" value="${doctorWork.startTime}" type="hidden"/>
				                </div>
				                <div class="listItem txtarea">
				                	<label class="lbl-ipt-tit">
				                		<span class="star">*</span>结束时间：
				                	</label>
				                	${doctorWork.endTime}
				                  	<input name="doctorWork.endTime" value="${doctorWork.endTime}" type="hidden"/>
				                </div>
							</li>
							<li class="inputList-li">
								<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否停诊：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctorWork.stopWork" value="true" <c:if test="${doctorWork.stopWork}"> checked="checked" </c:if> />
										是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctorWork.stopWork" value="false" <c:if test="${!doctorWork.stopWork}"> checked="checked" </c:if>/>
										否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>门诊类型：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="doctorWork.regType">
											<c:forEach var="item" items="${regTypeList}">
												<option value="${item.key}" <c:if test="${doctorWork.regType == item.key}">selected="selected"</c:if>>${item.value}</option>
											</c:forEach>
										</select>
									</div>
				                    <span class="errortip">${errors['doctorWork.regType'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
				                <div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>金额：
		                     		</label>
			                        <input type="text" class="ipt" name="doctorWork.amount" value="${doctorWork.amount}" onkeyup="intFormat(this,1);"/>
				                    <span class="errortip">${errors['doctorWork.amount'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>号源：
		                     		</label>
			                        <input type="text" class="ipt" name="doctorWork.noSource" value="${doctorWork.noSource}" onkeyup="intFormat(this,1);"/>
				                    <span class="errortip">${errors['doctorWork.noSource'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="${doctorWork.creater.realName}" />
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="<s:date name="doctorWork.createTime" format="yyyy-MM-dd HH:mm:ss" />" />
			                   	</div>
			                </li>
						</ul>
					</div>
				</div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editDoctorWorkForm.submit();">&nbsp;&nbsp;
					<input class="formButton" onclick="window.location='${path}/admin/doctorWork!list.action?doctorId=${doctorId}'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		displayDiv('typeBtn', 'typeDiv', '${errors}');
	</script>
</html>