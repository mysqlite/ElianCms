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
		<title>编辑科室</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<jsp:include page="../../page/include/image_upload.jsp"></jsp:include>
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">科室添加</c:if>
				<c:if test="${edit}">科室修改</c:if>
	        </h3>
	    </div>
		<div class="body">
		<c:if test="${!empty channelId}">
			<form name="editDepartmentForm" action="${path}/admin/department_c!save.action" method="post">
		</c:if>
		<c:if test="${empty channelId}">
		     <c:if test="${ztree}">
		      <form name="editDepartmentForm" action="${path}/admin/departmentTree!save.action" method="post">
			      <input type="hidden" name="hospId" value="${hospId}"/>
	              <input type="hidden" name="ztree" value="${ztree}"/>
		     </c:if>
		      <c:if test="${!ztree}">
			  <form name="editDepartmentForm" action="${path}/admin/department!save.action" method="post">
			 </c:if>
		</c:if>
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="leaf" value="${leaf}"/>
				<input type="hidden" name="channelId" value="${channelId}"/>
				<input type="hidden" name="action" value="${action}"/>
				<input type="hidden" name="department.id" value="${department.id}">
				<input type="hidden" name="department.version" value="${department.version}">
				<input type="hidden" name="department.createTime" value="${department.createTime}"/>
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
										<input type="radio" name="department.disable" value="true"
											<c:if test="${department.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="department.disable" value="false"<c:if test="${!department.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室性质：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="department.natureId">
											<c:forEach var="item" items="${natureList}">
												<option value="${item.id}" <c:if test="${department.natureId == item.id}">selected="selected"</c:if>>${item.typeName}</option>
											</c:forEach>
										</select>
									</div>
				                    <span class="errortip">${errors['department.natureId'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室类型：
		                     		</label>
		                     		<div>
									    <input id="keyId" class="formText" type="text" readonly="true" value="${deptTypeName}" 
									    	onfocus="showMenu('${path}/admin/department!tree.action'); return false;" onkeydown="checkBackSpace()"/>
									    <input id="valueId" type="hidden" name="department.typeId" value="${department.typeId}"/>
									    <div id="treeDiv"><ul id="treeContent" class="ztree" style="width:200px;"/></div>
									</div>
				                    <span class="errortip">${errors['department.typeId'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>科室全称：
		                     		</label>
			                        <input type="text" class="ipt" name="department.deptName" value="${department.deptName}" />
				                    <span class="errortip">${errors['department.deptName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			传真：
		                     		</label>
			                        <input type="text" class="ipt" name="department.fax" value="${department.fax}" />
				                    <span class="errortip">${errors['department.fax'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电子邮箱：
		                     		</label>
			                        <input type="text" class="ipt" name="department.email" value="${department.email}" />
				                    <span class="errortip">${errors['department.email'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			地址：
		                     		</label>
			                        <input type="text" class="ipt" name="department.address" value="${department.address}" />
				                    <span class="errortip">${errors['department.address'][0]}</span>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			电话：
		                     		</label>
			                        <input type="text" class="ipt" name="department.phone" value="${department.phone}" />
				                    <span class="errortip">${errors['department.phone'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>创建时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" name="department.createTime" value="<fmt:formatDate value='${department.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="department.deptSort" value="${department.deptSort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">${errors['department.deptSort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通挂号：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="department.reg" value="true"
											<c:if test="${department.reg}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="department.reg" value="false"<c:if test="${!department.reg}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			开通挂号时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" name="department.regTime" value="<fmt:formatDate value='${department.regTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否开通随访：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="department.followUp" value="true"
											<c:if test="${department.followUp}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="department.followUp" value="false"<c:if test="${!department.followUp}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                   	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			随访开通时间：
		                     		</label>
			                        <input type="text" class="ipt" disabled="true" value="<fmt:formatDate value='${department.followUpTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem listItem-full-width">
		                       		<label class="lbl-ipt-tit">
		                     			科室图片：
		                     		</label>
		                     		<div id="idCardTd" >
		                     		</div>
			                        <script type="text/javascript">
				                    	loadingImageButton("idCardTd",'${department.deptImg}',"department.deptImg",false,1)
				                    </script>
				                    <span class="errortip">${errors['department.deptImg'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			简要介绍：
		                     		</label>
		                     		<textarea name="department.shortDesc" class="formTextarea valid">${department.shortDesc}</textarea>
				                    <span class="errortip">${errors['department.shortDesc'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem txtarea">
		                       		<label class="lbl-ipt-tit">
		                     			详细介绍：
		                     		</label>		                     		
		                     		<textarea id="description" name="department.description"  class="formTextediter" style="width:750;height:200">${department.description}</textarea>
				                    <script>
				                      var editor1;KindEditor.ready(function(K) {editor1 = K.create('textarea[id="description"]');});    
				                    </script>
				                    <span class="errortip">${errors['department.description'][0]}</span>
				                    <div style="display:none">
							          	<textarea name="editorPrevImg">${department.description}</textarea>
							        </div>
			                   	</div>
			                </li>
			            </ul>
			        </div>
			    </div>				
				<div class="buttonArea">
	           		<c:if test="${!empty channelId}">
                       <jsp:include page="contentButton.jsp"></jsp:include>
                    </c:if>
                    <c:if test="${empty channelId}">
                       <input class="formButton" value="确  定" type="button" onclick="submits(this.form,editor1);">&nbsp;&nbsp;
                       <c:if test="${ztree}">
                          <input class="formButton" onclick="window.location='${path}/admin/departmentTree!list.action?hospId=${hospId}&ztree=${ztree}'" value="返  回" type="button">
                       </c:if>
                       <c:if test="${!ztree}">
                          <input class="formButton" onclick="window.location='${path}/admin/department!list.action'" value="返  回" type="button">
                       </c:if>
	           		   
				    </c:if>
				</div>
			</form>
		</div>
	</body>
</html>