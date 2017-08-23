<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html lang="zh-CN">
	<head>
		<title>编辑站点角色</title>
		<jsp:include page="../../page/include/headEdit.jsp"></jsp:include>
	</head>
	<body class="input">
		<div class="main-top-hd clearfix">
        	<h3 class="cur">
	        	<c:if test="${!edit}">站点角色添加</c:if>
				<c:if test="${edit}">站点角色修改</c:if>
	        </h3>
	    </div>
		<div class="body">
			<form name="editSiteRoleForm" action="${path}/admin/siteRole!save.action" method="post">
				<input type="hidden" name="token" value="${token}"/>
				<input type="hidden" name="edit" value="${edit}"/>
				<input type="hidden" name="siteRole.id" value="${siteRole.id}"/>
				<input type="hidden" name="siteRole.role.id" value="${siteRole.role.id}"/>
				<input type="hidden" name="siteRole.role.compType" value="${siteRole.role.compType}"/>
				<input type="hidden" name="siteRole.role.createTime" value="${siteRole.role.createTime}"/>
				
				<div class="divInputTable">
		            <h3 class="main-tit-bar" id="typeBtn">基本信息</h3>
		            <div id="typeDiv">
		            	<ul class="inputList">
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>站点名称：
		                     		</label>
		                     		<div class="select">
				                        <select class="listbar-sel" name="siteRole.site.id">
				                        	<option value="">--请选择--</option>
											<c:forEach var="r" items="${siteList}">
												<option value="${r.id}" <c:if test="${r.id == siteRole.site.id}">selected="selected"</c:if>>${r.siteName}</option>
											</c:forEach>
										</select>
									</div>
									<span class="errortip">
										<s:fielderror>
										    <s:param>siteRole.site.id</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>是否启用：
		                     		</label>
			                        <label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="siteRole.role.disable" value="true"
											<c:if test="${siteRole.role.disable}"> checked="checked" </c:if> />
											是 
									</label>
									<label class="lbl-ipt-tit radioWrap">
										<input type="radio" name="siteRole.role.disable" value="false"<c:if test="${!siteRole.role.disable}"> checked="checked" </c:if>/>
											否
									</label>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>角色名称：
		                     		</label>
			                        <input type="text" class="formText" name="siteRole.role.roleName" value="${siteRole.role.roleName}" />
									<span class="errortip">
										<s:fielderror >
										    <s:param>siteRole.role.roleName</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                	<div class="listItem">
		                       		<label class="lbl-ipt-tit">
		                     			<span class="star">*</span>排序：
		                     		</label>
									<input type="text" class="formText" name="siteRole.role.roleSort" value="${siteRole.role.roleSort}" onkeyup="intFormat(this,1,99);"/>
									<span class="errortip">
										<s:fielderror >
										    <s:param>siteRole.role.roleSort</s:param>
									    </s:fielderror>
									</span>
			                   	</div>
			                </li>
			                <li class="inputList-li">
			                    <div class="listItem txtarea">
			                    	<label class="lbl-ipt-tit">角色描述：</label>
			                        <textarea name="siteRole.role.roleDesc" class="formTextarea valid">${siteRole.role.roleDesc}</textarea>
			                    </div>
			                </li>
			            </ul>
			        </div>
			    </div>
				<div class="buttonArea">
					<input class="formButton" value="确  定" type="button" onclick="document.editSiteRoleForm.submit();">&nbsp;&nbsp;
            		<input class="formButton" onclick="window.location='${path}/admin/siteRole!list.action'" value="返  回" type="button">
				</div>
			</form>
		</div>
		<script type="text/javascript">  
			displayDiv('typeBtn','typeDiv','${errors}');
		</script>
	</body>
</html>		