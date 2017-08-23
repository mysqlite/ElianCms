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
		<title>编辑医生</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">医生添加</c:if>
				<c:if test="${edit}">医生修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<c:if test="${!empty channelId}">
				<form name="editDoctorForm" action="${path}/admin/doctor_c!save.action" method="post">
			</c:if>
			<c:if test="${empty channelId}">
				<c:if test="${ztree}">
				    <form name="editDoctorForm" action="${path}/admin/doctorTree!save.action" method="post">
				    <input type="hidden" name="ztree" value="${ztree}"/>
                    <input type="hidden" name="hospId" value="${hospId}"/>
                    <input type="hidden" name="departId" value="${departId}"/>
				</c:if>
				<c:if test="${!ztree}">
	               <form name="editDoctorForm" action="${path}/admin/doctor!save.action" method="post">
	            </c:if>
			   </c:if>
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="doctor.id" value="${doctor.id}">
				<input type="hidden" name="doctor.version" value="${doctor.version}">
				<input type="hidden" name="doctor.createTime" value="${doctor.createTime}"/>
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.disable" value="true"
											<c:if test="${doctor.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.disable" value="false"<c:if test="${!doctor.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>姓名：
		                     		</label>
			                        <input type="text" class="ipt" name="doctor.doctName" value="${doctor.doctName}" />
				                    <span class="errortip">${errors['doctor.doctName'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="doctor.dept.id">
				                        	<option value="">--请选择--</option>
											<c:forEach var="item" items="${deptList}">
												<option value="${item.id}" <c:if test="${doctor.dept.id == item.id}">selected="selected"</c:if>>${item.deptName}</option>
											</c:forEach>
										</select>
									</div>
				                    <span class="errortip">${errors['doctor.dept.id'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>性别：
		                     		</label>
									<label class="lbl-ipt-tit radioWrap">
				                    	<c:forEach var="c" items="${sexList}">
				                    		<label class="lbl-ipt-tit radioWrap">
				                        		<input type="radio" name="doctor.gender" value="${c.key}" <c:if test="${c.key == doctor.gender}"> checked="checked" </c:if>/>${c.value}
				                        	</label>
				                        </c:forEach>
				                    </label>
				                    <span class="errortip">${errors['doctor.gender'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			生日：
		                     		</label>
		                     		<input name="doctor.birthday" value="<fmt:formatDate value='${doctor.birthday}' pattern='yyyy-MM-dd'/>" class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					                <span class="errortip" name="errortip">${errors['doctor.birthday'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			职称：
		                     		</label>
			                        <input type="text" class="ipt" name="doctor.jobTitle" value="${doctor.jobTitle}" />
				                    <span class="errortip">${errors['doctor.jobTitle'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			毕业院校：
		                     		</label>
			                        <input type="text" class="ipt" name="doctor.graduateSchool" value="${doctor.graduateSchool}" />
				                    <span class="errortip">${errors['doctor.graduateSchool'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem txtarea">
			                        <label class="lbl-ipt-tit">职务：</label>
			                        <textarea name="doctor.dutyName" class="formTextarea valid">${doctor.dutyName}</textarea>
			                        	<span class="errortip">${errors['doctor.dutyName'][0]}</span>
		                    	</div>
		                    	<div class="listItem txtarea">
			                        <label class="lbl-ipt-tit">专长：</label>
			                        <textarea name="doctor.speciality" class="formTextarea valid">${doctor.speciality}</textarea>
			                        	<span class="errortip">${errors['doctor.speciality'][0]}</span>
		                    	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			从业时间：
		                     		</label>
		                     		<input name="doctor.jobBeginTime" name="doctor.jobBeginTime" value="<fmt:formatDate value='${doctor.jobBeginTime}' pattern='yyyy-MM-dd'/>" class="ipt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					                <span class="errortip">${errors['doctor.jobBeginTime'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电子邮箱：
		                     		</label>
			                        <input type="text" class="ipt" name="doctor.email" value="${doctor.email}" />
				                    <span class="errortip">${errors['doctor.email'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			手机：
		                     		</label>
			                        <input type="text" class="ipt" name="doctor.moblie" value="${doctor.moblie}" />
				                    <span class="errortip">${errors['doctor.moblie'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
				                     <label class="lbl-ipt-tit"><span class="star">*</span>学历：</label>
				                     <div class="select">
				                          <select name="doctor.education">${doctor.education}
				                          <c:forEach var="item" items="${educationList}" varStatus="e">
				                             <option value="${item.key}" <c:if test="${item.key eq doctor.education}"> selected="selected" </c:if> >${item.value}</option>
				                          </c:forEach>
				                          </select>
				                     </div>
				                     <span class="errortip">${errors['doctor.education'][0]}</span>
				                  </div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" name="doctor.createTime" value="<fmt:formatDate value='${doctor.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="doctor.doctSort" value="${doctor.doctSort}" onkeyup="intFormat(this,1);"/>
									<span class="errortip">${errors['doctor.doctSort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通挂号：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.reg" value="true"
											<c:if test="${doctor.reg}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.reg" value="false"<c:if test="${!doctor.reg}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			开通挂号时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" name="doctor.regTime" value="<fmt:formatDate value='${doctor.regTime}' pattern='yyyy-MM-dd'/>" />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通随访：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.followUp" value="true"
											<c:if test="${doctor.followUp}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="doctor.followUp" value="false"<c:if test="${!doctor.followUp}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			随访开通时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" name="doctor.followUpTime" value="<fmt:formatDate value='${doctor.followUpTime}' pattern='yyyy-MM-dd'/>" />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			医生图片：
		                     		</label>
		                     		<div id="idCardTd" >
		                     		</div>
			                        <script type="text/javascript">
				                    	loadingImageButton("idCardTd",'${doctor.doctImg}',"doctor.doctImg",false,1);
				                    </script>
				                    <span class="errortip">${errors['doctor.doctImg'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			详细介绍：
		                     		</label>
		                     		<textarea id="intro" name="doctor.introduction" class="formTextediter">${doctor.introduction}</textarea>
				                    <script type="text/javascript">
                  		    			var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="intro"]');});    
                    				</script>
                    				<div style="display:none">
			          					 <textarea name="editorPrevImg">${doctor.introduction}</textarea>
			        				 </div>
				                    <span class="errortip">${errors['doctor.introduction'][0]}</span>
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<script type="text/javascript">
				function submitDraft(form, editor1, status,publish){
					document.getElementById("contentStatus").value = status;
					G("publish").value=publish;
					submits(form,editor1);
				}
				</script>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1);">&nbsp;&nbsp;
					<c:if test="${!empty controlId}">
					<input type="hidden" id="contentStatus" name="status" value="${status}"/>
					<input type="hidden" id="publish" name="publish" value="${publish}"/>
					<input type="hidden" id="controlId" name="controlId" value="${controlId}"/>
					<input class="formButton" value="保存且发布" type="button" onclick="submitDraft(this.form,editor1, 3,true);">&nbsp;&nbsp;
		           	</c:if>
		           		<c:if test="${empty channelId}">
		           		     <c:if test="${!ztree}">
		           		         <input class="formButton" onclick="window.location='${path}/admin/doctor!list.action'" value="返  回" type="button">
		           		     </c:if>
		           		     <c:if test="${ztree}">
                                 <input class="formButton" onclick="window.location='${path}/admin/doctorTree!list.action?ztree=${ztree}&hospId=${hospId}&departId=${departId}'" value="返  回" type="button">
                             </c:if>
		           		</c:if>
		           		<c:if test="${!empty channelId}">
			               <input class="formButton" onclick="window.location=window.location='${path}/admin/content!list.action?leaf=${leaf}&channelId=${channelId}&action=${action}&status=${status}'" value="返  回" type="button">
			            </c:if>
				</div>
			</form>
		</div>
	</body>
</html>