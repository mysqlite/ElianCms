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
		<script type="text/javascript">
			function changeChannelType(value){
				var closePanel = document.getElementById("closePanel");
				if(value=="2"){
					closePanel.style.display='';
				}
				else{
					closePanel.style.display='none';
				}
			}
			
			function checkDoctorRegister(){
				var doctorId = document.getElementById('doctorIdData').value;
				var weeks = document.getElementById('weeksData').value;
				var rank = document.getElementById('rankData').value;
				
				var bool = false;
				$.ajax({
			    	type: 'POST',
			        url: '${path}/admin/doctorRegisterSet!checkDoctorRegister.action?doctorId='+doctorId+'&weeks='+weeks+'&rank='+rank,
			        async: false,
					dataType: "json",
					success:function(data){
						bool = data.MSG;
					}
			    });
				return bool;
			}
			
			function submitForm(formId){
				var bool = true;
				if (confirm('是否立即生成自动排班？')){
					document.getElementById('generate').value= true;
					if(checkDoctorRegister()=="true"){
						alert('当前排班已有患者挂号，不能修改!');
						bool = false;
					}
				}
				if(bool)
					document.getElementById(formId).submit();
			}
		</script>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
			<h3 class="cur">
				<c:if test="${!edit}">医生排班设置添加</c:if>
				<c:if test="${edit}">医生排班设置修改</c:if>
			</h3>
		</div>
		<div class=body>
			<form id="editDoctorRegisterSetForm" name="editDoctorRegisterSetForm"
				method="post" action="${path}/admin/doctorRegisterSet!save.action">
				<input id="doctorIdData" type="hidden" name="doctorId" value="${doctorId}">
				<input type="hidden" name="doctorRegisterSet.id" value="${doctorRegisterSet.id}">
				<input type="hidden" name="doctorRegisterSet.version" value="${doctorRegisterSet.version}">
				<input type="hidden" name="doctorRegisterSet.createTime" value="${doctorRegisterSet.createTime}"/>
				<input type="hidden" name="doctorRegisterSet.creater.id" value="${doctorRegisterSet.creater.id}"/>
				<input id="generate" type="hidden" name="generate" value="${generate}" />
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
		                     			<span class="star">*</span>星期：
		                     			<input id="weeksData" type="hidden" value="${doctorRegisterSet.weeks}"/>
		                     		</label>
		                     		<c:if test="${!edit}">
			                     		<div class="select">
					                        <select class="listbar-sel" name="doctorRegisterSet.weeks">
												<c:forEach var="item" items="${weekList}">
													<option value="${item.key}" <c:if test="${doctorRegisterSet.weeks == item.key}">selected="selected"</c:if>>${item.value}</option>
												</c:forEach>
											</select>
										</div>
									</c:if>
									<c:if test="${edit}">
										<input type="hidden" name="doctorRegisterSet.weeks" value="${doctorRegisterSet.weeks}">
										<c:forEach var="item" items="${weekList}">
											<c:if test="${doctorRegisterSet.weeks == item.key}">${item.value}</c:if>
										</c:forEach>
									</c:if>
				                    <span class="errortip">${errors['doctorRegisterSet.weeks'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>班次：
		                     			<input id="rankData" type="hidden" value="${doctorRegisterSet.rank}"/>
		                     		</label>
		                     		<c:if test="${!edit}">
			                     		<div class="select">
					                        <select class="listbar-sel" name="doctorRegisterSet.rank">
												<c:forEach var="item" items="${rankList}">
													<option value="${item.key}" <c:if test="${doctorRegisterSet.rank == item.key}">selected="selected"</c:if>>${item.value}</option>
												</c:forEach>
											</select>
										</div>
									</c:if>
									<c:if test="${edit}">
										<input type="hidden" name="doctorRegisterSet.rank" value="${doctorRegisterSet.rank}">
										<c:forEach var="item" items="${rankList}">
											<c:if test="${doctorRegisterSet.rank == item.key}">${item.value}</c:if>
										</c:forEach>
									</c:if>
				                    <span class="errortip">${errors['doctorRegisterSet.rank'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
			                   	<div class="listItem">
			                        <label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否循环：
		                     		</label>
									<c:forEach var="item" items="${cycleList}">
										<label class="lbl-ipt-tit radioWrap">
											<input type="radio" name="doctorRegisterSet.cycle" value="${item.key}" onchange="changeChannelType(this.value);" <c:if test="${doctorRegisterSet.cycle == item.key}"> checked="checked" </c:if>/>
											${item.value}
										</label>
									</c:forEach>
			                   	</div>
			                   	<div id="closePanel" style="display: <c:if test="${doctorRegisterSet.cycle == 0 || doctorRegisterSet.cycle == 1}"> none </c:if>">
				                   	<div class="listItem txtarea">
						                <label class="lbl-ipt-tit">截止时间：</label>
						                <input name="doctorRegisterSet.closeTime" value='<fmt:formatDate value="${doctorRegisterSet.closeTime}" pattern="yyyy-MM-dd"/>' 
						                	class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						                <span class="errortip">${errors['doctorRegisterSet.closeTime'][0]}</span>
					                </div>
					            </div>
			                </li>
							<li class="inputList-li">
			                   	<div class="listItem txtarea">
				                	<label class="lbl-ipt-tit">
				                		<span class="star">*</span>开始时间：
				                	</label>
				                 	<input name="doctorRegisterSet.startTime" value="${doctorRegisterSet.startTime}"
				                 		class="ipt" type="text" onClick="WdatePicker({dateFmt:'HH:mm'})" />
				                  	<span class="errortip" >${errors['doctorRegisterSet.startTime'][0]}</span>
				                </div>
				                <div class="listItem txtarea">
				                	<label class="lbl-ipt-tit">
				                		<span class="star">*</span>结束时间：
				                	</label>
				                 	<input name="doctorRegisterSet.endTime" value="${doctorRegisterSet.endTime}"
				                 		class="ipt" type="text" onClick="WdatePicker({dateFmt:'HH:mm'})" />
				                  	<span class="errortip" >${errors['doctorRegisterSet.endTime'][0]}</span>
				                </div>
							</li>
							<li class="inputList-li">
								<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>频率：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="doctorRegisterSet.step">
											<c:forEach var="item" items="${stepList}">
												<option value="${item.key}" <c:if test="${doctorRegisterSet.step == item.key}">selected="selected"</c:if>>${item.value}</option>
											</c:forEach>
										</select>
									</div>
				                    <span class="errortip">${errors['doctorRegisterSet.step'][0]}</span>
			                   	</div>
				                <div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>金额：
		                     		</label>
			                        <input type="text" class="ipt" name="doctorRegisterSet.amount" value="${doctorRegisterSet.amount}" onkeyup="intFormat(this,1);"/>
				                    <span class="errortip">${errors['doctorRegisterSet.amount'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
								<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>门诊类型：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="doctorRegisterSet.regType">
											<c:forEach var="item" items="${regTypeList}">
												<option value="${item.key}" <c:if test="${doctorRegisterSet.regType == item.key}">selected="selected"</c:if>>${item.value}</option>
											</c:forEach>
										</select>
									</div>
				                    <span class="errortip">${errors['doctorRegisterSet.regType'][0]}</span>
			                   	</div>
				                <div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>号源：
		                     		</label>
			                        <input type="text" class="ipt" name="doctorRegisterSet.noSource" value="${doctorRegisterSet.noSource}" onkeyup="intFormat(this,1);"/>
				                    <span class="errortip">${errors['doctorRegisterSet.noSource'][0]}</span>
			                   	</div>
							</li>
							<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建人：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="${doctorRegisterSet.creater.realName}" />
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value='<fmt:formatDate value="${doctorRegisterSet.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
			                   	</div>
			                </li>
						</ul>
					</div>
				</div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="submitForm('editDoctorRegisterSetForm')">&nbsp;&nbsp;
					<input class="formButton" onclick="window.location='${path}/admin/doctorRegisterSet!list.action?doctorId=${doctorId}'" value="返  回" type="button">
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		displayDiv('typeBtn', 'typeDiv', '${errors}');
	</script>
</html>