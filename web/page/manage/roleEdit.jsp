<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑角色</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">角色添加</c:if>
				<c:if test="${edit}">角色修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editRoleForm" action="${path}/admin/role!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="role.id" value="${role.id}">
				<input type="hidden" name="role.version" value="${role.version}">
				<input type="hidden" name="role.createTime" value="${role.createTime}"/>
				<input type="hidden" name="edit" value="${edit}">
				<c:if test="${!mainStation}">
					<input type="hidden" name="role.compType" value="${role.compType}">
					<input type="hidden" name="role.default" value="${role.default}"/>
				</c:if>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
			            	<c:if test="${mainStation}">
			            		<li class="inputList-li">
				                	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			<span class="star">*</span>是否默认：
			                     		</label>
				                        <label class="lbl-ipt-tit radioWrap">
											<input type="radio" name="role.default" value="true" <c:if test="${role.default}"> checked="checked" </c:if> />
											是 
										</label>
										<label class="lbl-ipt-tit radioWrap">
											<input type="radio" name="role.default" value="false" <c:if test="${!role.default}"> checked="checked" </c:if>/>
											否
										</label>
				                   	</div>
				                   	<div class="listItem">
			                       		<label class="lbl-ipt-tit">
			                     			<span class="star">*</span>组织类型：
			                     		</label>
			                     		<div class="select">
					                        <select class="listbar-sel" name="role.compType">
												<c:forEach var="item" items="${compTypeList}">
													<option value="${item.key}" <c:if test="${role.compType == item.key}">selected="selected"</c:if>>${item.value}</option>
												</c:forEach>
											</select>
										</div>
					                    <span class="errortip">${errors['role.compType'][0]}</span>
				                   	</div>
				                </li>
				            </c:if>
		            		<li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="role.disable" value="true"
											<c:if test="${role.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="role.disable" value="false"<c:if test="${!role.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>角色名称：
		                     		</label>
			                        <input type="text" class="ipt" name="role.roleName" value="${role.roleName}" />
				                    <span class="errortip">${errors['role.roleName'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="ipt" name="role.roleSort" value="${role.roleSort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">${errors['role.roleSort'][0]}</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">角色描述：</label>
			                        <textarea name="role.roleDesc" class="formTextarea valid">${role.roleDesc}</textarea>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editRoleForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/role!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>